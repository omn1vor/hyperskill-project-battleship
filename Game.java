package battleship;

import java.util.Scanner;

enum State {
    PLAYER_TURN,
    END
}

enum ShotResult {
    HIT,
    MISS,
    KILL
}

public class Game {

    private final Player p1;
    private final Player p2;
    private State state;
    private Player currentPlayer;
    private Player oppositePlayer;

    public Game() {
        this.p1 = new Player("Player 1");
        this.p2 = new Player("Player 2");
    }

    public void start() {
        currentPlayer = p1;
        oppositePlayer = p2;
        placeShips();
        passTurn();
        placeShips();
        passTurn();

        state = State.PLAYER_TURN;
        while (state != State.END) {
            System.out.println();
            oppositePlayer.board.print(true);
            System.out.println("-".repeat((Board.size + 1) * 2));
            currentPlayer.board.print(false);
            System.out.printf("%s, it's your turn:", currentPlayer.name);
            System.out.println();
            processTurn();
            passTurn();
        }

    }

    private void placeShips() {
        System.out.printf("%s, place your ships on the game field", currentPlayer.name);
        System.out.println();
        currentPlayer.board.print(true);
        Scanner scanner = new Scanner(System.in);
        for (ShipType shipType : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", shipType.title, shipType.size);
            System.out.println();
            Ship ship;
            boolean success = false;
            while (!success) {
                String input = scanner.nextLine();
                try {
                    ship = new Ship(shipType, IO.readCoordinatesAsStrings(input));
                    currentPlayer.board.placeShip(ship);
                    success = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() + " Try again:");
                }
            }
            currentPlayer.board.print(false);
        }
    }

    private void passTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        currentPlayer = currentPlayer == p1 ? p2 : p1;
        oppositePlayer = oppositePlayer == p1 ? p2 : p1;
    }

    private void processTurn() {
        Scanner scanner = new Scanner(System.in);

        if (state == State.PLAYER_TURN) {
            ShotResult result = null;
            boolean success = false;
            while (!success) {
                String input = scanner.nextLine();
                try {
                    result = oppositePlayer.board.placeShot(new Point(input));
                    success = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() + " Try again:");
                }
            }
            switch (result) {
                case HIT:
                    System.out.println("You hit a ship!");
                    break;
                case MISS:
                    System.out.println("You missed!");
                    break;
                case KILL:
                    if (oppositePlayer.board.getShipsAlive() <= 0) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        state = State.END;
                    } else {
                        System.out.println("You sank a ship!");
                    }
                    break;
            }
        }
    }

}
