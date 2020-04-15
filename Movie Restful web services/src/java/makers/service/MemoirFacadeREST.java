/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makers.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.ejb.Stateless;
import javax.json.*;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import makers.Memoir;
import entities.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.util.*;
import java.util.Map.Entry; 

/**
 *
 * @author Aravind
 */
@Stateless
@Path("makers.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
@GET
@Path("findByMovieName/{movieName}")
@Produces({"application/json"})
public List<Memoir> findByMovieName(@PathParam("movieName") String movieName) {
    Query query = em.createNamedQuery("Memoir.findByMovieName");
    query.setParameter("movieName", movieName);
    return query.getResultList();
}

@GET
@Path("findByComments/{comments}")
@Produces({"application/json"})
public List<Memoir> findByComments(@PathParam("comments") String comments) {
    Query query = em.createNamedQuery("Memoir.findByComments");
    query.setParameter("comments", comments);
    return query.getResultList();
}

@GET
@Path("findByRating/{rating}")
@Produces({"application/json"})
public List<Memoir> findByRating(@PathParam("rating") Integer rating) {
    Query query = em.createNamedQuery("Memoir.findByRating");
    query.setParameter("rating", rating);
    return query.getResultList();
}

@GET
@Path("findByMovieReleaseDate/{movieReleaseDate}")
@Produces({"application/json"})
public List<Memoir> findByMovieReleaseDate(@PathParam("movieReleaseDate") String movieReleaseDate) throws ParseException {
    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(movieReleaseDate);   
    Query query = em.createNamedQuery("Memoir.findByMovieReleaseDate");
    query.setParameter("movieReleaseDate", date);
    return query.getResultList();
}


@GET
@Path("findByWatchedOn/{watchedOn}")
@Produces({"application/json"})
public List<Memoir> findByWatchedOn(@PathParam("watchedOn") String watchedOn) throws ParseException{
   Date parseDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(watchedOn);
    Query query = em.createNamedQuery("Memoir.findByWatchedOn");
    query.setParameter("watchedOn", parseDate);
    return query.getResultList();
} 
/////////////////////////////////////////////////////////////////////////////////////////////
//Task 3-c
@GET
@Path("findByMemoirCinemaDynamic/{cinemaName}/{movieName}")
@Produces({"application/json"})
public List<Memoir> findByMemoirCinemaDynamic(@PathParam("cinemaName") String cinemaName, @PathParam("movieName") String movieName) {
     TypedQuery<Memoir> query = em.createQuery(
                 "SELECT m FROM Memoir m WHERE m.cinemaId.cinemaName = :cinemaName AND m.movieName = :movieName"
                    , Memoir.class);
     query.setParameter("cinemaName", cinemaName);
     query.setParameter("movieName", movieName);
    return query.getResultList();
} 
//////////////////////////////////////////////////////////////////////////////////////////////
//Task 3-d
@GET
@Path("findByMemoirCinemaStatic/{cinemaName}/{movieName}")
@Produces({"application/json"})
public List<Memoir> findByMemoirCinemaStatic(@PathParam("cinemaName") String cinemaName, @PathParam("movieName") String movieName) {
     Query query = em.createNamedQuery("Memoir.findByMemoirCinemaStatic");
     query.setParameter("cinemaName", cinemaName);
     query.setParameter("movieName", movieName);
    return query.getResultList();
} 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task4-a
@GET
@Path("findByNoOfMoviesWatchedPerSuburb/{personId}/{startDate}/{endDate}")
@Produces({MediaType.APPLICATION_JSON})
public List<MemoirOne> findByNoOfMoviesWatchedPerSuburb(@PathParam("personId") Integer personId, @PathParam("startDate") String fromDate, @PathParam("endDate") String toDate) throws ParseException{
     Date parseFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
     Date parseToDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
    
    TypedQuery<MemoirOne> q = em.createQuery("SELECT new entities.MemoirOne(m.cinemaId.cinemaLocation, count(m.watchedOn)) FROM Memoir AS m WHERE m.personId.personId = :personId AND m.watchedOn BETWEEN :from AND :to GROUP BY m.cinemaId.cinemaLocation", MemoirOne.class);
    q.setParameter("personId", personId);
    q.setParameter("from", parseFromDate);
    q.setParameter("to", parseToDate);
    return q.getResultList();
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task4-b
@GET
@Path("findMoviesCountBypersonIdAndYear/{personId}/{year}")
@Produces({MediaType.APPLICATION_JSON})
public Object findMoviesCountBypersonIdAndYear(@PathParam("personId") Integer personId, @PathParam("year") String from) throws ParseException{

Date fromYear = new SimpleDateFormat("yyyy").parse(from);
int a = Integer.parseInt(from);
int c = a + 1;
String to = Integer.toString(c);
Date toYear = new SimpleDateFormat("yyyy").parse(to);

TypedQuery<MemoirTwo> q = em.createQuery("SELECT new entities.MemoirTwo(m.watchedOn, count(m.watchedOn)) From Memoir As m where m.personId.personId = :personId and m.watchedOn BETWEEN :from AND :to GROUP BY m.watchedOn ",MemoirTwo.class);
q.setParameter("personId", personId);
q.setParameter("from", fromYear);
q.setParameter("to", toYear);

List<String> monthList = new ArrayList<>();
HashMap<String, Integer> hashMonthCount = new HashMap<>();
List<MemoirTwo> tempFirstList= q.getResultList();
int count = 0;

//stores unique month list 

 for(MemoirTwo row : tempFirstList){
      Format monthFormatter = new SimpleDateFormat("MMMM"); 
       String tempRowMonthName = monthFormatter.format(row.getMonthName());
       monthList.add(tempRowMonthName);         
 }
 
 //convert list to set to get unique month values
 Set<String> hashSetMonthList = new HashSet<>(monthList);
 

//Append month name and its count in the hashmap as key-value pair
for(String firstRow: hashSetMonthList){
    for(MemoirTwo secondRow : tempFirstList){
        
        Format monthFormatter = new SimpleDateFormat("MMMM");   
        String tempSecondRowMonthName = monthFormatter.format(secondRow.getMonthName());
        
        if(firstRow.equalsIgnoreCase(tempSecondRowMonthName)){
            count += secondRow.getTotalMoviesWatchedPerMonth();
        }
       
    }  
    hashMonthCount.put(firstRow, count);
    count = 0;
}
    //Create entry set to convert hashmap into list and perform iteration
    Set<Entry<String, Integer>> entrySet = hashMonthCount.entrySet();      
    ArrayList<Entry<String, Integer>> entryList = new ArrayList<>(entrySet);
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
    for(Entry<String, Integer> entry : entryList){
       JsonObject movieObject = Json.createObjectBuilder()
                .add("Month Name", entry.getKey())
                .add("Number of Movies",entry.getValue()).build();
        arrayBuilder.add(movieObject); 
    }
    
 
     JsonArray jArray = arrayBuilder.build();
    return jArray;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task 4-c
@GET
@Path("findByHighestRatingsOfMovie/{personId}")
@Produces({MediaType.APPLICATION_JSON})
public Object findByHighestRatingsOfMovie(@PathParam("personId") Integer personId){
    
    TypedQuery<MemoirThree> q = em.createQuery("SELECT new entities.MemoirThree(m.movieName, m.movieReleaseDate, MAX(m.rating)) FROM Memoir As m WHERE m.personId.personId = :personId GROUP BY m.movieName, m.movieReleaseDate ORDER BY MAX(m.rating) DESC", MemoirThree.class);   
    q.setParameter("personId", personId);
    
    List<MemoirThree> listMemoir = q.getResultList();  
    int maxRating = listMemoir.get(0).getHighestRating();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    
    for(MemoirThree row :  listMemoir){
        if(maxRating == row.getHighestRating()){
             JsonObject movieObject = Json.createObjectBuilder()
                .add("movieName", (String)row.getMovieName())
                .add("releaseDate",row.getReleaseDate().toString())
                .add("highestRating", row.getHighestRating()).build();
        arrayBuilder.add(movieObject);
        }
      
       
    }
     JsonArray jArray = arrayBuilder.build();
    return jArray;  
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task4-d
@GET
@Path("findMoviesWithSameReleaseAndWatchedYear/{personId}")
@Produces({MediaType.APPLICATION_JSON})
public Object findMoviesWithSameReleaseAndWatchedYear(@PathParam("personId") Integer personId) throws ParseException{
    TypedQuery<MovieReleasedWatchedDate> q = em.createQuery("SELECT new entities.MovieReleasedWatchedDate(m.movieName, m.movieReleaseDate, m.watchedOn) FROM Memoir As m WHERE m.personId.personId = :personId", MovieReleasedWatchedDate.class);
    q.setParameter("personId", personId);
    
    List<MovieReleasedWatchedDate> memoirList =  q.getResultList();   
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    
    for(MovieReleasedWatchedDate row :memoirList ){
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String releasedYear = yearFormat.format(row.getReleasedDate());
        String watchedYear = yearFormat.format(row.getWatchedDate());
        
        if(releasedYear.equals(watchedYear)){
             JsonObject movieObject = Json.createObjectBuilder()
                .add("Movie Name", (String)row.getMovieName())
                .add("Release Year",releasedYear)
                     .add("watched year",watchedYear).build();
        arrayBuilder.add(movieObject);
            
        }        
    }
    JsonArray jArray = arrayBuilder.build();
    return jArray;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task4-e
@GET
@Path("findByRemakeMoviesWithReleaseYear/{personId}")
@Produces({MediaType.APPLICATION_JSON})
public Object findByRemakeMoviesWithReleaseYear(@PathParam("personId") Integer personId) throws ParseException{
    
    TypedQuery<MemoirFour> queryOne = em.createQuery("SELECT new entities.MemoirFour(m.movieName, m.movieReleaseDate) FROM Memoir As m WHERE m.personId.personId = :personId", MemoirFour.class);
    queryOne.setParameter("personId", personId);
    
    List<MemoirFour> firstList = queryOne.getResultList();
    List<MemoirFour> secondList = queryOne.getResultList();
    List<MemoirFour> finalList = new ArrayList<MemoirFour>();
    
    //Takes the same movie name with different release date and store it in the final list
    for(MemoirFour firstListRow : firstList){
        for(MemoirFour secondListRow : secondList){
            if(firstListRow.getMovieName().equalsIgnoreCase(secondListRow.getMovieName())){
                
                DateFormat yearFormat = new SimpleDateFormat("yyyy");
                String tempFirstRowYear = yearFormat.format(firstListRow.getReleaseYear());
                String tempSecondRowYear = yearFormat.format(secondListRow.getReleaseYear());
              
                if(!tempFirstRowYear.equalsIgnoreCase(tempSecondRowYear)){
                    finalList.add(secondListRow);
                }
            }            
        }
    }
    
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();  
    for(MemoirFour row :  finalList){    
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String tempDate = dateFormat.format(row.getReleaseYear());
        
        JsonObject movieObject = Json.createObjectBuilder()
                .add("movieName", (String)row.getMovieName())
                .add("releaseYear",tempDate).build();
        arrayBuilder.add(movieObject);
    }
     JsonArray jArray = arrayBuilder.build();
    return jArray;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Task 4-f
@GET
@Path("findHighestMovieRatingsOfRecentYear/{personId}")
@Produces({MediaType.APPLICATION_JSON})
public List<MemoirThree> findHighestMovieRatingsOfRecentYear(@PathParam("personId") Integer personId) throws ParseException{
    
   //Get the recent year from calender 
    Calendar prevYear = Calendar.getInstance();
    prevYear.add(Calendar.YEAR, -1);
    String strYear = Integer.toString(prevYear.get(Calendar.YEAR));
    
    //convert the recent year to actual date
    Date fromDate = new SimpleDateFormat("yyyy").parse(strYear);
    int a = Integer.parseInt(strYear);
    int c = a + 1;
    String to = Integer.toString(c);
    Date toDate = new SimpleDateFormat("yyyy").parse(to);
  
    TypedQuery<MemoirThree> q = em.createQuery("SELECT new entities.MemoirThree(m.movieName, m.movieReleaseDate, MAX(m.rating)) FROM Memoir As m WHERE m.personId.personId = :personId AND m.movieReleaseDate BETWEEN :from AND :to GROUP BY m.movieName, m.movieReleaseDate ORDER BY MAX(m.rating) DESC", MemoirThree.class);   
    q.setParameter("personId", personId);
    q.setParameter("from", fromDate);
    q.setParameter("to", toDate);
      
    //returns top 5 highest rating scores of the movies that are released recent year
    return q.setMaxResults(5).getResultList();           
    
}

}