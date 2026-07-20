package gui;

import controller.*;
import gui.Autenticazione.SignInGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;

public class MainGui {
    private static JFrame mainFrame;

    private JPanel topPanel;

    private DashboardGui dashboardGui;
    private DashboardDipedenteGui dashboardDipedenteGui;

    private JPanel pagina;

    private UtenteController utente_controller = null;
    private ClienteController cliente_controller = null;
    private RiderController rider_controller = null;
    private DipendenteController dipendente_controller = null;

    private RistoranteController ristorante_controller = null;

    //________________________________________________________________________________________________________________________________________________
    // Gestione dashboard (si trovano nel package Custom Widget)

    public void aggiorna_finestra(){
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.pack();
    }

    private void crea_topPanel(){
        this.topPanel = new JPanel(new BorderLayout());
        mainFrame.add(topPanel,BorderLayout.NORTH);
    }

    public void mostra_dashboard(){
        if(dashboardGui == null) return;
        dashboardGui.setVisible(true);
    }

    public void nascondi_dashboard(){
        if(dashboardGui == null) return;
        dashboardGui.setVisible(false);
    }

    public void mostra_dashboard_dipedenti(Ristorante ristorante){
        if(dashboardDipedenteGui == null) return;
        dashboardDipedenteGui.setVisible(true);
        dashboardDipedenteGui.set_ristorante(ristorante);
    }

    public void nascondi_dashboard_dipedenti(){
        if(dashboardDipedenteGui == null) return;
        dashboardDipedenteGui.setVisible(false);
    }

    public void print_controller(){
        System.out.println(utente_controller + " " +  cliente_controller + " " + rider_controller + " " + dipendente_controller + " " + ristorante_controller);
    }

    public void distruggi_controller(){
        utente_controller = null;
        cliente_controller = null;
        dipendente_controller = null;
        rider_controller = null;
        ristorante_controller = null;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public JFrame get_mainFrame(){ return mainFrame; }

    public void set_mainFrame(JFrame mainFrame){
        if(this.mainFrame != null) return;
        this.mainFrame = mainFrame;
        this.mainFrame.setLayout(new BorderLayout());
    }

    public JPanel get_pagina(){ return pagina; }

    public void set_pagina(JPanel pagina){
        if(this.pagina != null) mainFrame.remove(this.pagina);
        this.pagina = pagina;
        mainFrame.add(pagina, BorderLayout.CENTER);
        aggiorna_finestra();
    }

    public DashboardGui get_dashboardGui(){ return dashboardGui;}

    public void set_dashboardGui(DashboardGui dashboardGui){
        if(this.dashboardGui != null) return;
        this.dashboardGui = dashboardGui;
        topPanel.add(dashboardGui,BorderLayout.NORTH);
    }

    public DashboardDipedenteGui get_dashboardDipedenteGui(){ return dashboardDipedenteGui;}

    public void set_dashboardDipedenteGui(DashboardDipedenteGui dashboardDipedenteGui){
        if(this.dashboardDipedenteGui != null) return;
        this.dashboardDipedenteGui = dashboardDipedenteGui;
        topPanel.add(dashboardDipedenteGui,BorderLayout.SOUTH);
    }

    public UtenteController get_utente_controller(){ return utente_controller;}

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }

    public ClienteController get_cliente_controller(){ return cliente_controller;}

    public void set_cliente_controller(ClienteController cliente_controller){
        this.cliente_controller = cliente_controller;
    }

    public RiderController get_rider_controller(){ return  rider_controller;}

    public void set_rider_controller(RiderController rider_controller){
        this.rider_controller = rider_controller;
    }

    public DipendenteController get_dipendente_controller(){ return dipendente_controller;}
    public void set_dipendente_controller(DipendenteController dipendente_controller){
        this.dipendente_controller = dipendente_controller;
    }

    public RistoranteController get_ristorante_controller(){ return ristorante_controller; }

    public void set_ristorante_controller(RistoranteController ristorante_controller){
        this.ristorante_controller = ristorante_controller;
    }

    //________________________________________________________________________________________________________________________________________________
    // Main

    public static void main(String[] args){
        MainGui mainGui = new MainGui();
        mainGui.set_mainFrame(new JFrame("FoodDelivery"));
        mainGui.get_mainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGui.get_mainFrame().setVisible(true);

        mainGui.crea_topPanel();

        SignInGui signInGui = new SignInGui(mainGui);
        mainGui.set_pagina(signInGui);



    }
}
