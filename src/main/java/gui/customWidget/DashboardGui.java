package gui.customWidget;

import exception.ErrorType;
import gui.Autenticazione.LoginGui;
import gui.Cliente.ClienteGui;
import gui.Cliente.OrdiniClientiGui;
import gui.Dipedente.DipedenteGui;
import gui.Dipedente.OrdiniDipedenteGui;
import gui.MainGui;
import gui.Rider.OrdiniRiderGui;
import gui.Rider.RiderGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardGui extends JPanel {
    private JPanel mainPanel;
    private JButton areaClientiButton;
    private JButton areaRiderButton;
    private JButton areaDipedentiButton;
    private JButton areaOrdiniButton;
    private JButton logoutButton;
    private JPanel dashboardPanel;

    public DashboardGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        areaClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        areaRiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new RiderGui(mainGui));
            }
        });

        areaDipedentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new DipedenteGui(mainGui));
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.get_utente_controller().logout();
                mainGui.set_pagina(new LoginGui(mainGui));
            }
        });
    }

    public void aggiungi_action_ordini_cliente(MainGui mainGui){
        areaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new OrdiniClientiGui(mainGui));
            }
        });
    }

    public void aggiungi_action_ordini_rider(MainGui mainGui){
        areaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainGui.get_rider_controller().utente_is_rider() == false){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new OrdiniRiderGui(mainGui));
            }
        });
    }

    public void aggiungi_action_ordini_dipedente(MainGui mainGui){
        areaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainGui.get_dipedente_controller().utente_is_dipedente() == false){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
            }
        });
    }

}
