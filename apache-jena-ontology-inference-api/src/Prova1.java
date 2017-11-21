import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import org.apache.log4j.varia.NullAppender;

/**
 * Created by valer on 14/11/2017.
 * Esempio 1 da "https://jena.apache.org/tutorials/rdf_api.html"
 */
public class Prova1 {
    public static void main(String[] args) {
        // some definitions
        String personURI    = "http://somewhere/JohnSmith";
        String fullName     = "John Smith";

    // create an empty Model
        Model model = ModelFactory.createDefaultModel();

    // create the resource
        Resource johnSmith = model.createResource(personURI);

    // add the property
        johnSmith.addProperty(VCARD.FN, fullName);

        model.write(System.out, "Turtle");

    }
}
