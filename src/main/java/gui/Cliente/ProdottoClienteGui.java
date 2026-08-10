package gui.Cliente;

import gui.MainGui;
import exception.BusinessError;
import exception.ErrorType;
import model.*;
import model.Menu;
import model.Prodotto;
import model.RigaOrdine;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProdottoClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel ordinmeButtonPanel;
    private JPanel clientiButtonPanel;
    private JPanel quantitaButtonPanel;

    private JButton aumentaQuantitaButton;
    private JButton aggiungiAllOrdineButton;
    private JButton creaOrdineButton;
    private JButton tornaIndietroClienteButton;
    private JButton diminuisciQuantitaButton;
    private JButton rimuoviDalOrdineButton;


    private JLabel quantitaProdottoLabel;
    private JLabel indirizzoLabel;

    private JTextField indirizzoTextField;
    private DefaultComboBoxModel<Ordine> ordineComboBoxModel = new DefaultComboBoxModel<Ordine>();
    private JComboBox<Ordine> ordiniComboBox;

    private MainGui mainGui;
    private Ristorante ristorante;
    private Menu menu;
    private Prodotto prodotto;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ProdottoClienteGui(MainGui mainGui, Ristorante ristorante, model.Menu menu, Prodotto prodotto){
        this.mainGui = mainGui;
        this.ristorante = ristorante;
        this.menu = menu;
        this.prodotto = prodotto;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        RigaOrdine riga_ordine = new RigaOrdine(prodotto,1);
        aggiorna_combo_box_ordini();


        tornaIndietroClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new MenuClienteGui(mainGui,ristorante,menu));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione quantita Prodotto

        aumentaQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = (Ordine) ordineComboBoxModel.getSelectedItem();
                mainGui.get_cliente_controller().aumenta_quantita_prodotto(ordine,riga_ordine);
                quantitaProdottoLabel.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        diminuisciQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = (Ordine) ordineComboBoxModel.getSelectedItem();
                try {
                    mainGui.get_cliente_controller().diminuisci_quantita_prodotto(ordine,riga_ordine);
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                quantitaProdottoLabel.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione del Ordine

        creaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.get_cliente_controller().crea_ordine(indirizzoTextField.getText(),ristorante);
                aggiorna_combo_box_ordini();
            }
        });

        aggiungiAllOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Ordine ordine = (Ordine) obj;
                    mainGui.get_cliente_controller().aggiungi_riga(ordine, prodotto);
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        rimuoviDalOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Ordine ordine = (Ordine) obj;
                mainGui.get_cliente_controller().rimuovi_riga(ordine,riga_ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_combo_box_ordini(){
        ordineComboBoxModel.removeAllElements();
        ArrayList<Ordine> ordini = mainGui.get_cliente_controller().get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordineComboBoxModel.addElement(ordine);
    }
}
