package Factory;

import animals.Animal;

/**
 * Interface that returns kind of the animal
 * @author Abdalla Wattawda 
 *
 */
public interface AbstractZooFactory {
	public Animal produceAnimal(String type);
	/**
	 * 
	 * @param foodType
	 * @return AbstractZooFactory
	 */
	public static AbstractZooFactory createAnimalFactory(String foodType){
		AbstractZooFactory factory;
		if(foodType.equals("Plant"))
			return new HerbivoreFactory();
		else if (foodType.equals("Mix"))
			return new OmnivoreFactory();
		else if (foodType.equals("Meat"))
			return new CarnivoreFactory();
		else
			return null;
}
}
