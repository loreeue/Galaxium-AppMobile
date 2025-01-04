package com.example.practica3.galaxium;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public NaveEspacial nave;
    public ElementoEspacial combustible;
    public ElementoEspacial elementoEspacial;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean[][] campos = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        nave = new NaveEspacial(this);
        colocarAsteroideYBateria();
    }

    private void colocarAsteroideYBateria() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = nave.partes.size();
        for (int i = 0; i < len; i++) {
            Astronautas parte = nave.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        // Posición para el asteroide
        int asteroideX, asteroideY;
        do {
            asteroideX = random.nextInt(MUNDO_ANCHO);
            asteroideY = random.nextInt(MUNDO_ALTO);
        } while (campos[asteroideX][asteroideY]);

        elementoEspacial = new ElementoEspacial(asteroideX, asteroideY, ElementoEspacial.TIPO_2);

        // Posición para la batería
        int bateriaX, bateriaY;
        do {
            bateriaX = random.nextInt(MUNDO_ANCHO);
            bateriaY = random.nextInt(MUNDO_ALTO);
        } while (campos[bateriaX][bateriaY] || (bateriaX == asteroideX && bateriaY == asteroideY));

        combustible = new ElementoEspacial(bateriaX, bateriaY, ElementoEspacial.TIPO_1);
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;

            // Avanzar la nave
            nave.avance();
            Astronautas head = nave.partes.get(0);

            // Comprobar colisión con los bordes
            if (head.x < 0 || head.x >= MUNDO_ANCHO || head.y < 0 || head.y >= MUNDO_ALTO) {
                finalJuego = true; // Finalizar el juego si toca el borde
                return;
            }

            // Comprobar colisión con la propia nave
            if (nave.comprobarColision()) {
                finalJuego = true;
                return;
            }

            // Colisión con el asteroide
            if (head.x == elementoEspacial.x && head.y == elementoEspacial.y && elementoEspacial.tipo == ElementoEspacial.TIPO_2) {
                puntuacion = Math.max(0, puntuacion - 1); // Resta 1 punto
                colocarAsteroideYBateria(); // Reposicionar asteroide y batería
            }

            // Colisión con la batería
            if (head.x == combustible.x && head.y == combustible.y) {
                puntuacion += INCREMENTO_PUNTUACION; // Aumenta puntos
                nave.extenderNave(); // Extiende la nave

                // Reposicionar asteroide y batería
                colocarAsteroideYBateria();

                // Ajustar velocidad si corresponde
                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}