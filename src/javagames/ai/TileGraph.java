package javagames.ai;

import javagames.game.BoundsObject;
import javagames.game.GameObject;
import javagames.util.GameConstants;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileGraph
{

    class Tile
    {
        ArrayList<BoundsObject> contents;

        Tile()
        {
            contents = new ArrayList<BoundsObject>();
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

    public static final int DEF_GRAPH_SIZE = 10;
    protected Vector2f tileSize;
    protected Tile[][] grid;

    public TileGraph()
    {
        this(DEF_GRAPH_SIZE, DEF_GRAPH_SIZE, new Vector2f(GameConstants.UNIT_SIZE));
    }

    public TileGraph(int w, int h)
    {
        this(w,h, new Vector2f(GameConstants.UNIT_SIZE));
    }

    public TileGraph(int w, int h, Vector2f tileSize)
    {
        this.tileSize = tileSize;
        grid = new Tile[w][h];
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                grid[x][y] = new Tile();
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
        Vector2f v = obj.getPosition();
        Point p = v.div(tileSize).toPoint();
        grid[p.x][p.y].addObject(obj);
    }
}
