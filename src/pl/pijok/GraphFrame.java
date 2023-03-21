package pl.pijok;

import pl.pijok.graph.HistogramPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GraphFrame extends JFrame {

    private final HistogramPanel histogramPanel;

    public GraphFrame(List<KNNResult> dataForGraph) {
        this.histogramPanel = new HistogramPanel();

        Random random = new Random();

        for(KNNResult result : dataForGraph){
            histogramPanel.addHistogramColumn(
                    String.valueOf(result.getK()),
                    (int) result.getPrecision(),
                    new Color(
                            random.nextInt(255),
                            random.nextInt(255),
                            random.nextInt(255)
                            )
            );
        }

        histogramPanel.layoutHistogram();

        setTitle("Graph");
        getContentPane().add(histogramPanel);
        pack();
        setVisible(true);
    }

}
