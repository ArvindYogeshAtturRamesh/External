/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
/**
 *
 * @author Aravind
 */
public class MemoirThree {

   
    
    String movieName;
    Date releaseDate;
    int highestRating;
    
    public MemoirThree(){
        
    }

    public MemoirThree(String movieName, Date releaseDate, int highestRating) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.highestRating = highestRating;
    }
    
     public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getHighestRating() {
        return highestRating;
    }

    public void setHighestRating(int highestRating) {
        this.highestRating = highestRating;
    }     
}
