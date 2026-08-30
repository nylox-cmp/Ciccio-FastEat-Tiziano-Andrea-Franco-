package model;

import java.util.ArrayList;

public enum Ruolo{
    BASE,
    GESTIONALE,
    MANAGER;

    public static String converti_ruolo_to_string(Ruolo ruolo){
        String string = "";
        switch (ruolo){
            case BASE:
                string = "Base";
                break;
            case GESTIONALE:
                string = "Gestionale";
                break;
            case MANAGER:
                string = "Manager";
                break;
        }
        return string;
    }
}
