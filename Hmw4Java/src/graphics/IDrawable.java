package graphics;

import java.awt.Graphics;

/**
 * IDrawable interface -> draw in the panel, zoo
 * @author Amer Watted
 *
 */

public interface IDrawable
{
	 public final static String PICTURE_PATH = "pictures\\";
	 public void loadImages(String nm);
	 public void drawObject(Graphics g);
	 public String getColor();	 
} 
