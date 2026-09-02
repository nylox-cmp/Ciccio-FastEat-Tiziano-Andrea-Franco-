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

    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DashboardClienteGui(CanvasGui canvasGui){
        this.main = canvasGui.main;

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
                aggiorna_punti_fedelta_label();
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    public void aggiorna_punti_fedelta_label(){
        puntiFedeltaLabel.setText("punti fedelta: " + String.valueOf(main.get_cliente_controller().get_punti_fedelta()));
    }
}
