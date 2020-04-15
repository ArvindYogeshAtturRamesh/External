package makers;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import makers.Cinema;
import makers.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-14T12:52:34")
@StaticMetamodel(Memoir.class)
public class Memoir_ { 

    public static volatile SingularAttribute<Memoir, Date> watchedOn;
    public static volatile SingularAttribute<Memoir, Date> movieReleaseDate;
    public static volatile SingularAttribute<Memoir, String> comments;
    public static volatile SingularAttribute<Memoir, Cinema> cinemaId;
    public static volatile SingularAttribute<Memoir, Integer> rating;
    public static volatile SingularAttribute<Memoir, Integer> movieId;
    public static volatile SingularAttribute<Memoir, Person> personId;
    public static volatile SingularAttribute<Memoir, String> movieName;

}