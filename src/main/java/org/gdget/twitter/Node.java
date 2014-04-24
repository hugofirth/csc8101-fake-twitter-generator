package org.gdget.twitter;

/**
 * Created by hugofirth on 24/01/2014.
 */
public abstract class Node implements NodeInterface {

    private Long nodeID;

    @Override
    public void setNodeId(Long nodeID)
    {
        this.nodeID = nodeID;
    }

    @Override
    public Long getNodeId()
    {
        return this.nodeID;
    }

}
