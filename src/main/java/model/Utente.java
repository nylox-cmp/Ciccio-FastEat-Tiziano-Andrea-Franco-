package model;

import exception.ErrorType;

import java.util.ArrayList;

public class Utente {
    private String email;
    private String password;
    private String nickname;
    private String nome;
    private String cognome;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Utente(String email, String password, String nickname, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utente utente = (Utente) o;
        return utente.get_nickname().equals(this.get_nickname());
    }

    @Override
    public String toString(){
        return this.nickname;
    }


    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public String get_email(){ return email; }
    public void set_email(String email){ this.email = email; }

    public String get_password(){ return password; }
    public void set_password(String password){ this.password = password; }

    public String get_nickname(){ return this.nickname; }
    public void set_nickname(String nickname){ this.nickname = nickname; }

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_cognome(){ return cognome; }
    public void set_cognome(String cognome){ this.cognome = cognome; }
}
