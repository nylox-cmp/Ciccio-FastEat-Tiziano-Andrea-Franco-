package gui.Autenticazione;

import controller.UtenteController;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui extends JPanel {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton accediButton;
    private JButton registratiButton;
    private JPanel textFieldPanel;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordPasswordField;
    private JLabel tittoloLabel;

    public LoginGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = emailTextField.getText();

                mainGui.set_utente_controller(new UtenteController());
                ErrorType error = mainGui.get_utente_controller().login(email, password);
                if (error != ErrorType.NESSUN_ERRORE) {
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(error),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new SignInGui(mainGui));
            }
        });
    }
}
