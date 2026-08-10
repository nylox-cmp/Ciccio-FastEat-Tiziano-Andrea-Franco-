package gui;
import com.sun.tools.javac.Main;
import gui.Autenticazione.SignInGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;
import main.*;

import javax.swing.*;
import java.awt.*;

public class CanvasGui {
    private static JFrame mainFrame;
    private JPanel topPanel;
    private DashboardGui dashboardGui;
    private DashboardDipedenteGui dashboardDipedenteGui;
    private JPanel pagina;

    public main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public CanvasGui(main.Main main){
        this.main = main;
        set_mainFrame(new JFrame("FoodDelivery"));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        crea_topPanel();
    }

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

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get and Set

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

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get Set dashboard

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
}
