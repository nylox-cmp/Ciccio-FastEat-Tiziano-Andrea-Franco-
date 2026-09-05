package dao;

import model.Dipendente;
import model.Ordine;
import model.Rider;
import model.Ruolo;

import java.util.ArrayList;

public interface DipendenteDAO {

    public void licenziati(String nickname);

    public void accetta_rider(String codice_ordine,String nickname_rider);

    public void rifiuta_rider(String codice_ordine,String nickname_rider);

    public void modifica_ruolo_dipedente(String nickaname_dipedente, Ruolo ruolo);

    public void licenzia_dipedente(String nickname_dipedente);

    public ArrayList<Dipendente> get_subordinati(String codice_ristorante,Ruolo ruolo);

    public ArrayList<Ordine> get_ordini_ristorante(String codice_ristorante);

    public ArrayList<Rider> get_rider_proposti_consegna(String codice_ordine);
}
