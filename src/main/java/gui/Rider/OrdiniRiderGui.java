package gui.Rider;

import exception.BusinessError;
import exception.ErrorType;
import gui.FrameManagerGui;
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
    private JButton segnalaOrdineInConsegnaButton;

    private FrameManagerGui frameManagerGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param frameManagerGui the canvas gui
     */
    public OrdiniRiderGui(FrameManagerGui frameManagerGui){
        this.frameManagerGui = frameManagerGui;
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniDaConsegnareLista, frameManagerGui);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        ordiniDaConsegnareLista.setModel(ordiniDaConsegnareListModel);
        ordiniPropostiLista.setModel(ordiniPropostiListModel);

        aggiorna_OrdiniDaConsegnare();
        aggiorna_OrdiniProposti();

        //________________________________________________________________________________________________________________________________________________
        // ListSelectionListener per poter visualizzare il cotenuto del OrdineProposto

        /**
         * @author Tiziano
         */
        ordiniPropostiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null) return;
                contenutoOrdine.aggiorna_lista(ordine);
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Richieste Consegna

        /**
         * @author Tiziano
         */
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
                }
            }
        });

        /**
         * @author Tiziano
         */
        cancellaRichiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try{
                    main.get_rider_controller().cancella_richiesta_approvazione_consegna(ordine);
                    aggiorna_OrdiniProposti();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione StatoOrdine OrdiniDaConsegnare

        /**
         * @author Tiziano
         */
        segnalaOrdineInConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniDaConsegnareLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try{
                    main.get_rider_controller().segnala_ordine_as_in_consegna(ordine);
                    aggiorna_OrdiniDaConsegnare();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * @author Tiziano
         */
        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniDaConsegnareLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    main.get_rider_controller().conferma_consegna_ordine(ordine);
                    aggiorna_OrdiniDaConsegnare();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione ListeOrdini

    /**
     * @author Tiziano
     * Aggiorna ordini proposti.
     */
    public void aggiorna_OrdiniProposti(){
        ordiniPropostiListModel.clear();
        ArrayList<Ordine> ordini = main.get_rider_controller().get_ordini_proposti();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniPropostiListModel.addElement(ordine);
    }

    /**
     * @łauthor Tiziano
     * Aggiorna ordini da consegnare.
     */
    public void aggiorna_OrdiniDaConsegnare(){
        ordiniDaConsegnareListModel.clear();
        ArrayList<Ordine> ordini = main.get_rider_controller().get_ordini_da_consegnare();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
                ordiniDaConsegnareListModel.addElement(ordine);
    }

}

