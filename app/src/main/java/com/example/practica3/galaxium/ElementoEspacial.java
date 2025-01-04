package com.example.practica3.galaxium;

public class ElementoEspacial {
    public static final int TIPO_1 = 0; // Combustible
    public static final int TIPO_2 = 1; // Asteroide
    public int x, y;
    public int tipo;

    public ElementoEspacial(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }
}