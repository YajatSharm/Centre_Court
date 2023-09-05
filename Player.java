package GameComponents;
/*
 * @author Yajat Sharma 
 * @submitted to: Mr. Ryan McComiskey 
 * Computer Science 30
 * 
 * @Class Description: This class is created to do anything and everything with the tennis player in the game. It contains all the necessary fields and the methods that would be needed to create and use a player. 
 */
import java.util.*;

// import Ball;

public class Player {
    //the access modifier for these attributes is set to protected to ensure that they are inherited by the computerPlayer class 
    //and are available in the GameComponents package but are otherwise hidden and can only be accessed using getters and setters
    protected Scanner keyb = new Scanner(System.in);
    private String name;
    protected double stamina;
    protected int score;
    protected boolean serving;
    protected boolean pointLost;
    protected static Random rand = new Random();
    protected double[][] serveProbabilities = {{0.95, 0.85, 0.65, 0.25, 0.02}, {0.60, 0.65, 0.75}};
    protected double[][] shotProbabilities = {{0.05, 0.25, 0.65, 0.85, 0.95}, {0.21, 0.55, 0.90}};
    protected int[] speeds = {20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};

    protected Ball ball1 = new Ball();

    //object constructor to use if no name is provided
    public Player() {
        this.setName("No name provided");
        this.setStamina(100);
        this.setScore(0);
    }

    //object constructor to use when a player name is provided
    public Player(String name) {
        this.setName(name);
        this.setStamina(100);
        this.setScore(0);
    }

    //the player stamina along with their name is printed
    @Override
    public String toString() {
        String returnStr = "";
        return returnStr += this.getName() + " has " + this.getStamina() + "% of his stamina left";
    }

    public void setPointLost(boolean tOrF) {
        this.pointLost = tOrF;
    }

    public boolean isPointLost() {
        return this.pointLost;
    }

    public void setServing(boolean trueOrFalse) {
        this.serving = trueOrFalse;
    }

    public boolean isServing() {
        return this.serving;
    }

    /**
     * this setter ensures that score attribute is never set to any value above 11 or any value less than 0
     * @param score is the parameter that will be used to set the score attribute 
     */
    public void setScore(int score) {
        if(score >= 0 && score <= 11) {
            this.score = score;
        } else if (score > 11) {
            this.score = score;
        } else {
            this.score = 0;
        }
    }

    public int getScore() {
        return this.score;
    }

    public void setName(String s) {
        if(!s.equals("") && s != null) {
            this.name = s;
        } else {
            System.out.println("Please enter a valid name.");
        }
    }

    public String getName() {
        return this.name;
    }

    //setter to set the stamina, stamina can be between 0 and 100
    public void setStamina(double stamina) {
        if(stamina >= 0 && stamina <= 100) {
            this.stamina = stamina;
        } else if (stamina > 100) {
            this.stamina = 100;
        } else {
            this.stamina = 0;
        }
    }

    public double getStamina() {
        return this.stamina;
    }

    public void forehand() {
        this.setStamina(this.getStamina() - 5);
    }

    public void backhand() {
        this.setStamina(this.getStamina() - 6);
    }
    /**
     * a general method that implements the input algorithm required to deal with common input bugs
     * @param menuChoice is the variable that will hold the input value everytime this method is called
     * @return menuChoice to use the method as a way to set another variable's value
     */
    public int inputAlgo(int menuChoice) {
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

    /**
     * A method to control the shot of the user controlled player
     * The user will be asked to choose the type of shot and the direction of the shot they would like to make which will result in two probability values being chosen from the 2-D array
     * These values will then be passed as arguments to the inOrOut(), in the end the user will be notified if their shot was in or Out
     */
    public void shot() {
        int menuChoiceShot = 0, menuChoiceDirection = 0;
        double probabilityShotType = 0, probabilityShotDirection = 0;
        System.out.println("Choose your shot: ");
        System.out.println("1. Backhand\n" + "2. Forehand\n");
        menuChoiceShot = inputAlgo(menuChoiceShot);

        System.out.println("Choose the direction of your shot: \n" + "1. Down the line\n" + "2. Centre\n" + "3. Crosscourt\n");
        menuChoiceDirection = inputAlgo(menuChoiceDirection);

        //makes a selection from the shot probabilities array based on the user's choice of the shot
        switch (menuChoiceShot) {
            case 1:
            probabilityShotType = shotProbabilities[0][0];
            forehand();
            break;

            case 2: 
            probabilityShotType = shotProbabilities[0][1];
            backhand();
            break;

            default:
            System.out.println("Please choose a valid option.");
            break;
        }

        //makes a selection from the shot probabilties array based on the user's choice of the shot direction
        switch(menuChoiceDirection) {
            case 1: 
            probabilityShotDirection = shotProbabilities[1][0];
            break;

            case 2:
            probabilityShotDirection = shotProbabilities[1][1];
            break;

            case 3:
            probabilityShotDirection = shotProbabilities[1][2];
            break;

            default:
            System.out.println("Please choose a valid option");
            break;
        }

        int indexSpeed = rand.nextInt(17) + 5;
        ball1.setSpeed(speeds[indexSpeed]);

        //the ball object determines if the ball has landed in or out 
        if(ball1.inOrOut(probabilityShotType, probabilityShotDirection)){
            System.out.println("Your shot was in! The ball travelling at a speed of " + ball1.getSpeed() + " km/h");
            this.setPointLost(false);
        } else {
            System.out.println("Your shot was out!");
            this.setPointLost(true);
        }
        this.setStamina(this.getStamina() - 6);
    }

    /**
     * A similar method as the shot() which is designed to control the user controlled player's serve
     * The user will make two choices to select the strength of their serve and the positioning of their serve. These choices will result in two probabilities being generated.
     * The two probabilties will then be passed as arguments to the inOrOut() method, in the end the user will be notified if their serve was in or out
     */
    public void serve() {
        int menuChoiceServeStrength = 0, menuChoiceServePosition = 0;
        double probabilityStrength = 0, probabilityPosition = 0;
        System.out.println("Choose the strength of your 1st serve:");
        System.out.println("1. Just put it in\n" + "2. Medium Strength\n" + "3. Hard Serve\n" + "4. Super Hard Serve\n" + "5. Andy Roddick (Warning: The probability of this serve going in is really low)");
        menuChoiceServeStrength = inputAlgo(menuChoiceServeStrength);

        System.out.println("Where would you like to hit your serve in the service box?");
        System.out.println("1. Far corner\n" + "2. Centre\n" + "3. In corner\n");
        menuChoiceServePosition = inputAlgo(menuChoiceServePosition);
        this.setStamina(this.getStamina() - 10);

        //makes a selection from the serve probabilites array based on the user choice of serve strength
        switch (menuChoiceServeStrength) {
            case 1:
            probabilityStrength = serveProbabilities[0][0];
            ball1.setSpeed(80);
            break;

            case 2: 
            probabilityStrength = serveProbabilities[0][1];
            ball1.setSpeed(100);
            break;

            case 3:
            probabilityStrength = serveProbabilities[0][2];
            ball1.setSpeed(140);
            break;

            case 4:
            probabilityStrength = serveProbabilities[0][3];
            ball1.setSpeed(170);
            break;

            case 5:
            probabilityStrength = serveProbabilities[0][4];
            ball1.setSpeed(250);
            break;

            default:
            System.out.println("Please choose a valid option.");
            break;
        }

        //makes a selection based on the user choice of the serve position from the serve probabilites array
        switch(menuChoiceServePosition) {
            case 1: 
            probabilityPosition = serveProbabilities[1][0];
            break;

            case 2:
            probabilityPosition = serveProbabilities[1][1];
            break;

            case 3:
            probabilityPosition = serveProbabilities[1][2];
            break;

            default:
            System.out.println("Please choose a valid option");
            break;
        }

        //the ball object determines if the ball has landed in or out
        if(ball1.inOrOut(probabilityStrength, probabilityPosition)){
            System.out.println("Your shot was in! The ball is travelling at a speed of " + ball1.getSpeed() + " km/h");
            this.setPointLost(false);
        } else {
            System.out.println("Your shot was out!");
            this.setPointLost(true);
        }
    }
}