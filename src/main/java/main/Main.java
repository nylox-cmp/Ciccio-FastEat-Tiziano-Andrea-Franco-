package main;

import controller.*;
import gui.*;
import gui.Autenticazione.SignInGui;

public class Main {
    private UtenteController utente_controller = null;
    private ClienteController cliente_controller = null;
    private RiderController rider_controller = null;
    private DipendenteController dipendente_controller = null;
    private OrdiniController ordini_controller = null;
    private RistoranteController ristorante_controller = null;

    //________________________________________________________________________________________________________________________________________________
    // Metodi Gestione Controller

    public void distruggi_controller(){
        utente_controller = null;
        cliente_controller = null;
        dipendente_controller = null;
        rider_controller = null;
        ristorante_controller = null;
        ordini_controller = null;
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get Set

    public UtenteController get_utente_controller(){ return utente_controller;}
    public void set_utente_controller(UtenteController utente_controller){this.utente_controller = utente_controller;}

    public ClienteController get_cliente_controller(){ return cliente_controller;}
    public void set_cliente_controller(ClienteController cliente_controller){this.cliente_controller = cliente_controller;}

    public RiderController get_rider_controller(){ return  rider_controller;}
    public void set_rider_controller(RiderController rider_controller){
        this.rider_controller = rider_controller;
    }

    public DipendenteController get_dipendente_controller(){ return dipendente_controller;}
    public void set_dipendente_controller(DipendenteController dipendente_controller){this.dipendente_controller = dipendente_controller;}

    public RistoranteController get_ristorante_controller(){ return ristorante_controller; }
    public void set_ristorante_controller(RistoranteController ristorante_controller){this.ristorante_controller = ristorante_controller;}

    public OrdiniController get_ordini_controller(){ return ordini_controller;}
    public void set_ordine_controller(OrdiniController ordini_controller){this.ordini_controller = ordini_controller;}

    //________________________________________________________________________________________________________________________________________________
    // Main

    public static void main(String args[]){
        Main main = new Main();
        CanvasGui canvas = new CanvasGui(main);
        canvas.set_pagina(new SignInGui(canvas));
    }

}
