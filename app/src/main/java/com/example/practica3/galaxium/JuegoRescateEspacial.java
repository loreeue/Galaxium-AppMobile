package com.example.practica3.galaxium;

import com.example.practica3.Pantalla;
import com.example.practica3.androidimpl.AndroidJuego;

public class JuegoRescateEspacial extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}