package org.ies.tierno;

import java.util.concurrent.Semaphore;

public class ChargingStation {
    private final Semaphore charges = new Semaphore(4);
    private StationStats sStats;

    public ChargingStation(StationStats sStats) {
        this.sStats = sStats;
    }

    public ChargeResult charge(Vehicle vehicle) {

        try {
            charges.acquire(1);
            System.out.println(vehicle.plate() + " cargando");
            Thread.sleep(vehicle.kwh() * 20L);
            sStats.register(vehicle.kwh());
            System.out.println(vehicle.plate() + " Finalizó la carga");
            charges.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ChargeResult(vehicle.plate(), vehicle.kwh(), vehicle.kwh() * 45);
    }
}
