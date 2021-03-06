package ru.job4j.collection.set;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class SimpleSetTest {

    @Test
    public void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertTrue(set.add(1));
        assertFalse(set.contains(2));
        assertTrue(set.contains(1));
        assertFalse(set.add(1));
    }

    @Test
    public void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertTrue(set.add(null));
        assertTrue(set.contains(null));
        assertFalse(set.add(null));
    }

    @Test
    public void whenIteratorHasNext() {
        Set<String> set = new SimpleSet<>();
        set.add("Строка");
        Iterator<String> it = set.iterator();
        assertTrue(it.hasNext());
        assertTrue(it.next().equals("Строка"));
        assertFalse(it.hasNext());
    }

}