package com.valerio.job;

/**
 * Created by valer on 28/11/2017.
 */
public class AddUponThresholdMeasuresJob implements Runnable {
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
        System.out.println(serviceURI);
    }
}