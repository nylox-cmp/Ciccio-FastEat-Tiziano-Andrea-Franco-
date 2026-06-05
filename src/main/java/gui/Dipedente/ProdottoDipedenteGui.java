package gui.Dipedente;

import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;

public class ProdottoDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel TopPanelDipedenti;
    private JButton TornaIndietroDipedentiButton;
    private JLabel ProdottoInfoDipedentiLabel;
    private JPanel ModificaProttoPanel;
    private JLabel NomePrdottoLabel;
    private JTextField NomeTextField;
    private JLabel PrezzoProdottoLabel;
    private JTextField PrezzoTextField;
    private JButton modificaButton;
    private JButton cancellaProdottoButton;

    public ProdottoDipedenteGui(MainGui mainGui, Ristorante ristorante, Menu menu, Prodotto prodotto){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);
    }
}
