/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makers.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
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
import makers.Person;

/**
 *
 * @author Aravind
 */
@Stateless
@Path("makers.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
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
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
@Path("findByFirstname/{firstname}")
@Produces({"application/json"})
public List<Person> findByFirstName(@PathParam("firstname") String firstname){
    Query query = em.createNamedQuery("Person.findByFirstname");
    query.setParameter("firstname", firstname);
    return query.getResultList();
}
@GET
@Path("findByLastname/{lastname}")
@Produces({"application/json"})
public List<Person> findByLastname(@PathParam("lastname") String lastname){
    
    Query query = em.createNamedQuery("Person.findByLastname");
    query.setParameter("lastname", lastname);
    return query.getResultList();
}

@GET
@Path("findByGender/{gender}")
@Produces({"application/json"})
public List<Person> findByGender(@PathParam("gender") String gender){
    Query query = em.createNamedQuery("Person.findByGender");
    query.setParameter("gender", gender);
    return query.getResultList();
}

@GET
@Path("findByDob/{dob}")
@Produces({"application/json"})
public List<Person> findByDob(@PathParam("dob") String dob) throws ParseException{
    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dob);   
    Query query = em.createNamedQuery("Person.findByDob");
    query.setParameter("dob", date);
    return query.getResultList();
}



@GET
@Path("findByAddress/{address}")
@Produces({"application/json"})
public List<Person> findByAddress(@PathParam("address") String address){
    Query query = em.createNamedQuery("Person.findByAddress");
    query.setParameter("address", address);
    return query.getResultList();
}

@GET
@Path("findByState/{state}")
@Produces({"application/json"})
public List<Person> findByState(@PathParam("state") String state){
    Query query = em.createNamedQuery("Person.findByState");
    query.setParameter("state", state);
    return query.getResultList();
}

@GET
@Path("findByPostcode/{postcode}")
@Produces({"application/json"})
public List<Person> findByState(@PathParam("postcode") Integer postcode){
    Query query = em.createNamedQuery("Person.findByPostcode");
    query.setParameter("postcode", postcode);
    return query.getResultList();
}

//Task 3-b
@GET
@Path("findByFullAddress/{address}/{state}/{postcode}")
@Produces({"application/json"})
public List<Person> findByFullAddress(@PathParam("address") String address, @PathParam("state") String state, @PathParam("postcode") Integer postcode){
    
    TypedQuery<Person> query = 
            em.createQuery(
                    "SELECT p FROM Person p WHERE  p.address = :address AND p.state = :state AND p.postcode = :postcode", Person.class);
    query.setParameter("address", address);
    query.setParameter("state", state);
    query.setParameter("postcode", postcode);
    return query.getResultList();   
}









}
