/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.util.Date;
/**
 *
 * @author Aravind
 */
public class MovieReleasedWatchedDate {
    
      
    String movieName;
    Date releasedDate;
    Date watchedDate;
    
    
    public MovieReleasedWatchedDate(){
        
    }

    public MovieReleasedWatchedDate(String movieName, Date releasedDate, Date watchedDate) {
        this.movieName = movieName;
        this.releasedDate = releasedDate;
        this.watchedDate = watchedDate;
    }
  
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Date getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }
    
  
    
}
