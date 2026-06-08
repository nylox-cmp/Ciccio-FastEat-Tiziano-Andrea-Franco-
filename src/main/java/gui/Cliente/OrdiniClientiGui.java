package gui.Cliente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.*;

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

    private JButton applicaScontoButton;
    private JButton annulaOrdineButton;
    private JButton confermaConsegnaButton;

    private DefaultListModel<RigaOrdine> contenutoOrdineListModel = new DefaultListModel<RigaOrdine>();
    private JList<RigaOrdine> contenutoOrdineLista;
    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniLista;

    private JLabel puntiFedeltaLabel;
    private JLabel quantitaLabel;
    private JTextField puntiFedeltaTextField;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniClientiGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ordiniLista.setModel(ordiniListModel);
        contenutoOrdineLista.setModel(contenutoOrdineListModel);

        applicaScontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Ordine ordine = ordiniLista.getSelectedValue();
            if(ordine == null || puntiFedeltaTextField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int punti_fedelta  = Integer.parseInt(puntiFedeltaTextField.getText());
            mainGui.get_ordine_controller().applica_sconto(ordine,punti_fedelta);
            }
        });

        annulaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_cliente_controller().annulla_ordine(ordine);
            }
        });

        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_cliente_controller().conferma_consegna_ordine(ordine);
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
