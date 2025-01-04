package com.example.practica3.galaxium;

import java.util.ArrayList;
import java.util.List;

public class NaveEspacial {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;
    private final Mundo mundo;
    public List<Astronautas> partes = new ArrayList<>();
    public int direccion;

    public NaveEspacial(Mundo mundo) {
        direccion = ARRIBA;
        partes.add(new Astronautas(5, 6));
        partes.add(new Astronautas(5, 7));
        partes.add(new Astronautas(5, 8));
        this.mundo = mundo;
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void extenderNave() {
        Astronautas end = partes.get(partes.size()-1);
        partes.add(new Astronautas(end.x, end.y));
    }

    public void avance() {
        Astronautas head = partes.get(0);

        int nextX = head.x;
        int nextY = head.y;

        // Calcula la siguiente posición según la dirección
        if (direccion == ARRIBA) {
            nextY -= 1;
        }
        if (direccion == ABAJO) {
            nextY += 1;
        }
        if (direccion == IZQUIERDA) {
            nextX -= 1;
        }
        if (direccion == DERECHA) {
            nextX += 1;
        }

        // Nueva funcionalidad -> Verifica si la nave choca con los bordes
        if (nextX < 0 || nextX >= Mundo.MUNDO_ANCHO || nextY < 0 || nextY >= Mundo.MUNDO_ALTO) {
            mundo.finalJuego = true;
            return;
        }

        // Actualiza la posición de la nave
        for (int i = partes.size() - 1; i > 0; i--) {
            Astronautas before = partes.get(i - 1);
            Astronautas current = partes.get(i);
            current.x = before.x;
            current.y = before.y;
        }

        head.x = nextX;
        head.y = nextY;
    }

    public boolean comprobarColision() {
        int len = partes.size();
        Astronautas astronautas = partes.get(0);
        for(int i = 1; i < len; i++) {
            Astronautas parte = partes.get(i);
            if(parte.x == astronautas.x && parte.y == astronautas.y)
                return true;
        }
        return false;
    }
}