package sk.stuba.fei.uim.oop.logic;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.game.Stone;
import sk.stuba.fei.uim.oop.game.Tile;
import sk.stuba.fei.uim.oop.gui.Board;
import sk.stuba.fei.uim.oop.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameLogic {
    @Getter
    private final JFrame frame;
    @Getter
    private final Board board;
    @Getter
    private final JLabel playerLabel;
    @Getter
    private final JLabel boardLabel;
    @Getter
    private final LogicEventHandler handler;
    @Getter
    private final Player[] players;
    @Getter @Setter
    private Player currentPlayer;
    private int currentPlayerIndex;

    private Map<Tile, List<Tile>> possibleTiles;
    private final Timer timer;
    private final EndChecker endChecker;

    private static final int AI_DELAY = 1500;
    private static final String ACTIVE_LABEL = "Active player: ";
    private static final String SIZE_LABEL = "Current board size: ";
    public static final String WINNER_LABEL = "Winner is: ";

    public GameLogic(JFrame frame) {
        this.frame = frame;
        this.handler = new LogicEventHandler(this);
        this.board = new Board(Board.DEFAULT_BOARD_SIZE, handler);
        this.timer = new Timer(GameLogic.AI_DELAY, handler);
        this.timer.setActionCommand("TIMER");

        this.players = new Player[] {
                new Player("You", Color.WHITE, "white", Player.HUMAN),
                new Player("PC", Color.BLACK, "black", Player.AI)
        };
        this.currentPlayerIndex = Player.FIRST;
        this.currentPlayer = this.players[this.currentPlayerIndex];

        this.addStartStones();
        int boardSize = this.board.getBoardSize();
        this.boardLabel = new JLabel(GameLogic.SIZE_LABEL + boardSize + "x" + boardSize, SwingConstants.CENTER);
        this.playerLabel = new JLabel(GameLogic.ACTIVE_LABEL + this.currentPlayer.getText(), SwingConstants.CENTER);

        this.endChecker = new EndChecker(this);
        this.playerMove();
    }

    public void repaint() {
        this.board.repaint();
    }

    public void restart() {
        this.timer.stop();
        this.board.removeAll();
        this.board.create();
        this.addStartStones();
        this.board.revalidate();
        this.updateBoardLabel();
        for (Player player : this.players) {
            player.setSkipMove(false);
            player.clearNumberOfStones();
        }
        this.currentPlayerIndex = Player.FIRST;
        this.currentPlayer = this.players[this.currentPlayerIndex];
        this.updatePlayerLabel();
        this.playerMove();
    }

    public void continuePlayerMove(Tile newStoneTile) {
        if (this.currentPlayer.isHuman()) {
            this.disableTiles();
        }
        if (newStoneTile != null) {
            this.changeStones(newStoneTile);
        }
        this.nextPlayer();
        this.playerMove();
    }

    public Tile aiChooseTile() {
        int mostStones = 0;
        Tile newStoneTile = null;
        for (Tile key : this.possibleTiles.keySet()) {
            int currentStones = this.possibleTiles.get(key).size();
            if (currentStones > mostStones) {
                mostStones = currentStones;
                newStoneTile = key;
            }
        }
        return newStoneTile;
    }

    public void stopTimer() {
        this.timer.stop();
    }

    private void updateBoardLabel() {
        int boardSize = this.board.getBoardSize();
        this.boardLabel.setText(GameLogic.SIZE_LABEL + boardSize + "x" + boardSize);
    }

    private void updatePlayerLabel() {
        this.playerLabel.setText(GameLogic.ACTIVE_LABEL + this.currentPlayer.getText());
    }

    private void playerMove() {
        this.updatePlayerLabel();
        this.updatePossibleTiles();
        if (this.currentPlayer != null) {
            if (this.currentPlayer.isHuman()) {
                this.enableTiles();
            } else {
                this.timer.start();
            }
        }
    }

    private void updatePossibleTiles() {
        this.possibleTiles = this.board.getPossibleTiles(this.currentPlayer.getColor());
        if (this.possibleTiles.isEmpty()) {
            this.currentPlayer.setSkipMove(true);
            this.endChecker.gameEndsCheckOrNextPlayer();
        } else {
            this.currentPlayer.setSkipMove(false);
            this.highlightTiles();
        }
    }

    private void highlightTiles() {
        this.possibleTiles.keySet().forEach(tile -> tile.setStone(new Stone(this.board, Stone.HIGHLIGHT)));
    }

    private void enableTiles() {
        this.possibleTiles.keySet().forEach(tile -> tile.setEnabled(true));
    }

    private void disableTiles() {
        this.possibleTiles.keySet().forEach(tile -> tile.setEnabled(false));
    }

    private void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
        this.currentPlayer = this.players[this.currentPlayerIndex];
    }

    private void addStartStones() {
        int mid = this.board.getBoardSize() / 2;
        int playerPos = Player.FIRST;
        int[][] stonePositions = new int[][] {{mid - 1, mid}, {mid - 1, mid - 1}, {mid, mid - 1}, {mid, mid}};
        for (int[] stonePos : stonePositions) {
            this.addStoneAt(this.players[playerPos].getColor(), stonePos[0], stonePos[1]);
            playerPos = (playerPos + 1) % this.players.length;
        }
    }

    private void addStoneAt(Color c, int row, int col) {
        int boardSize = this.board.getBoardSize();
        ((Tile)this.board.getComponent(boardSize * row + col)).setStone(new Stone(this.board, c));
    }

    private void changeStones(Tile newStoneTile) {
        newStoneTile.changeStone(this.currentPlayer.getColor());
        this.possibleTiles.get(newStoneTile).forEach(tile -> tile.changeStone(this.currentPlayer.getColor()));
        this.possibleTiles.remove(newStoneTile);
        this.possibleTiles.keySet().forEach(Tile::removeStone);
    }
}
