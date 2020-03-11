import java.util.ArrayList;
import java.util.Scanner;
/**
 * ShipList class act as an intermediate class between Ship class and Game class
 * to add the player/computer ship details in the Array List
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class ShipList
{
    private ArrayList<Ship> ships;

    /**
     * Constructor for objects of class ShipList
     */
    public ShipList()
    {
    	ships = new ArrayList<Ship>();   
    }
    
    /**
     * This method sets the ship details in the Array List
     * It is applicable for both player and computer ship
     * @param newShips pass array list to set the ship details
     */
    public void setShips(ArrayList<Ship> newShips)
    {
        ships = newShips;
    }
    
    /**
     * This method returns the ship details in the Array List 
     * It is applicable for both player and computer ship
     * @return returns the array list of the player/computer ship
     */
    public ArrayList<Ship> getShips()
    {
        return ships;
    }
    
    /**
     * This method adds the player ship detail in the array list
     * @param shipName pass players current ship name
     * @param xPos pass ship X coordinate
     * @param yPos pass ship Y coordinate
     * @param randomMultipleHitsNeeded pass multiple hits needed value for the particular ship
     */
    public void playerShipDetail(String shipName,int xPos,int yPos, int randomMultipleHitsNeeded )
    {
    	int noOfHitsMade=0;
    	
    	try
    	{
    		Ship ship = new Ship();	
        	ship.setShipName(shipName);    		
        	ship.setXPos(xPos);    		
        	ship.setYPos(yPos);
       		ship.setNoOfHitsNeeded(randomMultipleHitsNeeded);    		
        	ship.setNoOfHitsMade(noOfHitsMade);    		
        	ships.add(ship);   		
    	}
    	catch(Exception e)
    	{
    		System.out.println("playerShipDetail Exception: "+e);
    	}
    	   	
    }
    
    /**
     *  This method adds the computer ship detail in the array list
     * @param shipName pass computers current ship name 
     * @param randomXPosition pass randomly generated ship X coordinate
     * @param randomYPosition pass randomly generated ship Y coordinate
     * @param randomMultipleHits pass multiple hits needed value for the particular ship
     */
    public void computerShipDetail(String shipName, int randomXPosition, int randomYPosition, int randomMultipleHits)
    
    {
    	
    	int hitsMade =  0;
    	
    	try
    	{
    		Ship ship = new Ship();
        	ship.setShipName(shipName);
        	ship.setXPos(randomXPosition); 	
        	ship.setYPos(randomYPosition);    	
        	ship.setNoOfHitsNeeded(randomMultipleHits);
        	ship.setNoOfHitsMade(hitsMade);  	
        	ships.add(ship);
    	}
    	catch(Exception e)
    	{
    		System.out.println("computerShipDetail Exception: "+e);
    	}
    	
    }    
}
