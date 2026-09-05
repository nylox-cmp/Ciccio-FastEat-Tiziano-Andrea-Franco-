package gui.Rider;

import controller.RiderController;
import exception.ErrorType;
import gui.FrameManagerGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiderGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel registrazioneButtonPanel;

    private JLabel titoloLabel;
    private JLabel mezzoTrasportoLabel;

    private JTextField mezzoTrasportoTextField;
    private JButton registratiButton;

    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Franco
     *
     * @param frameManagerGui the canvas gui
     */
    public RiderGui(FrameManagerGui frameManagerGui) {
        this.main = frameManagerGui.main;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        /**
         * @author Franco
         */
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mezzo_trasporto = mezzoTrasportoTextField.getText();
                if (mezzo_trasporto.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                main.get_utente_controller().registra_rider(mezzo_trasporto);
                main.set_rider_controller(new RiderController());
                frameManagerGui.set_pagina(new OrdiniRiderGui(frameManagerGui));
            }
        });
    }
}