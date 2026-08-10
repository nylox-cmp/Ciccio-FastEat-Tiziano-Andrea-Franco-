package gui.Cliente;

import exception.ErrorType;
import gui.CanvasGui;
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

    private CanvasGui canvasGui;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteClienteGui(CanvasGui canvasGui, Ristorante ristorante){
        this.canvasGui = canvasGui;
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
                canvasGui.set_pagina(new ClienteGui(canvasGui));
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
                canvasGui.set_pagina(new MenuClienteGui(canvasGui,ristorante,menu));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Lista

    public void aggiorna_menu_lista(){
        menuListModel.clear();
        ArrayList<model.Menu> menu_list = canvasGui.main.get_cliente_controller().get_menu(ristorante);
        if(menu_list == null) return;

        for(Menu menu : menu_list)
            menuListModel.addElement(menu);
    }

}
