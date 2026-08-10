package gui.Cliente;

import exception.ErrorType;
import gui.MainGui;
import model.Menu;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RistoranteClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JPanel topPanelClienti;
    private JPanel buttonPanel;

    private JButton tornaIndietroButton;
    private JButton apriButton;

    private JLabel infoRistoranteClienteLabel;
    private DefaultListModel<Menu> menuListModel = new DefaultListModel<Menu>();
    private JList<Menu> menuLista;
    private JScrollPane menuScrollPane;

    private MainGui mainGui;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteClienteGui(MainGui mainGui, Ristorante ristorante){
        this.mainGui = mainGui;
        this.ristorante = ristorante;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        infoRistoranteClienteLabel.setText(ristorante.toString());
        aggiorna_menu_lista();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = menuLista.getSelectedValue();
                if(menu == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new MenuClienteGui(mainGui,ristorante,menu));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Lista

    public void aggiorna_menu_lista(){
        menuListModel.clear();
        ArrayList<model.Menu> menu_list = mainGui.get_cliente_controller().get_menu(ristorante);
        if(menu_list == null) return;

        for(Menu menu : menu_list)
            menuListModel.addElement(menu);
    }

}
