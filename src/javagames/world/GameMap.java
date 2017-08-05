package javagames.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javagames.g2d.Sprite;
import javagames.game.GameObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class GameMap extends GameObject 
{
	private final int SIZE = 100;
	private float tileSize = 10.f;
	private Vector2f offset;
	protected Sprite[] tiles;
	protected int[][] grid;
	
	public GameMap(Sprite[] tiles, float tileSize) 
	{
		super();
		this.tileSize = tileSize;
		offset = new Vector2f((1-SIZE) * tileSize * 0.5f);
		grid = new int[SIZE][SIZE];
		this.tiles = tiles;
		Random r = new Random();
		for(int x = 0; x < SIZE; x++)
		{
			for(int y = 0; y < SIZE; y++)
			{
				grid[x][y] = r.nextInt(tiles.length);
			}
		}
	}
	
	public void setGrid(int[][] grid)
	{
		this.grid = grid;
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		for(int x=0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[x].length; y++)
			{
				tiles[grid[x][y]].render((Graphics2D)g, view, offset.add(new Vector2f(x * tileSize, y*tileSize)), 0);
			}
		}
	}

}
