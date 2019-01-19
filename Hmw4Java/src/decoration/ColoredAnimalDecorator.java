package decoration;

import animals.Animal;
/**
 * Colored Animal decorate class
 * @author Abdalla Wattawda
 *
 */
public class ColoredAnimalDecorator implements ColoredAnimal{
	private ColoredAnimal coloredAnimal;
	Animal animal;
	public ColoredAnimalDecorator(Animal an) {
		
		this.animal=an;
	}
	

	/**
	 * function -> paint the animal 
	 */
	@Override
	public void PaintAnimal(String color) {
	
		String nm=null;
		animal.PaintAnimal(color);
		Animal an =((Animal)animal);
		an.setColor(color);
		if (an.getName().equals("Bear"))
			nm="bea";
		if (an.getName().equals("Elephant"))
			nm="elf";
		if (an.getName().equals("Giraffe"))
			nm="grf";
		if (an.getName().equals("Lion"))
			nm="lio";
		if (an.getName().equals("Turtle"))
			nm="trt";
		an.loadImages(nm);
		}
	}


