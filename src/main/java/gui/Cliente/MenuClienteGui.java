package gui.Cliente;

import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel topPanelClienti;
    private JButton tornaIndietroButton;
    private JLabel infoMenuLabel;
    private JList<Prodotto> prodottiLista;
    private JPanel bottomPanel;

    public MenuClienteGui(MainGui mainGui, Ristorante ristorante,Menu menu){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);

        infoMenuLabel.setText(menu.toString());

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });

        prodottiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Prodotto prodotto = prodottiLista.getSelectedValue();
                mainGui.set_pagina(new ProdottoClienteGui(mainGui,ristorante,menu,prodotto));
            }
        });
    }
}
