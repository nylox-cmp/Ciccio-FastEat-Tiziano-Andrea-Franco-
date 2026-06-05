package gui.Cliente;

import exception.ErrorType;
import model.Prodotto;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.RigaOrdine;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private JLabel labelQuantita;
    private JLabel infoPrdottoClienteLabel;
    private JLabel indirizzoLabel;

    private JTextField indirizzoTextField;
    private JComboBox ordiniComboBox;
    private JButton rimuoviDalOrdineButton;

    public ProdottoClienteGui(MainGui mainGui, Ristorante ristorante, Menu menu, Prodotto prodotto){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);

        tornaIndietroClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new MenuClienteGui(mainGui,ristorante,menu));
            }
        });

        RigaOrdine riga_ordine = new RigaOrdine(prodotto,0);

        aumentaQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                riga_ordine.aumenta_quantita();
                labelQuantita.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        diminuisciQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorType error = riga_ordine.diminuisci_quantita();
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                labelQuantita.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        creaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.get_cliente_controller().crea_ordine(indirizzoTextField.getText(),ristorante);
            }
        });

        aggiungiAllOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
