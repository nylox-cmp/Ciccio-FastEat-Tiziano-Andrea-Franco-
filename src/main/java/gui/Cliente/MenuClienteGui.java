package gui.Cliente;

import exception.ErrorType;
import gui.MainGui;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JLabel infoMenuLabel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;

    private DefaultListModel<Prodotto> prodottoListModel = new DefaultListModel<Prodotto>();
    private JList<Prodotto> prodottiLista;
    private JScrollPane prodottiJSrollPane;

    private JButton tornaIndietroButton;

    private MainGui mainGui;
    private Ristorante ristorante;
    private Menu menu;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuClienteGui(MainGui mainGui, Ristorante ristorante,Menu menu){
        this.mainGui = mainGui;
        this.ristorante = ristorante;
        this.menu = menu;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        infoMenuLabel.setText(menu.toString());
        prodottiLista.setModel(prodottoListModel);

        aggiorna_lista_prodotti();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione


        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Aggiornamento Lista

    public void aggiorna_lista_prodotti(){
        prodottoListModel.clear();
        ArrayList<Prodotto> prodotti = mainGui.get_cliente_controller().get_prodotti(menu);
        if(prodotti == null) return;

        for(Prodotto prodotto : prodotti)
            prodottoListModel.addElement(prodotto);
    }

}
