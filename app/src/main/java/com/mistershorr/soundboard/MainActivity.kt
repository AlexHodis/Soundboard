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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var buttonLowG : Button
    lateinit var buttonA : Button
    lateinit var buttonBb : Button
    lateinit var buttonB : Button
    lateinit var buttonC : Button
    lateinit var buttonCsharp : Button
    lateinit var buttonD : Button
    lateinit var buttonDsharp : Button
    lateinit var buttonE : Button
    lateinit var buttonF : Button
    lateinit var buttonFsharp : Button
    lateinit var buttonG : Button
    lateinit var buttonGsharp : Button
    lateinit var soundPool : SoundPool
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
        setContentView(R.layout.activity_main)

        val inputStream = resources.openRawResource(R.raw.song)
        val jsonText = inputStream.bufferedReader().use {
            it.readText()
        }
        Log.d(TAG,"onCreate: $jsonText")

        val gson = Gson()
        val type = object : TypeToken<List<Note>>() { }.type
        val questions = gson.fromJson<List<Note>>(jsonText, type)
        Log.d(TAG, "onCreate: \n${questions}")


        wireWidgets()
        initializeSoundPool()
        setListeners()
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        buttonLowG.setOnClickListener(soundBoardListener)
        buttonA.setOnClickListener(soundBoardListener)
        buttonBb.setOnClickListener(soundBoardListener)
        buttonB.setOnClickListener(soundBoardListener)
        buttonC.setOnClickListener(soundBoardListener)
        buttonCsharp.setOnClickListener(soundBoardListener)
        buttonD.setOnClickListener(soundBoardListener)
        buttonDsharp.setOnClickListener(soundBoardListener)
        buttonE.setOnClickListener(soundBoardListener)
        buttonF.setOnClickListener(soundBoardListener)
        buttonFsharp.setOnClickListener(soundBoardListener)
        buttonG.setOnClickListener(soundBoardListener)
        buttonGsharp.setOnClickListener(soundBoardListener)
    }


    private fun wireWidgets() {
        buttonLowG = findViewById(R.id.button_main_lowg)
        buttonA = findViewById(R.id.button_main_a)
        buttonBb = findViewById(R.id.button_main_bb)
        buttonB = findViewById(R.id.button_main_b)
        buttonC = findViewById(R.id.button_main_c)
        buttonCsharp = findViewById(R.id.button_main_csharp)
        buttonD = findViewById(R.id.button_main_d)
        buttonDsharp = findViewById(R.id.button_main_dsharp)
        buttonE = findViewById(R.id.button_main_e)
        buttonF = findViewById(R.id.button_main_f)
        buttonFsharp = findViewById(R.id.button_main_fsharp)
        buttonG = findViewById(R.id.button_main_g)
        buttonGsharp = findViewById(R.id.button_main_gsharp)
    }

    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
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
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
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