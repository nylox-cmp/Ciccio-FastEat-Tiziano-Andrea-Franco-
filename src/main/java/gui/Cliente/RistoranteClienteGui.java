package gui.Cliente;

import gui.MainGui;
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
    private DefaultListModel<Menu> menuListModel = new DefaultListModel<Menu>();
    private JList<Menu> menuClientiLista;
    private JScrollPane menuScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteClienteGui(MainGui mainGui, Ristorante ristorante){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        infoRistoranteClienteLabel.setText(ristorante.toString());

        tornaIndietroClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        menuClientiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Menu menu = menuClientiLista.getSelectedValue();
                if(menu != null)
                    mainGui.set_pagina(new MenuClienteGui(mainGui,ristorante,menu));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________

}
