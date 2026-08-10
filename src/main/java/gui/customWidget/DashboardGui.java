package gui.customWidget;

import controller.DipendenteController;
import controller.RiderController;
import controller.RistoranteController;
import gui.CanvasGui;
import gui.Autenticazione.LoginGui;
import gui.Cliente.ClienteGui;
import gui.Cliente.OrdiniClientiGui;
import gui.Dipedente.DipedenteGui;
import gui.Dipedente.OrdiniDipedenteGui;
import gui.Rider.OrdiniRiderGui;
import gui.Rider.RiderGui;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardGui extends JPanel {
    private JPanel mainPanel;
    private JPanel dashboardPanel;

    private JButton areaClientiButton;
    private JButton areaRiderButton;
    private JButton areaDipendentiButton;
    private JButton areaOrdiniButton;
    private JButton logoutButton;
    private JButton cancellaAccountButton;

    private JLabel infoUtenteLabel;

    private Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DashboardGui(CanvasGui canvasGui){
        this.main = canvasGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_nickname_label(canvasGui);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Transazione Gui

        areaClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostra_area_ordiniClienti();
                canvasGui.nascondi_dashboard_dipedenti();
                canvasGui.set_pagina(new ClienteGui(canvasGui));
            }
        });

       areaRiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(main.get_utente_controller().utente_is_rider() == false){
                    main.get_utente_controller().load_dati_rider();

                    if(main.get_utente_controller().utente_is_rider() == false) {
                        canvasGui.set_pagina(new RiderGui(canvasGui));
                        return;
                    }
                    else{
                        main.set_rider_controller(new RiderController());
                    }
                }
                nascondi_area_OrdiniClienti();
                canvasGui.nascondi_dashboard_dipedenti();
                canvasGui.set_pagina(new OrdiniRiderGui(canvasGui));
            }
        });

        areaDipendentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(main.get_utente_controller().utente_is_dipendente() == false){
                    main.get_utente_controller().load_dati_dipedente();

                    if(main.get_utente_controller().utente_is_dipendente() == false) {
                        canvasGui.set_pagina(new DipedenteGui(canvasGui));
                        return;
                    }
                    else{
                        canvasGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(canvasGui));
                        main.set_dipendente_controller(new DipendenteController());
                        main.set_ristorante_controller(new RistoranteController(main.get_dipendente_controller()));
                    }
                }

                nascondi_area_OrdiniClienti();
                canvasGui.mostra_dashboard_dipedenti(main.get_dipendente_controller().get_dipendente().get_ristorante());
                canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
            }
        });

        areaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.set_pagina(new OrdiniClientiGui(canvasGui));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Account Utente

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.get_utente_controller().logout();
                main.distruggi_controller();

                canvasGui.nascondi_dashboard();
                canvasGui.nascondi_dashboard_dipedenti();
                canvasGui.set_pagina(new LoginGui(canvasGui));
            }
        });

        cancellaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainPanel, "sei sicuro di volere eliminare l'account? l'eliminazione dell'account comportera la cancellazione dei ristoranti in cui sei Manager e licenzimento dei dipedenti ", "FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    main.get_utente_controller().cancella_account();
                    main.distruggi_controller();

                    canvasGui.nascondi_dashboard();
                    canvasGui.nascondi_dashboard_dipedenti();
                    canvasGui.set_pagina(new LoginGui(canvasGui));
                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Label

    public void aggiorna_nickname_label(CanvasGui canvasGui){
        infoUtenteLabel.setText(main.get_utente_controller().get_utente().toString());
    }

    public void mostra_area_ordiniClienti(){
        areaOrdiniButton.setVisible(true);
    }
    public void nascondi_area_OrdiniClienti(){
        areaOrdiniButton.setVisible(false);
    }

}
