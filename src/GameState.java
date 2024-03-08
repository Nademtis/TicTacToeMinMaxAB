import java.util.ArrayList;

public class GameState {
    private char[][] board;
    private char charTurn;

    public GameState(char charTurn) {
        this.charTurn = charTurn;
        this.board = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
    }

    public ArrayList<Move> getMoveList() {
        ArrayList<Move> moveList = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') {
                    moveList.add(new Move(r, c, charTurn));
                }
            }
        }
        //System.out.println("this moveList was: " + moveList.size() + " size");
        return moveList;
    }

    public void makeMove(Move move) {
        board[move.row][move.col] = move.playerChar;
        switchTurn();
    }

    public void undoMove(Move move) {
        board[move.row][move.col] = ' ';
        switchTurn();
    }

    private void switchTurn() {
        charTurn = (charTurn == 'X') ? 'O' : 'X';
    }

    public int staticEvaluation() {
        int[][] fieldValueTable =
                        {{3, 2, 3},
                        {2, 4, 2},
                        {3, 2, 3}};
        int result = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == 'X') result += fieldValueTable[r][c];  // 'X' is Maximizer
                if (board[r][c] == 'O') result -= fieldValueTable[r][c];  // 'O' is Minimizer
            }
        }
        //System.out.println(result);
        return result;
    }


    public char[][] getBoard() {
        return board;
    }

    public void updateBoard(char[][] board) {
        this.board = board;
        switchTurn();
    }
    /*public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        //System.out.println("DRAW - Board has been filled");
        return true;
    }*/
}
