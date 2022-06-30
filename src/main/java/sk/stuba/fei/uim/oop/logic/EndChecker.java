package sk.stuba.fei.uim.oop.logic;

import sk.stuba.fei.uim.oop.gui.DialogIcon;
import sk.stuba.fei.uim.oop.player.Player;

import javax.swing.*;
import java.util.Objects;

public class EndChecker {
    private final GameLogic logic;

    public EndChecker(GameLogic logic) {
        this.logic = logic;
    }

    public void gameEndsCheckOrNextPlayer() {
        if (this.allPlayersSkipped()) {
            if (this.logic.getCurrentPlayer() != null) {
                this.logic.stopTimer();
                this.getWinner();
            }
            this.logic.setCurrentPlayer(null);
        } else {
            this.logic.continuePlayerMove(null);
        }
    }

    private boolean allPlayersSkipped() {
        boolean someoneCanPlay = false;
        for (Player player : this.logic.getPlayers()) {
            if (!player.skippedMove()) {
                someoneCanPlay = true;
                break;
            }
        }
        return !someoneCanPlay;
    }

    private void getWinner() {
        this.computePlayersStones();
        Player winner = this.findWinner();
        if (winner != null) {
            boolean draw = this.checkDraw(winner);
            this.logic.getPlayerLabel().setText((draw)? "Draw!" : (GameLogic.WINNER_LABEL + winner.getText()));
            this.logic.repaint();
            JOptionPane.showMessageDialog(this.logic.getFrame(), this.createDialogString(draw, winner),
                    "Game ends", JOptionPane.PLAIN_MESSAGE, (draw)? null : new DialogIcon(winner.getColor()));
        }
    }

    private void computePlayersStones() {
        for (Player player : this.logic.getPlayers()) {
            player.computeNumberOfStones(this.logic.getBoard());
        }
    }

    private Player findWinner() {
        Player winner = null;
        for (Player player : this.logic.getPlayers()) {
            if (winner == null) {
                winner = player;
            } else if (player.getNumberOfStones() > winner.getNumberOfStones()) {
                winner = player;
            }
        }
        if (winner != null && winner.getNumberOfStones() == 0) {
            return null;
        }
        return winner;
    }

    private boolean checkDraw(Player winner) {
        for (Player player : this.logic.getPlayers()) {
            if (!Objects.equals(player, winner)) {
                if (player.getNumberOfStones() == winner.getNumberOfStones()) {
                    return true;
                }
            }
        }
        return false;
    }

    private String createDialogString(boolean draw, Player winner) {
        StringBuilder s = new StringBuilder();
        if (draw) {
            s.append("\nDraw!");
        } else {
            s.append("\nWinner is ").append(winner.getText());
        }
        s.append("\n\nStones:\n");
        for (Player p : this.logic.getPlayers()) {
            s.append(p.getText()).append(": ").append(p.getNumberOfStones()).append("\n");
        }
        return s.toString();
    }
}
