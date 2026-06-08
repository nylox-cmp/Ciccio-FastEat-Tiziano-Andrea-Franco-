package gui.Cliente;

import com.sun.tools.javac.Main;
import controller.ClienteController;
import gui.MainGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ClienteGui extends JPanel {
    private JPanel mainPanel;

    private DefaultListModel<Ristorante> ristoranteListModel = new DefaultListModel<Ristorante>();
    private JList<Ristorante> ristorantiLista;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.mostra_dashboard();
        mainGui.set_dashboardGui(new DashboardGui(mainGui));
        mainGui.get_dashboardGui().mostra_area_ordini();
        mainGui.get_dashboardGui().aggiorna_nickname_label(mainGui);

        ristorantiLista.setModel(ristoranteListModel);

        ristorantiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_lista_ristoranti(){

    }
}

