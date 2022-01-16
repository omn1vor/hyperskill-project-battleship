package battleship;

import java.util.regex.Pattern;

class Point {

    int row;
    int col;

    Point(String coordinates) {
        if (!isValidString(coordinates)) {
            throw new IllegalArgumentException(String.format("Error! You entered the wrong coordinates: %s!", coordinates));
        }
        int x = getRowFromString(coordinates);
        int y = getColFromString(coordinates);
        if (!isValidPoint(x, y)) {
            throw new IllegalArgumentException(String.format("Error! You entered the wrong coordinates: %s!", coordinates));
        }
        this.row = x;
        this.col = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    public static Point[] readCoordinates(String[] arr) {
        final int pair = 2;
        Point[] coordinates = new Point[pair];
        for (int i = 0; i < pair; i++) {
            coordinates[i] = new Point(arr[i]);
        }
        return coordinates;
    }

    private boolean isValidString(String coordinate) {
        return Pattern.matches("^[A-J]\\d+$", coordinate.toUpperCase());
    }

    private boolean isValidPoint(int row, int col) {
        return row > 0 && row <= Board.size && col > 0 && col <= Board.size;
    }

    private int getRowFromString(String coordinate) {
        char c = coordinate.toUpperCase().charAt(0);
        final char rowStart = 'A' - 1;
        return c - rowStart;
    }

    private int getColFromString(String coordinate) {
        return Integer.parseInt(coordinate.substring(1));
    }


}
