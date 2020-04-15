/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement

/**
 *
 * @author Aravind
 */
public class MemoirOne {

    String suburbs;
    long moviesWatchedPerSuburb;
    public MemoirOne(){
        
    }

    public MemoirOne(String suburbs, long moviesWatchedPerSuburb) {
        this.suburbs = suburbs;
        this.moviesWatchedPerSuburb = moviesWatchedPerSuburb;
    }
    

    public String getSuburbs() {
        return suburbs;
    }

    public void setSuburbs(String suburbs) {
        this.suburbs = suburbs;
    }

    public long getMoviesWatchedPerSuburb() {
        return moviesWatchedPerSuburb;
    }

    public void setMoviesWatchedPerSuburb(long moviesWatchedPerSuburb) {
        this.moviesWatchedPerSuburb = moviesWatchedPerSuburb;
    }  
}
