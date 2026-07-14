package gui.Cliente;

import controller.ClienteController;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ClienteGui extends JPanel {
    private JPanel mainPanel;
    private JScrollPane ristorantiJScrollPane;

    private DefaultListModel<Ristorante> ristoranteListModel = new DefaultListModel<Ristorante>();
    private JList<Ristorante> ristorantiLista;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.set_cliente_controller(new ClienteController());
        mainGui.set_dashboardGui(new DashboardGui(mainGui));

        mainGui.mostra_dashboard();
        mainGui.get_dashboardGui().mostra_area_ordiniClienti();
        mainGui.get_dashboardGui().aggiorna_nickname_label(mainGui);

        ristorantiLista.setModel(ristoranteListModel);

        //________________________________________________________________________________________________________________________________________________
        // LIstSelectionLIstener di Ristorante

        ristorantiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_lista_ristoranti(){

    }
}

