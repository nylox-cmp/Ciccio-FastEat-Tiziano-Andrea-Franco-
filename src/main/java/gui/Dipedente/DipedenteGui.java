package gui.Dipedente;

import controller.DipendenteController;
import controller.RistoranteController;
import exception.BusinessError;
import exception.ErrorType;
import gui.FrameManagerGui;
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

    /**
     * @author Andrea
     *
     * @param frameManagerGui the canvas gui
     */
    public DipedenteGui(FrameManagerGui frameManagerGui){
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Registrazione e Richiesta Assunzione Dipendente

        /**
         * @author Andrea
         */
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
                main.set_ristorante_controller(new RistoranteController(main.get_dipendente_controller()));

                frameManagerGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(frameManagerGui,main.get_dipendente_controller().get_dipendente()));
                frameManagerGui.set_pagina(new OrdiniDipedenteGui(frameManagerGui));
            }
        });

        /**
         * @author Andrea
         */
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

                    main.set_dipendente_controller(new DipendenteController());
                    main.set_ristorante_controller(new RistoranteController(main.get_dipendente_controller()));

                    frameManagerGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(frameManagerGui,main.get_dipendente_controller().get_dipendente()));
                    frameManagerGui.set_pagina(new OrdiniDipedenteGui(frameManagerGui));
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
