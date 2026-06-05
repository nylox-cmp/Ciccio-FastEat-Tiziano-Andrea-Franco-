package gui.Rider;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ordine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdiniRiderGui extends JPanel{
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel contenutoOrdinePanel;
    private JPanel ordiniInCaricoPanel;
    private JPanel buttonPanel;
    private JPanel richiestaOrdiniPanel;
    private JPanel richiestaButtonPanel;

    private JList<Package> contenutoOrdineLista;
    private JList<Ordine> ordiniLista;
    private JList<Ordine> ordiniPropostiLista;

    private JButton richiestaOrdineButton;
    private JButton confermaConsegnaButton;


    public OrdiniRiderGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_rider(mainGui);

        richiestaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniPropostiLista.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_rider_controller().conferma_consegna_ordine(ordine);
            }
        });
    }
}
