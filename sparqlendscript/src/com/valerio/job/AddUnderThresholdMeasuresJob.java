package com.valerio.job;

import com.valerio.endpointaccess.FusekiMod;
import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Model;

import java.util.Scanner;

/**
 * Created by valer on 27/11/2017.
 */
public class AddUnderThresholdMeasuresJob implements Runnable {
    private String serviceURI = "http://localhost:3030/Lombardia";
    @Override
    public void run() {
        // Do your quarterly job here.
//        Scanner scanner = new Scanner(System.in);
//        Boolean flag = true;
//        FusekiMod fusekiMod = new FusekiMod();
//        Model model = fusekiMod.getRDF(serviceURI);
//        fusekiMod.addMeasurement(model,fusekiMod.measureGenerator(1,10));
//        model.write(System.out,"Turtle");
//        fusekiMod.updateRDF(serviceURI,model);
        try{
            FusekiMod fusekiMod = new FusekiMod();
            Model model = fusekiMod.getRDF(serviceURI);
            fusekiMod.addMeasurement(model,fusekiMod.measureGenerator(1,10));
            model.write(System.out,"Turtle");
            fusekiMod.updateRDF(serviceURI,model);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("current time: "+ System.currentTimeMillis());
    }

}