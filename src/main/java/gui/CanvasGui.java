package gui;
import com.sun.tools.javac.Main;
import gui.Autenticazione.SignInGui;
import gui.customWidget.DashboardClienteGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;
import main.*;

import javax.swing.*;
import java.awt.*;

public class CanvasGui {
    private static JFrame mainFrame;
    private JPanel dashboardPanel;

    private DashboardGui dashboardGui;
    private DashboardDipedenteGui dashboardDipedenteGui;
    private DashboardClienteGui dashboardClienteGui;

    private JPanel pagina;

    public main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public CanvasGui(main.Main main){
        this.main = main;
        set_mainFrame(new JFrame("FoodDelivery"));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        this.dashboardPanel = new JPanel(new BorderLayout());
        mainFrame.add(dashboardPanel,BorderLayout.NORTH);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione dashboard (si trovano nel package Custom Widget)

    public void aggiorna_finestra(){
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void distruggi_all_dashbaord(){
        if(dashboardGui != null) {
            dashboardPanel.remove(dashboardGui);
            dashboardGui = null;
        }

        if(dashboardClienteGui != null) {
            dashboardPanel.remove(dashboardClienteGui);
            dashboardClienteGui = null;
        }

        if(dashboardDipedenteGui != null) {
            dashboardPanel.remove(dashboardDipedenteGui);
            dashboardDipedenteGui = null;
        }
    }

    public void nascondi_dashbaord(JPanel dashboard){
        if(dashboard == null) return;
        dashboard.setVisible(false);
    }

    public void mostra_dahsboard(JPanel dashboard){
        if(dashboard == null) return;
        dashboard.setVisible(true);
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
        dashboardPanel.add(dashboardGui,BorderLayout.NORTH);
    }

    public DashboardDipedenteGui get_dashboardDipedenteGui(){ return dashboardDipedenteGui;}

    public void set_dashboardDipedenteGui(DashboardDipedenteGui dashboardDipedenteGui){
        if(this.dashboardDipedenteGui != null) return;
        this.dashboardDipedenteGui = dashboardDipedenteGui;
        dashboardPanel.add(dashboardDipedenteGui,BorderLayout.SOUTH);
    }

    public DashboardClienteGui get_dashboardClienteGui(){ return dashboardClienteGui;}

    public void set_dashboardClienteGui(DashboardClienteGui dashboardClienteGui){
        if(this.dashboardClienteGui != null) return;
        this.dashboardClienteGui = dashboardClienteGui;
        dashboardPanel.add(dashboardClienteGui,BorderLayout.CENTER);
    }
}
