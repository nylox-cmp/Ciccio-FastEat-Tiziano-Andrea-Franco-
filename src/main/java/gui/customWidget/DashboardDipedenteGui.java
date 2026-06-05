package gui.customWidget;

import exception.ErrorType;
import gui.Dipedente.*;
import gui.MainGui;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardDipedenteGui extends JPanel{
    private JPanel mainPanel;

    private JComboBox areaGestioneComboBox;
    private JButton tornaIndietroButton;
    private JButton apriButton;
    private JPanel dashboardPanel;
    private JLabel tittoloLabel;

    public DashboardDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        tornaIndietroButton.setVisible(false);
        apriButton.setVisible(false);

        areaGestioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = areaGestioneComboBox.getSelectedItem();
                String area = (String) item;
                switch (area){
                    case "Ordini":
                        mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
                        break;
                    case "Ristorante":
                        mainGui.set_pagina(new RistoranteDipedenteGui(mainGui));
                        break;
                    case "Dipedenti":
                        mainGui.set_pagina(new GestioneDipedenteGui(mainGui));
                        break;
                }
            }
        });
    }

    public void aggiungi_pulsanti_ristorante(MainGui maingui, Ristorante ristorante,Menu menu){
        tornaIndietroButton.setVisible(true);
        apriButton.setVisible(true);

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maingui.set_pagina( new OrdiniDipedenteGui(maingui));
            }
        });

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(menu == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                maingui.set_pagina(new MenuDipedentiGui(maingui,ristorante,menu));
            }
        });
    }

    public void aggiungi_pulsanti_menu(MainGui mainGui, Ristorante ristorante, Menu menu, Prodotto prodotto){
        tornaIndietroButton.setVisible(true);
        apriButton.setVisible(true);

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina( new RistoranteDipedenteGui(mainGui));
            }
        });

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina( new ProdottoDipedenteGui(mainGui,ristorante,menu,prodotto));
            }
        });
    }

    }