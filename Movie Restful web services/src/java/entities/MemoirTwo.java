/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement



/**
 *
 * @author Aravind
 */
public class MemoirTwo {
     Date monthName;
    long totalMoviesWatchedPerMonth;
    
    public MemoirTwo(){
        
    }

    public MemoirTwo(Date monthName, long totalMoviesWatchedPerMonth) {
        this.monthName = monthName;
        this.totalMoviesWatchedPerMonth = totalMoviesWatchedPerMonth;
    }

   
    public Date getMonthName() {
        return monthName;
    }

    public void setMonthName(Date monthName) {
        this.monthName = monthName;
    }

    public long getTotalMoviesWatchedPerMonth() {
        return totalMoviesWatchedPerMonth;
    }

    public void setTotalMoviesWatchedPerMonth(long totalMoviesWatchedPerMonth) {
        this.totalMoviesWatchedPerMonth = totalMoviesWatchedPerMonth;
    }
       
}
