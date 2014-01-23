package com.hugofirth.twittergen.query;

import com.hugofirth.twittergen.generator.GeneratorInterface;

/**
 * Created by hugofirth on 15/01/2014.
 */
public interface QueryInterface {

    public GeneratorInterface getGenerator();
    public void execute();

}
