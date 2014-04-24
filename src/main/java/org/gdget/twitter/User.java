package org.gdget.twitter;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.*;

/**
 * Created by hugofirth on 15/01/2014.
 */
public class User extends Node {
    private static final Set<String> namesTaken = new HashSet<>();
    private static final RandomDataGenerator rng = new RandomDataGenerator();
    private final Integer maxNumFollowing;
    private final Integer maxNumFollowedBy;
    private final Integer maxNumSent;
    //Relationships
    private Map<String, User> following; // (This)-Follows->(User)
    private Map<String, User> followedBy; // (This)<-Follows-(User)
    private ArrayList<Tweet> sent; // (This)-Sent->(Tweet)
    //Fields
    private String name;

    /**
     * Instantiates a new User
     *
     * @param name the User's name
     */
    public User(String name, Integer maxNumUsers, Integer maxNumTweetsPerUser)
    {
        //If the name is taken - throw an exception
        if(namesTaken.contains(name)){
            throw new IllegalArgumentException("User name "+name+" taken!");
        }
        //Otherwise assign the name and "take" it
        this.name = name;
        namesTaken.add(name);
        //Get the random maximum number of followers for User, random number distribution according to Gamma probability distribution function.
        Integer randNumFollowedBy = (int)Math.round(rng.nextGamma(2, (0.12*maxNumUsers)));
        this.maxNumFollowedBy = (randNumFollowedBy<maxNumUsers)?((randNumFollowedBy>0)?randNumFollowedBy:1):maxNumUsers;
        //Get the random maximum number of follows by User, random number distribution according to Guassian probability distribution function with
        //randNumFollowedBy as the provided mean. This simulates the correlation between the number of follows and followers.
        Integer randNumFollowing = (int)Math.round(rng.nextGaussian(randNumFollowedBy, maxNumUsers*0.05));
        this.maxNumFollowing = (randNumFollowing<maxNumUsers)?((randNumFollowing>0)?randNumFollowing:1):maxNumUsers;
        //Get the random maximum number of tweets sent by User, random number distribution according to Guassian probability distribution function with
        //randNumFollowedBy as the provided mean. This simulates the correlation between the number of tweets and followers.
        Integer randNumSent = (int)Math.round(rng.nextGaussian(randNumFollowedBy, maxNumUsers*0.05));
        randNumSent = (randNumSent<maxNumUsers)?((randNumSent>0)?randNumSent:1):maxNumUsers-1;
        this.maxNumSent = randNumSent*(maxNumTweetsPerUser/maxNumUsers);
        //Initialise empty collections
        this.following = new HashMap<>();
        this.followedBy = new HashMap<>();
        this.sent = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * User (this) follows User (u)
     *
     * @param u the User being followed
     */
    public void follow(User u)
    {
        if(!(this.following.size()<this.maxNumFollowing)){
            throw new IndexOutOfBoundsException("This User may not follow more than "+this.maxNumFollowing+" users!");
        }
        u.addFollower(this);
        this.following.put(u.getName(), u);
    }

    /**
     * User (this) followed by User (u)
     *
     * @param u the User following this User
     */
    private void addFollower(User u)
    {
        if(!(this.followedBy.size()<this.maxNumFollowedBy)){
            throw new IndexOutOfBoundsException("This User may not have more than "+this.maxNumFollowedBy+" followers!");
        }
        this.followedBy.put(u.getName(), u);
    }

    /**
     * User (this) sends Tweet (t)
     *
     * @param t the Tweet being sent
     */
    public void send(Tweet t)
    {
        this.sent.add(t);
    }

    /**
     * Boolean check - is User (this) following User (u)?
     *
     * @param u the User potentially being followed
     * @return boolean
     */
    public Boolean isFollowing(User u)
    {
        return this.following.containsKey(u.getName());
    }

    /**
     * Boolean check - is User (this) followed by User (u)?
     *
     * @param u the User potentially following this User
     * @return boolean
     */
    public Boolean isFollowedBy(User u)
    {
        return this.followedBy.containsKey(u.getName());
    }

    /**
     * Returns the Users this User is following
     *
     * @return collection of Users
     */
    public Map<String, User> getFollowing()
    {
        return following;
    }

    /**
     * Returns the Users this User is followed by
     *
     * @return collection of Users
     */
    public Map<String, User> getFollowedBy()
    {
        return followedBy;
    }

    /**
     * Gets max num following.
     *
     * @return the max num following
     */
    public Integer getMaxNumFollowing()
    {
        return maxNumFollowing;
    }

    /**
     * Gets max num followed by.
     *
     * @return the max num followed by
     */
    public Integer getMaxNumFollowedBy()
    {
        return maxNumFollowedBy;
    }

    /**
     * Gets max num tweets sent
     *
     * @return the max num tweets sent
     */
    public Integer getMaxNumSent()
    {
        return maxNumSent;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets sent Tweet objects
     *
     * @return the sent Tweet objects
     */
    public ArrayList<Tweet> getSent()
    {
        return sent;
    }

    /**
     * Clears the namesTaken hashset.
     */
    public static void clearNamesTaken()
    {
        namesTaken.clear();
    }

}
