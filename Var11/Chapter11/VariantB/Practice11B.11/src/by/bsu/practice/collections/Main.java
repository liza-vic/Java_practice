package by.bsu.practice.collections;

import by.bsu.practice.collections.exception.NullStringException;
import by.bsu.practice.collections.service.Encryption;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String... args) throws NullStringException {
        // time counting
        Date currentTime = new Date();
        List<Integer> result = Encryption.encryption("./0","\\]^");
        Date newTime = new Date();
        long msDelayArray = newTime.getTime() - currentTime.getTime();

        System.out.println("Keys are: ");
        System.out.println(result);
        System.out.println("Operation time in msSeconds :");
        System.out.println(msDelayArray);
    }
}
