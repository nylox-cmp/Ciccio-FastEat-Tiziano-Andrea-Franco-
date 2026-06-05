package gui.Dipedente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.Ordine;
import model.Rider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdiniDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel riderPropostiPanel;
    private JPanel riderPropostiButtonPanel;
    private JPanel ordiniDipedentiPanel;
    private JPanel ordiniDipedentiButtonPanel;

    private JButton cancellaButton;
    private JButton senglaProntoAlRitiroButton;
    private JButton rifiutaButton;
    private JButton accettaButton;

    private JList<Ordine> ordiniLista;
    private JList<Rider> riderPropostiOrdineLista;

    public OrdiniDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_dipedente(mainGui);

        accettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiOrdineLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipedente_controller().accetta_rider(ordine,rider);
            }
        });

        rifiutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiOrdineLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipedente_controller().rifiuta_rider(ordine,rider);
            }
        });

        cancellaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if (ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipedente_controller().anulla_ordine(ordine);
            }
        });

        senglaProntoAlRitiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if (ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = mainGui.get_dipedente_controller().segnala_ordine_pronto_ritiro(ordine);
                if (error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
