package org.gdget;

import org.gdget.generator.Generator;
import org.gdget.query.Neo4jQuery;

import java.io.IOException;
import java.net.URL;

/**
 * Created by hugofirth on 15/01/2014.
 */
public class Main {

    public static void main(String[] args){

        try {
            URL namesDictionary = Main.class.getResource("/fake_twitter_names.txt");
            URL hashtagsDictionary = Main.class.getResource("/fake_twitter_hashtags.txt");
            Generator generator = new Generator(namesDictionary.getPath(), hashtagsDictionary.getPath());
            generator.execute(1000, 250, 2000);
            Neo4jQuery query = new Neo4jQuery(generator);
            query.execute();
        } catch(IOException e) {
            System.out.println("Failed to instantiate generator object with exception "+e.toString());
            System.exit(1);
        }

    }
}
