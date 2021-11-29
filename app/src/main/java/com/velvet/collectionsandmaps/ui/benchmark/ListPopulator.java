package com.velvet.collectionsandmaps.ui.benchmark;

import java.util.List;
import java.util.Random;

public class ListPopulator {

    public static List populate(List list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(getRandomNumber(-10000,10000));
        }
        return list;
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
