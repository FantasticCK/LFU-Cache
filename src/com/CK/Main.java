package com.CK;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new TreeMap<>((o1, o2) -> {
            return o2 - o1;
        });
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);
        map.put(4, 40);
        map.put(5, 50);
        map.put(1, 100);
        for (Integer i : map.keySet()) {
            System.out.println("key: " + i + ", value: " + map.get(i));
        }
    }
}

class LFUCache {

    int capacity;
    int min = 1;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); //key:value
    HashMap<Integer, Integer> count = new HashMap<Integer, Integer>(); //key:count
    HashMap<Integer, LinkedHashSet<Integer>> list = new HashMap<Integer, LinkedHashSet<Integer>>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
        list.put(1, new LinkedHashSet<Integer>());
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        if (count.get(key) == min && list.get(min).size() == 1)
            min++;

        list.get(count.get(key)).remove(key);
        count.put(key, count.get(key) + 1);
        if (!list.containsKey(count.get(key))) {
            list.put(count.get(key), new LinkedHashSet<Integer>());
        }
        list.get(count.get(key)).add(key);

        return map.get(key);
    }

    public void remove() {
        int key = list.get(min).iterator().next();
        list.get(min).remove(key);
        count.remove(key);
        map.remove(key);
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;

        if (!map.containsKey(key)) {
            if (map.size() == capacity)
                remove();
            map.put(key, value);
            count.put(key, 1);
            list.get(1).add(key);
            min = 1;
        } else {
            map.put(key, value);
            get(key);
        }
    }
}
