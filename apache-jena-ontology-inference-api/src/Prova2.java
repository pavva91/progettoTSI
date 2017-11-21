import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

/**
 * Created by valer on 14/11/2017.
 * Esempio 2 da "https://jena.apache.org/tutorials/rdf_api.html"
 */
public class Prova2 {
    public static void main(String[] args) {
        // some definitions
        String personURI    = "http://somewhere/JohnSmith";
        String fullName     = "John Smith";

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource and add the property, can be more compactly written in a cascading style:
        Resource johnSmith = model.createResource(personURI).addProperty(VCARD.FN, fullName);



        model.write(System.out, "Turtle");
    }
}
