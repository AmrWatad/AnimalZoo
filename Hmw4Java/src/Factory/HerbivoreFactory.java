package Factory;

import animals.Animal;
import animals.Elephant;
import animals.Giraffe;
import animals.Turtle;

/**
 * 
 * @author Abdalla Wattawda
 *
 */
public class HerbivoreFactory implements AbstractZooFactory {

	@Override
	public Animal produceAnimal(String type) {
		
		if("Elephant".equals(type))
			return new Elephant();
		else if("Turtle".equals(type))
			return new Turtle();
		else if("Giraffe".equals(type))
			return new Giraffe();
		
			return null;
	}

}
