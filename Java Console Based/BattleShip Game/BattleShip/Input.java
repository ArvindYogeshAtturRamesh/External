
/**
 * Input class is a basic class to read some inputs
 *
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class Input
{
  

    /**
     * Constructor for objects of class Input
     */
    public Input()
    {
        // initialise instance variables
       
    }
    
    /**
     * This method helps to continue the game by pressing the ENTER key 
     */
    public void pressAnyKeyToContinue()
    { 
           System.out.println("Press Enter key to continue...");
           try
           {
               System.in.read();
               System.out.print('\u000C'); 
           }  
           catch(Exception e)
           {}  
    }
 
}
