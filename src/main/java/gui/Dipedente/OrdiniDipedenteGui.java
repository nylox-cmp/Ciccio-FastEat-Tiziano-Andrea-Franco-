package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class OrdiniDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel RiderPropostiPanel;
    private JPanel RiderPropostiButtonPanel;
    private JButton rifiutaButton;
    private JButton accettaButton;
    private JList RiderPropostiOrdineLista;
    private JPanel OrdiniDipedentiPanel;
    private JPanel OrdiniDipedentiButtonPanel;
    private JButton cancellaButton;
    private JButton senglaProntoAlRitiroButton;
    private JList OrdiniDIpedentiPanel;

    public OrdiniDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);
    }
}
