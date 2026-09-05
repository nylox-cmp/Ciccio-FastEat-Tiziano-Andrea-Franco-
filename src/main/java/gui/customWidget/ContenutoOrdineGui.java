package gui.customWidget;

import controller.OrdiniController;
import gui.FrameManagerGui;
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

    private FrameManagerGui frameManagerGui;

    //________________________________________________________________________________________________________________________________________________
    // Costurttore

    /**
     * @author Tiziano
     * Instantiates a new Contenuto ordine gui.
     *
     * @param ordiniLista the ordini lista
     * @param frameManagerGui   the canvas gui
     */
    public ContenutoOrdineGui(JList<Ordine> ordiniLista, FrameManagerGui frameManagerGui){
        this.frameManagerGui = frameManagerGui;
        frameManagerGui.main.set_ordine_controller(new OrdiniController());

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        contenutoOrdineLista.setModel(contenutoOrdineListModel);

        //________________________________________________________________________________________________________________________________________________

        /**
         * @author Tiziano
         */
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

    /**
     * @author Tiziano
     * Aggiorna lista.
     *
     * @param ordine the ordine
     */
    public void aggiorna_lista(Ordine ordine){
        contenutoOrdineListModel.clear();
        ArrayList<RigaOrdine> righe_ordine = frameManagerGui.main.get_ordini_controller().get_contenuto_ordine(ordine);
        if(righe_ordine == null) return;

        for(RigaOrdine riga_ordine : righe_ordine)
            contenutoOrdineListModel.addElement(riga_ordine);
    }

}
