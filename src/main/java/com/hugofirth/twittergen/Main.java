package com.hugofirth.twittergen;

import com.hugofirth.twittergen.generator.Generator;
import com.hugofirth.twittergen.query.Neo4jQuery;

import java.io.IOException;

/**
 * Created by hugofirth on 15/01/2014.
 */
public class Main {

    public static void main(String[] args){

        try {
            Generator generator = new Generator("/fake_twitter_names.txt", "/fake_twitter_hashtags.txt");
            generator.execute(1000, 250, 2000);
            Neo4jQuery query = new Neo4jQuery(generator);
            query.execute();
        } catch(IOException e) {
            System.out.println("Failed to instantiate generator object with exception "+e.toString());
            System.exit(1);
        }

    }
}
