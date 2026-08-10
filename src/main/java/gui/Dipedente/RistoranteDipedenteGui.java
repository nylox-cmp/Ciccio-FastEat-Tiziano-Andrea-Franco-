package gui.Dipedente;

import gui.MainGui;
import controller.RistoranteController;
import exception.BusinessError;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import model.Menu;
import model.Ristorante;
import model.Ruolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RistoranteDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JPanel menuListPanel;
    private JPanel inoPanel;
    private JPanel menuButtonPanel;
    private JPanel menuPanel;
    private JPanel modificaRistorantePanel;

    private JLabel indirizzoLabel;
    private JLabel infoRistoranteLabel;
    private JLabel nomeMenuLabel;
    private JLabel nomeRistoranteLabel;

    private JTextField nomeMenuTextField;
    private JTextField indirizzoTextField;
    private JTextField nomeRistoranteTextField;

    private JButton modificaRistoranteButton;
    private JButton creaMenuButton;
    private JButton cancellaMenuButton;
    private JButton cancellaRistoranteButton;
    private JButton apriButton;
    private JButton tornaIndietroButton;

    private JScrollPane menuJScrollPane;
    private JList<Menu> menuLista;
    private DefaultListModel<Menu> menuListModel = new DefaultListModel<Menu>();

    private MainGui mainGui;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteDipedenteGui(MainGui mainGui, Ristorante ristorante){
        this.mainGui = mainGui;
        this.ristorante = ristorante;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.set_ristorante_controller(new RistoranteController(mainGui.get_dipendente_controller()));
        aggiorna_infoLabel();

        menuLista.setModel(menuListModel);
        aggiorna_lista_menu();

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

                 try {
                     mainGui.get_ristorante_controller().modifica_ristorante(nome, indirizzo);
                     infoRistoranteLabel.setText(ristorante.toString() + " codice autenticazione: "+ ristorante.get_codice_ristorante());
                 }
                 catch (BusinessError error){
                     JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                 }
            }
        });

        cancellaRistoranteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler eliminare questo Ristorante e licenziare tutti i suoi dipendenti e cancellare tutti i suoi prodotti e menu?", "FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {

                    try {
                        mainGui.get_ristorante_controller().cancella_ristorante();
                        mainGui.nascondi_dashboard_dipedenti();
                        mainGui.set_pagina(new ClienteGui(mainGui));
                    }
                    catch (BusinessError error) {
                        JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                }
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

               try {
                   mainGui.get_ristorante_controller().crea_menu(nome);
                   aggiorna_lista_menu();
               }
               catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
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
                if(scelta == JOptionPane.YES_OPTION) {

                   try {
                       mainGui.get_ristorante_controller().cancella_menu(menu);
                       aggiorna_lista_menu();

                   }
                   catch(BusinessError error) {
                        JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = menuLista.getSelectedValue();
                if (menu == null) {
                    JOptionPane.showMessageDialog(mainGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new MenuDipedentiGui(mainGui, ristorante, menu));
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Lista

    public void aggiorna_lista_menu(){
        menuListModel.clear();
        ArrayList<Menu> menu_list = mainGui.get_ristorante_controller().get_menu();
        if(menu_list == null) return;

        for(Menu menu : menu_list)
                menuListModel.addElement(menu);
    }

    //________________________________________________________________________________________________________________________________________________
    // Metdoto Aggiornmaneto infoLabel

    public void aggiorna_infoLabel(){
        if(mainGui.get_dipendente_controller().get_dipendente().get_ruolo().ordinal() >= Ruolo.GESTIONALE.ordinal()) {
            infoRistoranteLabel.setText(ristorante.toString() + " codice ristorante: " + ristorante.get_codice_ristorante());
            return;
        }
        infoRistoranteLabel.setText(ristorante.toString());
    }
}
