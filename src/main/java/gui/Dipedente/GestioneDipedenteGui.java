package gui.Dipedente;

import exception.BusinessError;
import exception.ErrorType;
import gui.FrameManagerGui;
import model.Dipendente;
import model.Ruolo;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

    private FrameManagerGui frameManagerGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Franco
     *
     * @param frameManagerGui the canvas gui
     */
    public GestioneDipedenteGui(FrameManagerGui frameManagerGui){
        this.frameManagerGui = frameManagerGui;
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        subordinatiLista.setModel(subordinatiListModel);

        popola_ruoli_combobox(main.get_dipendente_controller().get_dipendente());

        aggiorna_subordinati_lista();

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione (Dipendenti) Subordinati

        /**
         * @author Franco
         */
        licenziaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = subordinatiLista.getSelectedValue();
                if(dipendente == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    main.get_dipendente_controller().licenzia_dipendente(dipendente);
                    aggiorna_subordinati_lista();
                }
                catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * @author Franco
         */
        seletoreRuoloCombox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente subordinato = subordinatiLista.getSelectedValue();
                if(subordinato == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Ruolo ruolo_selezionato = Ruolo.valueOf((String) seletoreRuoloCombox.getSelectedItem());
                    main.get_dipendente_controller().modifica_ruolo_dipendente(subordinato, ruolo_selezionato);
                    aggiorna_subordinati_lista();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    /**
     * @author Franco
     */
    private void aggiorna_subordinati_lista(){
        subordinatiListModel.clear();
        ArrayList<Dipendente> subordinati = main.get_dipendente_controller().get_subordinati();
        if(subordinati == null) return;

        for(Dipendente subordinato : subordinati)
            subordinatiListModel.addElement(subordinato);
    }

    /**
     * @author Franco
     */
    private void popola_ruoli_combobox(Dipendente dipendente_corrente) {
        String[] ruoli_assegnabili = Arrays.stream(Ruolo.values())
                .filter(ruolo -> ruolo.ordinal() < dipendente_corrente.get_ruolo().ordinal())
                .map(Ruolo::name)
                .toArray(String[]::new);

        seletoreRuoloCombox.setModel(new DefaultComboBoxModel<>(ruoli_assegnabili));
    }
}
