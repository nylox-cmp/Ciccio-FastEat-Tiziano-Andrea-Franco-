package controller;

import model.*;

import java.util.ArrayList;

public class DatiUtente{
    private Cliente cliente = null;
    private Rider rider = null;
    private Dipedente dipedente = null;

    public void clear(){
        cliente = null;
        rider = null;
        dipedente = null;
    }

    public void set_cliente(Utente utente, int punti_fedelta){
        this.cliente = new Cliente(utente,punti_fedelta);
    }

    public void set_rider(Utente utente,String mezzo_trasporto){
        this.rider = new Rider(utente,mezzo_trasporto);
    }

    public void set_dipendente(Utente utente, Ruolo ruolo,Ristorante ristorante){
        this.dipedente = new Dipedente(utente,ruolo,ristorante);
    }

    public Cliente get_utente(){ return cliente; }
    public Rider get_rider(){ return rider;}
    public Dipedente get_dipedente(){ return  dipedente; }
}