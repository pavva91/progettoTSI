package com.valerio.job;

import com.valerio.endpointaccess.FusekiMod;
import org.apache.jena.rdf.model.Model;

/**
 * Created by valer on 28/11/2017.
 */
public class AddOverThresholdMeasuresJob implements Runnable {
    private String serviceURI = "http://localhost:3030/Lombardia";
    @Override
    public void run() {
        // Do your quarterly job here.
//        Scanner scanner = new Scanner(System.in);
//        Boolean flag = true;
        try{
            FusekiMod fusekiMod = new FusekiMod();
            Model model = fusekiMod.getRDF(serviceURI);
            fusekiMod.addMeasurement(model,fusekiMod.measureGenerator(10,20));
            model.write(System.out,"Turtle");
            fusekiMod.updateRDF(serviceURI,model);
            System.out.println(serviceURI);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
