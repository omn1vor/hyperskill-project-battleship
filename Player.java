package battleship;

public class Player {

    final String name;
    final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

}
