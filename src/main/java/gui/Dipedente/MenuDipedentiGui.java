package gui.Dipedente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.ArrayList;

public class MenuDipedentiGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JLabel nomeMenuLabel;
    private JPanel infoPanel;
    private JPanel modificaMenuPanel;
    private JPanel prodottiPanel;

    private JButton creaProdottoButton;
    private JButton cancellaProdottoButton;
    private JButton modificaMenuButton;

    private JTextField nomeProdottoTextField;
    private JTextField nomeMenuTextField;
    private JTextField prezzoTextField;

    private JLabel prezzoLabel;
    private JLabel nomeProdottoLabel;
    private JLabel nomeModificaMenuLabel;

    private JList<Prodotto> prodottiLista;
    private DefaultListModel<Prodotto> prodottiListModel = new DefaultListModel<Prodotto>();
    private JScrollPane prodottoJScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public MenuDipedentiGui(MainGui mainGui, Ristorante ristorante, Menu menu) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainGui.get_dashboardDipedenteGui().aggiungi_pulsanti_menu(mainGui,ristorante,menu,prodottiLista);

        nomeMenuLabel.setText(menu.toString());
        mainGui.get_ristorante_controller().set_menu(menu);

        prodottiLista.setModel(prodottiListModel);
        aggiorna_lista_prodotti(menu);

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Menu

        modificaMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovoNome = nomeMenuTextField.getText().trim();
                if (nuovoNome.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ErrorType error = mainGui.get_ristorante_controller().modifica_menu(nuovoNome);
                if (error != ErrorType.NESSUN_ERRORE) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nomeMenuLabel.setText(nuovoNome);
            }
        });

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Prodotto

        creaProdottoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeProdottoTextField.getText().trim();
                String prezzo_string = prezzoTextField.getText().trim();

                if (nome.isEmpty() || prezzo_string.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double prezzo = Double.parseDouble(prezzo_string);
                    ErrorType error = mainGui.get_ristorante_controller().crea_prodotto(nome,prezzo);

                    if (error != ErrorType.NESSUN_ERRORE) {
                        JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    aggiorna_lista_prodotti(menu);
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancellaProdottoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prodotto prodotto = prodottiLista.getSelectedValue();
                if(prodotto == null){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.ELEMENTO_SELEZIONATO_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ErrorType error = mainGui.get_ristorante_controller().cancella_prodotto(prodotto);
                if(error != ErrorType.NESSUN_ERRORE){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                aggiorna_lista_prodotti(menu);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________


    public void aggiorna_lista_prodotti(Menu menu){
        prodottiListModel.clear();
        ArrayList<Prodotto> prodotti = menu.get_prodotti();
        if(menu != null && prodotti != null){
            for(Prodotto prodotto : prodotti){
                prodottiListModel.addElement(prodotto);
            }
        }
    }
}
