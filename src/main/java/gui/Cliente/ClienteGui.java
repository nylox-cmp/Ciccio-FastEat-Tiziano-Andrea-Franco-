package gui.Cliente;

import controller.ClienteController;
import exception.ErrorType;
import gui.CanvasGui;
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

    private CanvasGui canvasGui;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteGui(CanvasGui canvasGui){
        this.canvasGui = canvasGui;
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        if(canvasGui.main.get_utente_controller().utente_is_cliente() == false){
            canvasGui.main.get_utente_controller().load_dati_cliente();
            if(canvasGui.main.get_utente_controller().utente_is_cliente() == false)
                canvasGui.main.get_utente_controller().registra_cliente();
        }

        canvasGui.main.set_cliente_controller(new ClienteController());
        canvasGui.set_dashboardGui(new DashboardGui(canvasGui));

        canvasGui.mostra_dashboard();
        canvasGui.get_dashboardGui().mostra_area_ordiniClienti();
        canvasGui.get_dashboardGui().aggiorna_nickname_label(canvasGui);

        ristorantiLista.setModel(ristoranteListModel);
        aggiorna_lista_ristoranti(get_search_text());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiorna_lista_ristoranti(get_search_text());
            }
        });

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante ristorante = ristorantiLista.getSelectedValue();
                if(ristorante == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                canvasGui.set_pagina(new RistoranteClienteGui(canvasGui,ristorante));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________

    private void aggiorna_lista_ristoranti(String search){
        ristoranteListModel.clear();
        ArrayList<Ristorante> ristoranti = canvasGui.main.get_cliente_controller().get_ristoranti(search);
        if(ristoranti == null) return;

        for(Ristorante ristorante : ristoranti)
            ristoranteListModel.addElement(ristorante);
    }

    private String get_search_text(){
        String search = searchTextField.getText();
        if(search.isEmpty()) search = "";
        return search;
    }
}

