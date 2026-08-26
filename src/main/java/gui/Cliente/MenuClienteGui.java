package gui.Cliente;

import gui.CanvasGui;
import exception.BusinessError;
import exception.ErrorType;
import model.*;
import model.Menu;
import model.Prodotto;
import model.RigaOrdine;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel ProdottiPanel;
    private JPanel ordinePanel;
    private JPanel quantitaButtonPanel;
    private JPanel ordinmeButtonPanel;
    private JLabel indirizzoLabel;
    private JPanel infoMenuPanel;

    private JButton aumentaQuantitaButton;
    private JButton aggiungiAllOrdineButton;
    private JButton creaOrdineButton;
    private JButton tornaIndietroClienteButton;
    private JButton diminuisciQuantitaButton;
    private JButton rimuoviDalOrdineButton;
    private JButton tornaIndietroButton;

    private JTextField indirizzoTextField;
    private JLabel quantitaProdottoLabel;
    private JLabel infoMenuLabel;

    private JScrollPane prodottoScrollPane;
    private DefaultListModel<Prodotto> prodottListModel = new DefaultListModel<Prodotto>();
    private JList<Prodotto> prodottiJList = new JList<Prodotto>();

    private DefaultComboBoxModel<Ordine> ordiniComboBoxModel = new DefaultComboBoxModel<Ordine>();
    private JComboBox<Ordine> ordiniComboBox = new JComboBox<Ordine>();

    private CanvasGui canvasGui;
    private main.Main main;
    private Ristorante ristorante;
    private Menu menu = null;
    private RigaOrdine riga_ordine = null;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuClienteGui(CanvasGui canvasGui, Ristorante ristorante,Menu menu){
        this.canvasGui = canvasGui;
        this.main = canvasGui.main;
        this.ristorante = ristorante;
        this.menu = menu;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        infoMenuLabel.setText(menu.toString());
        prodottiJList.setModel(prodottListModel);
        ordiniComboBox.setModel(ordiniComboBoxModel);

        aggiorna_lista_prodotti();
        aggiorna_combo_box_ordini();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione quantita Prodotto

        aumentaQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.PRODOTTO_NON_PRESENTE_ORDINE,"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ordine ordine = (Ordine) ordiniComboBoxModel.getSelectedItem();
                main.get_cliente_controller().aumenta_quantita_prodotto(ordine,riga_ordine);
                quantitaProdottoLabel.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        diminuisciQuantitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.PRODOTTO_NON_PRESENTE_ORDINE, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ordine ordine = (Ordine) ordiniComboBoxModel.getSelectedItem();
                try {
                    main.get_cliente_controller().diminuisci_quantita_prodotto(ordine,riga_ordine);
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                quantitaProdottoLabel.setText(String.valueOf(riga_ordine.get_quantita()));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Ordine

        creaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indirizzo = indirizzoTextField.getText();
                if(indirizzo.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                main.get_cliente_controller().crea_ordine(indirizzo,ristorante);
                aggiorna_combo_box_ordini();
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione RigheOrdine

        aggiungiAllOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiJList.getSelectedValue();
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Ordine ordine = (Ordine) obj;
                    riga_ordine = main.get_cliente_controller().aggiungi_riga(ordine, prodotto);
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        rimuoviDalOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(riga_ordine == null){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.PRODOTTO_NON_PRESENTE_ORDINE,"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object obj = ordiniComboBox.getSelectedItem();
                if(obj == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Ordine ordine = (Ordine) obj;
                main.get_cliente_controller().rimuovi_riga(ordine,riga_ordine);
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.set_pagina(new RistoranteClienteGui(canvasGui,ristorante));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Aggiornamento Lista

    private void aggiorna_lista_prodotti(){
        ArrayList<Prodotto> prodotti_list = main.get_cliente_controller().get_prodotti(menu);
        if(prodotti_list == null) return;

        for(Prodotto prodotto : prodotti_list)
            prodottListModel.addElement(prodotto);
        System.out.println(prodotti_list + " " + prodottListModel.size());
    }

    private void aggiorna_combo_box_ordini(){
        ordiniComboBoxModel.removeAllElements();
        ArrayList<Ordine> ordini = main.get_cliente_controller().get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniComboBoxModel.addElement(ordine);
        System.out.println(ordini + " " + ordiniComboBoxModel.getSize());
    }

}
