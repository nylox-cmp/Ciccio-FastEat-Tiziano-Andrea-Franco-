package test;

import controller.*;
import model.*;
import exception.ErrorType;

import java.util.Scanner;

public class Test_controller {

    public static void login_sing_in(UtenteController utenteController, Scanner scanner) {
        System.out.println("1.Login \n2.Sign in \ninserisci il numero dell'operazione da eseguire:");
        int scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {
            case 1:
                System.out.println("inserisci la email: ");
                String email = scanner.nextLine();
                System.out.println("inserisci la password: ");
                String password = scanner.nextLine();
                ErrorType error = utenteController.login(email, password);
                if (error == ErrorType.NESSUN_ERRORE)
                break;
            case 2:
                System.out.println("inserisci la email: ");
                email = scanner.nextLine();
                System.out.println("inserisci la password: ");
                password = scanner.nextLine();
                System.out.println("inserisci il nickname: ");
                String nickname = scanner.nextLine();
                System.out.println("inserisci nome: ");
                String nome = scanner.nextLine();
                System.out.println("inserisci cognome: ");
                String cognome = scanner.nextLine();
                error = utenteController.sing_in(email, password, nickname, nome, cognome);
                if (error == ErrorType.NESSUN_ERRORE)
                    break;
        }
    }

    public static void area_cliente(ClienteController clienteController,Scanner scanner){
        int scelta = 1;
        while (scelta != 0){
            System.out.println("\n\n0.Esci \n1.crea ordine \n2.cancella ordine \n3.conferma consegna ordine \n4.Area rider \n5.Area dipedenti \ninserisci il numero dell'operazione da eseguire:");
            scelta = scanner.nextInt();
            scanner.nextLine();


        }
    }

    public static void main(String[] args){
        UtenteController utenteController = new UtenteController();
        Scanner scanner = new Scanner(System.in);

        login_sing_in(utenteController,scanner);
        Utente utente = utenteController.get_utente();
        System.out.println("\n"+ utente.get_email() + " " + utente.get_password() + " " + utente.get_nickname() + " " + utente.get_nome() + " " + utente.get_cognome());
        ClienteController clienteController = new ClienteController();
        clienteController.set_utente_controller(utenteController);
        area_cliente(clienteController,scanner); // non possso ordinare se non ci sono ristoranti :C (mi serve il database)
    }
}
