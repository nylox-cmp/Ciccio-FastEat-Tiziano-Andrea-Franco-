package gui.Dipedente;

import gui.MainGui;
import exception.BusinessError;
import exception.ErrorType;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuDipedentiGui extends JPanel {
    private JPanel mainPanel;
    private JLabel nomeMenuLabel;
    private JPanel infoPanel;
    private JPanel prodottiListPanel;
    private JPanel settingPanel;
    private JPanel prodottiPanel;
    private JPanel modificaMenuPanel;
    private JPanel buttonPanel;

    private JButton creaProdottoButton;
    private JButton cancellaProdottoButton;
    private JButton modificaMenuButton;
    private JButton tornaIndietroButton;

    private JLabel nomeProdottoLabel;
    private JLabel prezzoLabel;
    private JLabel nomeModificaMenuLabel;

    private JTextField nomeProdottoTextField;
    private JTextField nomeMenuTextField;
    private JTextField prezzoTextField;

    private JList<Prodotto> prodottiLista;
    private JScrollPane prodottoJScrollPane;
    private JButton modificaButton;
    private DefaultListModel<Prodotto> prodottiListModel = new DefaultListModel<Prodotto>();

    private MainGui mainGui;
    private Ristorante ristorante;
    private Menu menu;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuDipedentiGui(MainGui mainGui, Ristorante ristorante,Menu menu) {
        this.mainGui = mainGui;
        this.ristorante = ristorante;
        this.menu = menu;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        nomeMenuLabel.setText(menu.toString());

        prodottiLista.setModel(prodottiListModel);
        aggiorna_lista_prodotti(menu);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Menu

        modificaMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovoNome = nomeMenuTextField.getText().trim();
                if (nuovoNome.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    mainGui.get_ristorante_controller().modifica_menu(menu,nuovoNome);
                    nomeMenuLabel.setText(nuovoNome);
                }
                catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Prodotto

        creaProdottoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeProdottoTextField.getText().trim();
                String prezzo_string = prezzoTextField.getText().trim();

                if (nome.isEmpty() || prezzo_string.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double prezzo = Double.parseDouble(prezzo_string);
                    try {
                        mainGui.get_ristorante_controller().crea_prodotto(menu,nome, prezzo);
                        aggiorna_lista_prodotti(menu);
                    }
                    catch (BusinessError error) {
                        JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancellaProdottoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiLista.getSelectedValue();
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    mainGui.get_ristorante_controller().cancella_prodotto(menu,prodotto);
                    aggiorna_lista_prodotti(menu);
                }
                catch (BusinessError error){
                JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiLista.getSelectedValue();
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nome = nomeProdottoTextField.getText();
                String prezzo_string = prezzoTextField.getText().trim();
                if(nome.isEmpty() || prezzo_string.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double prezzo = Double.parseDouble(prezzo_string);
                    try {
                        mainGui.get_ristorante_controller().modifica_prodotto(prodotto,nome, prezzo);
                    }
                    catch (BusinessError error) {
                        JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new RistoranteDipedenteGui(mainGui, ristorante));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Aggiornamento Lista

    public void aggiorna_lista_prodotti(Menu menu){
        prodottiListModel.clear();
        ArrayList<Prodotto> prodotti = mainGui.get_ristorante_controller().get_prodotti(menu);
        if(prodotti == null) return;

        for(Prodotto prodotto : prodotti)
            prodottiListModel.addElement(prodotto);
    }

}
