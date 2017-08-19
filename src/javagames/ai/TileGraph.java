package javagames.ai;

import javagames.game.BoundsObject;
import javagames.game.GameObject;
import javagames.util.GameConstants;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;


import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TileGraph implements Graph<BoundsObject>
{


    class Tile extends BoundsObject
    {
        ArrayList<BoundsObject> contents;

        Tile(Vector2f size)
        {
            this(new BoundingBox(size));
        }

        Tile(BoundingShape bounds)
        {
            super(bounds);
            contents = new ArrayList<BoundsObject>();
            setName("Tile");
        }

        public boolean isEmpty()
        {
            return contents.isEmpty();
        }

        public void addObject(BoundsObject obj)
        {
            contents.add(obj);
        }

        public boolean removeObject(BoundsObject obj)
        {
            return contents.remove(obj);
        }
    }

    class TileEdge implements Connection<BoundsObject>
    {
        BoundsObject fromNode;
        BoundsObject toNode;
        int cost;

        TileEdge(BoundsObject fromNode, BoundsObject toNode)
        {
            this(fromNode,toNode,1);
        }

        TileEdge(BoundsObject fromNode, BoundsObject toNode, int cost)
        {
            this.fromNode=fromNode;
            this.toNode=toNode;
            this.cost = cost;
        }

        /**
         * returns the cost of this connection.
         */
        @Override
        public int getCost() {
            return cost;
        }

        /**
         * Returns the node that this connection came from.
         *
         * @return the node that this connection came from.
         */
        @Override
        public BoundsObject getFromNode() {
            return fromNode;
        }

        /**
         * Returns the node that this connection leads to.
         *
         * @return node that this connection leads to.
         */
        @Override
        public BoundsObject getToNode() {
            return toNode;
        }
    }

    public static final int DEF_GRAPH_SIZE = 10;
    protected Vector2f tileSize;
    protected Vector2f offset;
    protected Tile[][] grid;

    public TileGraph()
    {
        this(DEF_GRAPH_SIZE, DEF_GRAPH_SIZE, new Vector2f(GameConstants.UNIT_SIZE));
    }

    public TileGraph(int w, int h)
    {
        this(w,h, new Vector2f(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));
    }

    public TileGraph(int w, int h, Vector2f worldSize)
    {
        this(w,h,worldSize.div(new Vector2f(w,h)), worldSize.div(2.f));
    }

    public TileGraph(int w, int h, Vector2f tileSize, Vector2f offset)
    {
        this.tileSize = tileSize;
        this.offset = offset;
        grid = new Tile[w][h];
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                grid[x][y] = new Tile(tileSize);
            }
        }
    }

    public void clear()
    {
        for(int x =0; x < grid.length; x++)
        {
            for(int y = 0; y < grid.length;y++)
            {
                grid[x][y].contents.clear();
            }
        }
    }

    public void addObjects(List<BoundsObject> objects)
    {
        for(BoundsObject bo: objects)
        {
            addObject(bo);
        }
    }

    public void addObject(BoundsObject obj)
    {
        Point p = getCoords(obj.getPosition());
        grid[p.x][p.y].addObject(obj);
        System.out.printf("World Coord: %s | Graph Coord: %s\n", obj.getPosition().toString(), p.toString());
    }

    /**
     * Returns grid coordinates of a given position
     * @param position World Position to convert
     * @return location within the grid.
     */
    private Point getCoords(Vector2f position)
    {
       Vector2f v = position.add(offset);
       return v.div(tileSize).toPoint();
    }

    private Tile getTileAt(int x, int y)
    {
        if(x<0 || y < 0 || x >= grid.length || y >= grid[x].length) return null;
        return grid[x][y];
    }

    @Override
    public  TileEdge[] getConnections(BoundsObject fromNode)
    {
        TileEdge[] edges = new TileEdge[4];//(Connection<BoundsObject>[])new Object[4];
        Point p = getCoords(fromNode.getPosition());

        edges[0] = new TileEdge(fromNode,getTileAt(p.x+1, p.y));
        edges[1] = new TileEdge(fromNode,getTileAt(p.x,p.y+1));
        edges[2] = new TileEdge(fromNode,getTileAt(p.x,p.y-1));
        edges[3] = new TileEdge(fromNode,getTileAt(p.x-1, p.y));
        return edges;
    }

    @Override
    public String toString()
    {
       StringBuffer str = new StringBuffer();
       for(int x = 0; x < grid.length; x++)
       {
           str.append("[");
           for(int y = 0; y < grid[x].length; y++)
           {
               if(grid[x][y].isEmpty())
                   str.append(".");
               else
                   str.append("x");
           }
           str.append("]\n");
       }

       return str.toString();
    }
}
