package javagames.ai;

public interface Connection <T>
{
    /**
     * returns the cost of this connection.
     */
    int getCost();

    /**
     * Returns the node that this connection came from.
     * @return the node that this connection came from.
     */
     T getFromNode();

    /**
     * Returns the node that this connection leads to.
     * @return node that this connection leads to.
     */
    T getToNode();

}
