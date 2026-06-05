package gui.Cliente;

import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RistoranteClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JPanel topPanelClienti;
    private JButton tornaIndietroClientiButton;
    private JLabel infoRistoranteClienteLabel;
    private JList<Menu> menuClientiLista;

    public RistoranteClienteGui(MainGui mainGui, Ristorante ristorante){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);

        tornaIndietroClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        infoRistoranteClienteLabel.setText(ristorante.toString());

        menuClientiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Menu menu = menuClientiLista.getSelectedValue();
                mainGui.set_pagina(new MenuClienteGui(mainGui,ristorante,menu));
            }
        });

    }
}
