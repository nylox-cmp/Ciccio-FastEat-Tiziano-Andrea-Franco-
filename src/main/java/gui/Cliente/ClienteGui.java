package gui.Cliente;

import controller.ClienteController;
import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.RigaOrdine;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClienteGui extends JPanel {
    private JPanel mainPanel;

    private DefaultListModel<Ristorante> ristoranteListModel = new DefaultListModel<Ristorante>();
    private JList<Ristorante> ristorantiLista;
    private JScrollPane ristorantiJScrollPane;
    private JPanel buttonPanel;
    private JButton apriButton;

    private MainGui mainGui;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteGui(MainGui mainGui){
        this.mainGui = mainGui;
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        if(mainGui.get_utente_controller().utente_is_cliente() == false){
            mainGui.get_utente_controller().load_dati_cliente();
            if(mainGui.get_utente_controller().utente_is_cliente() == false)
                mainGui.get_utente_controller().registra_cliente();
        }

        mainGui.set_cliente_controller(new ClienteController());
        mainGui.set_dashboardGui(new DashboardGui(mainGui));


        mainGui.mostra_dashboard();
        mainGui.get_dashboardGui().mostra_area_ordiniClienti();
        mainGui.get_dashboardGui().aggiorna_nickname_label(mainGui);

        ristorantiLista.setModel(ristoranteListModel);
        aggiorna_lista_ristoranti();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                if(ristorante == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_lista_ristoranti(){
        ristoranteListModel.clear();
        ArrayList<Ristorante> ristoranti = mainGui.get_cliente_controller().get_ristoranti();
        if(ristoranti == null) return;

        for(Ristorante ristorante : ristoranti)
            ristoranteListModel.addElement(ristorante);
    }
}

