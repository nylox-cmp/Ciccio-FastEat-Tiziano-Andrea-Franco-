package gui.Cliente;

import controller.ClienteController;
import exception.ErrorType;
import gui.FrameManagerGui;
import gui.customWidget.DashboardClienteGui;
import gui.customWidget.DashboardGui;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ClienteGui extends JPanel {
    private JPanel mainPanel;

    private DefaultListModel<Ristorante> ristoranteListModel = new DefaultListModel<Ristorante>();
    private JList<Ristorante> ristorantiLista;
    private JButton apriButton;
    private JPanel ristorantiPanel;
    private JPanel buttonPanel;
    private JScrollPane ristorantiJScrollPane;
    private JPanel ricercaPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel nomeIndrizzoLabel;

    private FrameManagerGui frameManagerGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     * @param frameManagerGui
     */
    public ClienteGui(FrameManagerGui frameManagerGui){
        this.frameManagerGui = frameManagerGui;
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        if(main.get_utente_controller().utente_is_cliente() == false){
            main.get_utente_controller().load_dati_cliente();
            if(main.get_utente_controller().utente_is_cliente() == false)
                main.get_utente_controller().registra_cliente();
        }
        main.set_cliente_controller(new ClienteController());

        frameManagerGui.set_dashboardGui(new DashboardGui(frameManagerGui));
        frameManagerGui.set_dashboardClienteGui(new DashboardClienteGui(frameManagerGui));

        frameManagerGui.get_dashboardGui().aggiorna_nickname_label(frameManagerGui);
        frameManagerGui.get_dashboardClienteGui().aggiorna_punti_fedelta_label();

        ristorantiLista.setModel(ristoranteListModel);
        aggiorna_lista_ristoranti(get_search_text());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        /**
         * @author Tiziano
         */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiorna_lista_ristoranti(get_search_text());
            }
        });

        /**
         * @author Tiziano
         */
        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                if(ristorante == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                frameManagerGui.set_pagina(new RistoranteClienteGui(frameManagerGui,ristorante));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________

    /**
     * @author Tiziano
     * @param search
     */
    private void aggiorna_lista_ristoranti(String search){
        ristoranteListModel.clear();
        ArrayList<Ristorante> ristoranti = main.get_cliente_controller().get_ristoranti(search);
        if(ristoranti == null) return;

        for(Ristorante ristorante : ristoranti)
            ristoranteListModel.addElement(ristorante);
    }

    /**
     * @author Tiziano
     * @return
     */
    private String get_search_text(){
        String search = searchTextField.getText();
        if(search.isEmpty()) search = "";
        return search;
    }
}

