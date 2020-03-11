import java.util.Random;
/**
 * CoordinateGenerator class helps to generate the random values to some fields for the game.
 *
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class CoordinateGenerator
{
    // instance variables - replace the example below with your own
    private int minimumValue;
    private int maximumValue;


    /**
     * Constructor for objects of class CoordinateGenerator
     */
    public CoordinateGenerator()
    {
       
    }
    
    /**
     * Method to generate random numbers
     * @param min the minimum value
     * @param max the maximum value
     * @return the random number
     */
    public int generateCoordinates(int min, int max)
    {
       Random random=new Random();
       //int randomValue=random.nextInt(max + 1 - min) + min;
       int randomValue=(int)(Math.random() * (max - min)) + min;

       return randomValue;
    }
    
    /**
     * Sets minimum valus
     * @param newMinValue
     */
    public void setMinimumValue(int newMinValue)
    {
        minimumValue = newMinValue;
    }
    
    /**
     * Sets maximum value
     * @param newMaxValue
     */
    public void setMaximumValue(int newMaxValue)
    {
        maximumValue = newMaxValue;
    }
    
    /**
     * Gets minimum value
     * @return
     */
    public int getMinimumValue()
    {
        return minimumValue;
    }
    
    /**
     * gets maximum value
     * @return
     */
    public int getMaximumValue()
    {
        return maximumValue;
    }
    

  
}
