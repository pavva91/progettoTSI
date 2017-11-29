package com.valerio.job;

import com.valerio.endpointaccess.FusekiMod;
import com.valerio.vocabulary.LOMBARDIA_SENSORE;
import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by valer on 27/11/2017.
 */
public class AddUnderThresholdMeasuresJob implements Runnable {
    private String serviceURI = "http://localhost:3030/Lombardia";
    @Override
    public void run() {
        try {
            FusekiMod fusekiMod = new FusekiMod();
            Model model = fusekiMod.getRDF(serviceURI);

            ResIterator iter = fusekiMod.getSensorFromTipology(model, "Temperatura");
            List<String> sensorIds = new ArrayList<>();
            String sensorId;
            if (iter.hasNext()) {
                while (iter.hasNext()) {
                    Resource resource = iter.nextResource();
                    sensorIds.add(resource.getProperty(LOMBARDIA_SENSORE.idsensore).getString());
                }
                int idx = new Random().nextInt(sensorIds.size());
                sensorId = (sensorIds.get(idx));
                System.out.println("ID under: " + sensorId);
                fusekiMod.addMeasurement(model, fusekiMod.measureGenerator(1, 10), sensorId);
                fusekiMod.updateRDF(serviceURI, model);
            } else {
                System.out.println("No sensors of that tipology found in the database");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}