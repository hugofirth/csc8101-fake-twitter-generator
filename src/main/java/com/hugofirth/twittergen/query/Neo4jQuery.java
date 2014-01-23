package com.hugofirth.twittergen.query;

import com.hugofirth.twittergen.generator.GeneratorInterface;
import com.hugofirth.twittergen.twitter.Hashtag;
import com.hugofirth.twittergen.twitter.Tweet;
import com.hugofirth.twittergen.twitter.User;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hugofirth on 23/01/2014.
 */
public class Neo4jQuery implements QueryInterface{

    private GeneratorInterface generator;
    private Map<String, String> config;
    private BatchInserter inserter;

    public Neo4jQuery(GeneratorInterface generator){
        if(!(generator.getNodes().size()>0)){
            throw new IllegalStateException("The generator provided has not yet been executed!");
        }

        //TODO: Generalise config values - these values for 1M nodes and 2M relationships
        this.config = new HashMap<>();
        this.config.put("neostore.nodestore.db.mapped_memory", "9M");
        this.config.put("neostore.relationshipstore.db.mapped_memory", "66M");
        this.config.put("neostore.propertystore.db.mapped_memory", "50M");
        this.config.put("neostore.propertystore.db.strings.mapped_memory", "100M");
        this.config.put("neostore.propertystore.db.arrays.mapped_memory", "0M");

        this.inserter = BatchInserters.inserter("target/twitter_data", this.config);
    }

    @Override
    public GeneratorInterface getGenerator() {
        return this.generator;
    }

    @Override
    public void execute() {
        //Create User node label & index
        Label userLabel = DynamicLabel.label("User");
        inserter.createDeferredSchemaIndex(userLabel).on("name").create();
        //Create Tweet node label
        Label tweetLabel = DynamicLabel.label("Tweet");
        //Create Hashtag node label & index
        Label hashtagLabel = DynamicLabel.label("Hashtag");
        inserter.createDeferredSchemaIndex(hashtagLabel).on("name").create();

        //Create User node properties
        Map<String, Object> userProperties = new HashMap<>();
        //Create Hashtag node properties
        Map<String, Object> hashtagProperties = new HashMap<>();

        //Create User nodes in Neo4j
        for(User u: this.generator.getUsers()){
            userProperties.put("name", u.getName());
            u.setNodeId(inserter.createNode(userProperties, userLabel));
        }
        //Create Tweet nodes in Neo4j
        for(Tweet t: this.generator.getTweets()){
            t.setNodeId(inserter.createNode(null, tweetLabel));
        }
        //Create Hashtag nodes in Neo4j
        for(Hashtag h: this.generator.getHashtags()){
            hashtagProperties.put("name", h.getName());
            h.setNodeId(inserter.createNode(hashtagProperties, hashtagLabel));
        }

        //Create relationships
        RelationshipType follows = DynamicRelationshipType.withName("FOLLOWS");
        RelationshipType sent = DynamicRelationshipType.withName("SENT");
        RelationshipType mentions = DynamicRelationshipType.withName("MENTIONS");
        RelationshipType retweets = DynamicRelationshipType.withName("RETWEETS");
        RelationshipType contains = DynamicRelationshipType.withName("CONTAINS");

        //Loop through Users creating follow and sent
        for(User u: this.generator.getUsers()){
            //Create follows relationships
            for(User followed: u.getFollowing().values()){
                inserter.createRelationship(u.getNodeId(), followed.getNodeId(), follows, null);
            }
            //Create sent relationships
            for(Tweet sentTweet: u.getSent()){
                inserter.createRelationship(u.getNodeId(), sentTweet.getNodeId(), sent, null);
            }
        }

        //Loop through Tweets creating retweets, contains and mentions
        for(Tweet t: this.generator.getTweets()){
            //Create mentions relationships
            if(t.getMentions() != null){
                inserter.createRelationship(t.getNodeId(), t.getMentions().getNodeId(), mentions, null);
            }
            //Create contains relationships
            if(t.getContains() != null){
                inserter.createRelationship(t.getNodeId(), t.getContains().getNodeId(), contains, null);
            }
            //Create retweets relationships
            if(t.getRetweets() != null){
                inserter.createRelationship(t.getNodeId(), t.getRetweets().getNodeId(), retweets, null);
            }
        }

        inserter.shutdown();
    }
}
