package gui;
import gui.customWidget.DashboardClienteGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class FrameManagerGui {
    private static JFrame mainFrame;
    private JPanel dashboardPanel;

    private DashboardGui dashboardGui;
    private DashboardDipedenteGui dashboardDipedenteGui;
    private DashboardClienteGui dashboardClienteGui;

    private JPanel pagina;

    public main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param main the main
     */
    public FrameManagerGui(main.Main main){
        this.main = main;
        set_mainFrame(new JFrame("FoodDelivery"));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        this.dashboardPanel = new JPanel(new BorderLayout());
        mainFrame.add(dashboardPanel,BorderLayout.NORTH);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione dashboard (si trovano nel package Custom Widget)

    /**
     * @łauthor Tiziano
     * Aggiorna finestra.
     */
    public void aggiorna_finestra(){
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * @author Tiziano
     * Distruggi all dashbaord.
     */
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

    /**
     * @author Tiziano
     * Nascondi dashbaord.
     *
     * @param dashboard the dashboard
     */
    public void nascondi_dashbaord(JPanel dashboard){
        if(dashboard == null) return;
        dashboard.setVisible(false);
    }

    /**
     * Mostra dahsboard.
     *
     * @param dashboard the dashboard
     */
    public void mostra_dahsboard(JPanel dashboard){
        if(dashboard == null) return;
        dashboard.setVisible(true);
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get and Set


    public JFrame get_mainFrame(){ return mainFrame; }

    /**
     * @author Tiziano
     * Set main frame.
     *
     * @param mainFrame the main frame
     */
    public void set_mainFrame(JFrame mainFrame){
        if(this.mainFrame != null) return;
        this.mainFrame = mainFrame;
        this.mainFrame.setLayout(new BorderLayout());
    }

    public JPanel get_pagina(){ return pagina; }

    /**
     * @author Tiziano
     * Set pagina.
     *
     * @param pagina the pagina
     */
    public void set_pagina(JPanel pagina){
        if(this.pagina != null) mainFrame.remove(this.pagina);
        this.pagina = pagina;
        mainFrame.add(pagina, BorderLayout.CENTER);
        aggiorna_finestra();
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get Set dashboard


    public DashboardGui get_dashboardGui(){ return dashboardGui;}

    /**
     * @author Tiziano
     * Set dashboard gui.
     *
     * @param dashboardGui the dashboard gui
     */
    public void set_dashboardGui(DashboardGui dashboardGui){
        if(this.dashboardGui != null) return;
        this.dashboardGui = dashboardGui;
        dashboardPanel.add(dashboardGui,BorderLayout.NORTH);
    }

    public DashboardDipedenteGui get_dashboardDipedenteGui(){ return dashboardDipedenteGui;}

    /**
     * @author Tiziano
     * Set dashboard dipedente gui.
     *
     * @param dashboardDipedenteGui the dashboard dipedente gui
     */
    public void set_dashboardDipedenteGui(DashboardDipedenteGui dashboardDipedenteGui){
        if(this.dashboardDipedenteGui != null) return;
        this.dashboardDipedenteGui = dashboardDipedenteGui;
        dashboardPanel.add(dashboardDipedenteGui,BorderLayout.SOUTH);
    }

    public DashboardClienteGui get_dashboardClienteGui(){ return dashboardClienteGui;}

    /**
     * Set dashboard cliente gui.
     *
     * @param dashboardClienteGui the dashboard cliente gui
     */
    public void set_dashboardClienteGui(DashboardClienteGui dashboardClienteGui){
        if(this.dashboardClienteGui != null) return;
        this.dashboardClienteGui = dashboardClienteGui;
        dashboardPanel.add(dashboardClienteGui,BorderLayout.CENTER);
    }
}
