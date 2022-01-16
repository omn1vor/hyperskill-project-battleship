package battleship;

import java.util.Arrays;

class Board {

    final static int size = 10;
    private final char[][] matrix = new char[size][size];
    private final Ship[] ships = new Ship[ShipType.values().length];
    private final char fog = '~';
    private final char shipIntact = 'O';
    private final char shipHit = 'X';
    private final char miss = 'M';
    private int shipsAlive;

    public Board() {
        shipsAlive = ShipType.values().length;
        for (char[] arr : matrix) {
            Arrays.fill(arr, fog);
        }
    }

    void print(boolean fogOfWar) {
        char c;
        for (int row = 0; row < size; row++) {
            // printing column legend
            if (row == 0) {
                System.out.print("  ");
                for (int i = 1; i <= size; i++) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            for (int col = 0; col < size; col++) {
                if (col == 0) {
                    // first column is row legend
                    System.out.print((char) ('A' + row) + " ");
                }
                c = fogOfWar && matrix[row][col] == shipIntact ? fog : matrix[row][col];
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    void placeShip(Ship ship) {
        for (Ship shipOnBoard : ships) {
            if (shipOnBoard == null) {
                continue;
            }
            if (ship.crosses(shipOnBoard)) {
                throw new IllegalArgumentException("Error! You placed it too close to another one.");
            }
        }
        ships[ship.type.ordinal()] = ship;
        for (int row = ship.start.row; row <= ship.end.row; row++) {
            for (int col = ship.start.col; col <= ship.end.col; col++) {
                matrix[row - 1][col - 1] = shipIntact;
            }
        }
    }

    ShotResult placeShot(Point point) {
        char target = matrix[point.row - 1][point.col - 1];
        switch (target) {
            case shipIntact:
            case shipHit:
                matrix[point.row - 1][point.col - 1] = shipHit;
                return registerShipHit(point);
            case fog:
            case miss:
                matrix[point.row - 1][point.col - 1] = miss;
                return ShotResult.MISS;
            default:
                throw new IllegalArgumentException("Error! Unknown area type hit! Please contact support!");
        }
    }

    private ShotResult registerShipHit(Point point) {
        for (Ship ship : ships) {
            if (ship.hasPoint(point)) {
                boolean itWasAlive = ship.lives > 0;
                if (itWasAlive) {
                    ship.lives--;
                }
                if (itWasAlive && ship.lives <= 0) {
                    if (shipsAlive > 0) {
                        shipsAlive--;
                    }
                    return ShotResult.KILL;
                }
            }
        }
        return ShotResult.HIT;
    }

    public int getShipsAlive() {
        return shipsAlive;
    }
}
