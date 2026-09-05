package gui.Cliente;

import exception.ErrorType;
import gui.FrameManagerGui;
import model.Menu;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class RistoranteClienteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel MenuPanel;
    private JPanel buttonPanel;

    private JButton tornaIndietroButton;
    private JButton apriButton;

    private JLabel infoRistoranteClienteLabel;
    private DefaultListModel<Menu> menuListModel = new DefaultListModel<Menu>();
    private JList<Menu> menuJlist;
    private JScrollPane menuScrollPane;
    private JPanel topPanelClienti;

    private FrameManagerGui frameManagerGui;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param frameManagerGui  the canvas gui
     * @param ristorante the ristorante
     */
    public RistoranteClienteGui(FrameManagerGui frameManagerGui, Ristorante ristorante){
        this.frameManagerGui = frameManagerGui;
        this.ristorante = ristorante;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        menuJlist.setModel(menuListModel);
        infoRistoranteClienteLabel.setText(ristorante.toString());
        aggiorna_menu_lista();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener di navigazione

        /**
         * @author Tiziano
         */
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
            }
        });

        /**
         * @author Tiziano
         */
        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = menuJlist.getSelectedValue();
                if(menu == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                frameManagerGui.set_pagina(new MenuClienteGui(frameManagerGui,ristorante,menu));
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Metodo Aggiornamento Lista

    /**
     * @author Tiziano
     * Aggiorna menu lista.
     */
    public void aggiorna_menu_lista(){
        menuListModel.clear();
        ArrayList<model.Menu> menu_list = frameManagerGui.main.get_cliente_controller().get_menu(ristorante);
        if(menu_list == null) return;

        for(Menu menu : menu_list)
            menuListModel.addElement(menu);
    }

}
