import java.io.IOException;
import java.util.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.Math;

/**
 * This class act as a client class  with all the main methods to start the application
 *
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class Game
{
    // instance variables - replace the example below with your own
    private ShipList playerShips;
    private ShipList computerShips;
    
 
    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        playerShips = new ShipList();
        computerShips = new ShipList();
    }
    
    /**
     * This method starts the application to play
     */
    public void start()
    {
    
        //Manual Input
        
        /*int gridWidthHeight=4;
        boolean multipleHitsNeeded=false;
        boolean compShipVisible=true;
        int shipSize=3;*/
        
        int gridWidthHeight = 0;
        boolean multipleHitsNeeded = false;
        boolean compShipVisible=false;
        int shipSize=0;
        
        Input input = new Input();
        try
        {
            FileIO write = new FileIO();
            String[] array = new String[4];
            array= write.readInputs();
            if (array != null)
            {
                gridWidthHeight= Integer.parseInt(array[0]);
                multipleHitsNeeded= Boolean.parseBoolean(array[1]);
                compShipVisible= Boolean.parseBoolean(array[2]);
                shipSize = Integer.parseInt(array[3]);
                
            }
                printHeading(gridWidthHeight, shipSize, multipleHitsNeeded, compShipVisible);
                input.pressAnyKeyToContinue();
                callPlayerShip(playerShips, shipSize,gridWidthHeight, multipleHitsNeeded);
                callComputerShip(computerShips, shipSize, gridWidthHeight, multipleHitsNeeded);
                playGame(gridWidthHeight, multipleHitsNeeded, compShipVisible );            
        }
                
        catch(IOException e)
        {
            System.out.println("Exception is: "+e);
        }
        
        
    }
    
    /**
     * This method plays the game, once it ends, it writes the results to the text file.
     * @param gridWidthHeight determines the height and width of the grid
     * @param multipleHitsNeeded determines whether multiple hits need to be true/false
     * @param computerShipVisible determines whether the computer ships can be visible to the player
     */
    public void playGame( int gridWidthHeight,boolean multipleHitsNeeded, boolean computerShipVisible)
    {
        int playerScore = 0;
        int computerScore = 0;
        boolean playerDestroyedShips = false;
        boolean computerDestroyedShips = false;
        
        Scanner console = new Scanner(System.in);
        Input input = new Input();
        FileIO fileIO = new FileIO();
        
        try
        {
            for (int i=0; i<500; i++)
            {           
                input.pressAnyKeyToContinue();
                
                System.out.println("Beginning Round: "+(i+1));
                System.out.println("Player Score: " + playerScore);
                System.out.println("Computer Score: "+computerScore);
                System.out.println("ENEMY SHIP ONLY SHOWN WHEN DEMO MODE IS ON!!");
                
                System.out.println("Displaying the Player Grid:");
                System.out.println("");
                initGrid(playerShips, gridWidthHeight, multipleHitsNeeded, true);
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("");
                
            
                System.out.println("Displaying the Computer Grid");
                System.out.println("");
                initGrid(computerShips, gridWidthHeight,multipleHitsNeeded, computerShipVisible);
                System.out.println("");
                System.out.println("");
                
                playerScore = inputPlayerGrid(computerShips, gridWidthHeight, playerScore);
                computerScore = inputComputerGrid(playerShips, gridWidthHeight, computerScore); 
                
                playerDestroyedShips=checkShipDestroyed(playerShips);
                computerDestroyedShips=checkShipDestroyed(computerShips);
                
                if (playerDestroyedShips == true)
                {
                    
                    System.out.println("Congratulations, Computer Wins");
                    System.out.println("");
                    break;
                }
                else if (computerDestroyedShips == true)
                {
                    System.out.println("");
                    System.out.println("Congratulations, Player Wins");
                    System.out.println("");
                    break;
                }
            }
            System.out.println("Player Score: "+playerScore);
            System.out.println("Computer Score: "+computerScore);
            fileIO.writeOutput(playerScore , computerScore);
            
        }
        catch(Exception e) 
        {
            System.out.println("playGame Error Message: " +e);
        }
   }
    
   
    /**
     *This method gets the computers ship details and 
     *makes the player to play and the score will also be updated  
     * 
     * @param localShipList can be computerShips to retrieve the respective ship list details
     * @param gridWidthHeight is passed as a parameter to mentions X and Y position range
     * @param score will be updated for player based on their hits 
     * @return returns the final score of the player.
     */
    public int inputPlayerGrid(ShipList localShipList , int gridWidthHeight, int score)
    {
        //pass the computer shiplist to check whether the xPos and yPos is available
        Scanner console = new Scanner(System.in);
        int xPos = 0;
        int yPos = 0;
        boolean flag;
        Validation validation = new Validation();
        
        try
        {

            System.out.println("Player to  make a guess");
            xPos = validation.integerValidation(console, "x", gridWidthHeight);         
            yPos = validation.integerValidation(console, "y", gridWidthHeight);
            System.out.println("");
            
           flag = hitShipCoordinate(localShipList, xPos, yPos);
             if (flag == true)
         {           
             System.out.println("Wow, Player Hits");
             System.out.println("");
             score += 10;            
         }
         else
         {           
              System.out.println("Player Miss!!!");
              System.out.println("");
         }
            
        }
        catch(Exception e)
        {
            System.out.println("inputPlayerGrid Messsage: "+e);
        }
        
                
        return score;       
    }
   
    /**
     * This method gets the player ship details and
     * makes the computer to play and the score will also be updated
     * 
     * @param localShipList can be playerShips to retrieve the respective ship list details
     * @param gridWidthHeight is passed as a parameter to mentions X and Y position range
     * @param score  will be updated for the computer based on their hits 
     * @return  returns the final score of the computer
     */
    public int inputComputerGrid(ShipList localShipList , int gridWidthHeight, int score)
    {
        //pass the player shiplist to check whether the xPos and yPos is available      
        int xPos = 0;
        int yPos = 0;
        boolean flag;
        
        try
        {
            System.out.println("Computer to  make a guess");
            xPos = computerRandomPos(gridWidthHeight);          
            yPos = computerRandomPos(gridWidthHeight);
            
            System.out.println("");
            System.out.println("Computer x guess: "+xPos);
            System.out.println("Computer y guess: "+yPos);
            System.out.println("");
            
            //pass the player shiplist to check whether the xPos and yPos is available
            flag = hitShipCoordinate(localShipList, xPos, yPos);
            if (flag == true)
            {           
                System.out.println("Wow, Computer Hits");
                System.out.println("");
                score += 10;            
            }
            else
            {           
                System.out.println("Computer Miss!!!");
                System.out.println("");
            }
        }
        catch(Exception e)
        {
            System.out.println("inputComputerGrid Messsage: "+e);
        }                        
        return score;       
    }
    
    /**
     * This method checks whether the particular ship is destroyed or not,
     * it is applicable for both the player and computer ships
     * 
     * @param localShipList can be player/computer ship list
     * @return returns true if destroyed, else false
     */
    public boolean checkShipDestroyed(ShipList localShipList)
    {
        boolean flag = false;
        int currentNoOfHitsNeeded = 0;
        int countShip = 0;
        int destroyedShip = 0;
        
        try
        {
            while (countShip < localShipList.getShips().size())
            {
                currentNoOfHitsNeeded =getParticularShipNoOfHitsNeeded(localShipList, countShip);
                if (currentNoOfHitsNeeded == 0)
                {
                    destroyedShip++;
                }
                countShip++;
            }
            if(destroyedShip == localShipList.getShips().size())
            {
                flag = true;
            }
            else
            {
                flag = false;
            }
        }
        catch(Exception e)
        {
            System.out.println("checkShipDestroyed Messsage: "+e);
        }
                        
        return flag;
    }
    
    //Checks whether hit that particular ship or not
    /**
     * This method checks whether the player/computer hits their ships each other
     * This method can be used by both player and computer.
     * 
     * @param localShipList can be player/computer ship list
     * @param randomXPostion gets the respective x-position from player/computer
     * @param randomYPosition gets the respective y-position from player/computer 
     * @return returns true if hits the particular ship, else false
     */
    public boolean hitShipCoordinate(ShipList localShipList, int randomXPostion, int randomYPosition)
    {
        boolean flag = false;
        int countShips = 0;
        int currentXPosition = 0;
        int currentYPosition = 0;
        int currentNoOfHitsNeeded = 0;
        int currentNoOfHitsMade = 0;
        
        try
        {
            while (countShips < localShipList.getShips().size())
            {           
                currentXPosition = getParticularShipXPosition(localShipList, countShips);
                currentYPosition = getParticularShipYPosition(localShipList, countShips);
                currentNoOfHitsNeeded =getParticularShipNoOfHitsNeeded(localShipList, countShips);
                currentNoOfHitsMade = getParticularShipNoOfHitsMade(localShipList, countShips);
                
                if (randomXPostion == currentXPosition && randomYPosition == currentYPosition )
                {
                    if (currentNoOfHitsNeeded != 0)
                    {
                        localShipList.getShips().get(countShips).setNoOfHitsNeeded(currentNoOfHitsNeeded - 1);
                        localShipList.getShips().get(countShips).setNoOfHitsMade(currentNoOfHitsMade + 1);
                        flag = true;
                    }
                    else
                    {
                        System.out.println("");
                        System.out.println("Ship has already been destroyed");
                        System.out.println("");
                    }
                }
                countShips++;
            
            }
        }
        catch(Exception e)
        {
            System.out.println("hitShipCoordinate Exception: "+e);
        }
        
        return flag;      
    }
    
    /**
     * This method initializes the grid and it is a common grid for both player and computer
     * Each ship coordinates go and sits to their respective position in the grid
     * @param localShipList can player/computer ship list
     * @param gridWidthHeight determines the grid width and height
     * @param multipleHitsNeeded determines whether multiple hits need to be true/false
     * @param shipVisible determines whether the ships need to be visible to the player and computer each other
     */
   public void initGrid(ShipList localShipList, int gridWidthHeight,boolean multipleHitsNeeded, boolean shipVisible)
   {
       String water="~";
       String placeShip="0";
       String damageShip = "D";
       String destroyShip = "X";
       
       int newGridWidthHeight= gridWidthHeight * gridWidthHeight;
       ArrayList<String> putWater = new ArrayList<String>();
      
       try
       {
           for (int i=0; i<newGridWidthHeight; i++)
           {
               putWater.add(water);
           }
           
          if(multipleHitsNeeded == true)
          {
              for (int j=0; j<localShipList.getShips().size(); j++)
               {
                   int xPos=getParticularShipXPosition(localShipList, j);
                   int yPos=getParticularShipYPosition(localShipList, j);
                   int currentNoOfHitsNeeded= getParticularShipNoOfHitsNeeded(localShipList, j);
                   String currentShipName= getParticularShipName(localShipList, j);
                   int z= ((gridWidthHeight * yPos) + (xPos));
                   
                   if (shipVisible == true)
                   {
                    if ((currentNoOfHitsNeeded > 0 && currentNoOfHitsNeeded < 3))
                       {
                        putWater.set(z, damageShip);            
                       }
                       else if (currentNoOfHitsNeeded == 0)
                       {
                        putWater.set(z, destroyShip);
                        
                        System.out.println("Unfortunately, Ship "+currentShipName+" has been destroyed!");
                        System.out.println("");
                       }
                       else 
                       {
                        putWater.set(z, placeShip);
                       }
                   }
                   else
                   {
                      if ((currentNoOfHitsNeeded > 0 && currentNoOfHitsNeeded < 3))
                       {
                        putWater.set(z, damageShip);            
                       }
                       else if (currentNoOfHitsNeeded == 0)
                       {
                        putWater.set(z, destroyShip);
                        System.out.println("Unfortunately, Ship "+currentShipName+" has been destroyed!");
                        System.out.println("");
                       }
                       else
                       {
                        putWater.set(z, water);
                       }
                   }
                   
               }
               
              displayGrid(putWater, gridWidthHeight);
          }
          else
          {
              for (int j=0; j<localShipList.getShips().size(); j++)
               {
                   int xPos=getParticularShipXPosition(localShipList, j);
                   int yPos=getParticularShipYPosition(localShipList, j);
                   int currentNoOfHitsNeeded= getParticularShipNoOfHitsNeeded(localShipList, j);
                   String currentShipName= getParticularShipName(localShipList, j);
                   int z= ((gridWidthHeight * yPos) + (xPos));
                   
                  if (shipVisible == true)
                  {
                    if (currentNoOfHitsNeeded == 0)
                       {
                        putWater.set(z, destroyShip);                   
                        System.out.println("Unfortunately, Ship "+currentShipName+" has been destroyed!");
                        System.out.println("");
                       }
                     else
                       {
                        putWater.set(z, placeShip);
                       }
                  }
                  else
                  {
                    if (currentNoOfHitsNeeded == 0)
                       {
                        putWater.set(z, destroyShip);                   
                        System.out.println("Unfortunately, Ship "+currentShipName+" has been destroyed!");
                        System.out.println("");
                       }
                       else
                       {
                        putWater.set(z, water);
                       }
                  }           
               } 
               displayGrid(putWater, gridWidthHeight);
          }
       }
       catch(Exception e)
       {
           System.out.println("initGrid Exception: "+e);
       }   
   }
    
   /**
    * A common method for both player and computer to display their respective grid
    * @param getGrid sends the player/computer grid list respectively
    * @param gridWidthHeight determines the grid width and height of the player/computer
    */
    public void displayGrid(ArrayList<String> getGrid, int gridWidthHeight)
    {
        try
        {
             for (int start = 0; start < getGrid.size(); start += gridWidthHeight) 
             {
                    int end = Math.min(start + gridWidthHeight, getGrid.size());
                    List<String> subList = getGrid.subList(start, end);
                    
                    for (int i=0; i<subList.size(); i++)
                    {
                        System.out.print(subList.get(i));
                    }
                    
                    System.out.println("");
                }
        }
        catch(Exception e)
        {
            System.out.println("displayGrid Exception: "+e);
        }
       
        
    }
    
    
   
   /**
    * This method prints the introductory display of the game
    * @param gridWidthHeight determines the height and width of the grid
    * @param multipleHitsNeeded determines whether multiple hits need to be true/false
    * @param computerShipVisible determines whether the computer ships can be visible to the player
    * @param shipSize determines the size of the ships
    */
    public void printHeading(int gridWidthHeight, int shipSize,boolean multipleHitsNeeded, boolean compShipVisible)
    {
        System.out.println("+==========================================================================+");
        System.out.println("|                                                                          |");
        System.out.println("|                  Welcome to the BattleShip game                          |");
        System.out.println("|                                                                          |");
        System.out.println("+==========================================================================+");
        
        System.out.println("The game will use the grid size defined in the settings file");
        System.out.println("Playing gridsize set as ( 15 * 15 ) :" +gridWidthHeight); 
        System.out.println("Maximum number of ships allowed as: " +shipSize);
        System.out.println("Multiple hits allowed per ship set as:  "+ multipleHitsNeeded);
        System.out.println("Compute Ships Visible: "+compShipVisible);
        
    }
    
    /**
     * Calls the player ship to enter the player ship details
     * @param localPlayerShipList sends the playerShips object to store the ship list details
     * @param shipSize sends the size of the ship
     * @param gridWidthHeight sends grid width and height of the ship
     * @param multipleHitsNeeded pass the boolean values of multiple hits needed (true/false)
     */
    
    public void callPlayerShip(ShipList localPlayerShipList, int shipSize,int gridWidthHeight,boolean multipleHitsNeeded)
    {
        int randomMultipleHitsNeeded;
        try
        {
            if (multipleHitsNeeded == true)
            {               
                randomMultipleHitsNeeded=generateRandomMultipleHitsNeeded();                            
                genericPlayerShip(localPlayerShipList, shipSize, gridWidthHeight, randomMultipleHitsNeeded);
                
            }
            else
            {
                int otherMultipleHitsNeeded = 1;
                genericPlayerShip(localPlayerShipList, shipSize, gridWidthHeight, otherMultipleHitsNeeded);
            }
        }
        catch(Exception e)
        {
            System.out.println("callPlayerShip Exception: "+e);
        }
        
    }
    
    /**
     * Allows the player to enter their ship details
     * @param localPlayerShipList sends the playerShips object to store the ship details
     * @param shipSize sends the size of the ship
     * @param gridWidthHeight sends grid width and height of the ship
     * @param multipleHitsNeeded pass the boolean values of multiple hits needed (true/false)
     */
    public void genericPlayerShip(ShipList localPlayerShipList, int shipSize,  int gridWidthHeight, int multipleHitsNeeded)
    {
        
        String shipName;
        int xPos;
        int yPos; 
        int playerShipCount=0;
        Scanner console = new Scanner(System.in);
        Validation validation = new Validation();
        System.out.println("Loading Player Settings:");        
        try
        {
             while (playerShipCount < shipSize)
             {
                 System.out.println("Please enter the details for the "+ (playerShipCount + 1) +" ship:");
                 System.out.println("ShipName:");
                 shipName = console.next();
                 
                 while (!validation.stringLengthWithinRange(shipName, 3, 15))
                 {
                     System.out.println("Length of the ship name should be between 3 and 15");
                     System.out.println("ShipName:");
                     shipName = console.next();
                 }
                 
                 xPos = validation.integerValidation(console, "x", gridWidthHeight);         
                 yPos = validation.integerValidation(console, "y", gridWidthHeight);
                 while (checkShipCoordinate(localPlayerShipList, xPos, yPos))
                 {
                     System.out.println("------------------------------------------------------------");
                     System.out.println("x and y co-ordinates are repeated. Enter the different one");
                     System.out.println("------------------------------------------------------------");
                     xPos = validation.integerValidation(console, "x", gridWidthHeight);             
                     yPos = validation.integerValidation(console, "y", gridWidthHeight);
                     
                 }

                 localPlayerShipList.playerShipDetail(shipName, xPos, yPos, multipleHitsNeeded );
                 System.out.println("Ship "+(playerShipCount + 1) + " is ready");
                 playerShipCount++;      
             }              
        }
        catch(Exception e)
        {
            System.out.println("genericPlayerShip Exception: "+e);
        }        
    }
    
    /**
     * Calls the computer ship to randomly enter the computer ship details
     * @param localPlayerShipList sends the computerShips object to store the ship list details
     * @param shipSize sends the size of the ship
     * @param gridWidthHeight sends grid width and height of the ship
     * @param multipleHitsNeeded pass the boolean values of multiple hits needed (true/false)
     */
    
    public void callComputerShip(ShipList localComputershipList, int shipSize,int gridWidthHeight,boolean multipleHitsNeeded)
    {
        int randomMultipleHitsNeeded;   
        System.out.println("Loading Computer Settings");        
        try
        {
            if (multipleHitsNeeded == true)
            {
                randomMultipleHitsNeeded=generateRandomMultipleHitsNeeded();
                genericComputerShip(localComputershipList, shipSize, gridWidthHeight, randomMultipleHitsNeeded);                
            }
            else
            {
                int otherMultipleHitsNeeded = 1;
                genericComputerShip(localComputershipList, shipSize, gridWidthHeight, otherMultipleHitsNeeded);             
            }
            System.out.println("Computer Settings generated");
        }
        catch(Exception e)
        {
            System.out.println("callComputerShip Exception: "+e);
        }            
    }
    
    /**
     * Allows the computer to enter their ship details randomly
     * @param localPlayerShipList sends the computerShips object to store the ship details
     * @param shipSize sends the size of the ship
     * @param gridWidthHeight sends grid width and height of the ship
     * @param multipleHitsNeeded pass the boolean values of multiple hits needed (true/false)
     */
    public void genericComputerShip(ShipList localComputershipList, int shipSize, int gridWidthHeight, int multipleHitsNeeded )
    {
        
        int randomXPosition;
        int randomYPosition;
        int computerShipCount=0;
        
        try
        {
            while (computerShipCount < shipSize)
            {
                String shipName = Integer.toString(computerShipCount + 1);
                
                randomXPosition=computerRandomPos(gridWidthHeight);
                randomYPosition=computerRandomPos(gridWidthHeight);
                
                //before calling the below method, validate the x and y coordinates with other ships
                while (checkShipCoordinate(localComputershipList, randomXPosition,randomYPosition))
                {
                    randomXPosition=computerRandomPos(gridWidthHeight);
                    randomYPosition=computerRandomPos(gridWidthHeight);
                }
                localComputershipList.computerShipDetail(shipName,randomXPosition, randomYPosition, multipleHitsNeeded);
                computerShipCount++;
            }     
        }
        catch(Exception e)
        {
            System.out.println("genericComputerShip Exception: "+e);
        }
          
    }
    
    /**
     * This method generates the random number for multiple hits needed variable
     * @return returns a random number
     */
    public int generateRandomMultipleHitsNeeded()
    {
        CoordinateGenerator gen= new CoordinateGenerator();
        int randomMultipleHitsNeeded=gen.generateCoordinates(4,5);
        return randomMultipleHitsNeeded;
    }
    
    /**
     * This method generates the random position for the computer ships
     * This method handles both x and y position of the computer ships
     * @param gridWidthHeight pass grid width and height as a range for the computer ships
     * @return returns a random number
     */
    public int computerRandomPos( int gridWidthHeight)
    {
        CoordinateGenerator gen= new CoordinateGenerator();     
        int randomPos=gen.generateCoordinates(0, gridWidthHeight);
        return randomPos;
    }
    
    /**
     * This method checks the ship coordinates x and y with the previous ships
     * This method is common for both player and computer ships 
     * @param localShipList can be playerShips/computerShips object to call their respective ships
     * @param randomXPostion pass x position of the particular ship
     * @param randomYPosition pass y position of the particular ship
     * @return returns true if coordinates matches with previous ship coordinates else false
     */
    public boolean checkShipCoordinate(ShipList localShipList, int randomXPostion, int randomYPosition)
    {
        boolean flag = false;
        int countShips = 0;
        int currentXPosition = 0;
        int currentYPosition = 0;
        
        try
        {
            while (countShips < localShipList.getShips().size())
            {
                currentXPosition = getParticularShipXPosition(localShipList, countShips);
                currentYPosition = getParticularShipYPosition(localShipList, countShips);
                if (randomXPostion == currentXPosition && randomYPosition == currentYPosition )
                {
                    flag = true;
                }
                countShips++;
            
            }
        }
        catch(Exception e)
        {
            System.out.println("checkShipCoordinate Exception: "+e);
        }
        
       return flag;
        
    }
    
    /**
     * This method returns the current ship name of the particular ship
     * @param localShipList can be playerShip/computerShip object
     * @param particularShipCount pass the array list index of the particular ship
     * @return returns the particular ship name
     */
    public String getParticularShipName(ShipList localShipList, int particularShipCount)
    {
        String shipName= localShipList.getShips().get(particularShipCount).getShipName();
        return shipName;
    }
    
    /**
     * This method returns the current X position of the particular ship
     * @param localShipList can be playerShip/computerShip object
     * @param particularShipCount pass the array list index of the particular ship
     * @return returns the value of particular ship x position 
     */
    public int getParticularShipXPosition(ShipList localShipList, int particularShipCount)
    {
        int xPos= localShipList.getShips().get(particularShipCount).getXPos();
        return xPos;
    }
    
    /**
     * This method returns the current Y position of the particular ship
     * @param localShipList can be playerShip/computerShip object
     * @param particularShipCount pass the array list index of the particular ship
     * @return returns the value of particular ship X position 
     */
    public int getParticularShipYPosition(ShipList localShipList, int particularShipCount)
    {
        int yPos= localShipList.getShips().get(particularShipCount).getYPos();
        return yPos;
    }
    
    /**
     * This method returns the current number of hits needed for the particular ship
     * @param localShipList can be playerShip/computerShip object
     * @param particularShipCount pass the array list index of the particular ship
     * @return returns the value of current number of hits needed 
     */
    public int getParticularShipNoOfHitsNeeded(ShipList localShipList, int particularShipCount)
    {
        int noOfHitsNeeded= localShipList.getShips().get(particularShipCount).getNoOfHitsNeeded();
        return noOfHitsNeeded;
    }
    
    /**
     * This method returns the current number of hits made for the particular ship
     * @param localShipList can be playerShip/computerShip object
     * @param particularShipCount pass the array list index of the particular ship
     * @return returns the value of current number of hits needed 
     */
    public int getParticularShipNoOfHitsMade(ShipList localShipList, int particularShipCount)
    {
        int noOfHitsMade= localShipList.getShips().get(particularShipCount).getNoOfHitsMade();
        return noOfHitsMade;
    }
    
    /**
     * This method sets the new player ship
     * @param newPlayerShips pass new player ship details
     */
    public void setPayerShips(ShipList newPlayerShips)
    {
        playerShips = newPlayerShips;
    }
    
    /**
     * This method returns the player ship details
     * @return returns the player ship details
     */
    public ShipList getPlayerShips()
    {
        return playerShips;
    }
    
    /**
     * This method sets the new computer ship
     * @param newComputerShips pass new computer ship details
     */
    public void setComputerShips(ShipList newComputerShips)
    {
        computerShips = newComputerShips;
    }
    
    /**
     * This method returns the computer ship details
     * @return returns the computer ship details
     */
    public ShipList getComputerShips()
    {
        return computerShips;
    }
    
}
