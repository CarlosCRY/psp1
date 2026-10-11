package org.ies.tierno;

import java.util.concurrent.Semaphore;

public class CharginStation {
    private final Semaphore charges = new Semaphore(4);

    public ChargeResult charge(Vehicle vehicle) {

        try {
            charges.acquire(1);
            System.out.println(vehicle.plate() + " cargando");
            Thread.sleep(vehicle.kwh() * 20L);
            //Registro jornada
            System.out.println(vehicle.plate() + " Finalizó la carga");
            charges.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
