// Written in 2015 by Thilo Planz 
// To the extent possible under law, I have dedicated all copyright and related and neighboring rights 
// to this software to the public domain worldwide. This software is distributed without any warranty. 
// http://creativecommons.org/publicdomain/zero/1.0/
package com.valerio.endpointaccess;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import com.valerio.vocabulary.LOMBARDIA;
import com.valerio.vocabulary.LOMBARDIA_SENSORE;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.*;

import org.apache.jena.query.*;
import org.apache.jena.vocabulary.DCTypes;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.XSD;

import static org.apache.jena.sparql.vocabulary.FOAF.familyName;
import static org.apache.jena.sparql.vocabulary.FOAF.givenName;


public class FusekiMod {


	/**
	 *     Carica file rdf (Path interno) in fuseki sparql endpoint es serviceURI: http://localhost:3030/<NomeDataSet>
	 *      NB Se su fuseki si seleziona "Persistent – dataset will persist across Fuseki restarts" alla creazione del
	 *      dataset questa funzione si richiama solo all'avvio
	 * @param rdf il path (ad es C://.../RDFstore.rdf) interno
	 * @param serviceURI il dataset fuseki (ad es http://localhost:3030/<NomeDataSet>NomeDataSet</NomeDataSet>)
	 * @throws IOException
	 */
	public void uploadRDF(File rdf, String serviceURI)
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

	public  void updateRDF(String serviceURI, Model model){
		DatasetAccessor accessor = DatasetAccessorFactory
				.createHTTP(serviceURI);
		accessor.putModel(model);
	}

	/**
	 *
	 * @param model
	 * @param filePath - path_assoluto/nomefile
	 * @throws IOException
	 */
	public void writeModelOnFile(Model model, String filePath) throws IOException{
		FileWriter out = new FileWriter(filePath);
		try {
			model.write( out, "RDF/XML-ABBREV" );
		}
		finally {
			try {
				out.close();
			}
			catch (IOException closeException) {
				System.out.println(closeException.getMessage());
			}
		}
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

	public Double measureGenerator(int min, int max){
		Random rn = new Random();
		int n = max - min;
		double i = rn.nextDouble() * n;
		return min + i;
	}

	public ResIterator getSensorFromId(Model model, String idsensore){
		ResIterator iter = model.listSubjectsWithProperty(LOMBARDIA_SENSORE.idsensore,idsensore);
		return iter;
	}
	public ResIterator getSensorFromTipology(Model model, String tipologia){
		ResIterator iter = model.listSubjectsWithProperty(LOMBARDIA_SENSORE.tipologia,tipologia);
		return iter;
	}

	public  void addMeasurement(Model model, Double value, String sensorId){


		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timestampS = Objects.toString(timestamp.getTime(),null);
		String measurementId = timestampS + sensorId;
		String measurementURI = "http://localhost:3030/Lombardia/"+measurementId;

		// addProperty add another statement to the Model
		Resource measurement
				= model.createResource(measurementURI)
				.addProperty(LOMBARDIA_SENSORE.idsensore,sensorId)
				.addProperty(LOMBARDIA.timestamp,timestampS)
				.addProperty(RDF.value, value.toString());
	}
}