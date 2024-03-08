import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {

    private char playerChar;
    private char enemyChar;
    private Scanner scanner;
    private GameState gameState;
    public char[][] board;

    public TicTacToe(char playerChar, char enemyChar) {
        this.playerChar = playerChar;
        this.enemyChar = enemyChar;
        scanner = new Scanner(System.in);
        gameState = new GameState(playerChar);
    }

    public void gameLoop(int depth) {
        boolean gameIsRunning = true;
        do {
            board = gameState.getBoard();
            printBoard(board);

            if (isGameOver()) {
                System.out.println("GAME OVER");
                //printBoard(board); // print the finished board
                gameIsRunning = false;
            } else {
                if (gameIsRunning) {
                    playerMove();
                    printBoard(board);
                }

                if (gameIsRunning) {
                    Move enemyMove = getBestMoveForEnemy(gameState, depth, enemyChar);
                    gameState.makeMove(enemyMove);
                    waitForSeconds(1);
                }
            }
        } while (gameIsRunning);
    }


    public void playerMove() {
        int row, col;
        do {
            System.out.println("Enter your move - first the row, then column ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ');
        board[row][col] = playerChar; //add the player char to the selected tile
        gameState.updateBoard(board); //update the current Gamestate with the new board
    }

    public Move getBestMoveForEnemy(GameState gameState, int depth, char charTurn) {
        Move bestMove = null;
        int bestEval = (charTurn == 'X') ? -100 : 100;

        ArrayList<Move> moveList = gameState.getMoveList();

        for (Move move : moveList) {
            gameState.makeMove(move);
            //int score = minMax(gameState, depth - 1, charTurn);
            int score = alphaBeta(gameState, depth - 1, charTurn,-100,100);
            gameState.undoMove(move);

            //'X'=max    'O'=min
            if ((charTurn == 'X' && score > bestEval) || (charTurn == 'O' && score < bestEval)) {
                bestEval = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public int minMax(GameState gameState, int depth, char charTurn) {
        // if the depth is 0 or the game is over, return the static evaluation of the current state
        if (depth == 0 || isGameOver()) {
            return gameState.staticEvaluation();
        }

        int bestEval = (charTurn == 'X') ? -100 : 100;

        ArrayList<Move> moveList = gameState.getMoveList();

        for (Move move : moveList) {
            gameState.makeMove(move);
            int score = minMax(gameState, depth - 1, (charTurn == 'X') ? 'O' : 'X'); //ternary operator - swap player
            gameState.undoMove(move);

            if ((charTurn == 'X' && score > bestEval) || (charTurn == 'O' && score < bestEval)) {
                bestEval = score;
            }
        }

        return bestEval;
    }

    public int alphaBeta(GameState gameState, int depth, char charTurn, int alpha, int beta) {

        // if the depth is 0 or the game is over, return the static evaluation of the current state
        if (depth == 0 || isGameOver()) {
            return gameState.staticEvaluation();
        }

        int bestEval;

        // max player X, find the highest eval
        if (charTurn == 'X') {
            bestEval = Integer.MIN_VALUE;

            // Find moves based on the gameState
            ArrayList<Move> moveList = gameState.getMoveList();

            for (Move move : moveList) {
                gameState.makeMove(move);
                int score = alphaBeta(gameState, depth - 1, 'O', alpha, beta);
                gameState.undoMove(move); //undoMove - to rollback the gameState after calculations

                bestEval = Math.max(bestEval, score);
                alpha = Math.max(alpha, bestEval);

                if (beta <= alpha) {
                    break;  // cut off
                }
            }
        } else {
            // min player O, find the lowest eval
            bestEval = Integer.MAX_VALUE;

            // Find moves based on the gameState
            ArrayList<Move> moveList = gameState.getMoveList();

            for (Move move : moveList) {
                gameState.makeMove(move);
                int score = alphaBeta(gameState, depth - 1, 'X', alpha, beta);
                gameState.undoMove(move);

                bestEval = Math.min(bestEval, score);
                beta = Math.min(beta, bestEval);

                if (beta <= alpha) {
                    break;  // yay cut off
                }
            }
        }
        return bestEval;
    }

    public boolean isGameOver() {
        return isBoardFull() || isWinner(enemyChar) || isWinner(playerChar);
    }


    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        //System.out.println("DRAW - Board has been filled");
        return true;
    }

    public boolean isWinner(char playerSymbol) {
        // Check rækker og columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == playerSymbol && board[i][1] == playerSymbol && board[i][2] == playerSymbol) ||
                    (board[0][i] == playerSymbol && board[1][i] == playerSymbol && board[2][i] == playerSymbol)) {
                return true;  // playerSymbol won
            }
        }
        // Check diagonalt
        if ((board[0][0] == playerSymbol && board[1][1] == playerSymbol && board[2][2] == playerSymbol) ||
                (board[0][2] == playerSymbol && board[1][1] == playerSymbol && board[2][0] == playerSymbol)) {
            return true; // playerSymbol won
        }

        return false;
    }

    public void printBoard(char[][] boardToPrint) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(boardToPrint[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
