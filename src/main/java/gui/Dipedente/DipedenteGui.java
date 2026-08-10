package gui.Dipedente;

import gui.CanvasGui;
import controller.DipendenteController;
import exception.BusinessError;
import exception.ErrorType;
import gui.customWidget.DashboardDipedenteGui;
import main.Main;

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

    private Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipedenteGui(CanvasGui canvasGui){
        this.main = canvasGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

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
                main.get_utente_controller().crea_ristorante(nome,indirizzo);
                main.set_dipendente_controller(new DipendenteController());
                canvasGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(canvasGui));
                canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
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
                    main.get_utente_controller().registrazione_dipedente_ristorante(codice_ristorante);
                    canvasGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(canvasGui));
                    canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
