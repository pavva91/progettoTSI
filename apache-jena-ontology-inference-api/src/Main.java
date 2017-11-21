import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);


//    public static void main(String[] args) {
//        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
//
//        System.out.println("VIET - Jena Ontology & Inference API demo");
//        Model m = ModelFactory.createDefaultModel();
//        String NS = "http://bibooki.com/test/";
//
//        Resource viet = m.createResource(NS + "Viet");
//        Property studyAt = m.createProperty(NS + "studyAt");
//
//        viet.addProperty(studyAt, "Konkuk Graduate University", XSDDatatype.XSDstring);
//        viet.addProperty(studyAt, "bELLA", XSDDatatype.XSDstring);
//        m.write(System.out, "Turtle");
//
//    }

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        Boolean flag = true;
        FusekiMod fusekiMod = new FusekiMod();
        Model model = fusekiMod.getRDF("http://localhost:3030/Lombardia");

//        model.setNsPrefix("measure","<http://localhost:3030/Lombardia/>");

        try {
            while (flag) {
                System.out.println("Please input a line");
                long then = System.currentTimeMillis();
                String line = scanner.nextLine();
                long now = System.currentTimeMillis();

                switch (line.toString()) {
                    case "exit":
                        flag = false;
                        break;
                    case "upload": //Carica file rdf su dataset
                        fusekiMod.uploadRDF(new File("C:/apache-jena-ontology-inference-api/src/lombardia.rdf"),"http://localhost:3030/Lombardia");
                        break;
                    case "measure": // Aggiunge misura al dataset
                        fusekiMod.addMeasurement(model,fusekiMod.measureGenerator(1,10));
                        model.write(System.out,"Turtle");
                        fusekiMod.updateRDF("http://localhost:3030/Lombardia",model);
                        break;

                }

                System.out.printf("Waited %.3fs for user input%n", (now - then) / 1000d);
                System.out.printf("User input was: %s%n", line);
            }
        } catch(IOException e) {
            // System.in has been closed
            System.out.println(e.getMessage());
        }
    }
}
