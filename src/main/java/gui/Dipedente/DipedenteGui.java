package gui.Dipedente;

import controller.DipendenteController;
import exception.BusinessError;
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
    private JTextField codiceAutenticazioneTextField;

    private JButton registraButton;
    private JButton richiestaDipedenteButton;

    private JLabel codiceAutenticazioneLabel;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.get_dashboardGui().nascondi_area_OrdiniClienti();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Registrazione e Richiesta Assunzione Dipendente

        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String indirizzo = indirizzoTextField.getText();

                if(nome.isEmpty() || indirizzo.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_utente_controller().crea_ristorante(nome,indirizzo);
                mainGui.set_dipendente_controller(new DipendenteController());
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

                try {
                    mainGui.get_utente_controller().registrazione_dipedente_ristorante(codice_ristorante);
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
