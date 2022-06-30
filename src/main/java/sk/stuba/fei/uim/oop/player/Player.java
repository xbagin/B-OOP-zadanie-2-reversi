package sk.stuba.fei.uim.oop.player;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.game.Stone;
import sk.stuba.fei.uim.oop.game.Tile;
import sk.stuba.fei.uim.oop.gui.Board;

import java.awt.*;
import java.util.Objects;

public class Player {
    private final String name;
    @Getter
    private final Color color;
    private final String colorName;
    @Getter
    private final boolean human;
    @Setter
    private boolean skipMove;
    @Getter
    private int numberOfStones;

    public static final int FIRST = 0;
    public static final boolean HUMAN = true;
    public static final boolean AI = false;

    public Player(String name, Color color, String colorName, boolean human) {
        this.name = name;
        this.color = color;
        this.colorName = colorName;
        this.human = human;
        this.skipMove = false;
        this.numberOfStones = 0;
    }

    public String getText() {
        return this.name + " (" + this.colorName + ")";
    }

    public boolean skippedMove() {
        return this.skipMove;
    }

    public void clearNumberOfStones() {
        this.numberOfStones = 0;
    }

    public void computeNumberOfStones(Board board) {
        this.numberOfStones = 0;
        int boardSize = board.getBoardSize();
        for (int i = 0; i < boardSize * boardSize; i++) {
            if (this.sameColor(((Tile)board.getComponent(i)).getStone())) {
                this.numberOfStones++;
            }
        }
    }

    private boolean sameColor(Stone stone) {
        if (stone == null) {
            return false;
        }
        return Objects.equals(stone.getColor(), this.color);
    }
}
