package makers;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import makers.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-14T12:52:34")
@StaticMetamodel(Cinema.class)
public class Cinema_ { 

    public static volatile SingularAttribute<Cinema, Integer> cinemaId;
    public static volatile CollectionAttribute<Cinema, Memoir> memoirCollection;
    public static volatile SingularAttribute<Cinema, String> cinemaName;
    public static volatile SingularAttribute<Cinema, String> cinemaLocation;

}