package gui.Cliente;

import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.*;

import javax.swing.*;
import java.awt.*;

public class OrdiniClientiGui extends JPanel {
    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JList<Prodotto> ContenutoOrdineLista;
    private JPanel OrdiniPanel;
    private JPanel ButtonPanelCliente;
    private JList<Ordine> OrdiniClientiLista;
    private JPanel ContenutoOrdinePanel;
    private JButton annulaOrdineButton;
    private JButton confermaConsegnaButton;
    private JPanel ConsegnaPanel;
    private JPanel PuntiFedeltaPanel;
    private JLabel QuantitaLabel;
    private JButton AggiungiButton;
    private JButton DiminuisciButton;
    private JButton ApplicaScontoButton;
    private JLabel puntiFedeltaLabel;

    public OrdiniClientiGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);
    }
}
