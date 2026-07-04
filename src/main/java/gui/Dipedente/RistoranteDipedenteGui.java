package gui.Dipedente;

import controller.RistoranteController;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.MainGui;
import model.Menu;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RistoranteDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel modificaRistorantePanel;
    private JPanel menuPanel;

    private JLabel infoRistoranteLabel;
    private JLabel nomeRistoranteLabel;
    private JLabel indirizzoLabel;

    private JTextField nomeMenuTextField;
    private JTextField indirizzoTextField;
    private JTextField nomeRistoranteTextField;

    private JButton modificaRistoranteButton;
    private JButton creaMenuButton;
    private JButton cancellaMenuButton;
    private JButton cancellaRistoranteButton;

    private JList<Menu> menuLista;
    private DefaultListModel<Menu> menuListModel = new DefaultListModel<Menu>();
    private JLabel nomeMenuLabel;
    private JScrollPane menuJScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteDipedenteGui(MainGui mainGui, Ristorante ristorante){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.set_ristorante_controller(new RistoranteController(mainGui.get_dipedente_controller()));
        mainGui.get_dashboardDipedenteGui().aggiungi_pulsanti_ristorante(mainGui,ristorante,menuLista);
        infoRistoranteLabel.setText(ristorante.toString()+ " incassi: " + ristorante.get_incassi() + " codice ristorante: "  + ristorante.get_codice_ristorante());

        menuLista.setModel(menuListModel);
        aggiorna_lista_menu(ristorante);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Ristorante

        modificaRistoranteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String nome = nomeRistoranteTextField.getText();
                 String indirizzo = indirizzoTextField.getText();
                 if(nome.isEmpty() || indirizzo.isEmpty()){
                     JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                     return;
                 }

                 ErrorType error = mainGui.get_ristorante_controller().modifica_ristorante(nome,indirizzo);
                 if(error != ErrorType.NESSUN_ERRORE){
                     JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 infoRistoranteLabel.setText(ristorante.toString() + " incassi: " + ristorante.get_incassi() + " codice autenticazione: "+ ristorante.get_codice_ristorante());
            }
        });

        cancellaRistoranteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler eliminare questo Ristorante e licenziare tutti i suoi dipendenti e cancellare tutti i suoi prodotti e menu?", "FastFood", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.NO_OPTION) return;

                ErrorType error = mainGui.get_dipedente_controller().cancella_ristorante();
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.nascondi_dashboard_dipedenti();
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });


        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Menu

        creaMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeMenuTextField.getText();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ErrorType error = mainGui.get_ristorante_controller().crea_menu(nome);
                if (error != ErrorType.NESSUN_ERRORE) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_lista_menu(ristorante);
            }
        });

        cancellaMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = menuLista.getSelectedValue();
                if(menu == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int scelta = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler eliminare questo menu e tutti i suoi prodotti?", "Conferma", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.NO_OPTION || scelta == JOptionPane.CLOSED_OPTION) return;

                ErrorType error = mainGui.get_ristorante_controller().cancella_menu(menu);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_lista_menu(ristorante);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_lista_menu(Ristorante ristorante){
        menuListModel.clear();
        ArrayList<Menu> menus = ristorante.get_menu();
        if(ristorante != null && menus!= null){
            for(Menu menu : menus){
                menuListModel.addElement(menu);
            }
        }
    }

}
