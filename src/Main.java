import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int Y_SIZE = 10; //indicates the x-axis length
    private static final int X_SIZE = 10; //indicates the y-axis length
    private static final int SHIPS_NUM = 5; //default number of ships
    private static final String HUMAN_SHIP = "@"; //to represent human ship on the board
    private static final String COMPUTER_SHIP = "2"; //to represent computer ship on the board
    private static final String HUMAN_SUNK_SHIP = "x"; //to represent human sunk ships on the board
    private static final String COMPUTER_SUNK_SHIP = "!"; //to represent computer sunk ships on the board
    private static final String MISS_HIT = "-"; //to represent human missed on the board
    private static String[][] ocean; //default ocean board in String array list for representing the y and x coordinate respectively
    private static String[][] computerBoard;
    /* to represent the allocation of computer ships on the ocean;
    at the same time, to use for coordinates cross checking during the game play
     */
    public static void main(String[] args) {
        int x; //x-axis counter variable
        int y; //y-axis counter variable

        ocean = new String[Y_SIZE][X_SIZE];
        computerBoard = new String[Y_SIZE][X_SIZE];

        //to initial the game board and computer board respectively
        for (y = 0; y < Y_SIZE; y++) {
            for (x = 0; x < X_SIZE; x++) {
                ocean[y][x] = " ";
                computerBoard[y][x] = " ";
            }
        }
        printBoard(ocean);

        //to deploy human ships on game board
        System.out.println("Deploy your ships:");
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= SHIPS_NUM; i++) {
            x = -1;
            y = -1;
            while ((x == -1 && y == -1) || (!(x == -1 && y == -1) && (ocean[y][x] == HUMAN_SHIP))) {
                if (!(x == -1 && y == -1) && (ocean[y][x] == HUMAN_SHIP)) {
                    System.out.println("Invalid location. Please choose a different location.");
                    x = -1;
                    y = -1;
                }

                while (x < 0 || x >= X_SIZE) {
                    System.out.print("Enter X coordinate for your " + i + ". ship:");
                    x = scanner.nextInt();
                }
                while (y < 0 || y >= Y_SIZE) {
                    System.out.print("Enter Y coordinate for your " + i + ". ship:");
                    y = scanner.nextInt();
                }
            }
            ocean[y][x] = HUMAN_SHIP;
        }
        printBoard(ocean);

        //to deploy computer ships
        System.out.println("Computer is deploying ships");
        Random random = new Random();
        for (int i = 1; i <= SHIPS_NUM; i++) {
            x = random.nextInt(X_SIZE);
            y = random.nextInt(Y_SIZE);
            while ((ocean[y][x] == HUMAN_SHIP) || (computerBoard[y][x] == COMPUTER_SHIP)) {
                x = random.nextInt(X_SIZE);
                y = random.nextInt(Y_SIZE);
            }
            computerBoard[y][x] = COMPUTER_SHIP;
            System.out.println(i + ". ship DEPLOYED");
        }
        System.out.println("------------------------------");

        //uncomment line below if want to see and make sure got computer ships deployed
        //printBoard(computerBoard);

        //to start playing the game
        boolean humanTurn = true;
        int humanShips = SHIPS_NUM;
        int computerShips = SHIPS_NUM;
        while (!(humanShips == 0 || computerShips == 0)) {
            if (humanTurn) {
                System.out.println("Your TURN");
                x = -1;
                y = -1;
                while (x < 0 || x >= X_SIZE) {
                    System.out.print("Enter X coordinate: ");
                    x = scanner.nextInt();
                }
                while (y < 0 || y >= Y_SIZE) {
                    System.out.print("Enter Y coordinate: ");
                    y = scanner.nextInt();
                }
                if ((computerBoard[y][x] == COMPUTER_SHIP) && (ocean[y][x] == " ")) {
                    System.out.println("Boom! You sunk the ship!");
                    ocean[y][x] = COMPUTER_SUNK_SHIP;
                    computerShips--;
                } else if (ocean[y][x] == HUMAN_SHIP) {
                    System.out.println("Oh no, you sunk you own ship :(");
                    ocean[y][x] = HUMAN_SUNK_SHIP;
                    humanShips--;
                } else {
                    System.out.println("Sorry, you missed");
                    if (ocean[y][x] == " ")
                        ocean[y][x] = MISS_HIT;
                }
            } else {
                System.out.println("COMPUTER'S TURN.");
                x = random.nextInt(X_SIZE);
                y = random.nextInt(Y_SIZE);
                if (ocean[y][x] == HUMAN_SHIP) {
                    System.out.println("The Computer sunk one of your ships");
                    ocean[y][x] = HUMAN_SUNK_SHIP;
                    humanShips--;
                } else if ((computerBoard[y][x] == COMPUTER_SHIP)  && (ocean[y][x] == " ")) {
                    System.out.println("The Computer sunk one of its own ships");
                    ocean[y][x] = COMPUTER_SUNK_SHIP;
                    computerShips--;
                } else {
                    System.out.println("Computer missed");
                }

                printBoard(ocean);

                System.out.println("");
                System.out.println("Your ships: " + humanShips + "  |  Computer ships: " + computerShips);
            }
            System.out.println("");
            humanTurn = !humanTurn;
        }

        printBoard(ocean);

        System.out.println("");
        System.out.println("Your ships: " + humanShips + "  |  Computer ships: " + computerShips);
        System.out.println("");

        if (humanShips == 0) {
            System.out.println("Oh No! You lose the battle :(");
        } else {
            System.out.println("Hooray! You win the battle :)");
        }
    }

    private static void printBoard(String[][] board) {
        for (int y = 0; y < Y_SIZE+2; y++) {
            if (y==0 || y==Y_SIZE+1) {
                for (int x = 0; x < X_SIZE+2; x++) {
                    if (x==0 || x==X_SIZE+1) {
                        System.out.print("   ");
                    } else {
                        if (x-1 >= 0 && x-1 < X_SIZE)
                            System.out.print(x-1 + " ");
                    }
                }
            } else {
                if (y-1 >= 0 && y-1 < Y_SIZE) {
                    for (int x = 0; x < X_SIZE+2; x++) {
                        if (x==0) {
                            System.out.print(y-1 + "|");
                        } else if(x==X_SIZE+1) {
                            System.out.print( " |" + String.valueOf(y-1));
                        } else {
                            System.out.print(" " + board[y-1][x-1]);
                        }
                    }
                }
            }

            System.out.println();
        }
    }
}
