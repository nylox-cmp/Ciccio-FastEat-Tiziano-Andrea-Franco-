package gui.Dipedente;

import exception.ErrorType;
import gui.MainGui;
import gui.customWidget.DashboardDipedenteGui;
import gui.customWidget.DashboardGui;
import model.Prodotto;
import model.Ristorante;
import model.Menu;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdottoDipedenteGui extends JPanel {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanelDipedenti;
    private JPanel modificaProttoPanel;

    private JTextField nomeTextField;
    private JTextField prezzoTextField;

    private JButton modificaButton;

    private JLabel prodottoInfoLabel;
    private JLabel nomePrdottoLabel;
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
                    ErrorType error = mainGui.get_ristorante_controller().modifica_prodotto(nome,prezzo);
                    if (error != ErrorType.NESSUN_ERRORE) {
                        JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error), "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    prodottoInfoLabel.setText(prodotto.toString());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(ErrorType.INPUT_NON_NUMERICO), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
