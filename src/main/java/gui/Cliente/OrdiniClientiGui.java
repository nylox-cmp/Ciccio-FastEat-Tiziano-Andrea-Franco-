package gui.Cliente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardGui;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private JList<Prodotto> contenutoOrdineLista;
    private JList<Ordine> ordiniLista;

    private JLabel puntiFedeltaLabel;
    private JLabel quantitaLabel;
    private JTextField puntiFedeltaTextField;

    public OrdiniClientiGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_cliente(mainGui);

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
}
