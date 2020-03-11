
/**
 * The Ship class act as a model class to set and get the fields
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class Ship
{
    // instance variables - replace the example below with your own
    private String shipName;
    private int xPos;
    private int yPos;
    private int noOfHitsMade;
    private int noOfHitsNeeded;
   
    /**
     * Default constructor for the Ship class
     */
    public Ship()
    {
    	shipName = "Titanic";
        xPos = 0;
        yPos = 0;
        noOfHitsMade = 0;
        noOfHitsNeeded = 0;
    }
    
    /**
     * Parameterized constructor for the ship class
     * @param newShipName pass current ship name of the particular ship
     * @param newXPos pass current X position of the particular ship
     * @param newYPos pass current Y position of the particular ship
     * @param newNoOfHitsMade pass current number of hits made for the particular ship
     * @param newNoOfHitsNeeded pass current number of hits neede for the particular ship
     */
    public Ship(String newShipName, int newXPos, int newYPos, int newNoOfHitsMade, int newNoOfHitsNeeded)
    {
    	shipName = newShipName;
    	xPos = newXPos;
    	yPos = newYPos;
    	noOfHitsMade = newNoOfHitsMade;
    	noOfHitsNeeded = newNoOfHitsNeeded;
    }
    
    /**
     * This method sets the ship name of the particular ship
     * @param newShipName pass current ship name of the particular ship
     */
    public void setShipName(String newShipName)
    {
        shipName = newShipName;
    }
    
    /**
     * This method sets the X position of the particular ship
     * @param newXPos pass current X position of the particular ship
     */
    public void setXPos(int newXPos)
    {
        xPos= newXPos;
    }
    
    /**
     * This method sets the Y position of the particular ship
     * @param newYPos pass current Y position of the particular ship
     */
    public void setYPos(int newYPos)
    {
        yPos = newYPos;
    }
    
    /**
     * This method sets the number of hits made for the particular ship
     * @param newNoOfHitsMade pass the value of current number of hits made for the particular ship 
     */
    public void setNoOfHitsMade(int newNoOfHitsMade)
    {
        noOfHitsMade = newNoOfHitsMade;
    }
    
    /**
     * This method sets the number of hits needed for the particular ship
     * @param newNoOfHitsMade pass the value of current number of hits needed for the particular ship 
     */
    public void setNoOfHitsNeeded(int newNoOfHitsNeeded)
    {
        noOfHitsNeeded = newNoOfHitsNeeded;
    }
    
    /**
     * This method gets the current ship name of the particular ship
     * @return returns the current ship name of the particular ship
     */
    public String getShipName()
    {
        return shipName;
    }
    
    /**
     * This method gets the current X position of the particular ship
     * @return returns the current X position of the particular ship
     */
    public int getXPos()
    {
       return xPos;
    }
    
    /**
     * This method gets the current Y position of the particular ship
     * @return returns the current Y position of the particular ship
     */
    public int getYPos()
    {
        return yPos;
    }
    
    /**
     * This method gets the value of number of hits made for the particular ship
     * @return returns the value of current number of hits made for the particular ship
     */
    public int getNoOfHitsMade()
    {
        return noOfHitsMade;
    }
    
    /**
     * This method gets the value of number of hits needed for the particular ship
     * @return returns the value of current number of hits needed for the particular ship 
     */
    public int getNoOfHitsNeeded()
    {
        return noOfHitsNeeded;
    }
   
}
