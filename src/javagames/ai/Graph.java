package javagames.ai;

public interface Graph <T>
{
    public Connection<T>[] getConnections(T fromNode);
}
