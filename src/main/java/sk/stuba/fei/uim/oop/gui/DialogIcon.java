package sk.stuba.fei.uim.oop.gui;

import javax.swing.*;
import java.awt.*;

public class DialogIcon implements Icon {
    private final int width;
    private final int height;
    private final Color color;
    private static final int SIZE = 100;

    public DialogIcon(Color color) {
        this.width = DialogIcon.SIZE;
        this.height = DialogIcon.SIZE;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Color clr = g.getColor();
        g.setColor(this.color);
        g.fillOval(x, y, this.width, this.height);
        g.setColor(clr);
    }

    @Override
    public int getIconWidth() {
        return this.width;
    }

    @Override
    public int getIconHeight() {
        return this.height;
    }
}
