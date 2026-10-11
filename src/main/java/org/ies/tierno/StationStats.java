package org.ies.tierno;

import java.util.Objects;

public class StationStats {
    private int totalKwh;
    private long totalCents;

    public StationStats(int totalKwh, long totalCents) {
        this.totalKwh = totalKwh;
        this.totalCents = totalCents;
    }

    public int getTotalKwh() {
        return totalKwh;
    }

    public void setTotalKwh(int totalKwh) {
        this.totalKwh = totalKwh;
    }

    public long getTotalCents() {
        return totalCents;
    }

    public void setTotalCents(long totalCents) {
        this.totalCents = totalCents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StationStats that = (StationStats) o;
        return totalKwh == that.totalKwh && totalCents == that.totalCents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalKwh, totalCents);
    }

    public void register (int kwh) {
        totalKwh += kwh;
        totalCents += kwh + 45;
    }

    public StatsSnapshot snapshot() {
        return new StatsSnapshot(totalKwh, totalCents);
    }
}
