package org.gdget.query;

import org.gdget.generator.GeneratorInterface;

/**
 * Created by hugofirth on 15/01/2014.
 */
public interface QueryInterface {

    public GeneratorInterface getGenerator();
    public void execute();

}
