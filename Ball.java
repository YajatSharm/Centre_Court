/*
 * @author Yajat Sharma 
 * @submitted to: Mr. Ryan McComiskey 
 * Computer Science 30
 * 
 * @Class Description: This class contains information about the ball object. The ball object has its speed and a boolean value that indicates if it has fallen inside the court or out 
 */
package GameComponents;
import java.util.*;

public class Ball {
    int speed;
    private static Random rand = new Random();

    /**
     * 
     * @param probabilityStrength the probabibility of the ball landing in the court based on the strength of the shot
     * @param probabilityPosition the probabibility of the ball landing in the court based on the court position of the shot
     * @return a boolean value based on the combined probabilities to know if the ball landed in or not
     */
    public boolean inOrOut(double probabilityStrength, double probabilityPosition) {
        double probability = probabilityStrength * probabilityPosition;
        int randomNum = rand.nextInt();
        return (double) randomNum < probability; 
    }

    /**
     * a setter for the ball speed which ensures that the speed is between 0 and 253
     * @param speed is the parameter that is used to set the speed of the ball
     */
    public void setSpeed(int speed) {
        if(speed >= 0 && speed <= 253) {
            this.speed = speed;
        } else if(speed > 253) {
            this.speed = 253;
        } else {
            this.speed = 0;
        }
    }

    public int getSpeed() {
        return this.speed;
    }
}
