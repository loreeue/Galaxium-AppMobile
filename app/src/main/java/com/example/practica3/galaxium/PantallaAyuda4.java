package com.example.practica3.galaxium;

import com.example.practica3.Graficos;
import com.example.practica3.Input.TouchEvent;
import com.example.practica3.Juego;
import com.example.practica3.Pantalla;
import java.util.List;

public class PantallaAyuda4 extends Pantalla {
    public PantallaAyuda4(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 254 && event.y > 375) {
                    juego.setScreen(new PantallaAyuda5(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.alerta.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.ayuda4, -15, 0);
        g.drawPixmap(Assets.botones, 256, 420, 0, 70, 64, 64);
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