package com.hugofirth.twittergen.twitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test of Hashtag class functionality
 */
public class HashtagTest {

    private Hashtag tag;

    @Before
    public void initObject()
    {
        tag = new Hashtag("FF");
    }

    @After
    public void teardownObjects()
    {
        Hashtag.clearNamesTaken();
    }

    /**
     * a Hashtag should be able to be created with a name (String)
     */
     @Test
    public void aHashtagShouldBeAbleToBeCreatedWithAName()
    {
        Hashtag tag = new Hashtag("Amazing");
        assertNotNull(tag);
    }

    /**
     * A Hashtag should not be able to be created with the same name (String as another Hashtag
     */
    @Test(expected = IllegalArgumentException.class)
    public void aHashtagShouldNotBeAbleToBeCreatedWithTheSameNameAsAnotherHashtag()
    {
        Hashtag tag = new Hashtag("FF");
    }

    /**
     * a Hashtag should be able to get all fields
     */
    @Test
    public void aHashtagShouldBeAbleToGetAllFields()
    {
        assertEquals("Hashtag failed to return name correctly.", tag.getName(), "FF");
        assertNotNull("Hashtag failed to return containedBy collection correctly.", tag.getContainedBy());
    }
}
