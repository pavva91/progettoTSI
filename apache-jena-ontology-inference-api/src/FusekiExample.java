// Written in 2015 by Thilo Planz 
// To the extent possible under law, I have dedicated all copyright and related and neighboring rights 
// to this software to the public domain worldwide. This software is distributed without any warranty. 
// http://creativecommons.org/publicdomain/zero/1.0/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.*;

import org.apache.jena.query.*;
import org.apache.jena.vocabulary.VCARD;

import static org.apache.jena.sparql.vocabulary.FOAF.familyName;
import static org.apache.jena.sparql.vocabulary.FOAF.givenName;


public class FusekiExample {


    /**
     *     Carica file rdf (Path interno) in fuseki sparql endpoint es serviceURI: http://localhost:3030/<NomeDataSet>
     *      NB Se su fuseki si seleziona "Persistent – dataset will persist across Fuseki restarts" alla creazione del
     *      dataset questa funzione si richiama solo all'avvio
     * @param rdf il path (ad es C://.../RDFstore.rdf) interno
     * @param serviceURI il dataset fuseki (ad es http://localhost:3030/<NomeDataSet>NomeDataSet</NomeDataSet>)
     * @throws IOException
     */
	public static void uploadRDF(File rdf, String serviceURI)
			throws IOException {

		// parse the file
		Model m = ModelFactory.createDefaultModel();
		try (FileInputStream in = new FileInputStream(rdf)) {
			m.read(in, null, "RDF/XML");
		}

        // upload the resulting model
		DatasetAccessor accessor = DatasetAccessorFactory
				.createHTTP(serviceURI);
		accessor.putModel(m);
	}



    /**
     * Carica il DataSet Fuseki dentro Jena (classe Model)
     * @param serviceURI
     * @return il modello (Dataset Jena)
     * @throws IOException
     */
	public static Model getRDF(String serviceURI) throws IOException{
	    DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
	    return accessor.getModel();

    }

    public static void updateRDF(String serviceURI, Model model){
        DatasetAccessor accessor = DatasetAccessorFactory
                .createHTTP(serviceURI);
        accessor.putModel(model);
    }



	public static void execSelectAndPrint(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = q.execSelect();

		ResultSetFormatter.out(System.out, results);

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			RDFNode x = soln.get("x");
			System.out.println(x);
		}
	}

	public static void execSelectAndProcess(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = q.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			// assumes that you have an "?x" in your query
			RDFNode x = soln.get("x");
			System.out.println(x);
		}
	}

	public static void addValerio(Model model){

        // some definitions
        String personURI    = "http://somewhere/ValerioMattioli";
        String givenName    = "Valerio";
        String familyName   = "Mattioli";
        String fullName     = givenName + " " + familyName;

        Resource valerioMattioli
                = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

    }

	public static void main(String[] argv) throws IOException {
        uploadRDF(new File("C:/apache-jena-ontology-inference-api/src/rows.rdf"),"http://localhost:3030/ProvaJena");
//		execSelectAndPrint(
//				"http://localhost:3030/Prova",
//				"SELECT ?x WHERE { ?y  <http://www.w3.org/2001/vcard-rdf/3.0#Family>  \"Jones\" ." +
//                        "?z <http://www.w3.org/2001/vcard-rdf/3.0#N> ?y." +
//                        "?z <http://www.w3.org/2001/vcard-rdf/3.0#FN> ?x}");
//
//		execSelectAndProcess(
//				"http://localhost:3030/Prova",
//				"SELECT ?x WHERE { ?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  \"John Smith\" }");
        Model model = getRDF("http://localhost:3030/ProvaJena");
        model.write(System.out, "Turtle");
//        addValerio(model);
        model.write(System.out, "Turtle");
//        updateRDF("http://localhost:3030/ProvaJena",model);

	}
}