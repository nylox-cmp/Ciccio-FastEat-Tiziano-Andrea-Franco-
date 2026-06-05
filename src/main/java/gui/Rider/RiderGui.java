package gui.Rider;

import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class RiderGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JLabel titoloLabel;
    private JPanel registrazioneButtonPanel;
    private JLabel mezzoTrasportoLabel;
    private JTextField mezzoTrasportoTextField;
    private JButton RegistratiButton;

    public RiderGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_rider(mainGui);
    }
}
