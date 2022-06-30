package sk.stuba.fei.uim.oop.game;

import lombok.Getter;

public enum Directions {
    UP(0, -1),
    UP_RIGHT(1, -1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, 1),
    DOWN(0, 1),
    DOWN_LEFT(-1, 1),
    LEFT(-1, 0),
    UP_LEFT(-1, -1);

    @Getter
    private final int x;
    @Getter
    private final int y;

    Directions(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
