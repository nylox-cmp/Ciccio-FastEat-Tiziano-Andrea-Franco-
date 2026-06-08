package gui.Rider;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ordine;
import model.RigaOrdine;

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
    private JPanel contenutoOrdinePanel;
    private JPanel ordiniInCaricoPanel;
    private JPanel buttonPanel;
    private JPanel richiestaOrdiniPanel;
    private JPanel richiestaButtonPanel;

    private DefaultListModel<RigaOrdine> contenutoOrdineListModel = new DefaultListModel<RigaOrdine>();
    private JList<RigaOrdine> contenutoOrdineLista;
    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniLista;
    private DefaultListModel<Ordine> ordiniPropostiListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniPropostiLista;

    private JButton richiestaOrdineButton;
    private JButton confermaConsegnaButton;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniRiderGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ordiniLista.setModel(ordiniListModel);
        contenutoOrdineLista.setModel(contenutoOrdineListModel);
        ordiniPropostiLista.setModel(ordiniPropostiListModel);

        //aggiorna ordini proposti

        ordiniPropostiLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Ordine ordine_proposto = ordiniPropostiLista.getSelectedValue();
                aggiorna_contenuto_ordine_lista(ordine_proposto);
            }
        });

        ordiniLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Ordine ordine_proposto = ordiniLista.getSelectedValue();
                aggiorna_contenuto_ordine_lista(ordine_proposto);
            }
        });

        richiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_rider_controller().richiedi_approvazzione_consegna(ordine);
            }
        });

        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_rider_controller().conferma_consegna_ordine(ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_contenuto_ordine_lista(Ordine ordine){
        contenutoOrdineListModel.clear();
        ArrayList<RigaOrdine> righe_ordine = ordine.get_rige_ordine();
        if(righe_ordine == null) return;

        for(RigaOrdine riga_ordine : righe_ordine)
            contenutoOrdineListModel.addElement(riga_ordine);
    }
}
