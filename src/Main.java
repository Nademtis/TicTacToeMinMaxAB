public class Main {
    public static void main(String[] args) {
        char playerChar = 'X';
        char enemyChar = 'O';
        int depth = 3;

        TicTacToe game = new TicTacToe(playerChar, enemyChar);
        game.gameLoop(depth);

        /*char [][] testBoard = new char[][]{
                {' ', ' ', ' '},
                {' ', 'O', ' '},
                {' ', ' ', ' '}
        };*/
        //staticEvaluation(board);
    }

    //method below is just for test evaluating a given board above
    /*static void staticEvaluation(char[][] testBoard) {
        int[][] fieldValueTable = {{3, 2, 3},
                {2, 4, 2},
                {3, 2, 3}};
        double result = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (testBoard[r][c] == 'X') result += fieldValueTable[r][c];  // 'X' is Maximizer
                if (testBoard[r][c] == 'O') result -= fieldValueTable[r][c];  // 'O' is Minimizer
            }
        }
        System.out.println(result);
    }*/
}