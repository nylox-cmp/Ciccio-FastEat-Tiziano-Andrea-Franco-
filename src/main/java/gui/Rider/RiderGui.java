package gui.Rider;

import gui.CanvasGui;
import controller.RiderController;
import exception.ErrorType;

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

    public RiderGui(CanvasGui canvasGui) {
        this.main = canvasGui.main;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

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
                canvasGui.set_pagina(new OrdiniRiderGui(canvasGui));
            }
        });
    }
}