/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makers;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aravind
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMovieId", query = "SELECT m FROM Memoir m WHERE m.movieId = :movieId")
    , @NamedQuery(name = "Memoir.findByMovieName", query = "SELECT m FROM Memoir m WHERE m.movieName = :movieName")
    , @NamedQuery(name = "Memoir.findByMovieReleaseDate", query = "SELECT m FROM Memoir m WHERE m.movieReleaseDate = :movieReleaseDate")
    , @NamedQuery(name = "Memoir.findByWatchedOn", query = "SELECT m FROM Memoir m WHERE m.watchedOn = :watchedOn")
    , @NamedQuery(name = "Memoir.findByComments", query = "SELECT m FROM Memoir m WHERE m.comments = :comments")
    , @NamedQuery(name = "Memoir.findByRating", query = "SELECT m FROM Memoir m WHERE m.rating = :rating")
    ,@NamedQuery(name = "Memoir.findByMemoirCinemaStatic", query = "SELECT m FROM Memoir m WHERE m.cinemaId.cinemaName = :cinemaName AND m.movieName = :movieName")
    
    ,@NamedQuery(name = "Memoir.abc", query = "SELECT m.rating FROM Memoir m")

})
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOVIE_ID")
    private Integer movieId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MOVIE_NAME")
    private String movieName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOVIE_RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date movieReleaseDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WATCHED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchedOn;
    @Size(max = 500)
    @Column(name = "COMMENTS")
    private String comments;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATING")
    private int rating;
    @JoinColumn(name = "CINEMA_ID", referencedColumnName = "CINEMA_ID")
    @ManyToOne(optional = false)
    private Cinema cinemaId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne(optional = false)
    private Person personId;

    public Memoir() {
    }

    public Memoir(Integer movieId) {
        this.movieId = movieId;
    }

    public Memoir(Integer movieId, String movieName, Date movieReleaseDate, Date watchedOn, int rating) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.watchedOn = watchedOn;
        this.rating = rating;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public Date getWatchedOn() {
        return watchedOn;
    }

    public void setWatchedOn(Date watchedOn) {
        this.watchedOn = watchedOn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieId != null ? movieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.movieId == null && other.movieId != null) || (this.movieId != null && !this.movieId.equals(other.movieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "makers.Memoir[ movieId=" + movieId + " ]";
    }
    
}
