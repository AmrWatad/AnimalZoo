package Factory;

import animals.Animal;
import animals.Lion;

/**
 * 
 * @author Abdalla Wattawda
 *
 */
public class CarnivoreFactory implements AbstractZooFactory {
	
	@Override
	public Animal produceAnimal(String type) {
	
		if("Lion".equals(type))
			return new Lion();
		return null;
	}

}
