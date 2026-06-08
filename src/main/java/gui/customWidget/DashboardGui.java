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
    private JPanel dashboardPanel;

    private JButton areaClientiButton;
    private JButton areaRiderButton;
    private JButton areaDipedentiButton;
    private JButton areaOrdiniButton;
    private JButton logoutButton;

    private JLabel infoUtente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DashboardGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_nickname_label(mainGui);

        areaClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.nascondi_dashboard_dipedenti();
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        areaRiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.nascondi_dashboard_dipedenti();
                if(mainGui.get_utente_controller().utente_is_rider()){
                    mainGui.set_pagina(new OrdiniRiderGui(mainGui));
                    return;
                }
                mainGui.set_pagina(new RiderGui(mainGui));
            }
        });

        areaDipedentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainGui.get_utente_controller().utente_is_dipedente()){
                    mainGui.get_dashboardDipedenteGui().aggiorna_ruolo_label(mainGui);
                    mainGui.set_pagina(new OrdiniDipedenteGui(mainGui));
                    return;
                }
                mainGui.set_pagina(new DipedenteGui(mainGui));
            }
        });

        areaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new OrdiniClientiGui(mainGui));
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainGui.nascondi_dashboard();
                mainGui.nascondi_dashboard_dipedenti();
                mainGui.set_pagina(new LoginGui(mainGui));
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_nickname_label(MainGui mainGui){
        infoUtente.setText(mainGui.get_utente_controller().get_utente().toString());
    }

    public void mostra_area_ordini(){
        areaOrdiniButton.setVisible(true);
    }

    public void nascondi_area_ordini(){
        areaOrdiniButton.setVisible(false);
    }
}
