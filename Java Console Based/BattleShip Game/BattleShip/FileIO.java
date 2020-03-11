import java.io.*;
import java.util.Scanner;

/**
 * FileIO class helps to read or write the data from the file.
 *
 * @author ARVIND YOGESH ATTUR RAMESH
 * @version 20th OCTOBER 2018
 */
public class FileIO
{
    private String fileName;
    
    
    /**
     * Constructor for objects of class FileIO
     */
    public FileIO()
    {
        
    }
    
    /**
     * 
     * @param newFileName Sets the new file name to the existing one
     */
    public void setFileName(String newFileName)
    {
        fileName = newFileName;
    }
    
    /**
     * 
     * @return returns the current file name
     */
    public String getFileName()
    {
        return fileName;
    }
    
    /**
     * This method writes output to the gameoutcome.txt file
     * @param playerScore pass the player score
     * @param computerScore pass the computer score
     * @throws IOException throws exception in other case
     */
    public void writeOutput(int playerScore, int computerScore) throws IOException
    {
            //PrintWriter writer = new PrintWriter(new FileWriter(fileName, true),true );
    	    fileName="gameoutcome.txt";
            FileWriter fw = new FileWriter(fileName);
            PrintWriter writer = new PrintWriter(fw);
            
            writer.println("Final score: "+" Player: ("+ playerScore+") Computer: ("+computerScore+")");
            
            if (playerScore > computerScore) 
            {
                writer.println("Player wins!!!");
            }
            else
            {
                writer.println("Computer wins!!!");
            }
            fw.close();
        
    }
    
    /**
     * This method reads input from the file called gamesettings.txt
     * @return returns the list which contains the game details to start the game
     * @throws IOException throws exception in other case
     */
    public String[] readInputs() throws IOException
    {
    	    fileName = "gamesettings.txt";
            FileReader fr = new FileReader(fileName);
            Scanner console = new Scanner(fr);
            String[] read = new String[4];
            String line= console.nextLine();
            System.out.println("The inputs are: "+line);
            read= line.split(",");
            fr.close();
            console.close();
            return read;
            
    }

}
