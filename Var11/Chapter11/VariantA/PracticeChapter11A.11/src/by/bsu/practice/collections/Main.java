package by.bsu.practice.collections;

import by.bsu.practice.collections.service.ListOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String... args){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, -5, 0, -9, 17, 2, 0,30));
        ListOperation.changeElements(list);
        System.out.println("Result array: ");
        System.out.println(list);
    }
}
