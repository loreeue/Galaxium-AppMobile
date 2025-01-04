package com.example.practica3.galaxium;

import com.example.practica3.Graficos;
import com.example.practica3.Input.TouchEvent;
import com.example.practica3.Juego;
import com.example.practica3.Pantalla;
import java.util.List;

public class PantallaMaximasPuntuaciones extends Pantalla {
    String[] lineas = new String[5];

    public PantallaMaximasPuntuaciones(Juego juego) {
        super(juego);

        for (int i = 0; i < 5; i++) {
            lineas[i] = (i + 1) + ". " + Configuraciones.maxPuntuaciones[i];
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 65 && event.y > 380) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.menuprincipal, 44, 20, 20, 190, 260, 42);

        int y = 100;
        for (int i = 0; i < 5; i++) {
            dibujarTexto(g, lineas[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assets.botones, 0, 420, 64, 70, 64, 64);
    }

    public void dibujarTexto(Graficos g, String linea, int x, int y) {
        int len = linea.length();
        for (int i = 0; i < len; i++) {
            char character = linea.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX + 5, 0, srcWidth + 4, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}