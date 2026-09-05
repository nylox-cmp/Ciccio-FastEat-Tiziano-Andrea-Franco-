package gui.customWidget;

import controller.DipendenteController;
import controller.RiderController;
import controller.RistoranteController;
import exception.BusinessError;
import gui.Autenticazione.LoginGui;
import gui.Cliente.ClienteGui;
import gui.Dipedente.DipedenteGui;
import gui.Dipedente.OrdiniDipedenteGui;
import gui.FrameManagerGui;
import gui.Rider.OrdiniRiderGui;
import gui.Rider.RiderGui;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Dashboard gui.
 */
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

    /**
     * Instantiates a new Dashboard gui.
     *
     * @param frameManagerGui the canvas gui
     */
    public DashboardGui(FrameManagerGui frameManagerGui){
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_nickname_label(frameManagerGui);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Transazione Gui

        areaClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                frameManagerGui.nascondi_dashbaord(frameManagerGui.get_dashboardDipedenteGui());
                frameManagerGui.mostra_dahsboard(frameManagerGui.get_dashboardClienteGui());
                frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
            }
        });

       areaRiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManagerGui.nascondi_dashbaord(frameManagerGui.get_dashboardDipedenteGui());
                frameManagerGui.nascondi_dashbaord(frameManagerGui.get_dashboardClienteGui());

                if(main.get_utente_controller().utente_is_rider() == false){
                    main.get_utente_controller().load_dati_rider();

                    if(main.get_utente_controller().utente_is_rider() == false) {
                        frameManagerGui.set_pagina(new RiderGui(frameManagerGui));
                        return;
                    }
                    else{
                        main.set_rider_controller(new RiderController());
                    }
                }

                frameManagerGui.set_pagina(new OrdiniRiderGui(frameManagerGui));
            }
        });

        areaDipendentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManagerGui.nascondi_dashbaord(frameManagerGui.get_dashboardClienteGui());

                if(main.get_utente_controller().utente_is_dipendente() == false){
                    main.get_utente_controller().load_dati_dipedente();

                    if(main.get_utente_controller().utente_is_dipendente() == false) {
                        frameManagerGui.set_pagina(new DipedenteGui(frameManagerGui));
                        return;
                    }
                    else{
                        main.set_dipendente_controller(new DipendenteController());
                        main.set_ristorante_controller(new RistoranteController(main.get_dipendente_controller()));
                        frameManagerGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(frameManagerGui,main.get_dipendente_controller().get_dipendente()));
                    }
                }

                frameManagerGui.mostra_dahsboard(frameManagerGui.get_dashboardDipedenteGui());
                frameManagerGui.set_pagina(new OrdiniDipedenteGui(frameManagerGui));
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Account Utente

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.get_utente_controller().logout();
                main.distruggi_controller();

                frameManagerGui.distruggi_all_dashbaord();
                frameManagerGui.set_pagina(new LoginGui(frameManagerGui));
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
                    frameManagerGui.distruggi_all_dashbaord();
                    frameManagerGui.set_pagina(new LoginGui(frameManagerGui));
                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Label

    /**
     * Aggiorna nickname label.
     *
     * @param frameManagerGui the canvas gui
     */
    public void aggiorna_nickname_label(FrameManagerGui frameManagerGui){
        infoUtenteLabel.setText(main.get_utente_controller().get_utente().toString());
    }

}
