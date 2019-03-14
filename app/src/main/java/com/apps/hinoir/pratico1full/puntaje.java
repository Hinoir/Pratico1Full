package com.apps.hinoir.pratico1full;

public class puntaje {
    //-------------------------------------------------------------
    //Atributo global
    //-------------------------------------------------------------
    private int puntaje;
    private static puntaje instance;
    //-------------------------------------------------------------
    //Metodos
    //-------------------------------------------------------------
    public puntaje() {
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    //-------------------------------------------------------------
    //Singleton
    //-------------------------------------------------------------
    public static synchronized puntaje getInstance(){
        if(instance==null){
            instance = new puntaje();
        }
        return instance;
    }
}
