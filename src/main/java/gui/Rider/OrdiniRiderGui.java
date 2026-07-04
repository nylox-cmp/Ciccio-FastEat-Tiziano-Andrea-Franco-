package gui.Rider;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.ContenutoOrdineGui;
import model.Ordine;
import model.Rider;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdiniRiderGui extends JPanel{
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel ordiniInCaricoPanel;
    private JPanel buttonPanel;
    private JPanel richiestaOrdiniPanel;
    private JPanel richiestaButtonPanel;
    private JPanel contenutoOrdinePanel;

    private DefaultListModel<Ordine> ordiniDaConsegnareListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniDaConsegnareLista;
    private DefaultListModel<Ordine> ordiniPropostiListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniPropostiLista;

    private JButton richiestaOrdineButton;
    private JButton confermaConsegnaButton;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniRiderGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniDaConsegnareLista);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        ordiniDaConsegnareLista.setModel(ordiniDaConsegnareListModel);
        ordiniPropostiLista.setModel(ordiniPropostiListModel);

        mainGui.get_dashboardGui().nascondi_area_OrdiniClienti();
        mainGui.get_dashboardGui().mostra_pagaRider();
        mainGui.get_dashboardGui().aggiorna_pagaRider(mainGui);

        //________________________________________________________________________________________________________________________________________________
        // ListSelectionListener per poter visualizzare il cotenuto del OrdineProposto

        ordiniPropostiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                contenutoOrdine.aggiorna_lista(ordine);
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener che Gestiscono lo StatoOrdine

        richiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = mainGui.get_rider_controller().richiedi_approvazzione_consegna(ordine);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_OrdiniProposti(mainGui.get_rider_controller().get_rider());
            }
        });

        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniDaConsegnareLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_rider_controller().conferma_consegna_ordine(ordine);
                aggiorna_OrdiniDaConsegnare(mainGui.get_rider_controller().get_rider());
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione ListeOrdini

    public void aggiorna_OrdiniProposti(Rider rider){
        ArrayList<Ordine> ordini = rider.get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniPropostiListModel.addElement(ordine);
    }

    public void aggiorna_OrdiniDaConsegnare(Rider rider){
        ArrayList<Ordine> ordini = rider.get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini) {
            if(ordine.get_rider() == rider)
                ordiniPropostiListModel.addElement(ordine);
        }
    }

}

