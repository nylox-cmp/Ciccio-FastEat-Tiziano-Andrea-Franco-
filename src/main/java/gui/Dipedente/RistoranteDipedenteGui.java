package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class RistoranteDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JPanel TopPanelDipedenti;
    private JButton TornaIndietroDipedentiButton;
    private JLabel RistoranteInfoDipedentiLabel;
    private JPanel DipedentiPanel;
    private JPanel ModificaPanel;
    private JLabel NomeLabel;
    private JTextField NomeTextField;
    private JLabel IndirizzoLabel;
    private JTextField IndirizzoTextField;
    private JButton modificaButton;
    private JButton cancellaRistoranteButton;
    private JList MenuDipedentiLista;

    public RistoranteDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
    }
}
