package loyal.level;

import java.util.ArrayList;
import java.util.List;

import loyal.Graphics.Screen;
import loyal.entities.Entity;
import loyal.entities.MapPlayer;
import loyal.level.tiles.Tile;

public class Level
{
	public byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height)
	{
		tiles = new byte[width*height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}

	private void generateLevel()
	{
		for(int y=0; y<height; y++)
		{
			for(int x=0; x<width; x++)
			{
				if(x*y % 10 < 7)
				{
					tiles[x+y*width] = Tile.GRASS.getId();
				}
				else
				{
					tiles[x+y*width] = Tile.STONE.getId();
				}
			}
		}
	}
	
	
	public void tick()
	{
		for(Entity e : entities)
		{
			e.tick();
		}
	}
	
	
	public void renderTiles(Screen screen, int xOffset, int yOffset)
	{
		if(xOffset<0)
		{
			xOffset = 0;
		}
		if(xOffset>((width<<3)-screen.width))
		{
			xOffset = ((width<<3)-screen.width);
		}
		if(yOffset<0)
		{
			yOffset = 0;
		}
		if(yOffset>((height<<3)-screen.height))
		{
			yOffset = ((height<<3)-screen.height);
		}
		
		screen.setOffset(xOffset, yOffset);
		
		for(int y=0; y<height; y++)
		{
			for(int x=0; x<width; x++)
			{
				getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
	}
	
	
	public void renderEntities(Screen screen)
	{
		for(Entity e : entities)
		{
			e.render(screen);
		}
	}
	

	public Tile getTile(int x, int y)
	{
		if(0 > x || x >= width || 0 > y || y >= height )
		{
			return Tile.VOID;
		}
		return Tile.tiles[tiles[x+y*width]];
	}

	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}
}
