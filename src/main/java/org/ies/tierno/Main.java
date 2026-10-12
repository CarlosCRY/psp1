package org.ies.tierno;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
                    System.out.println(cStation.charge(vehicle));
            });
        }

        eso.shutdown();

        eso.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println(cStation.getsStats().snapshot());

        int realTK = 0;
        int realTC = 0;
        for (Vehicle vehicle: vehicles) {
            realTK += vehicle.kwh();
            realTC += vehicle.kwh() * 45;
        }

        if (realTK == cStation.getsStats().snapshot().kwh() && realTC == cStation.getsStats().snapshot().cents()) {
            System.out.println(
                    "El snapshot coincide con el total de los KWH total de todos los coches y su valor en céntimos");
        } else {
            System.out.println("El snapshot NO coincide con los totales de los vehículos");
        }
    }

    public static String generatePlate() {
        return random.nextInt(1000, 10000) + " " + (char)random.nextInt(65, 90)
                + (char)random.nextInt(65, 90) + (char)random.nextInt(65, 90);
    }
}