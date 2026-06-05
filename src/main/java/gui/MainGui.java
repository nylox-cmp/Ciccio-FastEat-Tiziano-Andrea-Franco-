package gui;

import controller.*;
import gui.Autenticazione.*;

import javax.swing.*;
import java.awt.*;

public class MainGui {
    private static JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel pagina;

    private UtenteController utente_controller;
    private ClienteController cliente_controller;
    private RiderController rider_controller;
    private DipedenteController dipedente_controller;

    private OrdineController ordine_controller;
    private RistoranteContorller ristorante_controller;

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public JPanel get_pagina(){ return pagina; }
    public void set_pagina(JPanel pagina){
        if(this.pagina != null) mainFrame.remove(this.pagina);
        this.pagina = pagina;
        mainFrame.add(pagina, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public UtenteController get_utente_controller(){ return utente_controller;}
    public void set_utente_controller(UtenteController utente_controller){
        if(this.utente_controller == null) this.utente_controller = utente_controller;
    }

    public ClienteController get_cliente_controller(){ return cliente_controller;}
    public void set_cliente_contrller(ClienteController cliente_controller){
        if(this.cliente_controller == null) this.cliente_controller = cliente_controller;
    }

    public RiderController get_rider_controller(){ return  rider_controller;}
    public void set_rider_controller(RiderController riderController){
        if(this.rider_controller == null) this.rider_controller = rider_controller;
    }

    public DipedenteController get_dipedente_controller(){ return dipedente_controller;}
    public void set_dipedente_controller(DipedenteController dipedente_controller){
        if(this.dipedente_controller == null) this.dipedente_controller = dipedente_controller;
    }

    public OrdineController get_ordine_controller(){ return ordine_controller;}
    public void set_ordine_controller(OrdineController ordine_controller){
        if(this.ordine_controller == null) this.ordine_controller = ordine_controller;
    }

    public RistoranteContorller get_ristorante_controller(){ return ristorante_controller; }
    public void set_ristorante_controller(RistoranteContorller ristorante_controller){
        if(this.ristorante_controller == null) this.ristorante_controller = ristorante_controller;
    }

    //________________________________________________________________________________________________________________________________________________
    // Main

    public static void main(String[] args){
        mainFrame = new JFrame("main");
        MainGui mainGui = new MainGui();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        SignInGui signInGui = new SignInGui(mainGui);
        mainGui.set_pagina(signInGui);
    }
}
