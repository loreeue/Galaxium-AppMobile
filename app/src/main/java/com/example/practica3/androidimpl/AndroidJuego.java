package com.example.practica3.androidimpl;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;
import com.example.practica3.Pantalla;
import com.example.practica3.Audio;
import com.example.practica3.FileIO;
import com.example.practica3.Graficos;
import com.example.practica3.Input;
import com.example.practica3.Juego;
import com.example.practica3.R;
import java.util.Objects;

public abstract class AndroidJuego extends AppCompatActivity implements Juego {
    private AndroidFastRenderView renderView;
    private Graficos graficos;
    private Audio audio;
    private Input input;
    private FileIO fileIO;
    private Pantalla pantalla;
    private WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar el layout principal con Toolbar y RenderView
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Inicializar componentes del juego
        initializeGameComponents();
    }

    // Mover toda la lógica de inicialización del juego a este método
    private void initializeGameComponents() {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;

        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scaleX = (float) frameBufferWidth / metrics.widthPixels;
        float scaleY = (float) frameBufferHeight / metrics.heightPixels;

        // Vincula el renderView desde el layout
        renderView = findViewById(R.id.render_view);
        renderView.juego = this; // Asocia el juego con el renderView
        renderView.framebuffer = frameBuffer;

        graficos = new AndroidGraficos(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        pantalla = getStartScreen();

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "JuegoRescateEspacial:WakeLock");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Adquirir WakeLock con un timeout
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire(10 * 60 * 1000L /* 10 minutos */);
        }
        pantalla.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Liberar WakeLock
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        renderView.pause();
        pantalla.pause();

        if (isFinishing()) {
            pantalla.dispose();
        }
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graficos getGraphics() {
        return graficos;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Pantalla pantalla) {
        if (pantalla == null) {
            throw new IllegalArgumentException("Pantalla no debe ser null");
        }
        this.pantalla.pause();
        this.pantalla.dispose();
        pantalla.resume();
        pantalla.update(0);
        this.pantalla = pantalla;
    }

    @Override
    public Pantalla getCurrentScreen() {
        return pantalla;
    }
}