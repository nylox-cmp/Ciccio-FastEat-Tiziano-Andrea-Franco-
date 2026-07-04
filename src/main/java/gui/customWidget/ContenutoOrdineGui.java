package gui.customWidget;

import model.Ordine;
import model.RigaOrdine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class ContenutoOrdineGui extends JPanel {
    private JPanel mainPanel;

    private DefaultListModel<RigaOrdine> contenutoOrdineListModel = new DefaultListModel<RigaOrdine>();
    private JList<RigaOrdine> contenutoOrdineLista;
    private JScrollPane cotenutoOrdineJScrollPane;

    //________________________________________________________________________________________________________________________________________________
    // Costurttore

    public ContenutoOrdineGui(JList<Ordine> ordiniLista){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        contenutoOrdineLista.setModel(contenutoOrdineListModel);

        //________________________________________________________________________________________________________________________________________________


        ordiniLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                aggiorna_lista(ordine);
            }
        });
    }

    public ContenutoOrdineGui(){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        contenutoOrdineLista.setModel(contenutoOrdineListModel);
    }

    //________________________________________________________________________________________________________________________________________________

    public void aggiorna_lista(Ordine ordine){
        contenutoOrdineListModel.clear();
        ArrayList<RigaOrdine> righe_ordine = ordine.get_rige_ordine();
        if(righe_ordine == null) return;

        for(RigaOrdine riga_ordine : righe_ordine)
            contenutoOrdineListModel.addElement(riga_ordine);
    }

    //________________________________________________________________________________________________________________________________________________

    public JList<RigaOrdine> get_contenutoOrdineLista(){
        return contenutoOrdineLista;
    }

    public void set_contenutoOrdineLista(JList<RigaOrdine> contenutoOrdineLista){
        this.contenutoOrdineLista = contenutoOrdineLista;
    }
}
