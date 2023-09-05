/*
 * @author Yajat Sharma 
 * @submitted to: Mr. Ryan McComiskey 
 * Computer Science 30
 * 
 * @Project description: This is a text-based tennis game that uses object oriented programming concepts and procedural programming concepts to create the gameplay, player objects, and ball object.
 */
import java.util.*;

import GameComponents.ComputerPlayer;
import GameComponents.Player;

public class GamePlay {
    //these variable are declared as global to ensure that multiple methods in this class have acces to them
    private static Scanner keyb = new Scanner(System.in);
    private static Random rand = new Random();
    private static int menuChoice = 0, menuChoiceServe = 0;
    private static Player userPlayer = new Player();
    private static ComputerPlayer compPlayer = new ComputerPlayer();
    public static void main(String[] args) throws Exception {
        initializeGame();
    }

    /**
     * a method to initialize the game that displays the welcome menu to the user and based on the user's choice, it either calls the other game methods or displays a "thank you" message
     */
    public static void initializeGame() {
        String userName = "";
        System.out.println("Welcome to Centre Court");
        System.out.println("1. Start Game\n" + "2. Quit\n");

        if(inputAlgo(menuChoice) == 1) {
            System.out.println("Enter the name for your player: ");
            userName = keyb.nextLine();
            userPlayer.setName(userName);
            coinToss();
            playGame();
        } else {
            System.out.println("Thank you for playing centre court");
        }
    }

    /**
     * This method contains the actual gameplay which controls the entire match 
     */
    public static void playGame() {
        boolean gameOver = false;

        //a do-while loop is used to keep the game running until one of the player wins
        do {
            if(userPlayer.isServing()) {
                System.out.println(userPlayer.getName() + " is serving");
                sleep(2000);
                //the user player serves
                userPlayer.serve();
                //the while loop alternatively calls the .shot() methods of the userPlayer and compPlayer until one of them wins the point
                while(!userPlayer.isPointLost() && !compPlayer.isPointLost()) {
                    compPlayer.shot();
                    if(compPlayer.isPointLost()) {
                        continue;
                    } else {
                        userPlayer.shot();
                    }
                }
                //increase the score based on the who won the point
                if(compPlayer.isPointLost()) {
                    userPlayer.setScore(userPlayer.getScore() + 1);
                } else {
                    compPlayer.setScore(compPlayer.getScore() + 1);
                }
                //alternate the serve
                userPlayer.setServing(false);
                compPlayer.setServing(true);
                //switch the pointLost attribute of both players to false indicating that none of them have lost the upcoming point yet
                userPlayer.setPointLost(false);
                compPlayer.setPointLost(false);
            } else {
                for(int i = 3; i >= 0; i--) {
                    if(i == 3) {
                    System.out.print("The computer is serving");
                    } else {
                        System.out.print(".");
                    }
                    sleep(1000);
                }
                System.out.println(" ");
                sleep(2000);
                //the computer player serves
                compPlayer.serve();
                //the while loop alternatively calls the .shot() methods of the userPlayer and compPlayer until one of them wins the point
                while(!userPlayer.isPointLost() && !compPlayer.isPointLost()) {
                    userPlayer.shot();
                    if(userPlayer.isPointLost()) {
                        continue;
                    } else {
                        compPlayer.shot();
                    }
                }
                //increase the score based on the who won the point
                if(compPlayer.isPointLost()) {
                    userPlayer.setScore(userPlayer.getScore() + 1);
                } else {
                    compPlayer.setScore(compPlayer.getScore() + 1);
                }
                //alternate the serve
                userPlayer.setServing(true);
                compPlayer.setServing(false);
                //switch the pointLost attribute of both players to false indicating that none of them have lost the upcoming point yet
                userPlayer.setPointLost(false);
                compPlayer.setPointLost(false);
            }
            sleep(1000);
            //print out the score
            System.out.println("\nThe score is: \n" + userPlayer.getName() + " - " + userPlayer.getScore() + " & " + compPlayer.getName() + " - " + compPlayer.getScore());
            sleep(1000);
            if(userPlayer.getScore() == 11 || compPlayer.getScore() == 11) {
                gameOver = true;
                if(userPlayer.getScore() > compPlayer.getScore()) {
                    System.out.println("Winner: " + userPlayer.getName() + ", Congratulations!");
                } else {
                    System.out.println("Winner: Computer");
                    sleep(1000);
                    System.out.println("There is always a next time!");
                }
            } else {
                //print out player stats
                System.out.println("\nPlayer stats: ");
                System.out.println(userPlayer);
                System.out.println(compPlayer);
                System.out.println(" ");
            }
        } while(!gameOver);
        System.out.println("Thank you for playing centre court");
    }

    //A general method to add a pause between outputing results
    //Primarily exists to reduce code repetition
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //A method to conduct the pre-match coin toss
    public static void coinToss() {
        String menuChoiceToss;

        //A countdown before the toss begins (JUST FOR FUN!)
        for(int i = 3; i >= 1; i--) {
            if(i == 3) {
            System.out.print("The coin toss will take place in ..." + i + " ");
            } else {
                System.out.print(i + " ");
            }
            sleep(1000);
        }
        System.out.println(" ");
        System.out.println("Would you like heads (h) or tails (t)?"); //Ask the user for their choice
        menuChoiceToss = keyb.nextLine(); //record their choice 
        boolean result = randomTrueOrFalse();
        //match the user choice and the random true or false value, the chance of matching is 50/50 and therefore, the toss is random
        if((result && menuChoiceToss.equals("h")) || (result == false) && menuChoiceToss.equals("t")) {
            System.out.println("You have won the toss.");
            System.out.println("Press 1 to serve or press 2 to give the first serve to the opponent."); //Ask the user if they want to serve first or give the first serve to the opponent 
            menuChoiceServe = inputAlgo(menuChoiceServe); //record user choice
            //set the serving attribute for both players according to the user's choice
            if(menuChoiceServe == 1) {
                userPlayer.setServing(true);
                compPlayer.setServing(false);
            } else {
                userPlayer.setServing(false);
                compPlayer.setServing(true);
            }
        } else {
            System.out.println("You have lost the toss.");
            //there is a 50/50 chance that the opponent will choose to serve to give the first serve to the user
            //the serving attribute of both the players will be set according to the random true or false
            if(randomTrueOrFalse()) {
                System.out.println("The opponent has decided to serve.");
                compPlayer.setServing(true);
                userPlayer.setServing(false);
            } else {
                System.out.println("The opponent has decided to give you the first serve.");
                userPlayer.setServing(true);
                compPlayer.setServing(false);
            }
        }
    }

    //A method that returns either a boolean value of true or false
    public static boolean randomTrueOrFalse() {
        int randomNum = rand.nextInt(2);
        if(randomNum == 1) {
            return true;
        } else {
            return false;
        }
    }

    //A method that is used to run the input algorithm which is the same algorithm that is used everytime which reduces the amount of code required
    public static int inputAlgo(int menuChoice) {
        boolean inputType = true;
        while(inputType) {
            if(keyb.hasNextInt()) {
                menuChoice = keyb.nextInt();
            } else {
                keyb.next();
                continue;
            }
            inputType = false;
            keyb.nextLine();
        }
        return menuChoice;
    }
}