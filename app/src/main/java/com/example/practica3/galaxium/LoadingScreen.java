package com.example.practica3.galaxium;

import com.example.practica3.Graficos;
import com.example.practica3.Juego;
import com.example.practica3.Pantalla;
import com.example.practica3.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla {
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondo.jpg", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.ayuda4 = g.newPixmap("ayuda4.png", PixmapFormat.ARGB4444);
        Assets.ayuda5 = g.newPixmap("ayuda5.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.navearriba = g.newPixmap("navearriba.png", PixmapFormat.ARGB4444);
        Assets.naveizquierda = g.newPixmap("naveizquierda.png", PixmapFormat.ARGB4444);
        Assets.naveabajo = g.newPixmap("naveabajo.png", PixmapFormat.ARGB4444);
        Assets.navederecha = g.newPixmap("navederecha.png", PixmapFormat.ARGB4444);
        Assets.astronautas = g.newPixmap("astronauta.png", PixmapFormat.ARGB4444);
        Assets.combustible = g.newPixmap("combustible.png", PixmapFormat.ARGB4444);
        Assets.asteroide = g.newPixmap("asteroide.png", PixmapFormat.ARGB4444);
        Assets.alerta = juego.getAudio().nuevoSonido("alerta.mp3");
        Assets.explosion = juego.getAudio().nuevoSonido("explosion.mp3");

        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

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