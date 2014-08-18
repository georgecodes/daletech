package com.elevenware.daletech;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTypesMatcher {

    @Test
    public void matchesASingleExactClass() {

        TypesMatcher matcher = new TypesMatcher(String.class);
        assertTrue(matcher.matchesClasses(String.class));

    }

    @Test
    public void matchesAListOfClasses() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertTrue(matcher.matchesClasses(String.class, List.class, Map.class));

    }

    @Test
    public void matchesAListInAnyOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertTrue(matcher.matchesClasses(List.class, String.class, Map.class));

    }

    @Test
    public void matchesSubtypes() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertTrue(matcher.matchesClasses(String.class, ArrayList.class, HashMap.class));

    }

    @Test
    public void matchesInOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertTrue(matcher.matchesClassesInOrder(String.class, List.class, Map.class));

    }

    @Test
    public void matchesSubtypesInOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertTrue(matcher.matchesClassesInOrder(String.class, ArrayList.class, HashMap.class));

    }

    @Test
    public void theyHaveToBeInOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertFalse(matcher.matchesClassesInOrder(List.class, Map.class, String.class));

    }

    @Test
    public void subtypesHaveToBeInOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertFalse(matcher.matchesClassesInOrder(HashMap.class, ArrayList.class, String.class));

    }

    @Test
    public void nullDoesntMatch() {

        TypesMatcher matcher = new TypesMatcher(String.class);
        assertFalse(matcher.matchesClasses(null));

    }

    @Test
    public void fewerClassesDontMatch() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertFalse(matcher.matchesClasses(String.class));

    }

    @Test
    public void moreClassesDontMatch() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class, Map.class);
        assertFalse(matcher.matchesClasses(String.class, Object.class, List.class, Map.class));

    }

    @Test
    public void matchesASingleExactObject() {

        TypesMatcher matcher = new TypesMatcher(String.class);
        assertTrue(matcher.matchesObjects("Hello"));

    }

    @Test
    public void matchesObjects() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class);
        assertTrue(matcher.matchesObjects("Hello", Collections.emptyList()));

    }

    @Test
    public void matchesObjectsOutOfOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class);
        assertTrue(matcher.matchesObjects(Collections.emptyList(), "Hello"));

    }

    @Test
    public void matchesObjectsInfOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class);
        assertTrue(matcher.matchesObjectsInOrder("Hello",Collections.emptyList() ));

    }

    @Test
    public void nullsDontBreakThings() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class);
        assertTrue(matcher.matchesObjects(null, null));

    }

    @Test
    public void nullsDontBreakThingsInOrder() {

        TypesMatcher matcher = new TypesMatcher(String.class, List.class);
        assertTrue(matcher.matchesObjectsInOrder(null, null));

    }


}
