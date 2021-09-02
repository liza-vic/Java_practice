package by.bsu.practice.collections.service;

import by.bsu.practice.collections.exception.NullStringException;

import java.util.ArrayList;
import java.util.List;

public class Encryption {
    //размер 20 бит
    private static final int N = 1048576;
    public static List<Integer> encryption(String str1, String str2) throws NullStringException {
        //исключение если пришли неверные данные
        if(str1.length() == 0|| str2.length() == 0 || str2.length() != str1.length()){
            throw new NullStringException("Incorrect data");
        }
        //проверяем щифр по первому символу начальной и итоговой строки
        int symStart = str1.charAt(0);
        int symFinish = str2.charAt(0);
        List<Integer> result = new ArrayList<>();

        List<Double> list1 = new ArrayList<>();
        List<Double> list2 = new ArrayList<>();

        //заполняеам списки ключей
        fillList(list1);
        fillList(list2);

        //умножаем каждое значение списка на числовое представление начального символа
        for(int i = 1; i < list1.size();i++){
            list1.set(i,i * (double)symStart);
        }

        //делим шифрованное значение символа на значение в списке
        for(int i = 1; i < list2.size();i++){
            list2.set(i,symFinish / (double)i);
        }


        //ищем одинаковые значения в двух списках
        for (double a : list1) {
            for (int j = 1; j < list2.size(); j++) {
                if (a == list2.get(j))
                    result.add(j);

            }
            if (result.size() == 2){
                break;
            }

        }
        return result;

    }

//метод заполняет список соответсвующими индексами
    private static void fillList(List<Double> list){
        for(int i = 0; i < Encryption.N; i++){
            list.add((double)i);
        }
    }
}
