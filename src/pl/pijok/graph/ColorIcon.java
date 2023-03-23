package pl.pijok.graph;

import javax.swing.*;
import java.awt.*;

public class ColorIcon implements Icon {

    private final Color color;
    private final int width;
    private final int height;

    public ColorIcon(Color color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        int shadow = 3;
        g.fillRect(x, y, width - shadow, height);
        g.setColor(Color.GRAY);
        g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
    }

}
