package gui.customWidget;

import controller.OrdiniController;
import exception.ErrorType;
import gui.CanvasGui;
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

    private CanvasGui canvasGui;

    //________________________________________________________________________________________________________________________________________________
    // Costurttore

    public ContenutoOrdineGui(JList<Ordine> ordiniLista, CanvasGui canvasGui){
        this.canvasGui = canvasGui;

        canvasGui.main.set_ordine_controller(new OrdiniController());
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        contenutoOrdineLista.setModel(contenutoOrdineListModel);

        //________________________________________________________________________________________________________________________________________________


        ordiniLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Ordine ordine = ordiniLista.getSelectedValue();
                if(ordine == null) return;

                aggiorna_lista(ordine);
            }
        });
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Gestione Lista

    public void aggiorna_lista(Ordine ordine){
        contenutoOrdineListModel.clear();
        ArrayList<RigaOrdine> righe_ordine = canvasGui.main.get_ordini_controller().get_contenuto_ordine(ordine);
        if(righe_ordine == null) return;

        for(RigaOrdine riga_ordine : righe_ordine)
            contenutoOrdineListModel.addElement(riga_ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    public JList<RigaOrdine> get_contenutoOrdineLista(){
        return contenutoOrdineLista;
    }

}
