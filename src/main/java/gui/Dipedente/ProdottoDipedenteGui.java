package gui.Dipedente;

import exception.BusinessError;
import exception.ErrorType;
import gui.MainGui;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdottoDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanelDipendenti;
    private JPanel modificaProdottoPanel;

    private JTextField nomeTextField;
    private JTextField prezzoTextField;

    private JButton modificaButton;

    private JLabel prodottoInfoLabel;
    private JLabel nomeProdottoLabel;
    private JLabel prezzoProdottoLabel;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ProdottoDipedenteGui(MainGui mainGui, Ristorante ristorante, Menu menu, Prodotto prodotto){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainGui.get_dashboardDipedenteGui().aggiungi_pulsanti_prodotto(mainGui,ristorante,menu);
        prodottoInfoLabel.setText(prodotto.toString());

        //________________________________________________________________________________________________________________________________________________
        // ActionListener Gestione Prodotto

        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String prezzo_string = prezzoTextField.getText().trim();
                if(nome.isEmpty() || prezzo_string.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NULL), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double prezzo = Double.parseDouble(prezzo_string);
                    try {
                        mainGui.get_ristorante_controller().modifica_prodotto(nome, prezzo);
                        prodottoInfoLabel.setText(prodotto.toString());
                    }
                    catch (BusinessError error) {
                        JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
