package gui.Cliente;

import gui.CanvasGui;
import exception.BusinessError;
import exception.ErrorType;
import gui.customWidget.ContenutoOrdineGui;
import model.Ordine;
import model.RigaOrdine;

import javax.swing.*;
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

    private CanvasGui canvasGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniClientiGui(CanvasGui canvasGui){
        this.canvasGui = canvasGui;
        this.main = canvasGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ordiniLista.setModel(ordiniListModel);
        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniLista,canvasGui);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);
        quantitaLabel.setText(String.valueOf(main.get_cliente_controller().get_cliente().get_punti_fedelta()));

        aggiorna_ordiniLista();

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
                main.get_cliente_controller().conferma_consegna_ordine(ordine);
                aggiorna_ordiniLista();
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
                main.get_cliente_controller().annulla_ordine(ordine);
                aggiorna_ordiniLista();
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
                main.get_cliente_controller().conferma_creazione_ordine(ordine);
                aggiorna_ordiniLista();
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
                try {
                    main.get_cliente_controller().applica_sconto(ordine, punti_fedelta);
                    aggiorna_ordiniLista();

                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO),"Error", JOptionPane.ERROR_MESSAGE);
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
                main.get_cliente_controller().rimuovi_riga(ordine,riga_ordine);
                contenutoOrdine.aggiorna_lista(ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione OrdiniLista

    private void aggiorna_ordiniLista(){
        ordiniListModel.clear();
        ArrayList<Ordine> ordini = main.get_cliente_controller().get_ordini();

        for(Ordine ordine : ordini)
            ordiniListModel.addElement(ordine);
    }
}
