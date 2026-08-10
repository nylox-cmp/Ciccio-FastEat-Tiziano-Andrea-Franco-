package gui.Cliente;

import gui.CanvasGui;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    private CanvasGui canvasGui;
    private main.Main main;

    private Ristorante ristorante;
    private Menu menu;
    private ProdottoClienteGui prodottiGui;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuClienteGui(CanvasGui canvasGui, Ristorante ristorante, Menu menu){
        this.canvasGui = canvasGui;
        this.main = canvasGui.main;

        this.ristorante = ristorante;
        this.menu = menu;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        add(this.prodottiGui = new ProdottoClienteGui(canvasGui,ristorante,menu));

        infoMenuLabel.setText(menu.toString());
        prodottiLista.setModel(prodottoListModel);

        aggiorna_lista_prodotti();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.set_pagina(new RistoranteClienteGui(canvasGui,ristorante));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ListListener

        prodottiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Prodotto prodotto = prodottiLista.getSelectedValue();
                if(prodotto == null) return;

                prodottiGui.set_prodotto(prodotto);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Aggiornamento Lista

    public void aggiorna_lista_prodotti(){
        prodottoListModel.clear();
        ArrayList<Prodotto> prodotti = main.get_cliente_controller().get_prodotti(menu);
        if(prodotti == null) return;

        for(Prodotto prodotto : prodotti)
            prodottoListModel.addElement(prodotto);
    }

}
