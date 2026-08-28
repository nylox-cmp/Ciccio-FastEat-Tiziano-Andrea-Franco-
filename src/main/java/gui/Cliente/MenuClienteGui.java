package gui.Cliente;

import controller.OrdiniController;
import exception.BusinessError;
import exception.ErrorType;
import gui.CanvasGui;
import model.*;
import model.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuClienteGui extends JPanel{
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel prodottiPanel;
    private JPanel buttonPanel;
    private JPanel ordinePanel;

    private JLabel quantitaProdottoLabel;
    private JLabel infoMenuLabel;
    private JLabel indrizzoConsegnaLabel;

    private JTextField indirizzoTextField;

    private JButton tornaIndietroButton;
    private JButton aumentaQuantitaButton;
    private JButton diminuisciQuantitaButton;
    private JButton creaOrdineButton;
    private JButton aggungiAllOrdineButton;
    private JButton rimuoviDallOridneButton;

    private JScrollPane prodottiScrollPane;
    private  DefaultListModel<Prodotto> prodottoListModel = new DefaultListModel<Prodotto>();
    private JList<Prodotto> prodottiJList;
    private DefaultComboBoxModel<Ordine> ordiniComboBoxModel = new DefaultComboBoxModel<Ordine>();
    private JComboBox ordiniComboBox;

    private  CanvasGui canvasGui;
    private  main.Main main;

    private Ristorante ristorante;
    private Menu menu;
    private RigaOrdine riga_ordine;

    public MenuClienteGui(CanvasGui canvasGui, Ristorante ristorante, Menu menu){
        this.canvasGui = canvasGui;
        this.main = canvasGui.main;

        main.set_ordine_controller(new OrdiniController());

        this.ristorante = ristorante;
        this.menu = menu;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        infoMenuLabel.setText(menu.toString());
        ordiniComboBox.setModel(ordiniComboBoxModel);
        prodottiJList.setModel(prodottoListModel);

        aggiorna_lista_prodotti();
        aggiorna_combo_box_ordini();

        prodottiJList.addListSelectionListener(e -> aggiorna_riga_selezionata());
        ordiniComboBox.addActionListener(e -> aggiorna_riga_selezionata());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione quantita Prodotto

        aumentaQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.PRODOTTO_NON_PRESENTE_ORDINE),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ordine ordine = (Ordine) ordiniComboBoxModel.getSelectedItem();
                int quantita = riga_ordine.get_quantita() + 1;
                main.get_cliente_controller().aggiorna_quantita_rigaOrdine(ordine,riga_ordine,quantita);
                aggiorna_quantita_label(riga_ordine.get_quantita());
            }
        });

        diminuisciQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.PRODOTTO_NON_PRESENTE_ORDINE),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ordine ordine = (Ordine) ordiniComboBoxModel.getSelectedItem();
                try {
                    int quantita = riga_ordine.get_quantita() - 1;
                    main.get_cliente_controller().aggiorna_quantita_rigaOrdine(ordine,riga_ordine,quantita);
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_quantita_label(riga_ordine.get_quantita());
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Ordine

        creaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indirizzo = indirizzoTextField.getText();
                if(indirizzo.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                main.get_cliente_controller().crea_ordine(indirizzo,ristorante);
                aggiorna_combo_box_ordini();
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione RigheOrdine

        aggungiAllOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiJList.getSelectedValue();
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Ordine ordine = (Ordine) obj;
                    main.get_cliente_controller().aggiungi_riga(ordine, prodotto);
                    aggiorna_riga_selezionata();
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });

        rimuoviDallOridneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.PRODOTTO_NON_PRESENTE_ORDINE),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Ordine ordine = (Ordine) obj;
                main.get_cliente_controller().rimuovi_riga(ordine,riga_ordine.get_prodotto());
                aggiorna_riga_selezionata();
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.set_pagina(new RistoranteClienteGui(canvasGui,ristorante));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    public void aggiorna_quantita_label(int quantita){
        quantitaProdottoLabel.setText(String.valueOf(quantita));
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Aggiornamento Lista

    private void aggiorna_riga_selezionata() {
        Ordine ordine = (Ordine) ordiniComboBox.getSelectedItem();
        Prodotto prodotto = prodottiJList.getSelectedValue();
        if(ordine == null || prodotto == null) return;

        main.get_ordini_controller().get_contenuto_ordine(ordine);
        riga_ordine = ordine.get_riga_ordine_from_prodotto(prodotto);

        if(riga_ordine == null) aggiorna_quantita_label(0);
        else aggiorna_quantita_label(riga_ordine.get_quantita());
        System.out.println(riga_ordine + " " + ordine.get_righe_ordine());
    }

    private void aggiorna_lista_prodotti(){
        ArrayList<Prodotto> prodotti_list = main.get_cliente_controller().get_prodotti(menu);
        if(prodotti_list == null) return;

        for(Prodotto prodotto : prodotti_list)
            prodottoListModel.addElement(prodotto);
    }

    private void aggiorna_combo_box_ordini(){
        ordiniComboBoxModel.removeAllElements();
        ArrayList<Ordine> ordini = main.get_cliente_controller().get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniComboBoxModel.addElement(ordine);
    }
}
