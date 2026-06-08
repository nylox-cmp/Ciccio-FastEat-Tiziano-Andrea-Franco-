package gui.Autenticazione;

import controller.UtenteController;
import exception.*;
import gui.Cliente.ClienteGui;
import gui.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInGui extends JPanel{
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel singInPanel;

    private JLabel tittoloLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel nicknameLabel;
    private JLabel nomeLabel;
    private JLabel cognomeLabel;

    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField nicknameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;

    private JButton accediButton;
    private JButton registratiButton;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public SignInGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String nickname = nicknameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                if( nome.isEmpty() || cognome.isEmpty() || nickname.isEmpty() || email.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NON_VALIDO),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mainGui.set_utente_controller(new UtenteController());
                ErrorType error = mainGui.get_utente_controller().sign_in(email,password,nickname,nome, cognome);
                if(error != ErrorType.NESSUN_ERRORE) {
                    JOptionPane.showMessageDialog(mainPanel, ErrorType.converti_error_to_message(error),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainGui.set_pagina(new ClienteGui(mainGui));
            }
        });

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGui.set_pagina(new LoginGui(mainGui));
            }
        });
    }
}
