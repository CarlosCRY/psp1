package org.ies.tierno;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static Random random = new Random();
    private static final int NVEHICLES = 20;
    private static final int OCHO = 8;
    public static void main(String[] args) throws InterruptedException{
        ChargingStation cStation = new ChargingStation(new StationStats(0,0));
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < NVEHICLES; i++) {
            vehicles.add(new Vehicle(generatePlate(),random.nextInt(5, 10) * 10));
        }
        ExecutorService eso = Executors.newFixedThreadPool(OCHO);

        for (Vehicle vehicle: vehicles) {
            eso.submit(() -> {
                try {
                    System.out.println(cStation.charge(vehicle));
                } catch (InterruptedException e) {

                }
            });
        }
    }

    public static String generatePlate() {
        return random.nextInt(1000, 10000) + " " + (char)random.nextInt(65, 90)
                + (char)random.nextInt(65, 90) + (char)random.nextInt(65, 90);
    }
}