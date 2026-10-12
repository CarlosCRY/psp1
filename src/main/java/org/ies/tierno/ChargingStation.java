package org.ies.tierno;

import java.util.Objects;
import java.util.concurrent.Semaphore;

public class ChargingStation {
    private final Semaphore charges = new Semaphore(4);
    private StationStats sStats;

    public ChargingStation(StationStats sStats) {
        this.sStats = sStats;
    }

    public StationStats getsStats() {
        return sStats;
    }

    public void setsStats(StationStats sStats) {
        this.sStats = sStats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChargingStation that = (ChargingStation) o;
        return Objects.equals(charges, that.charges) && Objects.equals(sStats, that.sStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(charges, sStats);
    }

    public ChargeResult charge(Vehicle vehicle) {

        try {
            charges.acquire(1);
            System.out.println(vehicle.plate() + " cargando");
            Thread.sleep(vehicle.kwh() * 20L);
            sStats.register(vehicle.kwh());
            System.out.println(vehicle.plate() + " Finalizó la carga");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            charges.release(1);
        }
        return new ChargeResult(vehicle.plate(), vehicle.kwh(), vehicle.kwh() * 45);
    }
}
