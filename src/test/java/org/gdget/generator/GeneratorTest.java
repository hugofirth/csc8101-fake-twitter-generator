package org.gdget.generator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hugofirth on 15/01/2014.
 */
public class GeneratorTest {

    private URL userDictionaryResource;
    private URL hashtagDictionaryResource;
    private Generator generator;

    @Before
    public void setupTestClass()
    {
        this.userDictionaryResource = getClass().getResource("/fake_twitter_names_test.txt");
        this.hashtagDictionaryResource = getClass().getResource("/fake_twitter_hashtags_test.txt");
        assertNotNull("Test userDictionary file missing!", this.userDictionaryResource);
        assertNotNull("Test hashtagDictionary file missing!", this.hashtagDictionaryResource);
        try {
            this.generator = new Generator(this.userDictionaryResource.getPath(), this.hashtagDictionaryResource.getPath());
        } catch (IOException e) {
            fail("Failed to instantiate generator object with exception "+e.toString());
        }
    }

    /**
     * A Generator should be able to generate Users, Tweets & Hashtags
     */
    @Test
    public void aGeneratorShouldBeAbleToGenerateUsersTweetsHashtags()
    {
        int expectedNumUsers = 5;
        int expectedNumHashtags = 3;
        int maxNumTweetsPerUser = 10;
        assertTrue("Users collection isn't empty prior to generation!", generator.getUsers().isEmpty());
        assertTrue("Hashtags collection isn't empty prior to generation!", generator.getHashtags().isEmpty());
        assertTrue("Tweets collection isn't empty prior to generation!", generator.getTweets().isEmpty());
        generator.execute(expectedNumUsers, expectedNumHashtags, maxNumTweetsPerUser);
        assertEquals("Users not generated as expected!", generator.getUsers().size(), expectedNumUsers);
        assertEquals("Hashtags not generated as expected!", generator.getHashtags().size(), expectedNumHashtags);
        assertFalse("Tweets not generated as expected!", generator.getTweets().isEmpty());
    }

    /**
     * A Generator should be able to read from a dictionary file.
     */
    @Test
    public void aGeneratorShouldBeAbleToReadFromADictionaryFile()
    {
        List<String> expectedUsers = new LinkedList<String>();
        expectedUsers.add("@acorn");
        expectedUsers.add("@akannampilly");
        expectedUsers.add("@AWKIFalbum");
        expectedUsers.add("@BananaKarenina");
        expectedUsers.add("@BDUTT");
        List<String> expectedHashtags = new LinkedList<String>();
        expectedHashtags.add("#24");
        expectedHashtags.add("#Africa");
        expectedHashtags.add("#Discovery");

        assertEquals("Test data from user dictionary file not as expected!", expectedUsers, generator.getUserDictionary());
        assertEquals("Test data from hashtag dictionary file not as expected!", expectedHashtags, generator.getHashtagDictionary());
    }

    /**
     * A Generator should not be able to read from a non existent dictionary file
     */
    @Test(expected = IOException.class)
    public void aGeneratorShouldNotBeAbleToReadFromANonExistentDictionaryFile() throws IOException
    {
        Generator generator1 = new Generator("/not_a_file.txt", "/ditto.txt");
    }


}
