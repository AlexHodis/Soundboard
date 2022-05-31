package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val TAG = "MainActivity"
    lateinit var soundPool : SoundPool
    // key is the String Note, value is the Int Soundpool Id
    val noteMap = HashMap<String, Int>()
    //A, B♭, B, C, C♯, D, D♯, E, F, F♯, G, G♯
    var lowGNote = 0
    var aNote = 0
    var bbNote = 0
    var bNote = 0
    var cNote = 0
    var cSharpNote = 0
    var dNote = 0
    var dSharpNote = 0
    var eNote = 0
    var fNote = 0
    var fSharpNote = 0
    var gNote = 0
    var gSharpNote = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //assigns the inflated views to the binding variable
        binding = ActivityMainBinding.inflate(layoutInflater)
        // set the content view to the top-level view in the binding
        setContentView(binding.root)

        val inputStream = resources.openRawResource(R.raw.song)
        val jsonText = inputStream.bufferedReader().use {
            it.readText()
        }
        Log.d(TAG,"onCreate: $jsonText")

        val gson = Gson()
        val type = object : TypeToken<List<Note>>() { }.type
        val notes = gson.fromJson<List<Note>>(jsonText, type)
        Log.d(TAG, "onCreate: \n${notes}")


        initializeSoundPool()
        setListeners()

        binding.buttonMainPlaysong.setOnClickListener {
            GlobalScope.launch {
                playSong(notes)
            }
        }
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        binding.buttonMainLowg.setOnClickListener(soundBoardListener)
        binding.buttonMainA.setOnClickListener(soundBoardListener)
        binding.buttonMainBb.setOnClickListener(soundBoardListener)
        binding.buttonMainB.setOnClickListener(soundBoardListener)
        binding.buttonMainC.setOnClickListener(soundBoardListener)
        binding.buttonMainCsharp.setOnClickListener(soundBoardListener)
        binding.buttonMainD.setOnClickListener(soundBoardListener)
        binding.buttonMainDsharp.setOnClickListener(soundBoardListener)
        binding.buttonMainE.setOnClickListener(soundBoardListener)
        binding.buttonMainF.setOnClickListener(soundBoardListener)
        binding.buttonMainFsharp.setOnClickListener(soundBoardListener)
        binding.buttonMainG.setOnClickListener(soundBoardListener)
        binding.buttonMainGsharp.setOnClickListener(soundBoardListener)

    }

    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//        })
        lowGNote = soundPool.load(this, R.raw.scalelowg, 1)
        aNote = soundPool.load(this, R.raw.scalea, 1)
        bbNote = soundPool.load(this, R.raw.scalebb, 1)
        bNote = soundPool.load(this, R.raw.scaleb, 1)
        cNote =  soundPool.load(this, R.raw.scalec, 1)
        cSharpNote = soundPool.load(this, R.raw.scalecs, 1)
        dNote = soundPool.load(this, R.raw.scaled, 1)
        dSharpNote = soundPool.load(this, R.raw.scaleds, 1)
        eNote = soundPool.load(this, R.raw.scalee, 1)
        fNote = soundPool.load(this, R.raw.scalef, 1)
        fSharpNote = soundPool.load(this, R.raw.scalefs, 1)
        gNote = soundPool.load(this, R.raw.scaleg, 1)
        gSharpNote = soundPool.load(this, R.raw.scalegs, 1)

       //add notes to the map
        noteMap["LG"] = lowGNote
        noteMap.put("A", aNote)
        //the kotlin approach instead of using the put function...
        noteMap["B"] = bNote
        noteMap["Bb"] = bbNote
        noteMap["C"] = cNote
        noteMap["CS"] = cSharpNote
        noteMap["D"] = dNote
        noteMap["DS"] = dSharpNote
        noteMap["E"] = eNote
        noteMap["F"] = fNote
        noteMap["FS"] = fSharpNote
        noteMap["G"] = gNote
        noteMap["GS"] = gSharpNote
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    //Given a String of the note, like "D" or "FS" or "LG"
    //(D, F sharp, or low G) , play the corresponding sound from the soundpool
    private fun playnote(note: String) {
            playNote(noteMap[note] ?:0)
        }

    private suspend fun playSong(song: List<Note>){
        //loop through the notes
        //play each note based on the value in the object
        //delay based on the value in the object
        for(note in song){
            playnote(note.note)
            delay(note.duration.toLong())
        }

    }

    //suspend keyword indicates coroutine use inside of the function
    private suspend fun playSimpleSong(){
        //launch a coroutine by starting up a scope and calling the launch function
            playnote("LG")
            delay(500)
            playnote("A")
            delay(500)
            playnote("Bb")
            delay(500)
            playnote("B")
            delay(500)
            playnote("C")
            delay(500)
            playnote("CS")
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.button_main_a -> playNote(aNote)
                R.id.button_main_bb -> playNote(bbNote)
                R.id.button_main_b -> playNote(bNote)
                R.id.button_main_c -> playNote(cNote)
                R.id.button_main_csharp -> playNote(cSharpNote)
                R.id.button_main_d -> playNote(dNote)
                R.id.button_main_dsharp -> playNote(dSharpNote)
                R.id.button_main_e -> playNote(eNote)
                R.id.button_main_f -> playNote(fNote)
                R.id.button_main_fsharp -> playNote(fSharpNote)
                R.id.button_main_g -> playNote(gNote)
                R.id.button_main_gsharp -> playNote(gSharpNote)
                R.id.button_main_lowg -> playNote(lowGNote)
            }
        }
    }
}