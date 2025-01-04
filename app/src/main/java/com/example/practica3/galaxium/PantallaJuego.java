package com.example.practica3.galaxium;

import android.graphics.Color;
import com.example.practica3.Graficos;
import com.example.practica3.Input.TouchEvent;
import com.example.practica3.Juego;
import com.example.practica3.Pantalla;
import com.example.practica3.Pixmap;
import java.util.List;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";

    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (!touchEvents.isEmpty())
            estado = EstadoJuego.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 280 && event.y > 400) {
                    mundo.nave.girarIzquierda();
                }
                if(event.x > 254 && event.y > 375) {
                    mundo.nave.girarDerecha();
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            if(Configuraciones.sonidoHabilitado)
                Assets.explosion.play(1);
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.alerta.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 90 && event.x <= 215) {
                    if(event.y >= 150 && event.y <= 190) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.alerta.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if(event.y >= 210 && event.y <= 260) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.alerta.play(1);
                        juego.setScreen(new MainMenuScreen(juego));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 130 && event.x <= 190 && event.y >= 180 && event.y <= 240) {
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

        if (g != null) { // Verifica que g no sea null
            g.drawPixmap(Assets.fondo, 0, 0);
            drawWorld(mundo);
            if (estado == EstadoJuego.Preparado)
                drawReadyUI();
            if (estado == EstadoJuego.Ejecutandose)
                drawRunningUI();
            if (estado == EstadoJuego.Pausado)
                drawPausedUI();
            if (estado == EstadoJuego.FinJuego)
                drawGameOverUI();

            drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length() * 20 / 2, g.getHeight() - 42);
        }
    }

    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        NaveEspacial nave = mundo.nave;
        Astronautas head = nave.partes.get(0);
        ElementoEspacial elementoEspacial = mundo.elementoEspacial;
        ElementoEspacial bateria = mundo.combustible;

        // Dibujar el asteroide (elemento espacial)
        Pixmap stainPixmap = null;
        if (elementoEspacial.tipo == ElementoEspacial.TIPO_2) {
            stainPixmap = Assets.asteroide;
        } else if (elementoEspacial.tipo == ElementoEspacial.TIPO_1) {
            stainPixmap = Assets.combustible;
        }

        if (stainPixmap != null) {
            int x = elementoEspacial.x * 32;
            int y = elementoEspacial.y * 32;
            g.drawPixmap(stainPixmap, x, y);
        } else {
            System.err.println("stainPixmap es null. Verifica el tipo de elementoEspacial: " + elementoEspacial.tipo);
        }

        // Dibujar la batería
        Pixmap bateriaPixmap = Assets.combustible; // Pixmap para la batería
        if (bateriaPixmap != null && bateria != null) {
            int x = bateria.x * 32;
            int y = bateria.y * 32;
            g.drawPixmap(bateriaPixmap, x, y);
        } else {
            System.err.println("bateriaPixmap es null o la batería no está definida.");
        }

        // Dibujar las partes de la nave
        int len = nave.partes.size();
        for (int i = 1; i < len; i++) {
            Astronautas part = nave.partes.get(i);
            int x = part.x * 32;
            int y = part.y * 32;
            g.drawPixmap(Assets.astronautas, x - 15, y);
        }

        // Determinar el Pixmap de la cabeza según la dirección
        Pixmap headPixmap = null;
        if (nave.direccion == NaveEspacial.ARRIBA) {
            headPixmap = Assets.navearriba;
        } else if (nave.direccion == NaveEspacial.IZQUIERDA) {
            headPixmap = Assets.naveizquierda;
        } else if (nave.direccion == NaveEspacial.ABAJO) {
            headPixmap = Assets.naveabajo;
        } else if (nave.direccion == NaveEspacial.DERECHA) {
            headPixmap = Assets.navederecha;
        }

        // Dibujar la cabeza de la nave si está definida
        if (headPixmap != null) {
            int x = head.x * 32 + 16;
            int y = head.y * 32 + 16;
            g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2 - 15, y - headPixmap.getHeight() / 2);
        } else {
            System.err.println("headPixmap es null. Verifica la dirección de la nave: " + nave.direccion);
        }
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 47, 10);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.botones, 0, 0, 64, 130, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 70, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 70, 64, 64);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.menupausa, 47, 60);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.finjuego, 47, 10);
        g.drawPixmap(Assets.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graficos g, String linea, int x, int y) {
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
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        if(mundo.finalJuego) {
            Configuraciones.addScore(mundo.puntuacion);
            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}