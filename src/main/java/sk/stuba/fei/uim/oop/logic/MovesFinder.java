package sk.stuba.fei.uim.oop.logic;

import sk.stuba.fei.uim.oop.game.Directions;
import sk.stuba.fei.uim.oop.game.Stone;
import sk.stuba.fei.uim.oop.game.Tile;
import sk.stuba.fei.uim.oop.gui.Board;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MovesFinder {
    private final Board board;

    public MovesFinder(Board board) {
        this.board = board;
    }

    public Map<Tile, List<Tile>> findPossibleTiles(Color c) {
        Map<Tile, List<Tile>> tiles = new HashMap<>();
        for (int i = 0; i < this.board.getBoardSize() * this.board.getBoardSize(); i++) {
            Stone stone = ((Tile)this.board.getComponent(i)).getStone();
            if (this.freePosition(stone)) {
                List<Tile> line = new ArrayList<>();
                for (Directions dir : Directions.values()) {
                    line.addAll(this.scanForOpponentStones(dir, i, c));
                }
                if (!line.isEmpty()) {
                    tiles.put((Tile)this.board.getComponent(i), line);
                }
            }
        }
        return tiles;
    }

    private List<Tile> scanForOpponentStones(Directions d, int position, Color c) {
        List<Tile> line = new ArrayList<>();
        if (this.isInBoundsInDirection(position, d)) {
            int nextPos = this.nextTilePositionInDirection(position, d);
            Stone stone = ((Tile)this.board.getComponent(nextPos)).getStone();
            if (this.isRealStone(stone) && this.isNotStoneOfColor(stone, c)) {
                this.tillOwnStone(d, nextPos, c, line);
            }
        }
        return line;
    }

    private void tillOwnStone(Directions d, int position, Color c, List<Tile> line) {
        line.add((Tile)this.board.getComponent(position));
        if (this.isInBoundsInDirection(position, d)) {
            int nextPos = this.nextTilePositionInDirection(position, d);
            Stone stone = ((Tile)this.board.getComponent(nextPos)).getStone();
            if (this.isRealStone(stone)) {
                if (this.isNotStoneOfColor(stone, c)) {
                    this.tillOwnStone(d, nextPos, c, line);
                }
                return;
            }
        }
        line.clear();
    }

    private boolean freePosition(Stone s) {
        return s == null;
    }

    private int nextTilePositionInDirection(int position, Directions d) {
        return position + this.board.getBoardSize() * d.getY() + d.getX();
    }

    private boolean isNotStoneOfColor(Stone s, Color c) {
        return !Objects.equals(s.getColor(), c);
    }

    private boolean isRealStone(Stone stone) {
        return stone != null && !Objects.equals(stone.getColor(), Stone.HIGHLIGHT);
    }

    private boolean isInBoundsInDirection(int i, Directions d) {
        return this.yIsInBoundsInDirection(i, d) && this.xIsInBoundsInDirection(i, d);
    }

    private boolean yIsInBoundsInDirection(int i, Directions d) {
        int boardSize = this.board.getBoardSize();
        return (i / boardSize + d.getY() >= 0 && i / boardSize + d.getY() < boardSize);
    }

    private boolean xIsInBoundsInDirection(int i, Directions d) {
        int boardSize = this.board.getBoardSize();
        return (i % boardSize + d.getX() >= 0 && i % boardSize + d.getX() < boardSize);
    }
}
