package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class DipedenteGui extends JPanel {

    public DipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);
    }

    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JLabel tittoloLabel;
    private JTextField nomeTextField;
    private JTextField indirizzoTextField;
    private JButton RegistraButton;
    private JLabel nomeLabel;
    private JLabel indirizzoLabel;
    private JPanel ristorantePanel;
    private JPanel richiestaDipedentePanel;
    private JButton richiestaDipedenteButton;
    private JTextField codiceAutenticazioneTextField;
    private JLabel codiceAutenticazioneLabel;
}
