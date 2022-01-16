package battleship;

enum ShipType {
    CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    final String title;
    final int size;

    ShipType(String title, int size) {
        this.title = title;
        this.size = size;
    }
}

public class Ship {

    final ShipType type;
    final Point start;
    final Point end;
    int lives;

    Ship(ShipType type, String[] coordinates) {
        Point[] arr = Point.readCoordinates(coordinates);
        checkCoordinates(type, arr[0], arr[1]);

        this.type = type;
        this.lives = type.size;
        if (arr[0].row <= arr[1].row && arr[0].col <= arr[1].col) {
            this.start = arr[0];
            this.end = arr[1];
        } else {
            this.start = arr[1];
            this.end = arr[0];
        }
     }

    @Override
    public String toString() {
        return "Ship{" +
                "type=" + type +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    boolean crosses(Ship ship) {
        boolean rowCross = end.row >= ship.getStart().row - 1
                && start.row <= ship.getEnd().row + 1;
        boolean colCross = end.col >= ship.getStart().col - 1
                && start.col <= ship.getEnd().col + 1;
        return rowCross && colCross;
    }

    boolean hasPoint(Point point) {
        return point.row >= start.row && point.row <= end.row
                && point.col >= start.col && point.col <= end.col;
    }

    private void checkCoordinates(ShipType type, Point start, Point end) {
        if (start.row != end.row && start.col != end.col) {
            throw new IllegalArgumentException("Error! Wrong ship location!");
        }
        int len = Math.max(Math.abs(start.row - end.row), Math.abs(start.col - end.col)) + 1;
        if (len != type.size) {
            throw new IllegalArgumentException(String.format("Error! Wrong length of the %s!", type.title));
        }
    }

}
