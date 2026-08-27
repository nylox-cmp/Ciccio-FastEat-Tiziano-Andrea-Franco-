package gui.customWidget;

import gui.CanvasGui;
import gui.Cliente.ClienteGui;
import gui.Cliente.OrdiniClientiGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardClienteGui extends JPanel {
    private JPanel mainPanel;
    private JLabel puntiFedeltaLabel;
    private JComboBox areaComboBox;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DashboardClienteGui(CanvasGui canvasGui){
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        areaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Home":
                        canvasGui.set_pagina(new ClienteGui(canvasGui));
                        break;
                    case "Ordini":
                        canvasGui.set_pagina(new OrdiniClientiGui(canvasGui));
                        break;
                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    public void aggiorna_punti_fedelta_label(int quantita){
        puntiFedeltaLabel.setText("punti fedelta: " + String.valueOf(quantita));
    }
}
