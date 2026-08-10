package gui.Rider;

import gui.CanvasGui;
import exception.BusinessError;
import exception.ErrorType;
import gui.customWidget.ContenutoOrdineGui;
import model.Ordine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdiniRiderGui extends JPanel{
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel ordiniInCaricoPanel;
    private JPanel buttonPanel;
    private JPanel richiestaOrdiniPanel;
    private JPanel richiestaButtonPanel;
    private JPanel contenutoOrdinePanel;

    private DefaultListModel<Ordine> ordiniDaConsegnareListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniDaConsegnareLista;
    private DefaultListModel<Ordine> ordiniPropostiListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniPropostiLista;

    private JButton creaRichiestaOrdineButton;
    private JButton confermaConsegnaButton;
    private JButton cancellaRichiestaOrdineButton;

    private CanvasGui canvasGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniRiderGui(CanvasGui canvasGui){
        this.canvasGui = canvasGui;
        this.main = canvasGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniDaConsegnareLista,canvasGui);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        ordiniDaConsegnareLista.setModel(ordiniDaConsegnareListModel);
        ordiniPropostiLista.setModel(ordiniPropostiListModel);

        aggiorna_OrdiniDaConsegnare();
        aggiorna_OrdiniProposti();

        //________________________________________________________________________________________________________________________________________________
        // ListSelectionListener per poter visualizzare il cotenuto del OrdineProposto

        ordiniPropostiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                contenutoOrdine.aggiorna_lista(ordine);
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener che Gestiscono lo StatoOrdine

        creaRichiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    main.get_rider_controller().crea_richiesta_approvazione_consegna(ordine);
                    aggiorna_OrdiniProposti();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        cancellaRichiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniDaConsegnareLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try{
                    main.get_rider_controller().cancella_richiesta_approvazione_consegna(ordine);
                    aggiorna_OrdiniDaConsegnare();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniDaConsegnareLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                main.get_rider_controller().conferma_consegna_ordine(ordine);
                aggiorna_OrdiniDaConsegnare();
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione ListeOrdini

    public void aggiorna_OrdiniProposti(){
        ordiniPropostiListModel.clear();
        ArrayList<Ordine> ordini = main.get_rider_controller().get_ordini_proposti();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniPropostiListModel.addElement(ordine);
    }

    public void aggiorna_OrdiniDaConsegnare(){
        ordiniDaConsegnareListModel.clear();
        ArrayList<Ordine> ordini = main.get_rider_controller().get_ordini_da_consegnare();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
                ordiniDaConsegnareListModel.addElement(ordine);
    }

}

