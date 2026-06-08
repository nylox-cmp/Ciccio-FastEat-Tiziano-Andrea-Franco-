package gui.Dipedente;

import controller.DipedenteController;
import controller.UtenteController;
import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel ristorantePanel;
    private JPanel richiestaDipedentePanel;

    private JLabel tittoloLabel;
    private JLabel nomeLabel;
    private JLabel indirizzoLabel;

    private JTextField nomeTextField;
    private JTextField indirizzoTextField;
    private JTextField codiceAutenticazioneTextField;

    private JButton registraButton;
    private JButton richiestaDipedenteButton;

    private JLabel codiceAutenticazioneLabel;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.get_dashboardGui().nascondi_area_ordini();


        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String indirizzo = indirizzoTextField.getText();

                if(nome.isEmpty() && indirizzo.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_utente_controller().crea_ristorante(nome,indirizzo);
                mainGui.set_dipedente_controller(new DipedenteController(mainGui.get_utente_controller()));
                mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
            }
        });

        richiestaDipedenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice_autenticazione = codiceAutenticazioneTextField.getText();
                if(codice_autenticazione.trim().isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ErrorType error = mainGui.get_utente_controller().richiesta_assunzione_ristorante(codice_autenticazione);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(mainPanel,"Richiesta inviata con successo, attendi che la richiesta venga accetta prima di diventare dipendente del ristorante","Ciccio FastEat stuff",JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

}
