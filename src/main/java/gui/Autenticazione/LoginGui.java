package gui.Autenticazione;

import controller.UtenteController;
import exception.BusinessError;
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
    private JPanel textFieldPanel;

    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel tittoloLabel;

    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;

    private JButton accediButton;
    private JButton registratiButton;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public LoginGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = emailTextField.getText();

                if(email.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    mainGui.set_utente_controller(new UtenteController());
                    mainGui.get_utente_controller().login(email, password);
                    mainGui.set_pagina(new ClienteGui(mainGui));
                }
                catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
