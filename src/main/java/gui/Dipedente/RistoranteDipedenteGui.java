package gui.Dipedente;

import gui.CanvasGui;
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

    private CanvasGui canvasGui;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteDipedenteGui(CanvasGui canvasGui, Ristorante ristorante){
        this.canvasGui = canvasGui;
        this.ristorante = ristorante;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        canvasGui.main.set_ristorante_controller(new RistoranteController(canvasGui.main.get_dipendente_controller()));
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
                     canvasGui.main.get_ristorante_controller().modifica_ristorante(nome, indirizzo);
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
                        canvasGui.main.get_ristorante_controller().cancella_ristorante();
                        canvasGui.nascondi_dashboard_dipedenti();
                        canvasGui.set_pagina(new ClienteGui(canvasGui));
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
                   canvasGui.main.get_ristorante_controller().crea_menu(nome);
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
                       canvasGui.main.get_ristorante_controller().cancella_menu(menu);
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
                    JOptionPane.showMessageDialog(canvasGui.get_pagina(), ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                canvasGui.set_pagina(new MenuDipedentiGui(canvasGui, ristorante, menu));
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Lista

    public void aggiorna_lista_menu(){
        menuListModel.clear();
        ArrayList<Menu> menu_list = canvasGui.main.get_ristorante_controller().get_menu();
        if(menu_list == null) return;

        for(Menu menu : menu_list)
                menuListModel.addElement(menu);
    }

    //________________________________________________________________________________________________________________________________________________
    // Metdoto Aggiornmaneto infoLabel

    public void aggiorna_infoLabel(){
        if(canvasGui.main.get_dipendente_controller().get_dipendente().get_ruolo().ordinal() >= Ruolo.GESTIONALE.ordinal()) {
            infoRistoranteLabel.setText(ristorante.toString() + " codice ristorante: " + ristorante.get_codice_ristorante());
            return;
        }
        infoRistoranteLabel.setText(ristorante.toString());
    }
}
