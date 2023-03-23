package pl.pijok.graph;

import java.awt.*;

public class Bar {

    private final String label;
    private final int value;
    private final Color color;

    public Bar(String label, int value, Color color) {
        this.label = label;
        this.value = value;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

}
