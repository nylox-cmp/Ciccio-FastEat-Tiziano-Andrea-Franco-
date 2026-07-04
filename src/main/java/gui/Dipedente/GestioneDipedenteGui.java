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
    private JPanel richiesteAssunzioniPanel;
    private JPanel dipedentiPanel;
    private JPanel DipedentiButtonPanel;
    private JPanel assunzioiniButtonPanel;

    private JButton rifiutaRichestaButton;
    private JButton accettaRichiestaButton;
    private JButton licenziaButton;

    private JComboBox seletoreRuoloCombox;
    private JLabel ruoloLabel;

    private JList<Utente> richiestaAssunziniLista;
    private DefaultListModel<Utente> richiestaAssunzioniListModel = new DefaultListModel<Utente>();
    private JList<Dipedente> subordinatiLista;
    private JScrollPane richiestaAssunzioniJScrollPane;
    private JScrollPane subordinatiJscrollPane;
    private DefaultListModel<Dipedente> subordinatiListModel = new DefaultListModel<Dipedente>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public GestioneDipedenteGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_subordinati_lista(mainGui.get_dipedente_controller().get_dipedente());
        aggiorna_richiesta_assunzioni_lista(mainGui.get_dipedente_controller().get_dipedente());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Richieste Assunzioni

        rifiutaRichestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utente utente = richiestaAssunziniLista.getSelectedValue();
                if(utente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = mainGui.get_dipedente_controller().rimuovi_richiesta_assunzione(utente);
                if (error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_richiesta_assunzioni_lista(mainGui.get_dipedente_controller().get_dipedente());
            }
        });

        accettaRichiestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utente utente = richiestaAssunziniLista.getSelectedValue();
                if(utente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ErrorType error = mainGui.get_dipedente_controller().accetta_richiesta_assunzione(utente);
                if (error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_richiesta_assunzioni_lista(mainGui.get_dipedente_controller().get_dipedente());
            }
        });

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
                ErrorType error = mainGui.get_dipedente_controller().accetta_richiesta_assunzione(dipedente);
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

    public void aggiorna_richiesta_assunzioni_lista(Dipedente dipedente){
        richiestaAssunzioniListModel.clear();
        ArrayList<Utente> richieste = dipedente.get_ristorante().get_richieste_assunzioni();
        if(richieste == null) return;

        for(Utente richiesta : richieste)
            richiestaAssunzioniListModel.addElement(richiesta);
    }
}
