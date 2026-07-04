package gui.Dipedente;

import controller.DipedenteController;
import exception.ErrorType;
import gui.MainGui;

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
    private JTextField numeroTelefonoTextField;
    private JTextField codiceAutenticazioneTextField;

    private JButton registraButton;
    private JButton richiestaDipedenteButton;

    private JLabel codiceAutenticazioneLabel;
    private JLabel numeroTelefonoLabel;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.get_dashboardGui().nascondi_pagaRider();
        mainGui.get_dashboardGui().nascondi_area_OrdiniClienti();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Registrazione e Richiesta Assunzione Dipedente

        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String indirizzo = indirizzoTextField.getText();
                String numero_telefono = numeroTelefonoTextField.getText();

                if(nome.isEmpty() || indirizzo.isEmpty() || numero_telefono.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_utente_controller().crea_ristorante(nome,indirizzo,numero_telefono);
                mainGui.set_dipedente_controller(new DipedenteController(mainGui.get_utente_controller()));
                mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
            }
        });

        richiestaDipedenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice_ristorante = codiceAutenticazioneTextField.getText();
                if(codice_ristorante.trim().isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ErrorType error = mainGui.get_utente_controller().richiesta_assunzione_ristorante(codice_ristorante);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(mainPanel,"Richiesta inviata con successo, attendi che la richiesta venga accetta prima di diventare dipendente del ristorante","FastFood stuff",JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

}
