package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.game.Tile;
import sk.stuba.fei.uim.oop.logic.MovesFinder;
import sk.stuba.fei.uim.oop.logic.UniversalAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {
    @Getter @Setter
    private int boardSize;
    private final UniversalAdapter listener;
    private final MovesFinder finder;
    private static final int TILE_GAP = 3;
    public static final int DEFAULT_BOARD_SIZE = 6;

    public Board(int boardSize, UniversalAdapter listener) {
        super();
        this.boardSize = boardSize;
        this.listener = listener;
        this.finder = new MovesFinder(this);
        this.setBackground(Color.DARK_GRAY);
        this.create();
    }

    public void create() {
        this.setLayout(new GridLayout(this.boardSize, this.boardSize, Board.TILE_GAP, Board.TILE_GAP));
        for (int i = 0; i < this.boardSize * this.boardSize; i++) {
            this.add(new Tile(this.listener));
        }
    }

    public Map<Tile, List<Tile>> getPossibleTiles(Color c) {
        return this.finder.findPossibleTiles(c);
    }
}
