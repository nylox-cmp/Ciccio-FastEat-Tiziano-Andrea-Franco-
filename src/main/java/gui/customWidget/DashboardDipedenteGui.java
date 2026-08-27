package gui.customWidget;

import gui.CanvasGui;
import exception.BusinessError;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.Dipedente.*;
import model.RigaOrdine;
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

    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DashboardDipedenteGui(CanvasGui canvasGui, Ristorante ristorante) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        this.ristorante = ristorante;

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Selettore Gui

        areaGestioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaGestioneComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Ordini":
                        canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
                        break;
                    case "Ristorante":
                        canvasGui.set_pagina(new RistoranteDipedenteGui(canvasGui, DashboardDipedenteGui.this.ristorante));
                        break;
                    case "Dipendenti":
                        canvasGui.set_pagina(new GestioneDipedenteGui(canvasGui));
                        break;
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Dipendente

        licenziatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(canvasGui.get_pagina(), "Sei sicuro di volerti licenziare? , il licenziamneto nel ruolo di Manager comportera anche alla cancellazione del ristorante","FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    try {
                        canvasGui.main.get_dipendente_controller().licenziati();
                        canvasGui.nascondi_dashbaord(canvasGui.get_dashboardDipedenteGui());
                        canvasGui.set_pagina(new ClienteGui(canvasGui));
                    }
                    catch(BusinessError error){
                        JOptionPane.showMessageDialog(canvasGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    public void aggiorna_ruolo_label(CanvasGui canvasGui){
        infoDipendente.setText(Ruolo.converti_ruolo_to_string(canvasGui.main.get_dipendente_controller().get_dipendente().get_ruolo()));
    }

}