
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Validation class helps to validate the input values read from the user.
 *
 * @author ARINDYOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class Validation
{
	
    /**
     * Constructor for objects of class Validation
     */
    public Validation()
    {
        // initialise instance variables
    	//console = new Scanner(System.in);
       
    }
    
    /**
     * This method checks whether length of the string is with in the range
     * @param paramOne pass the current string
     * @param minRange pass the minimum range  value
     * @param maxRange pass the maximum range value
     * @return returns true if length of the string is with in the range else false
     */
    public boolean stringLengthWithinRange(String paramOne, int minRange, int maxRange)
    {
        if (((paramOne.length() >= minRange ) && (paramOne.length() <= maxRange)))
        return true;
       
      return false;
    }
    
    /**
     * Checks whether the integer is with in the range.
     * @param paramOne pass the current integer value
     * @param minRange pass the minimum range  value
     * @param maxRange pass the maximum range value
     * @return returns true if the integer is with in the range else false
     */
    public boolean intLengthWithinRange(int paramOne, int minRange, int maxRange)
    {
        if (((paramOne >= minRange ) && (paramOne <= maxRange)))
        return true;
       
      return false;
    }
   
    /**
     * Validate the integer
     * @param console helps to read the integer
     * @param position pass name of the ship 
     * @param gridWidthHeight pass grid width and height
     * @return
     */
   public int integerValidation(Scanner console, String position, int gridWidthHeight)
   {
	   int input = -1;
	   int currentGridWidhtHeight= (gridWidthHeight - 1);  
	   while (input< 0)
	   {		   
		   try
		   {			   
			   System.out.println("Ship " +position+ " position ( 0 -"+ currentGridWidhtHeight+ " )");
			   input = console.nextInt();
			   while (!intLengthWithinRange(input, 0, currentGridWidhtHeight))
			   {
				   try
				   {
					   System.out.println("Value must be with in the range");
					   System.out.println("Ship " +position+ " position ( 0 -"+ currentGridWidhtHeight+ " )");
					   input = console.nextInt();
				   }
				   catch(Exception e)
				   {
					   System.out.println("Input must be numeric: ");
					   if (console.hasNext())
					   {
						   console.next();
					   }		
					   
				   }
				  
			   }	
			   			  		   
		   }
		   catch(Exception e)
		   {
			   System.out.println("Input must be numeric: ");
			   if (console.hasNext())
			   {
				   console.next();
			   }		   
		   }		   
	   } 
	   return input;
	   
   }
 
}
