import by.bsu.practice.jdbc.exception.InvalidDataException;
import by.bsu.practice.jdbc.service.Operation;

public class Runner {
    public static void main(String... args) throws InvalidDataException {
        System.out.println(Operation.getCelestialBodiesLife("The Milky Way"));
        System.out.println(Operation.getCelestialBodiesMinRadius());
        System.out.println(Operation.getCelestialBodiesMaxSatellites());
        System.out.println(Operation.getCelestialBodiesMaxSatellitesMinRadius());
        System.out.println(Operation.findGalaxiesMaxTemperature());
    }

}
