package plants;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import mobility.ILocatable;
import mobility.Point;
import food.EFoodType;
import food.IEdible;
import graphics.IDrawable;
import graphics.ZooPanel;

/**
 * Plant abstract class implements IEdible,ILocatable,IDrawable
 * @author Amer Watted
 */

public abstract class Plant implements IEdible, ILocatable, IDrawable
{
	private ZooPanel panel;
	private BufferedImage food = null;
	
	public void loadImages(String nm)
	{
		try
		{
			food = ImageIO.read(new File(PICTURE_PATH + nm));
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	}

	public void drawObject(Graphics g)
	{
		if (food != null)
			g.drawImage(food,this.panel.getWidth() / 2,this.panel.getHeight() / 2, 50, 50, panel);
	}

	public String getColor() {return null;}
	private double height;
	private Point location;
	private double weight;

	/**
	 * constructor
	 * @param mypanel
	 */
	public Plant(ZooPanel mypanel)
	{
		this.panel = mypanel;
		Random rand = new Random();
		int x = rand.nextInt(30);
		int y = rand.nextInt(12);
		this.location = new Point(x, y);
		this.height = rand.nextInt(30);
		this.weight = rand.nextInt(12);
	}

	/**
	 * @return EFoodType.VEGETABLE
	 */
	public EFoodType getFoodtype() {return EFoodType.VEGETABLE;}

	/**
	 * @return this.height
	 */
	public double getHeight() {return this.height;}

	/**
	 * @return this.location
	 */
	public Point getLocation() {return this.location;}

	/**
	 * @return this.weight
	 */
	public double getWeight() {return weight;}

	/**
	 * @param height
	 * @return isSuccess = result
	 */
	public boolean setHeight(double height)
	{
		boolean isSuccess = (height >= 0);
		if (isSuccess)
			this.height = height;
		else
			this.height = 0;
		return isSuccess;
	}
	
	/**
	 * @param newLocation
	 * @return isSuccess = result
	 */
	public boolean setLocation(Point newLocation)
	{
		this.location = newLocation;
		return true;
	}

	/**
	 * @param weight
	 * @return isSuccess = result
	 */
	public boolean setWeight(double weight)
	{
		boolean isSuccess = (weight >= 0);
		if (isSuccess)
			this.weight = weight;
		else
			this.weight = 0;
		return isSuccess;
	}

	/**
	 * string
	 */
	public String toString() {return ("[" + this.getClass().getSimpleName() + "]");}
} 