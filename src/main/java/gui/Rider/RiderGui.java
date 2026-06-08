package gui.Rider;

import controller.RiderController;
import exception.ErrorType;
import gui.Dipedente.OrdiniDipedenteGui;
import gui.MainGui;
import gui.customWidget.DashboardGui;

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

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RiderGui(MainGui mainGui) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainGui.get_dashboardGui().nascondi_area_ordini();

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mezzo_trasporto = mezzoTrasportoTextField.getText();
                if (mezzo_trasporto.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_utente_controller().sing_in_rider(mezzo_trasporto);
                mainGui.set_pagina(new OrdiniRiderGui(mainGui));
            }
        });
    }
}