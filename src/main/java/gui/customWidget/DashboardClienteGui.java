package gui.customWidget;

import gui.Cliente.ClienteGui;
import gui.Cliente.OrdiniClientiGui;
import gui.FrameManagerGui;

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

    /**
     * @author Tiziano
     *
     * @param frameManagerGui the canvas gui
     */
    public DashboardClienteGui(FrameManagerGui frameManagerGui){
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        /**
         * @author Tiziano
         */
        areaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Home":
                        frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
                        break;
                    case "Ordini":
                        frameManagerGui.set_pagina(new OrdiniClientiGui(frameManagerGui));
                        break;
                }
                aggiorna_punti_fedelta_label();
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    /**
     * @author Tiziano
     * Aggiorna punti fedelta label.
     */
    public void aggiorna_punti_fedelta_label(){
        puntiFedeltaLabel.setText("punti fedelta: " + String.valueOf(main.get_cliente_controller().get_punti_fedelta()));
    }
}
