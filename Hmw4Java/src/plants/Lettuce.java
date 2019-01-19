package plants;

import graphics.ZooPanel;


/**
 * 
 * @author Abdalla Wattawda
 *
 */
public class Lettuce extends Plant
{
	ZooPanel mypanel;
	static private  Lettuce instance = null; 

	/**
	 * constructor
	 * @param mypanel
	 */
	private Lettuce(ZooPanel mypanel)
	{	
		super(mypanel);
		this.loadImages("lettuce.png");
	}
	/**
	 * function make sure to create one and only object 
	 * @param zoo
	 * @return instance
	 */
    public static Lettuce getInstance(ZooPanel zoo){
    	
    	if(instance ==null)
    		synchronized(ZooPanel.class)
    		{
    			if(instance==null)
    			instance=new Lettuce(zoo);
    		}
    	return instance;
    }	
} 