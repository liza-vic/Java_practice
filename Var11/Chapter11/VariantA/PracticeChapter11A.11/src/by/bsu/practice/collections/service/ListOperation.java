package by.bsu.practice.collections.service;

import java.util.Collections;
import java.util.List;

public class ListOperation {

    public static void changeElements(List<Integer> list){
        int j = 0;
        //пербирааем все элементы и все отрицательные в начало
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i) < 0) {
                if (i != j)
                    Collections.swap(list,i,j);
                j++;

            }
        }

        //все положительные в конец
        j = list.size() - 1;
        for (int i = list.size() -1; list.get(i) >= 0; i--) {

            if (list.get(i) > 0) {
                if (i != j)
                    Collections.swap(list,i,j);
                j--;

            }
        }
    }
}
