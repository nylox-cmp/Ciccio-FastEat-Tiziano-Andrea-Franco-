package model;

public enum Ruolo{
    OPERATORE,
    SUPERVISORE,
    DIRETTORE;

    /**
     * @author Tiziano
     *
     * @param ruolo
     * @return
     */
    public static String converti_ruolo_to_string(Ruolo ruolo){
        String string = "";
        switch (ruolo){
            case OPERATORE:
                string = "Base";
                break;
            case SUPERVISORE:
                string = "Supervisore";
                break;
            case DIRETTORE:
                string = "Manager";
                break;
        }
        return string;
    }
}
