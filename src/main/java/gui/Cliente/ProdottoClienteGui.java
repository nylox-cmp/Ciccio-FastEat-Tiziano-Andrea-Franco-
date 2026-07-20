package gui.Cliente;

import exception.BusinessError;
import exception.ErrorType;
import gui.MainGui;
import model.Ordine;
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
    private JPanel topPanelClienti;
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
    private JLabel infoPrdottoClienteLabel;
    private JLabel indirizzoLabel;

    private JTextField indirizzoTextField;
    private DefaultComboBoxModel<Ordine> ordineComboBoxModel = new DefaultComboBoxModel<Ordine>();
    private JComboBox<Ordine> ordiniComboBox;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ProdottoClienteGui(MainGui mainGui, Ristorante ristorante, Menu menu, Prodotto prodotto){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        RigaOrdine riga_ordine = new RigaOrdine(prodotto,1);
        aggiorna_combo_box_ordini(mainGui.get_cliente_controller().get_cliente().get_ordini());

        indirizzoLabel.setText(prodotto.toString());

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
                mainGui.get_cliente_controller().aumenta_quantita_prodotto(riga_ordine);
                quantitaProdottoLabel.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        diminuisciQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainGui.get_cliente_controller().diminuisci_quantita_prodotto(riga_ordine);
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
                aggiorna_combo_box_ordini(mainGui.get_cliente_controller().get_cliente().get_ordini());
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


    public void aggiorna_combo_box_ordini(ArrayList<Ordine> ordini){
        ordineComboBoxModel.removeAllElements();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordineComboBoxModel.addElement(ordine);
    }
}
