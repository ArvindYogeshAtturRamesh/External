/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement

/**
 *
 * @author Aravind
 */
public class MemoirFour {
        
    String movieName;
    Date releaseYear;
    
    public MemoirFour(){
        
    }
    public MemoirFour(String movieName, Date releaseYear) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    
     public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }   
}
