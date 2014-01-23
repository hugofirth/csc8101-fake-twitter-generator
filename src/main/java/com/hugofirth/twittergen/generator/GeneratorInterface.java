package com.hugofirth.twittergen.generator;

import com.hugofirth.twittergen.twitter.Hashtag;
import com.hugofirth.twittergen.twitter.NodeInterface;
import com.hugofirth.twittergen.twitter.Tweet;
import com.hugofirth.twittergen.twitter.User;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by hugofirth on 15/01/2014.
 */
public interface GeneratorInterface {

    public Collection<User> getUsers();
    public Collection<Tweet> getTweets();
    public Collection<Hashtag> getHashtags();
    public Collection<NodeInterface> getNodes();
    public List<String> getUserDictionary();
    public List<String> getHashtagDictionary();
    public void execute(Integer numUsers, Integer numHashtags, Integer numTweetsPerUser);

}
