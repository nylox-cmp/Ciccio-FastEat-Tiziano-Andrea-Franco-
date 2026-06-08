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
    private JLabel infoMenuLabel;
    private JPanel bottomPanel;

    private JButton tornaIndietroButton;
    private DefaultListModel<Prodotto> prodottoListModel = new DefaultListModel<Prodotto>();
    private JList<Prodotto> prodottiLista;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuClienteGui(MainGui mainGui, Ristorante ristorante,Menu menu){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);


        infoMenuLabel.setText(menu.toString());
        prodottiLista.setModel(prodottoListModel);

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
                if(prodotto != null)
                    mainGui.set_pagina(new ProdottoClienteGui(mainGui,ristorante,menu,prodotto));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


}
