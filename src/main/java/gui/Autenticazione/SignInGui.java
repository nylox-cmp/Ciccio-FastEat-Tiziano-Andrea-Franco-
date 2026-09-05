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

    /**
     * @author Andrea
     * @param frameManagerGui
     */
    public SignInGui(FrameManagerGui frameManagerGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);


        /**
         * @author Andrea
         */
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String nickname = nicknameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                if( nome.isEmpty() || cognome.isEmpty() || nickname.isEmpty() || email.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel,ErrorType.converti_error_to_message(ErrorType.INPUT_NULL),"Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    frameManagerGui.main.set_utente_controller(new UtenteController());
                    frameManagerGui.main.get_utente_controller().sign_in(email, password, nickname, nome, cognome);
                    frameManagerGui.set_pagina(new ClienteGui(frameManagerGui));
                }
                catch(BusinessError error){
                    JOptionPane.showMessageDialog(mainPanel,error.get_error_message(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });

        /**
         * @author Andrea
         */
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManagerGui.set_pagina(new LoginGui(frameManagerGui));
            }
        });
    }
}
