package com.example.gabriel.pruebaprog3ertri;

import android.util.Log;
import android.view.MotionEvent;

import java.awt.font.NumericShaper;
import java.util.Random;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateBy;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.ScaleTo;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.MotionStreak;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

public class clsJuego {
    Scene escenaADevolver;
    CCGLSurfaceView _VistaDelJuego;
    CCSize PantallaDelDispositivo;

    //constructor
    clsJuego(CCGLSurfaceView VistaDelJuego) {
        Log.d("Constructor clsJuego", "Comienza el constructor de la clase");
        _VistaDelJuego = VistaDelJuego;

    }

    public void ComenzarJuego() {
        Log.d("Comenzar", "Comienza el juego");
        Director.sharedDirector().attachInView(_VistaDelJuego);
        PantallaDelDispositivo = Director.sharedDirector().displaySize();

        Log.d("Comenzar", "Le digo al director que comience la escena");
        Director.sharedDirector().runWithScene(EscenaDelJuego());
    }

    private Scene EscenaDelJuego() {
        Log.d("EscenaDelJuego", "Comienza el armado de la escena del juego");

        Log.d("Escena del juego", "Declaro e instancio la escena en si");
        escenaADevolver = Scene.node();

        Log.d("EscenaDelJuego", "Declaro e instancio la capa que va a contener la imagen del fondo");
        CapaImagenes miCapaImagenes = new CapaImagenes();


        Log.d("EscenaADevolver", "Agrego a la escena la capa del fondo y la del frente");
        escenaADevolver.addChild(miCapaImagenes, 10);

        Log.d("EscenaADevolver", "Devuelvo al escena ya armada");
        return escenaADevolver;
    }

    class CapaImagenes extends Layer {

        Sprite ImagenArriba;
        Sprite ImagenAbajo;
        int ImagenMoviendose;

        public CapaImagenes() {
            Log.d("CapaTablero", "Comienza el constructor de CapaImagenes");
            Log.d("CapaTablero", "Pongo las Imagenes");
            this.setIsTouchEnabled(true);
            PonerImagenes();
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event){
            Log.d("ccTouchesBegan" , "cuando el usuario toca la pantalla");

            Log.d("ccTouchesBegan" , "genero un numero random que sea 0 o 1");
            Random rand = new Random();
            int numeroAzar= rand.nextInt(2);

            if (numeroAzar == 0){
                Log.d("ccTouchesBegan" , "se desplaza la imagen de arriba");
                ImagenArriba.runAction(MoveTo.action(2f, PantallaDelDispositivo.getWidth(),PantallaDelDispositivo.getHeight()));
                ImagenMoviendose = 0;
            }
            else{
                Log.d("ccTouchesBegan" , "se desplaza la imagen de abajo");
                ImagenMoviendose = 1;
                ImagenAbajo.runAction(MoveTo.action(2f, PantallaDelDispositivo.getWidth(),PantallaDelDispositivo.getHeight()));
            }
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            Log.d("ccTouchesMoved", "cuando el usuario mueve el dedo");
            if (ImagenMoviendose == 0){
                if (ImagenAbajo.getRotation() < 360) {
                    ImagenAbajo.runAction(RotateBy.action(0.01f, 15f));
                }
            }
            else{
                if (ImagenArriba.getRotation() < 360) {
                    ImagenArriba.runAction(RotateBy.action(0.01f, 15f));
                }
            }
            Log.d("ccTouchesMoved" , "rotacion de las imagenes:" +ImagenArriba.getRotation()+" - "+ImagenAbajo.getRotation());
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            Log.d("ccTouchesEnded", "cuando el usuario saca el dedo de la pantalla");

            IntervalAction Secuencia;
            ScaleBy scale = ScaleBy.action(1f,1.3f);
            ScaleTo scale2 = ScaleTo.action(1f,1f);
            Secuencia = Sequence.actions(scale,scale2,scale,scale2,scale,scale2);
            ImagenArriba.runAction(Secuencia);
            ImagenAbajo.runAction(Secuencia);

            return true;
        }

        private void PonerImagenes() {
            Log.d("PonerImagenes", "Comienzo a poner las imagenes");
            ImagenArriba = Sprite.sprite("torrenegra.png");
            ImagenAbajo = Sprite.sprite("torreblanca.png");

            ImagenArriba.setPosition(PantallaDelDispositivo.getWidth()/2, PantallaDelDispositivo.getHeight()*0.75f);
            ImagenAbajo.setPosition(PantallaDelDispositivo.getWidth()/2, PantallaDelDispositivo.getHeight()*0.25f);
            Log.d("PonerImagenes" , "poscicionX-" + ImagenArriba.getPositionX()+ " possicionX-" + ImagenAbajo.getPositionX());
            Log.d("PonerImagenes" , "poscicionY-" + ImagenArriba.getPositionY()+ " possicionY-" + ImagenAbajo.getPositionY());

            super.addChild(ImagenArriba);
            super.addChild(ImagenAbajo);
        }


    }
}
