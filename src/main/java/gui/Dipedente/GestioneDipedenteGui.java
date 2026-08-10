package gui.Dipedente;

import gui.MainGui;
import exception.BusinessError;
import exception.ErrorType;
import model.Dipendente;
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
    private JList<Dipendente> subordinatiLista;
    private JScrollPane subordinatiJscrollPane;
    private DefaultListModel<Dipendente> subordinatiListModel = new DefaultListModel<Dipendente>();

    private MainGui mainGui;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public GestioneDipedenteGui(MainGui mainGui){
        this.mainGui = mainGui;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        aggiorna_subordinati_lista();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione (Dipendenti) Subordinati

        licenziaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = subordinatiLista.getSelectedValue();
                if(dipendente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    mainGui.get_dipendente_controller().licenzia_dipendente(dipendente);
                    aggiorna_subordinati_lista();
                }
                catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        seletoreRuoloCombox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = subordinatiLista.getSelectedValue();
                Object item = seletoreRuoloCombox.getSelectedItem();
                if(dipendente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String ruolo_selezionato = (String) item;
                    switch (ruolo_selezionato) {
                        case "BASE":
                            mainGui.get_dipendente_controller().modifica_ruolo_dipendente(dipendente, Ruolo.BASE);
                            break;
                        case "GESTIONALE":
                            mainGui.get_dipendente_controller().modifica_ruolo_dipendente(dipendente, Ruolo.GESTIONALE);
                            break;
                    }
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                aggiorna_subordinati_lista();
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_subordinati_lista(){
        subordinatiListModel.clear();
        ArrayList<Dipendente> subordinati = mainGui.get_dipendente_controller().get_subordinati();
        if(subordinati == null) return;

        for(Dipendente subordinato : subordinati)
            subordinatiListModel.addElement(subordinato);
    }

}
