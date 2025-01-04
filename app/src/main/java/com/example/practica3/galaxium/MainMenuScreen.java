package com.example.practica3.galaxium;

import com.example.practica3.Graficos;
import com.example.practica3.Input.TouchEvent;
import com.example.practica3.Juego;
import com.example.practica3.Pantalla;
import java.util.List;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                // Botón de sonido
                if (inBounds(event, 0, 400, 65, 80)) { // Ajuste según coordenadas [0, 65] y [380, 440]
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;
                    if (Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                }

                // Botón de "Jugar"
                if (inBounds(event, 30, 220, 250, 60)) { // Ajuste según coordenadas [30, 280] y [242, 290]
                    juego.setScreen(new PantallaJuego(juego));
                    if (Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    return;
                }

                // Botón de "Puntuaciones"
                if (inBounds(event, 30, 310, 250, 50)) { // Ajuste según coordenadas [30, 280] y [290, 340]
                    juego.setScreen(new PantallaMaximasPuntuaciones(juego));
                    if (Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    return;
                }

                // Botón de "Ayuda"
                if (inBounds(event, 50, 380, 300, 100)) { // Ajuste según coordenadas [30, 280] y [350, 400]
                    juego.setScreen(new PantallaAyuda(juego));
                    if (Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        return event.x > x && event.x < x + width &&
                event.y > y && event.y < y + height;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        // Dibujar el fondo
        g.drawPixmap(Assets.fondo, 0, 0);

        // Dibujar el logo en la parte superior (más pequeño)
        g.drawPixmap(Assets.logo, 45, 2);

        // Dibujar el menú principal centrado en la parte inferior
        int menuX = (g.getWidth() - Assets.menuprincipal.getWidth()) / 2;
        int menuY = 140;
        g.drawPixmap(Assets.menuprincipal, menuX, menuY);

        // Dibujar el botón de sonido
        if (Configuraciones.sonidoHabilitado) {
            g.drawPixmap(Assets.botones, -5, 416, 0, 0, 66, 64);
        } else {
            g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);
        }
    }

    @Override
    public void pause() {
        Configuraciones.save(juego.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}