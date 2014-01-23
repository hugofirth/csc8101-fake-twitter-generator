package com.hugofirth.twittergen.twitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests of User class functionality
 */
public class UserTest {

    private User user;
    private Integer maxNumFollowersPerUser;
    private Integer maxNumTweetsPerUser;

    @Before
    public void initObjects()
    {
        maxNumFollowersPerUser = 100;
        maxNumTweetsPerUser = 200;
        user = new User("Alice", maxNumFollowersPerUser, maxNumTweetsPerUser);
    }

    @After
    public void teardownObjects()
    {
        User.clearNamesTaken();
    }

    /**
     * A User should be able to be created with a String (Name)
     */
    @Test
    public void aUserShouldBeAbleToBeCreatedWithAString()
    {
        User user = new User("Bob", maxNumFollowersPerUser, maxNumTweetsPerUser);
        assertNotNull(user);
    }

    /**
     * A User should not be able to be created with the same String (Name) as another User
     */
    @Test(expected = IllegalArgumentException.class)
    public void aUserShouldNotBeAbleToBeCreatedWithTheSameStringAsAnotherUser()
    {
        User user = new User("Alice", maxNumFollowersPerUser, maxNumTweetsPerUser);
    }

    /**
     * A User should be able to follow a User
     */
    @Test
    public void aUserShouldBeAbleToFollowAUser()
    {
        User temp = new User("John", maxNumFollowersPerUser, maxNumTweetsPerUser);
        user.follow(temp);

        assertTrue("Followed User's followedBy Collection does not contain following User", temp.isFollowedBy(user));
        assertTrue("Following User's follows Collection does not contain followed User", user.isFollowing(temp));
    }

    /**
     * A User should be able to return a collection of Users they follow
     */
    @Test
    public void aUserShouldBeAbleToReturnACollectionOfUsersTheyFollow()
    {
        User temp = new User("Jim", maxNumFollowersPerUser, maxNumTweetsPerUser);
        user.follow(temp);
        Map<String, User> expected = new HashMap<String, User>();
        expected.put(temp.getName(), temp);
        assertEquals(user.getFollowing(), expected);
    }

    /**
     * A User should be able to return a collection of Users following them
     */
    @Test
    public void aUserShouldBeAbleToReturnACollectionOfUsersFollowingThem()
    {
        User temp = new User("Jeremy", maxNumFollowersPerUser, maxNumTweetsPerUser);
        temp.follow(user);
        Map<String, User> expected = new HashMap<String, User>();
        expected.put(temp.getName(), temp);
        assertEquals(user.getFollowedBy(), expected);
    }

    /**
     * A User should not be able to follow more Users than its max (maxNumFollowing)
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void aUserShouldNotBeAbleToFollowMoreUsersThanItsMax()
    {
        for(int i = 0; i<user.getMaxNumFollowing()+1; i++){
            User temp = new User("name"+i, maxNumFollowersPerUser, maxNumTweetsPerUser);
            user.follow(temp);
        }
    }

    /**
     * A User should not be able to be followed by more Users than its max (maxNumFollowedBy)
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void aUserShouldNotBeAbleToBeFollowedByMoreUsersThanItsMax()
    {
        for(int i = 0; i<user.getMaxNumFollowedBy()+1; i++){
            User temp = new User("name"+i, maxNumFollowersPerUser, maxNumTweetsPerUser);
            temp.follow(user);
        }
    }

    /**
     * A User should be able to send a Tweet
     */
    @Test
    public void aUserShouldBeAbleToSendATweet()
    {
        Tweet tweet = mock(Tweet.class);
        user.send(tweet);
        assertTrue("User's sent Collection does not contain Tweet", user.getSent().contains(tweet));
    }

    /**
     * A User should be able to return a collection of Tweets they have sent
     */
    @Test
    public void aUserShouldBeAbleToReturnACollectionOfTweetsSent()
    {
        Tweet tweet = mock(Tweet.class);
        user.send(tweet);
        ArrayList<Tweet> expected = new ArrayList<Tweet>();
        expected.add(tweet);
        assertEquals(user.getSent(), expected);
    }

    /**
     * A User should have a random value for maxNumFollowing in range 1-200
     */
    @Test
    @Ignore //TODO: work out the neatest way to do this - mocking or acceptable range of distribution?
    public void aUserShouldHaveARandomValueForMaxNumFollowing()
    {

    }

    /**
     * A User should have a random value for maxNumFollowedBy in range 1-1000
     */
    @Test
    @Ignore //TODO: Same as above
    public void aUserShouldHaveARandomValueForMaxNumFollowedBy()
    {

    }

    /**
     * A User should be able to get all fields
     */
    @Test
    public void aUserShouldBeAbleToGetAllFields()
    {
        assertEquals("User failed to return name correctly", user.getName(), "Alice");
        assertNotNull("User failed to return following collection correctly", user.getFollowing());
        assertNotNull("User failed to return followedBy collection correctly", user.getFollowedBy());
        assertNotNull("User failed to return maxNumFollowing correctly", user.getMaxNumFollowing());
        assertNotNull("User failed to return maxNumFollowedBy correctly", user.getMaxNumFollowedBy());
    }
}
