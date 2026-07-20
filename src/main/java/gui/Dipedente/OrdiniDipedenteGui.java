package gui.Dipedente;

import controller.DipendenteController;
import exception.BusinessError;
import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.ContenutoOrdineGui;
import gui.customWidget.DashboardDipedenteGui;
import model.Ordine;
import model.Rider;
import model.Ristorante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdiniDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel riderPropostiPanel;
    private JPanel riderPropostiButtonPanel;
    private JPanel bottomPanel;
    private JPanel ordiniPanel;
    private JPanel ordiniDipendentiButtonPanel;
    private JPanel contenutoOrdinePanel;

    private JScrollPane ordiniJScrollPane;
    private JScrollPane riderPropostiJScrollPane;

    private JButton cancellaButton;
    private JButton segnalaProntoAlRitiroButton;
    private JButton rifiutaButton;
    private JButton accettaButton;

    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniLista;
    private DefaultListModel<Rider> riderPropostiListModel = new DefaultListModel<Rider>();
    private JList<Rider> riderPropostiLista;


    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public OrdiniDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.set_dashboardDipedenteGui(new DashboardDipedenteGui(mainGui));
        mainGui.set_dipendente_controller(new DipendenteController());
        mainGui.mostra_dashboard_dipedenti(mainGui.get_dipendente_controller().get_dipendente().get_ristorante());

        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui();
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        ordiniLista.setModel(ordiniListModel);
        riderPropostiLista.setModel(riderPropostiListModel);

        mainGui.get_dashboardGui().nascondi_area_OrdiniClienti();

        //________________________________________________________________________________________________________________________________________________
        // ListSelectionListener che mostra il contenuto dell'ordine e mostra i rider proposti

        ordiniLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null) return;
                contenutoOrdine.aggiorna_lista(ordine);
                aggiorna_riderPropostiLista(ordine);
            }
        });

        aggiorna_ordiniLista(mainGui.get_dipendente_controller().get_dipendente().get_ristorante());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Ordini

        cancellaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if (ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipendente_controller().annulla_ordine(ordine);
                aggiorna_ordiniLista(mainGui.get_dipendente_controller().get_dipendente().get_ristorante());
            }
        });

        segnalaProntoAlRitiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if (ordine == null) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    mainGui.get_dipendente_controller().segnala_ordine_pronto_ritiro(ordine);
                    aggiorna_ordiniLista(mainGui.get_dipendente_controller().get_dipendente().get_ristorante());
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Rider

        accettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipendente_controller().accetta_rider(ordine,rider);
                aggiorna_riderPropostiLista(ordine);
                aggiorna_ordiniLista(mainGui.get_dipendente_controller().get_dipendente().get_ristorante());
            }
        });

        rifiutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rider rider = riderPropostiLista.getSelectedValue();
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null || rider == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.get_dipendente_controller().rifiuta_rider(ordine,rider);
                aggiorna_riderPropostiLista(ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni sulle JList

    public void aggiorna_riderPropostiLista(Ordine ordine){
        riderPropostiListModel.clear();
        ArrayList<Rider> rider_proposti = ordine.get_rider_proposti();
        if(rider_proposti == null) return;

        for(Rider rider : rider_proposti)
            riderPropostiListModel.addElement(rider);
    }

    public void aggiorna_ordiniLista(Ristorante ristorante){
        ordiniListModel.clear();
        ArrayList<Ordine> ordini = ristorante.get_ordini();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniListModel.addElement(ordine);
    }
}
