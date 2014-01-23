package com.hugofirth.twittergen.twitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of Tweet class functionality
 */
public class TweetTest {

    private Tweet tweet;
    private User sender;
    private User senderOriginal;
    private User mentioned;
    private Hashtag contained;
    private Tweet retweeted;

    @Before
    public void initObjects()
    {
        sender  = mock(User.class);
        senderOriginal = mock(User.class);
        mentioned = mock(User.class);
        contained = mock(Hashtag.class);
        retweeted = new Tweet.Builder().setSentBy(senderOriginal).build();
        tweet = new Tweet.Builder().setSentBy(sender).setMentions(mentioned).setRetweets(retweeted).setContains(contained).build();
    }

    /**
     * A Tweet should be able to be created with a User (SentBy)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUser()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a User (Mentions)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserAUser()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setMentions(mentioned).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a User (Mentions) a Tweet (Retweets)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserAUserATweet()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setMentions(mentioned).setRetweets(retweeted).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a User (Mentions) a Hashtag (Contains)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserAUserAHashtag()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setMentions(mentioned).setContains(contained).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a User (Mentions) a Tweet (Retweets) a Hashtag (Contains)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserAUserATweetAHashtag()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setMentions(mentioned).setRetweets(retweeted).setContains(contained).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a Tweet (Retweets)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserATweet()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setRetweets(retweeted).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a Hashtag (Contains)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserAHashtag()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setContains(contained).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to be created with a User (SentBy) a Tweet (Retweets) a Hashtag (Contains)
     */
    @Test
    public void aTweetShouldBeAbleToBeCreatedWithAUserATweetAHashtag()
    {
        Tweet t = new Tweet.Builder().setSentBy(sender).setRetweets(retweeted).setContains(contained).build();
        assertNotNull(t);
    }

    /**
     * A Tweet should be able to get all fields
     */
    @Test
    public void aTweetShouldBeAbleToGetAllFields()
    {
        assertEquals("Tweet failed to return SentBy User", tweet.getSentBy(), sender);
        assertEquals("Tweet failed to return Mentions User", tweet.getMentions(), mentioned);
        assertEquals("Tweet failed to return Retweet Tweet", tweet.getRetweets(), retweeted);
        assertEquals("Tweet failed to return Contains Hashtag", tweet.getContains(), contained);
    }

}
