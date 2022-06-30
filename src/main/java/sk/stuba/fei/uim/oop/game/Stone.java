package sk.stuba.fei.uim.oop.game;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.gui.Board;

import java.awt.*;

public class Stone {
    @Getter @Setter
    private Color color;
    private final Board board;
    public static final Color HIGHLIGHT = Color.DARK_GRAY.brighter();

    public Stone(Board board, Color color) {
        this.board = board;
        this.color = color;
    }

    public void draw(Graphics g, int width, int height) {
        Color c = g.getColor();
        g.setColor(this.color);
        int size = this.size();
        g.fillOval((width - size) / 2, (height - size) / 2, size, size);
        g.setColor(c);
    }

    private int size() {
        return Math.min(this.board.getSize().width, this.board.getSize().height) * 4 / this.board.getBoardSize() / 5;
    }
}
