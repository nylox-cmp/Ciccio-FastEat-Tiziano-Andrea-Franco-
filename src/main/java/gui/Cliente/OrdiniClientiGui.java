package gui.Cliente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.ContenutoOrdineGui;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdiniClientiGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel ordiniPanel;
    private JPanel buttonPanelCliente;
    private JPanel contenutoOrdinePanel;
    private JPanel consegnaPanel;
    private JPanel puntiFedeltaPanel;
    private JPanel ContenutoOrdinePanel;

    private JButton applicaScontoButton;
    private JButton annulaOrdineButton;
    private JButton confermaConsegnaButton;
    private JButton rimuoviPrdottoOrdineButton;
    private JButton spedisciOrdineRistorante;


    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniLista;

    private JLabel puntiFedeltaLabel;
    private JLabel quantitaLabel;
    private JTextField puntiFedeltaTextField;
    private JScrollPane ordiniJScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniClientiGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ordiniLista.setModel(ordiniListModel);
        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniLista);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        //________________________________________________________________________________________________________________________________________________
        // ActionListner Gestione Stato Ordine

        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_cliente_controller().conferma_consegna_ordine(ordine);
                aggiorna_ordiniLista(mainGui.get_cliente_controller().get_cliente());
            }
        });

        annulaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_cliente_controller().annulla_ordine(ordine);
                aggiorna_ordiniLista(mainGui.get_cliente_controller().get_cliente());
            }
        });

        spedisciOrdineRistorante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_cliente_controller().cambia_stato_ordine_in_preparazione(ordine);
                aggiorna_ordiniLista(mainGui.get_cliente_controller().get_cliente());
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListner Gestione Punti Fedelta

        applicaScontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Ordine ordine = ordiniLista.getSelectedValue();

            if(ordine == null){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(puntiFedeltaTextField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                int punti_fedelta  = Integer.parseInt(puntiFedeltaTextField.getText());
                ErrorType error = mainGui.get_cliente_controller().applica_sconto(ordine,punti_fedelta);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_ordiniLista(mainGui.get_cliente_controller().get_cliente());
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione ContenutoOrdine

        rimuoviPrdottoOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                RigaOrdine riga_ordine = contenutoOrdine.get_contenutoOrdineLista().getSelectedValue();
                if(ordine == null || riga_ordine == null) return;
                mainGui.get_cliente_controller().rimuovi_riga(ordine,riga_ordine);
                contenutoOrdine.aggiorna_lista(ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione OrdiniLista

    public void aggiorna_ordiniLista(Cliente cliente){
        ordiniListModel.clear();
        ArrayList<Ordine> ordini = cliente.get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniListModel.addElement(ordine);
    }
}
