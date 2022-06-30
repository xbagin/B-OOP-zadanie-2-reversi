package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Reversi {
    public Reversi() throws HeadlessException {
        JFrame frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 850);
        frame.setLayout(new BorderLayout());

        InputStream inputStream = this.getClass().getResourceAsStream("/icon.png");
        if (inputStream != null) {
            try {
                frame.setIconImage(new ImageIcon(ImageIO.read(inputStream)).getImage());
            } catch (IOException ignored) { }
        }

        GameLogic logic = new GameLogic(frame);

        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(logic.getHandler());
        restartButton.setFocusable(false);

        JComboBox<String> dropDown = new JComboBox<>(new String[]{"6", "8", "10", "12"});
        dropDown.addItemListener(logic.getHandler());
        dropDown.setFocusable(false);

        JPanel menu = new JPanel();
        menu.setBackground(Color.LIGHT_GRAY);
        menu.setLayout(new GridLayout(1, 4));
        menu.add(logic.getPlayerLabel());
        menu.add(restartButton);
        menu.add(logic.getBoardLabel());
        menu.add(dropDown);

        frame.addKeyListener(logic.getHandler());
        frame.add(logic.getBoard(), BorderLayout.CENTER);
        frame.add(menu, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }
}
