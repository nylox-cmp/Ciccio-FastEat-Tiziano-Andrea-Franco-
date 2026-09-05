package gui.Autenticazione;

import controller.UtenteController;
import exception.BusinessError;
import exception.ErrorType;
import gui.Cliente.ClienteGui;
import gui.FrameManagerGui;

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


    public LoginGui(FrameManagerGui frameManagerGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        /**
         * @author Andrea
         */
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = passwordPasswordField.getText();

                if(email.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    frameManagerGui.main.set_utente_controller(new UtenteController());
                    frameManagerGui.main.get_utente_controller().login(email, password);
                    frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
                }
                catch (BusinessError error) {
                    JOptionPane.showMessageDialog(mainPanel, error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * @author Andrea
         */
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManagerGui.set_pagina(new SignInGui(frameManagerGui));
            }
        });
    }
}
