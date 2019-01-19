package plants;

import graphics.ZooFrame;
import graphics.ZooPanel;


/**
 * 
 * @author Abdalla Wattawda
 *
 */
public class Cabbage extends Plant
{
	ZooPanel mypanel;
	static private  Cabbage instance = null; // instance to create one object
	/**
	 *  constructor
	 * @param mypanel
	 */

	private Cabbage(ZooPanel mypanel)
	{	
		super(mypanel);
		this.loadImages("cabbage.png");
	}
	public  void restore(){
		this.loadImages("cabbage.png");
	}
	/**
	 * function make sure to create one and only object 
	 * @param zoo
	 * @return instance
	 */
    public static Cabbage getInstance(ZooPanel zoo){
    	
    	if(instance ==null)
    		synchronized(ZooPanel.class)
    		{
    			if(instance==null)
    				instance=new Cabbage(zoo);
    		}
    	return instance;
    }	
} 