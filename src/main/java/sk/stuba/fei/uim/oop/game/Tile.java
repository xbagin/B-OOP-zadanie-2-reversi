package sk.stuba.fei.uim.oop.game;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.logic.UniversalAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tile extends JPanel {
    @Getter @Setter
    private Stone stone;
    @Getter @Setter
    private boolean enabled;
    @Setter
    private int radius;
    private static final Color CIRCLE_COLOR = Color.WHITE;
    public static final Color COLOR = new Color(0, 150, 0);
    public static final Color HIGHLIGHT = Color.BLUE;

    public Tile(UniversalAdapter handler) {
        super();
        this.stone = null;
        this.enabled = false;
        this.setFocusable(false);
        this.setBackground(Tile.COLOR);
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
        this.radius = 0;
    }

    public void changeStone(Color color) {
        this.stone.setColor(color);
    }

    public void removeStone() {
        this.stone = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.stone != null) {
            this.stone.draw(g, this.getSize().width, this.getSize().height);
        }
        if (this.radius > 0 && Objects.equals(this.getBackground(), Tile.HIGHLIGHT)) {
            this.drawCircle(g);
        }
    }

    public void computeRadius(int x, int y) {
        x = Math.abs(this.getSize().width / 2 - x);
        y = Math.abs(this.getSize().height / 2 - y);
        this.radius = (int) Math.sqrt(x*x + y*y);
    }

    private void drawCircle(Graphics g) {
        Color color = g.getColor();
        g.setColor(Tile.CIRCLE_COLOR);
        int xMid = this.getSize().width / 2;
        int yMid = this.getSize().height / 2;
        g.drawOval(xMid - this.radius, yMid - this.radius, 2 * this.radius, 2 * this.radius);
        g.setColor(color);
    }
}
