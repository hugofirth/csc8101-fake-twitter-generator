package com.hugofirth.twittergen.twitter;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Hashtag
 */
public class Hashtag extends Node {

    private static Set<String> namesTaken = new HashSet<>();
    //Relationships
    private Set<Tweet> containedBy; // (This)<-Contains-(Tweet)
    //Fields
    private String name;

    /**
     * Instantiates a new Hashtag.
     *
     * @param name the name
     */
    public Hashtag(String name)
    {
        //If the name is taken - throw an exception
        if(namesTaken.contains(name)){
            throw new IllegalArgumentException("User name "+name+" taken!");
        }
        this.name = name;
        namesTaken.add(name);

        this.containedBy = new HashSet<>();
    }

    /**
     * Compares an object with this
     *
     * @param o the Object being compared to this
     * @return boolean representing this==o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hashtag hashtag = (Hashtag) o;

        if (!name.equals(hashtag.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets contained by.
     *
     * @return the contained by
     */
    public Set<Tweet> getContainedBy() {
        return containedBy;
    }

    /**
     * Adds a Tweet to the containedBy collection
     *
     * @param t the Tweet being added
     */
    public void containBy(Tweet t)
    {
        this.containedBy.add(t);
    }

    /**
     * Clears the namesTaken hashset.
     */
    public static void clearNamesTaken()
    {
        namesTaken.clear();
    }

}
