package gui.customWidget;

import exception.BusinessError;
import gui.Cliente.ClienteGui;
import gui.Dipedente.GestioneDipedenteGui;
import gui.Dipedente.OrdiniDipedenteGui;
import gui.Dipedente.RistoranteDipedenteGui;
import gui.FrameManagerGui;
import model.Dipendente;
import model.Ristorante;
import model.Ruolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DashboardDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel dashboardPanel;

    private JLabel tittoloLabel;
    private JLabel infoDipendente;

    private JComboBox areaGestioneComboBox;
    private JButton licenziatiButton;

    private Dipendente dipendente;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param frameManagerGui  the canvas gui
     * @param dipendente the dipendente
     */
    public DashboardDipedenteGui(FrameManagerGui frameManagerGui, Dipendente dipendente) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        this.dipendente = dipendente;
        this.ristorante = dipendente.get_ristorante();

        aggiorna_ruolo_label();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Selettore Gui

        /**
         * @author Tiziano
         */
        areaGestioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaGestioneComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Ordini":
                        frameManagerGui.set_pagina(new OrdiniDipedenteGui(frameManagerGui));
                        break;
                    case "Ristorante":
                        frameManagerGui.set_pagina(new RistoranteDipedenteGui(frameManagerGui, DashboardDipedenteGui.this.ristorante));
                        break;
                    case "Dipendenti":
                        frameManagerGui.set_pagina(new GestioneDipedenteGui(frameManagerGui));
                        break;
                }
                aggiorna_ruolo_label();
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Dipendente

        /**
         * @author Tiziano
         */
        licenziatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(frameManagerGui.get_pagina(), "Sei sicuro di volerti licenziare? , il licenziamneto nel ruolo di Manager comportera anche alla cancellazione del ristorante","FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    try {
                        frameManagerGui.main.get_dipendente_controller().licenziati();
                        frameManagerGui.nascondi_dashbaord(frameManagerGui.get_dashboardDipedenteGui());
                        frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
                    }
                    catch(BusinessError error){
                        JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    /**
     * @author Tiziano
     * Aggiorna ruolo label.
     */
    public void aggiorna_ruolo_label( ){
        infoDipendente.setText(Ruolo.converti_ruolo_to_string(dipendente.get_ruolo()));
    }

}