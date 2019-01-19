package animals;

import diet.Carnivore;
import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;

/**
 *Class Lion extends Animal class
 * @author Amer Watted
 *
 */

public class Lion extends Animal
{
	/**
	 * default constructor
	 */
	public Lion(){
		
		
	}
	/**
	 * setter function
	 * @param s
	 * @param x
	 * @param y
	 * @param h
	 * @param v
	 * @param c
	 * @param p
	 */
	 public void setter(int s,int x, int y, int h, int v, String c, ZooPanel p)
	 {
		 super.init("Lion",(int)(s * 0.745), (int)(s * 0.8), h, v, c, p);
		 setLocation(new Point(x, y));
		 setDiet(new Carnivore());
		 loadImages("lio");
		 cor_x4 = 0;
		 cor_y1 = (int) (-30 - size/3);
		 cor_y3 = (int) (size * 0.25);
		 cor_x5 = cor_x6 = -size/2;
		 cor_y5 = cor_y6 = -size/3;
		 cor_h = (int)(size * 0.73);
		 super.init("Lion",(int)(s * 0.745), (int)(s * 0.8), h, v, c, p);

	 }
	
} 