package pl.pijok;

import pl.pijok.graph.HistogramPanel;

import javax.swing.*;
import java.awt.*;

public class KNNFrame extends JFrame{

    private final GUIPanel guiPanel;

    public KNNFrame() {

        this.guiPanel = new GUIPanel();

        setTitle("KNN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(guiPanel);
        pack();
        setVisible(true);
    }

    public GUIPanel getGuiPanel() {
        return guiPanel;
    }


}
