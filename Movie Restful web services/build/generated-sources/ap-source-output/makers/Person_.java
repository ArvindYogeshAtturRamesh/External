package makers;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import makers.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-14T12:52:34")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> firstname;
    public static volatile SingularAttribute<Person, String> address;
    public static volatile SingularAttribute<Person, String> gender;
    public static volatile SingularAttribute<Person, Date> dob;
    public static volatile CollectionAttribute<Person, Memoir> memoirCollection;
    public static volatile SingularAttribute<Person, Integer> postcode;
    public static volatile SingularAttribute<Person, Integer> personId;
    public static volatile SingularAttribute<Person, String> state;
    public static volatile SingularAttribute<Person, String> lastname;

}