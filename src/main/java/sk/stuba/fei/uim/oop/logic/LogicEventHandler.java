package sk.stuba.fei.uim.oop.logic;

import sk.stuba.fei.uim.oop.game.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LogicEventHandler extends UniversalAdapter {
    private final GameLogic logic;
    private Tile pressedTile;

    public LogicEventHandler(GameLogic logic) {
        this.logic = logic;
        this.pressedTile = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                this.logic.getFrame().dispose();
                System.exit(0);
                break;
            case KeyEvent.VK_R:
                this.logic.restart();
                this.logic.repaint();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.commandIs(e, "RESTART")) {
            this.logic.restart();
        }
        if (this.commandIs(e, "TIMER")) {
            this.logic.stopTimer();
            this.logic.continuePlayerMove(this.logic.aiChooseTile());
        }
        this.logic.repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getSource() instanceof JComboBox) && (e.getItem() instanceof String)) {
            this.logic.getBoard().setBoardSize(Integer.parseInt((String)e.getItem()));
            this.logic.restart();
            this.logic.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            tile.setBackground(Tile.COLOR);
            this.logic.continuePlayerMove(tile);
            this.logic.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            this.pressedTile = tile;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            if (Objects.equals(tile, this.pressedTile)) {
                tile.setBackground(Tile.COLOR);
                this.logic.continuePlayerMove(tile);
                this.logic.repaint();
            }
        }
        this.pressedTile = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            tile.setBackground(Tile.HIGHLIGHT);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            tile.setBackground(Tile.COLOR);
            tile.setRadius(0);
        }
        this.pressedTile = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            tile.computeRadius(e.getX(), e.getY());
            tile.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        if (tile.isEnabled()) {
            if (!Objects.equals(tile.getBackground(), Tile.HIGHLIGHT)) {
                tile.setBackground(Tile.HIGHLIGHT);
            }
            tile.computeRadius(e.getX(), e.getY());
            tile.repaint();
        }
    }

    private boolean commandIs(ActionEvent e, String s) {
        return Objects.equals(e.getActionCommand(), s);
    }
}
