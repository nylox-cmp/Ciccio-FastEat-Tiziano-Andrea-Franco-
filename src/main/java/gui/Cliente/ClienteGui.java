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
    private JList<Ristorante> ristorantiLista;

    public ClienteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);
        mainGui.set_cliente_contrller(new ClienteController());



        ristorantiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                mainGui.set_pagina(new RistoranteClienteGui(mainGui,ristorante));
            }
        });
    }
}

