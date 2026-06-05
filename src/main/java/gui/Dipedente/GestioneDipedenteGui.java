package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class GestioneDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel richiesteAssunzioniPanel;
    private JPanel dipedentiPanel;
    private JPanel DipedentiButtonPanel;
    private JPanel assunzioiniButtonPanel;
    private JButton rimuoviButton;
    private JButton accettaButton;
    private JButton licenziaButton;
    private JComboBox comboBox1;
    private JLabel ruoloLabel;

    public GestioneDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);
    }
}
