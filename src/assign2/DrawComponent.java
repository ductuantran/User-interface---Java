package assign2;

import java.awt.Color;
import java.awt.Font;

public class DrawComponent {
		
	public int x0;
	public int y0;
	public int x1;
	public int y1;
	public Color color;
	public Font font;
	public String text;
	public boolean isString;
	public int xmin;
	public int ymin;
	public int xmax;
	public int ymax;
	public int size;
	
	public DrawComponent(int x0, int y0, int x1, int y1, Color color)
	{
		this.x0       = x0;
		this.y0       = y0;
		this.x1       = x1;
		this.y1       = y1;
		this.color    = color;
		this.isString = false;
	}
	
	public DrawComponent(int x0, int y0, String text, Color color, Font font, int size)
	{
		this.x0       = x0;
		this.y0       = y0;
		this.text     = text;
		this.color    = color;
		this.isString = true;
		this.font     = font;
		this.size     = size;
	}
}
