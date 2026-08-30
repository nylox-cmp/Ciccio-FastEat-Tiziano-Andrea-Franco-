package gui.customWidget;

import controller.DipendenteController;
import controller.RiderController;
import controller.RistoranteController;
import exception.BusinessError;
import exception.ErrorType;
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
            public void actionPerformed(ActionEvent e){
                canvasGui.nascondi_dashbaord(canvasGui.get_dashboardDipedenteGui());
                canvasGui.mostra_dahsboard(canvasGui.get_dashboardClienteGui());
                canvasGui.set_pagina(new ClienteGui(canvasGui));
            }
        });

       areaRiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.nascondi_dashbaord(canvasGui.get_dashboardDipedenteGui());
                canvasGui.nascondi_dashbaord(canvasGui.get_dashboardClienteGui());

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

                canvasGui.set_pagina(new OrdiniRiderGui(canvasGui));
            }
        });

        areaDipendentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasGui.nascondi_dashbaord(canvasGui.get_dashboardClienteGui());

                if(main.get_utente_controller().utente_is_dipendente() == false){
                    main.get_utente_controller().load_dati_dipedente();

                    if(main.get_utente_controller().utente_is_dipendente() == false) {
                        canvasGui.set_pagina(new DipedenteGui(canvasGui));
                        return;
                    }
                    else{
                        main.set_dipendente_controller(new DipendenteController());
                        main.set_ristorante_controller(new RistoranteController(main.get_dipendente_controller()));
                        canvasGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(canvasGui,main.get_dipendente_controller().get_dipendente()));
                    }
                }

                canvasGui.mostra_dahsboard(canvasGui.get_dashboardDipedenteGui());
                canvasGui.set_pagina(new OrdiniDipedenteGui(canvasGui));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Account Utente

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.get_utente_controller().logout();
                main.distruggi_controller();

                canvasGui.distruggi_all_dashbaord();
                canvasGui.set_pagina(new LoginGui(canvasGui));
            }
        });

        cancellaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int scelta = JOptionPane.showConfirmDialog(mainPanel, "sei sicuro di volere eliminare l'account? l'eliminazione dell'account comportera la cancellazione dei ristoranti in cui sei Manager e licenzimento dei dipedenti ", "FoodDelivery", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(scelta == JOptionPane.YES_OPTION) {
                    try {
                        main.get_utente_controller().cancella_account();
                    }
                    catch (BusinessError error){
                        JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    main.distruggi_controller();
                    canvasGui.distruggi_all_dashbaord();
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

}
