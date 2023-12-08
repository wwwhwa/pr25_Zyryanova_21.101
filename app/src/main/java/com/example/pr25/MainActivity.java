package com.example.pr25;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mByakuyaSound, mGenryuusaiSound, mHitsugayaSound, mIchigoSound, mRukiaSound, mUraharaSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mByakuyaSound = loadSound("ByakuyaBankaiSenbonzakuraKageyoshi.ogg");
        mGenryuusaiSound = loadSound("GenryuusaiZankanoTachi.ogg");
        mHitsugayaSound = loadSound("HitsugayaToushiroBankaiDaigurenHyourinmaru.ogg");
        mIchigoSound = loadSound("IchigoTensaZangetsu.ogg");
        mRukiaSound = loadSound("RukiaKuchikiHakkanoTogame.ogg");
        mUraharaSound = loadSound("UraharaKisukeBankai.ogg");

        ImageButton byakuyaImageButton = findViewById(R.id.image_byakuyakuchiki);
        byakuyaImageButton.setOnClickListener(onClickListener);

        ImageButton genryuusaiImageButton = findViewById(R.id.image_genryuusai);
        genryuusaiImageButton.setOnClickListener(onClickListener);

        ImageButton toshiroImageButton = findViewById(R.id.image_toshiro);
        toshiroImageButton.setOnClickListener(onClickListener);

        ImageButton ichigoImageButton = findViewById(R.id.image_ichigokurosaki);
        ichigoImageButton.setOnClickListener(onClickListener);

        ImageButton rukiaImageButton = findViewById(R.id.image_rukiakuchiki);
        rukiaImageButton.setOnClickListener(onClickListener);

        ImageButton uraharaImageButton = findViewById(R.id.image_uraharakisuke);
        uraharaImageButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_byakuyakuchiki) {
                playSound(mByakuyaSound);
            } else if (v.getId() == R.id.image_genryuusai) {
                playSound(mGenryuusaiSound);
            } else if (v.getId() == R.id.image_toshiro) {
                playSound(mHitsugayaSound);
            } else if (v.getId() == R.id.image_ichigokurosaki) {
                playSound(mIchigoSound);
            } else if (v.getId() == R.id.image_rukiakuchiki) {
                playSound(mRukiaSound);
            } else if (v.getId() == R.id.image_uraharakisuke) {
                playSound(mUraharaSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mByakuyaSound = loadSound("ByakuyaBankaiSenbonzakuraKageyoshi.ogg");
        mGenryuusaiSound = loadSound("GenryuusaiZankanoTachi.ogg");
        mHitsugayaSound = loadSound("HitsugayaToushiroBankaiDaigurenHyourinmaru.ogg");
        mIchigoSound = loadSound("IchigoTensaZangetsu.ogg");
        mRukiaSound = loadSound("RukiaKuchikiHakkanoTogame.ogg");
        mUraharaSound = loadSound("UraharaKisukeBankai.ogg");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}