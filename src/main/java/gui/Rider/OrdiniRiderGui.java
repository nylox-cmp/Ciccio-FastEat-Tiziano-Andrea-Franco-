package gui.Rider;

import com.sun.tools.javac.Main;
import gui.MainGui;
import gui.customWidget.DashboardGui;

import javax.swing.*;
import java.awt.*;

public class OrdiniRiderGui extends JPanel{
    private JPanel mainPanel;
    private JPanel BottomPanel;
    private JPanel ContenutoOrdinePanel;
    private JList ContenutoOrdineRiderLista;
    private JPanel OrdiniInCaricoPanel;
    private JPanel ButtonPanel;
    private JButton confermaConsegnaButton1;
    private JList OrdiniRiderLista;
    private JPanel RichiestaOrdiniPanel;
    private JPanel RichiestaButtonPanel;
    private JButton RichiestaOrdineButton;
    private JList OrdiniPropostiLista;

    public OrdiniRiderGui(MainGui mainGui){
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        DashboardGui dashboardGui = new DashboardGui(mainGui);
        add(dashboardGui, BorderLayout.NORTH);
        dashboardGui.aggiungi_action_ordini_rider(mainGui);
    }
}
