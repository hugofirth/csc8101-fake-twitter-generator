package org.gdget.generator;

import org.gdget.twitter.Hashtag;
import org.gdget.twitter.NodeInterface;
import org.gdget.twitter.Tweet;
import org.gdget.twitter.User;
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by hugofirth on 20/01/2014.
 */
public class Generator implements GeneratorInterface {



    private ArrayList<User> users;
    private ArrayList<Hashtag> hashtags;
    private ArrayList<Tweet> tweets;
    private List<String> userDictionary;
    private List<String> hashtagDictionary;

    public Generator(String userDictionaryPath, String hashtagDictionaryPath) throws IOException
    {
        //Read Dictionary Files
        Path userDictionaryFile = Paths.get(userDictionaryPath);
        Path hashtagDictionaryFile = Paths.get(hashtagDictionaryPath);
        this.userDictionary = Files.readAllLines(userDictionaryFile, StandardCharsets.UTF_8);
        this.hashtagDictionary = Files.readAllLines(hashtagDictionaryFile, StandardCharsets.UTF_8);
        //Instantiate empty collections
        this.users = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.tweets = new ArrayList<>();
    }

    @Override
    public Collection<User> getUsers()
    {
        return this.users;
    }

    @Override
    public Collection<Tweet> getTweets()
    {
        return this.tweets;
    }

    @Override
    public Collection<Hashtag> getHashtags()
    {
        return this.hashtags;
    }

    public Collection<NodeInterface> getNodes() {
        ArrayList<NodeInterface> nodes = new ArrayList<>();
        nodes.addAll(this.users);
        nodes.addAll(this.tweets);
        nodes.addAll(this.hashtags);
        return nodes;
    };

    @Override
    public List<String> getUserDictionary()
    {
        return this.userDictionary;
    }

    @Override
    public List<String> getHashtagDictionary()
    {
        return this.hashtagDictionary;
    }

    @Override
    public void execute(Integer numUsers, Integer numHashtags, Integer numTweetsPerUser)
    {
        this.generateUsers(numUsers, numTweetsPerUser);

        this.generateFollows(numUsers);

        this.generateTweets();

        this.generateHashtags(numHashtags);

        this.generateContains(numHashtags);

        this.generateMentions();

        this.generateRetweets();

    }

    private void generateUsers(Integer numUsers, Integer maxNumTweetsPerUser)
    {
        Iterator<String> itr = this.userDictionary.listIterator();
        Integer i = 0;
        Integer maxNumFollowersPerUser = numUsers-1;
        //Loop through the userDictionary up to the numUsers creating User objects
        while(i<numUsers && itr.hasNext()){
            String name = itr.next();
            this.users.add(new User(name, maxNumFollowersPerUser, maxNumTweetsPerUser));
            i++;
        }
    }

    private void generateFollows(Integer numUsers)
    {
        //Shuffle user collection
        Collections.shuffle(this.users);
        //For each user - create follow connections with every other user with "space"
        for(User u : this.users){
            Integer i = 0;
            while(u.getFollowing().size()<u.getMaxNumFollowing() && i<numUsers){
                User examining = this.users.get(i);
                if(!u.equals(examining) && examining.getFollowedBy().size()<examining.getMaxNumFollowedBy()){
                    u.follow(examining);
                }
                i++;
            }
        }
    }

    private void generateTweets()
    {
        //For each User u sent tweets up to maxSentBy
        for(User u : this.users){
            for(int i = 0; i<u.getMaxNumSent(); i++){
                Tweet t = new Tweet.Builder().setSentBy(u).build();
                u.send(t);
                this.tweets.add(t);
            }
        }
    }

    private void generateHashtags(Integer numHashtags)
    {
        Iterator<String> itr = this.hashtagDictionary.listIterator();
        Integer i = 0;
        //Loop through the hashtagDictionary up to the numUsers creating User objects
        while(i<numHashtags && itr.hasNext()){
            String name = itr.next();
            this.hashtags.add(new Hashtag(name));
            i++;
        }
    }

    private void generateContains(Integer numHashtags)
    {
        //numContains = 11% of Tweets
        Integer numContains = (int)Math.round(this.tweets.size()*0.11);
        //Generate probability distribution for which Hashtags will occur (linear)
        //TODO: refactor this to separate statistics package and make it implement the interface in commons math.
        int[] indices = new int[numHashtags];
        double[] probabilities = new double[numHashtags];
        double probabiltyUnit = 0.25/numHashtags;
        for(int i = 0; i<numHashtags; i++){
            indices[i] = i;
            probabilities[i] = probabiltyUnit*(numHashtags-i);
        }
        EnumeratedIntegerDistribution probabilityDist = new EnumeratedIntegerDistribution(indices, probabilities);
        //Create the comparator for a collection of Hashtags sorted by usage (containedBy.size())
        Comparator<Hashtag> c = new Comparator<Hashtag>() {
            @Override
            public int compare(Hashtag o1, Hashtag o2) {
                return Integer.compare(o1.getContainedBy().size(), o2.getContainedBy().size());
            }
        };
        //Shuffle Tweets
        Collections.shuffle(this.tweets);
        //For i upto numContains
        for(int i = 0; i<numContains; i++){
            //Get random Hashtag h (from a set sorted by size of containedBy and p i=1 > p i=2 >p i=3)
            Hashtag h = this.hashtags.get(probabilityDist.sample());
            this.tweets.get(i).setContains(h);
            //Sort Hashtags by usages
            Collections.sort(this.hashtags, c);
        }
    }

    private void generateMentions()
    {
        //numMentions = 47% of tweets
        Integer numMentions = (int)Math.round(this.tweets.size()*0.47);
        //Base probability of a mention
        Double mentionProbabilty = 0.2;
        //Random number gen
        Random rng = new Random();
        //Shuffle Tweets
        Collections.shuffle(this.tweets);
        //Get an iterator for tweets
        Iterator<Tweet> itr = this.tweets.listIterator();
        Integer i = 0;
        //Iterate until the desired number of tweets contain mentions
        while(i<numMentions && itr.hasNext()){
            User u = this.users.get(rng.nextInt(this.users.size()));
            Tweet t = itr.next();
            User sender = t.getSentBy();
            //If the randomly selected user is NOT the user who sent the tweet
            if(!sender.equals(u)){
                mentionProbabilty += (sender.isFollowing(u))?0.3:0;
                mentionProbabilty += (sender.isFollowedBy(u))?0.1:0;
                if(rng.nextDouble()<mentionProbabilty){
                    t.setMentions(u);
                    i++;
                }
            }
        }
    }

    private void generateRetweets()
    {
        //numRetweets = 13% of tweets
        Integer numRetweets = (int)Math.round(this.tweets.size()*0.13);
        //Random number gen
        Random rng = new Random();
        //Shuffle tweets
        Collections.shuffle(this.tweets);
        //Get an iterator for tweets
        Iterator<Tweet> itr = this.tweets.listIterator();
        Integer i = 0;
        //Iterate until the desired number of tweets are retweets
        while(i<numRetweets && itr.hasNext()){
            //Base probability of a retweet
            Double retweetProbability = 0.02;
            Tweet pRetweet = this.tweets.get(rng.nextInt(this.tweets.size()));
            Tweet t = itr.next();
            User originalSender = pRetweet.getSentBy();
            User newSender = t.getSentBy();
            //If the randomly selected tweet is NOT sent by the same user who sent the tweet
            if(!newSender.equals(originalSender)){
                //TODO: update this to be more realistic (linear or exponential distribution)
                retweetProbability += (newSender.isFollowing(originalSender))?0.3:0;
                retweetProbability += (pRetweet.getContains()!=null)?0.5:0;
                Double rand = rng.nextDouble();
                if(rng.nextDouble()<retweetProbability){
                    t.setRetweets(pRetweet);
                    i++;
                }
            }
        }
    }
}
