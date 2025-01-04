package com.example.practica3;

public interface Juego {
    Input getInput();

    FileIO getFileIO();

    Graficos getGraphics();

    Audio getAudio();

    void setScreen(Pantalla pantalla);

    Pantalla getCurrentScreen();

    Pantalla getStartScreen();
}