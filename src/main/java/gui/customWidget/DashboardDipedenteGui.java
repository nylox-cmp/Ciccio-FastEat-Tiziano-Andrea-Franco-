package gui.customWidget;

import gui.MainGui;
import exception.BusinessError;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.Dipedente.*;
import model.Menu;
import model.Prodotto;
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

    public DashboardDipedenteGui(MainGui mainGui) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Selettore Gui

        areaGestioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaGestioneComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Ordini":
                        mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
                        break;
                    case "Ristorante":
                        mainGui.set_pagina(new RistoranteDipedenteGui(mainGui, DashboardDipedenteGui.this.ristorante));
                        break;
                    case "Dipendenti":
                        mainGui.set_pagina(new GestioneDipedenteGui(mainGui));
                        break;
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Dipendente

        licenziatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainGui.get_pagina(), "Sei sicuro di volerti licenziare? , il licenziamneto nel ruolo di Manager comportera anche alla cancellazione del ristorante","FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    try {
                        mainGui.get_dipendente_controller().licenziati();
                        mainGui.nascondi_dashboard_dipedenti();
                        mainGui.set_pagina(new ClienteGui(mainGui));
                    }
                    catch(BusinessError error){
                        JOptionPane.showMessageDialog(mainGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Label

    public void aggiorna_ruolo_label(MainGui mainGui){
        infoDipendente.setText(Ruolo.converti_ruolo_to_string(mainGui.get_dipendente_controller().get_dipendente().get_ruolo()));
    }


    //________________________________________________________________________________________________________________________________________________
    // Metodi Set

    public void set_ristorante(Ristorante ristorante){this.ristorante = ristorante;}
}