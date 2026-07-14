package gui.customWidget;

import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.Dipedente.*;
import gui.MainGui;
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

    private JButton tornaIndietroButton;
    private JButton apriButton;

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
        tornaIndietroButton.setVisible(false);
        apriButton.setVisible(false);

        aggiorna_ruolo_label(mainGui);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Selettore Gui

        areaGestioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaGestioneComboBox.getSelectedItem();
                String area = (String) item;
                switch (area) {
                    case "Ordini":
                        nascondi_pulsanti_navigazione();
                        mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
                        break;
                    case "Ristorante":
                        mainGui.set_pagina(new RistoranteDipedenteGui(mainGui, DashboardDipedenteGui.this.ristorante));
                        break;
                    case "Dipedenti":
                        nascondi_pulsanti_navigazione();
                        mainGui.set_pagina(new GestioneDipedenteGui(mainGui));
                        break;
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Dipedente

        licenziatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainGui.get_pagina(), "Sei sicuro di volerti licenziare? , il licenziamneto nel ruolo di Manager comportera anche alla cancellazione del ristorante","FastFood", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    ErrorType error = mainGui.get_dipedente_controller().licenziati();
                    if (error != ErrorType.NESSUN_ERRORE) {
                        JOptionPane.showMessageDialog(mainGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    mainGui.nascondi_dashboard_dipedenti();
                    mainGui.set_pagina(new ClienteGui(mainGui));
                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void rimuovi_action(JButton button){
        for(ActionListener action : button.getActionListeners()){
            button.removeActionListener(action);
        }
    }

    public void aggiorna_ruolo_label(MainGui mainGui){
        infoDipendente.setText(Ruolo.converti_ruolo_to_string(mainGui.get_dipedente_controller().get_dipendente().get_ruolo()));
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiungi_pulsanti_ristorante(MainGui maingui, Ristorante ristorante, JList<Menu> menuLista) {
        tornaIndietroButton.setVisible(false);
        apriButton.setVisible(true);

        rimuovi_action(apriButton);

        apriButton.setText("apri menu");

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.Menu menu = menuLista.getSelectedValue();
                if (menu == null) {
                    JOptionPane.showMessageDialog(maingui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                maingui.get_ristorante_controller().set_menu(menu);
                maingui.set_pagina(new MenuDipedentiGui(maingui, ristorante, menu));
            }
        });
    }


    public void aggiungi_pulsanti_menu(MainGui mainGui, Ristorante ristorante, Menu menu, JList<Prodotto> prodottiLista) {
        tornaIndietroButton.setVisible(true);
        apriButton.setVisible(true);

        rimuovi_action(apriButton);
        rimuovi_action(tornaIndietroButton);

        apriButton.setText("apri prodotto");

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new RistoranteDipedenteGui(mainGui, ristorante));
            }
        });

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiLista.getSelectedValue();
                if (prodotto == null) {
                    JOptionPane.showMessageDialog(mainGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_ristorante_controller().set_prodotto(prodotto);
                mainGui.set_pagina(new ProdottoDipedenteGui(mainGui, ristorante, menu, prodotto));
            }
        });
    }

    public void aggiungi_pulsanti_prodotto(MainGui mainGui, Ristorante ristorante, Menu menu) {
        tornaIndietroButton.setVisible(true);
        apriButton.setVisible(false);

        rimuovi_action(tornaIndietroButton);

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new MenuDipedentiGui(mainGui, ristorante, menu));
            }
        });
    }

    private void nascondi_pulsanti_navigazione(){
        tornaIndietroButton.setVisible(false);
        apriButton.setVisible(false);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and set

    public Ristorante get_ristorante(){ return ristorante;}
    public void set_ristorante(Ristorante ristorante){this.ristorante = ristorante;}
}