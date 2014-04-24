package org.gdget.generator;

import org.gdget.twitter.Hashtag;
import org.gdget.twitter.NodeInterface;
import org.gdget.twitter.Tweet;
import org.gdget.twitter.User;

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
