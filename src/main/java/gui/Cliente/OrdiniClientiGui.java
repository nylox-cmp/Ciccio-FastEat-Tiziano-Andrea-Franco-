package gui.Cliente;

import exception.BusinessError;
import exception.ErrorType;
import gui.FrameManagerGui;
import gui.customWidget.ContenutoOrdineGui;
import model.Ordine;

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
    private JPanel bottomContenutoOrdinePanel;

    private JButton applicaScontoButton;
    private JButton annulaOrdineButton;
    private JButton confermaConsegnaButton;
    private JButton rimuoviPrdottoOrdineButton;
    private JButton confermaCreazioneOrdineButton;


    private DefaultListModel<Ordine> ordiniListModel = new DefaultListModel<Ordine>();
    private JList<Ordine> ordiniJList;

    private JLabel puntiFedeltaLabel;
    private JLabel quantitaLabel;
    private JTextField puntiFedeltaTextField;
    private JScrollPane ordiniJScrollPane;

    private FrameManagerGui frameManagerGui;
    private main.Main main;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     * @param frameManagerGui the canvas gui
     */
    public OrdiniClientiGui(FrameManagerGui frameManagerGui){
        this.frameManagerGui = frameManagerGui;
        this.main = frameManagerGui.main;

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        ordiniJList.setModel(ordiniListModel);

        ContenutoOrdineGui contenutoOrdine = new ContenutoOrdineGui(ordiniJList, frameManagerGui);
        contenutoOrdinePanel.add(contenutoOrdine,BorderLayout.CENTER);

        quantitaLabel.setText(String.valueOf(main.get_cliente_controller().get_punti_fedelta()));

        aggiorna_ordiniLista();

        //________________________________________________________________________________________________________________________________________________
        // ActionListner Gestione StatoOrdine

        /**
         * @author Tiziano
         */
        confermaCreazioneOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniJList.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    main.get_cliente_controller().conferma_creazione_ordine(ordine);
                    aggiorna_ordiniLista();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * @author Tiziano
         */
        confermaConsegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniJList.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    main.get_cliente_controller().conferma_consegna_ordine(ordine);
                    aggiorna_ordiniLista();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }

                quantitaLabel.setText(String.valueOf(main.get_cliente_controller().get_cliente().get_punti_fedelta()));
                frameManagerGui.get_dashboardClienteGui().aggiorna_punti_fedelta_label();

                frameManagerGui.get_dashboardClienteGui().aggiorna_punti_fedelta_label();

            }
        });

        /**
         * @author Tiziano
         */
        annulaOrdineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ordine ordine = ordiniJList.getSelectedValue();
                if(ordine == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    main.get_cliente_controller().annulla_ordine(ordine);
                    aggiorna_ordiniLista();
                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListner Gestione Punti Fedelta

        /**
         * @author Tiziano
         */
        applicaScontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Ordine ordine = ordiniJList.getSelectedValue();

            if(ordine == null){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(puntiFedeltaTextField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL),"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                int punti_fedelta  = Integer.parseInt(puntiFedeltaTextField.getText());
                try {
                    main.get_cliente_controller().applica_sconto(ordine, punti_fedelta);
                    quantitaLabel.setText(String.valueOf(main.get_cliente_controller().get_punti_fedelta()));
                    frameManagerGui.get_dashboardClienteGui().aggiorna_punti_fedelta_label();
                    aggiorna_ordiniLista();

                }
                catch (BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO),"Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione OrdiniLista

    /**
     * @author Tiziano
     */
    private void aggiorna_ordiniLista(){
        ordiniListModel.clear();
        ArrayList<Ordine> ordini = main.get_cliente_controller().get_ordini_cliente();
        if(ordini == null) return;

        for(Ordine ordine : ordini)
            ordiniListModel.addElement(ordine);
    }
}
