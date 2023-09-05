/*
 * @author Yajat Sharma 
 * @submitted to: Mr. Ryan McComiskey 
 * Computer Science 30
 * 
 * @Class Description: This is a subclass of the Player class which defines the computer player. The shot() and serve() methods are written differently so that the computer makes its own decisions. 
 */
package GameComponents;
public class ComputerPlayer extends Player {
    String difficulty;

    public ComputerPlayer() {
        super("Computer");
        this.setStamina(100);
        this.setScore(0);
    }

    //a general method which returns either true or false with 50/50 chance
    private static boolean randomTrueOrFalse() {
        int randomNum = rand.nextInt(2);
        if(randomNum == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the unique shot method for the computer player which takes into account it current stamina and makes decisions based on that
     */
    public void shot() {
        double probabilityShotType, probabilityShotDirection;
        if(stamina > 60) {
            //if the stamina is over 60, there is 33.3% chance of the computer hitting one of the fast shots
            if(randomTrueOrFalse()) {
                probabilityShotType = shotProbabilities[0][2];
                ball1.setSpeed(90);
            } else {
                if(randomTrueOrFalse()) {
                    probabilityShotType = shotProbabilities[0][3];
                    ball1.setSpeed(75);
                } else {
                    probabilityShotType = shotProbabilities[0][4];
                    ball1.setSpeed(100);
                }
            }
        } else {
            //if the stamina is below 60, the computer will hit one of the slower shots
            if(randomTrueOrFalse()) {
                probabilityShotType = shotProbabilities[0][0];
                ball1.setSpeed(60);
            } else {
                probabilityShotType = shotProbabilities[0][1];
                ball1.setSpeed(50);
            }
        }

        //The computer randomly chooses one of the shot directions which corresponds to one of the probabilities in the array
        if(rand.nextInt(2) < 0.33) {
            probabilityShotDirection = shotProbabilities[1][0];
        } else if (rand.nextInt(2) < 0.33) {
            probabilityShotDirection = shotProbabilities[1][1];
        } else {
            probabilityShotDirection = shotProbabilities[1][2];
        }
        //the ball object determines if the ball has landed in or not
        if(ball1.inOrOut(probabilityShotType, probabilityShotDirection)) {
            System.out.println("The opponent has returned your shot at a speed of " + ball1.getSpeed() + " km/h");
            this.setPointLost(false);
        } else {
            System.out.println("The opponent's shot was not in.");
            this.setPointLost(true);
        }
        this.setStamina(this.getStamina() - 5);
    }

    /**
     * This unique serve() method works on similar principles as the shot() method
     */
    public void serve() {
        double probabilityStrength;
        double probabilityPosition;
        if(this.stamina > 50) {
            //if the stamina is more than 50, the computer will hit one of the faster serves 
            if(randomTrueOrFalse()) {
                probabilityStrength = serveProbabilities[0][2];
                ball1.setSpeed(90);
            } else {
                if(randomTrueOrFalse()) {
                    probabilityStrength = serveProbabilities[0][3];
                    ball1.setSpeed(75);
                } else {
                    probabilityStrength = serveProbabilities[0][4];
                    ball1.setSpeed(100);
                }
            }
        } else {
            //if the stamina is less than 50, the computer will hit one of the slower serves
            if(randomTrueOrFalse()) {
                probabilityStrength = serveProbabilities[0][0];
                ball1.setSpeed(60);
            } else {
                probabilityStrength = serveProbabilities[0][1];
                ball1.setSpeed(50);
            }
        }

        //the computer randomly chooses one of the serve positions 
        if(rand.nextInt(2) < 0.33) {
            probabilityPosition = serveProbabilities[1][0];
        } else if (rand.nextInt(2) < 0.33) {
            probabilityPosition = serveProbabilities[1][1];
        } else {
            probabilityPosition = serveProbabilities[1][2];
        }
        //the ball object then determines if the ball has landed in and displays the approriate message
        if(ball1.inOrOut(probabilityStrength, probabilityPosition)) {
            System.out.println("The opponent has served the ball at a speed of " + ball1.getSpeed() + " km/h");
            this.setPointLost(false);
        } else {
            System.out.println("The opponent's serve was not in.");
            this.setPointLost(true);
        }
        this.setServing(false);
        this.setStamina(this.getStamina() - 10);
    }
}