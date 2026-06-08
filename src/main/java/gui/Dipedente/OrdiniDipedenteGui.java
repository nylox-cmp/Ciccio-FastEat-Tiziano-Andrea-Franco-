package gui.Dipedente;

import controller.DipedenteController;
import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardDipedenteGui;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdiniDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel riderPropostiPanel;
    private JPanel riderPropostiButtonPanel;
    private JPanel ordiniPanel;
    private JPanel ordiniDipedentiButtonPanel;

    private JButton cancellaButton;
    private JButton senglaProntoAlRitiroButton;
    private JButton rifiutaButton;
    private JButton accettaButton;

    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniLista;
    private DefaultListModel<Rider> riderPropostiListModel = new DefaultListModel<Rider>();
    private JList<Rider> riderPropostiLista;
    private DefaultListModel<RigaOrdine> contenutoOrdineModelList = new DefaultListModel<RigaOrdine>();
    private JList<RigaOrdine> contenutoOrdineLista;
    private JScrollPane riderPropostiJScrollPane;
    private JScrollPane ordiniJScrollPane;
    private JScrollPane contenutoOrdineJScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(mainGui));
        mainGui.set_dipedente_controller(new DipedenteController(mainGui.get_utente_controller()));
        mainGui.mostra_dashboard_dipedenti(mainGui.get_dipedente_controller().get_dipedente().get_ristorante());

        ordiniLista.setModel(ordiniListModel);
        contenutoOrdineLista.setModel(contenutoOrdineModelList);
        riderPropostiLista.setModel(riderPropostiListModel);

        aggiorna_ordini_lista(mainGui.get_dipedente_controller().get_dipedente().get_ristorante());


        ordiniLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                Ordine ordine = ordiniLista.getSelectedValue();
                aggiorna_contenuto_ordine_lista(ordine);
                aggiorna_rider_proposti_lista(ordine);
            }
        });

        accettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipedente_controller().accetta_rider(ordine,rider);
                aggiorna_rider_proposti_lista(ordine);
            }
        });

        rifiutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipedente_controller().rifiuta_rider(ordine,rider);
                aggiorna_rider_proposti_lista(ordine);
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
                aggiorna_ordini_lista(mainGui.get_dipedente_controller().get_dipedente().get_ristorante());
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
                aggiorna_ordini_lista(mainGui.get_dipedente_controller().get_dipedente().get_ristorante());
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_rider_proposti_lista(Ordine ordine){
        riderPropostiListModel.clear();
        ArrayList<Rider> rider_proposti = ordine.get_rider_proposti();
        if(rider_proposti == null) return;

        for(Rider rider : rider_proposti)
            riderPropostiListModel.addElement(rider);
    }

    public void aggiorna_ordini_lista(Ristorante ristorante){
        ordiniListModel.clear();
        ArrayList<Ordine> ordini = ristorante.get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniListModel.addElement(ordine);
    }

    public void aggiorna_contenuto_ordine_lista(Ordine ordine){
        contenutoOrdineModelList.clear();
        ArrayList<RigaOrdine> righe_ordine = ordine.get_rige_ordine();
        if(righe_ordine == null) return;

        for(RigaOrdine riga_ordine : righe_ordine)
            contenutoOrdineModelList.addElement(riga_ordine);
    }
}
