package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;

public class MenuDipedentiGui extends JPanel {

    public MenuDipedentiGui(MainGui mainGui, Ristorante ristorante,Menu menu){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);
    }

    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JPanel TopPanelDipedenti;
    private JButton tornaIndietroButton;
    private JPanel modificaPanel;
    private JLabel NomeLabel;
    private JTextField nomeTextField;
    private JButton modificaButton;
    private JButton cancellaButton;
    private JLabel nomeMenuLabel;
    private JList prodottiLista;
}
