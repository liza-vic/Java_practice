package by.bsu.practice.threads.entity;

import java.util.PrimitiveIterator;

//пункт следования с его зоной дальности
public enum Destination {
    MINSK(RangeZone.NEAR),
    MOSCOW(RangeZone.NEAR),
    LONDON(RangeZone.FAR),
    MADRID(RangeZone.FAR);

    private RangeZone zone;

    Destination(RangeZone zone){
        this.zone = zone;
    }

    public RangeZone getZone() {
        return zone;
    }
}
