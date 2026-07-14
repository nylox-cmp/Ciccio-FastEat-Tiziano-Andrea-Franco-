package gui.Dipedente;

import exception.ErrorType;
import gui.MainGui;
import model.Dipedente;
import model.Ruolo;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestioneDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel dipendentiPanel;
    private JPanel DipendentiButtonPanel;

    private JButton rifiutaRichestaButton;
    private JButton accettaRichiestaButton;
    private JButton licenziaButton;

    private JComboBox seletoreRuoloCombox;
    private JLabel ruoloLabel;

    private JList<Utente> richiestaAssunzioniLista;
    private DefaultListModel<Utente> richiestaAssunzioniListModel = new DefaultListModel<Utente>();
    private JList<Dipedente> subordinatiLista;
    private JScrollPane subordinatiJscrollPane;
    private DefaultListModel<Dipedente> subordinatiListModel = new DefaultListModel<Dipedente>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public GestioneDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_subordinati_lista(mainGui.get_dipedente_controller().get_dipedente());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione (Dipedenti) Subordinati

        licenziaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipedente dipedente = subordinatiLista.getSelectedValue();
                if(dipedente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = mainGui.get_dipedente_controller().licenzia_dipedente(dipedente);
                if (error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_subordinati_lista(mainGui.get_dipedente_controller().get_dipedente());
            }
        });

        seletoreRuoloCombox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipedente dipedente = subordinatiLista.getSelectedValue();
                Object item = seletoreRuoloCombox.getSelectedItem();
                if(dipedente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = ErrorType.NESSUN_ERRORE;
                String ruolo_selezionato = (String) item;
                switch (ruolo_selezionato){
                    case "BASE":
                       error = mainGui.get_dipedente_controller().modifica_ruolo_dipedente(dipedente,Ruolo.BASE);
                       break;
                    case "GESTIONALE":
                        error = mainGui.get_dipedente_controller().modifica_ruolo_dipedente(dipedente,Ruolo.GESTIONALE);
                        break;
                }
                if (error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_subordinati_lista(mainGui.get_dipedente_controller().get_dipedente());
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_subordinati_lista(Dipedente dipedente){
        subordinatiListModel.clear();
        ArrayList<Dipedente> subordinati = dipedente.get_subordinati();
        if(subordinati == null) return;

        for(Dipedente subordinato : subordinati)
            subordinatiListModel.addElement(subordinato);
    }

}
