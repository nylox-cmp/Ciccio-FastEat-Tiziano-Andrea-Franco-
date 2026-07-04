package controller;

import model.*;

import java.util.ArrayList;

public class DatiUtente{
    private Cliente cliente = null;
    private Rider rider = null;
    private Dipedente dipedente = null;

    //________________________________________________________________________________________________________________________________________________
    // Cliente

    public Cliente get_cliente(){ return cliente; }

    public void set_cliente(Utente utente, int punti_fedelta){
        this.cliente = new Cliente(utente,punti_fedelta);
    }

    //________________________________________________________________________________________________________________________________________________
    // Rider

    public Rider get_rider(){ return rider;}

    public void set_rider(Utente utente,String mezzo_trasporto){
        this.rider = new Rider(utente,mezzo_trasporto);
    }

    //________________________________________________________________________________________________________________________________________________
    // Dipedente

    public Dipedente get_dipedente(){ return  dipedente; }

    public void set_dipendente(Utente utente, Ruolo ruolo,Ristorante ristorante){
        this.dipedente = new Dipedente(utente,ruolo,ristorante);
    }

    public void rimuovi_dipedente(){ dipedente = null;}
}