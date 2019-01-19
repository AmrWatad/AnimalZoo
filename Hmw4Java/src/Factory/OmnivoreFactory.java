package Factory;

import animals.Animal;
import animals.Bear;

/**
 * 
 * @author Abdalla Wattawda
 *
 */
public class OmnivoreFactory implements AbstractZooFactory {

	@Override
	public Animal produceAnimal(String type) {
		
		if("Bear".equals(type))
			return new Bear();
	    return null;
	}

}
