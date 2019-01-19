package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import Factory.AbstractZooFactory;
import Memento.ZooMemento;
import ZooObserverPackage.ZooObserver;
import animals.Animal;
import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Lion;
import animals.Turtle;
import decoration.DecorateDialog;
import decoration.decorator;
import duplication.DuplicateDialog;
import food.EFoodType;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * Class ZooPanel : Define all the actions that in the bottom of the frame that on the panel . 
 * @author Amer Watted
 *
 */
public class ZooPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private static final int MAX_ANIMAL_NUMBER  = 10;
   private final String BACKGROUND_PATH = Animal.PICTURE_PATH + "savanna.jpg";
   private final String MEAT_PATH = Animal.PICTURE_PATH + "meat.gif";
   private final int RESOLUTION = 25; 
   private ZooFrame frame;
   private EFoodType Food;
   private JPanel p1;
   private JButton[] b_num1,b_num2;
   private String[] names1 = {"Add Animal", "Sleep", "Wake up", "Clear", "Food", "Info"};
   private String[] names2 = {"Decorate","Duplicate","Save state","Restor state", "Exit"};
   protected int foodIndex;
   protected ArrayList<Animal> animals;
   private Plant forFood = null;
   private JScrollPane scrollPane;
   private boolean isTableVisible;
   private int totalCount;
   private BufferedImage img, img_m;
   private boolean bgr;
   ZooObserver Controller;
   AbstractZooFactory factory;
   Executor threadPool;
   static private  ZooPanel instance=null;
   private ArrayList<ZooMemento> mementos;
   private Future<?> task;
   private ZooPanel(ZooFrame f)
   {
	   Controller = new ZooObserver(this);
	   Controller.start();
	   this.mementos=new ArrayList<ZooMemento>();
	   JPanel MainMenu= new JPanel(new GridLayout(2,1)); 	   //main panel for menu
	   frame = f;
	    Food = EFoodType.NOTFOOD;
	    totalCount = 0;
	    isTableVisible = false;
	    animals = new ArrayList<Animal>();
	    setBackground(new Color(255, 255, 255));
	    p1 = new JPanel();
	    JPanel p2=new JPanel();
	    
		p1.setLayout(new GridLayout(1, 7, 0, 0));
		p1.setBackground(new Color(0, 150, 255));
		
		p2.setLayout(new GridLayout(1, 7, 0, 0));
		p2.setBackground(new Color(0, 150, 255));		
		
		b_num1 = new JButton[names1.length];
		b_num2= new JButton[names2.length];
		for(int i = 0; i < names1.length; i++)
		{
		    b_num1[i]=new JButton(names1[i]);
		    b_num1[i].addActionListener(this);
		    b_num1[i].setBackground(Color.lightGray);
		    p1.add(b_num1[i]);				   
		}
		for(int i = 0; i < names2.length; i++)
		{
		    b_num2[i]=new JButton(names2[i]);
		    b_num2[i].addActionListener(this);
		    b_num2[i].setBackground(Color.lightGray);
		    p2.add(b_num2[i]);				   
		}		
		
		MainMenu.add(p1);
		MainMenu.add(p2);
		setLayout(new BorderLayout());
		add("South", MainMenu);

		img = img_m = null;
		bgr = false;
		try
		{
			img = ImageIO.read(new File(BACKGROUND_PATH));
		} 
		catch (IOException e)
		{
			System.out.println("Cannot load background");
		}
		try
		{
			img_m = ImageIO.read(new File(MEAT_PATH));
		} 
		catch (IOException e)
		{
			System.out.println("Cannot load meat");
		}
		threadPool = Executors.newFixedThreadPool(5);
		
   }		
   public static ZooPanel getInstance(ZooFrame f){
		// d.p singltone to creat one object 
   	if(instance ==null)
   		synchronized(ZooPanel.class)
   		{
   			if(instance==null)
   				instance=new ZooPanel(f);
   		}
   	return instance;
   }	
	
   public void paintComponent(Graphics g)
   {
	   	super.paintComponent(g);	
	   	
	   	if(bgr && (img != null))
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

	   	if(Food == EFoodType.MEAT)
	   		g.drawImage(img_m, getWidth()/2 - 20, getHeight()/2 - 20, 40, 40, this);
	    
	   	if((Food == EFoodType.VEGETABLE) && (forFood != null))
	   		forFood.drawObject(g);

	   	synchronized(this)
	   	{
		   	for(Animal an : animals)
		    	an.drawObject(g);
	   	}
   }
   
   public void setBackgr(int num)
   {
	   switch(num)
	   {
	   		case 0:
	   			setBackground(new Color(255,255,255));
	   			bgr = false; 
	   			break;
	   		case 1:
	   			setBackground(new Color(0,155,0));
	   			bgr = false; 
	   			break;
	   		default:
	   			bgr = true;   
	   }
	   repaint();
   }
   
   synchronized public EFoodType checkFood()
   {
	   return Food;
   }

   /**
    * eatFood function
    * @param an
    */
   synchronized public void eatFood(Animal an)
   {
	   if(Food != EFoodType.NOTFOOD)
	   {
		    if(Food == EFoodType.VEGETABLE)
		    	forFood = null;	  
		   	Food = EFoodType.NOTFOOD;
	   		an.eatInc();
	   		totalCount++;
	   		System.out.println("The " + an.getName() + " with " + an.getColor() + " color and size " + an.getSize() + " ate food.");
	   }
	   else
		   System.out.println("The " + an.getName() + " with " + an.getColor() + " color and size " + an.getSize() + " missed food.");
   }

   public void addDialog()
   {
	   String type;
	   if(animals.size()==MAX_ANIMAL_NUMBER)
		   JOptionPane.showMessageDialog(this, "You cannot add more than " + MAX_ANIMAL_NUMBER + " animals");
	   else
	   {
		  type= showFactoryDialog();
		   AddAnimalDialog dial = new AddAnimalDialog(this, "Add an animal to aquarium",type);
		   dial.setVisible(true);
	   }
   }
   public String showFactoryDialog(){
	   Object[] options = {"Herbivore", "Omnivore", "Carnivore"}; 
	   int n = JOptionPane.showOptionDialog(frame, "Please choose animal factory", "Animal factory", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
	   switch(n)
	   {
	   		case 0:
	   			factory=AbstractZooFactory.createAnimalFactory("Plant");
	   			return "Plant";
	   		case 1: // Cabbage
	   			factory=AbstractZooFactory.createAnimalFactory("Mix");
	   			return "Mix";
	   		default: // Lettuce
	   			factory=AbstractZooFactory.createAnimalFactory("Meat");
	   			return "Meat";
	   }
   }
  
public void addAnimal(String animal, int sz, int hor, int ver, String c)
   {
	   Animal an = factory.produceAnimal(animal);
	   an.setter(sz, 0, 0, hor, ver, c, this);
	   animals.add(an);
	   Future<?> task = ((ExecutorService)threadPool).submit(an);
	   an.setFuture(task);
	   an.addObserver(this.Controller);
   }
   public void addAnimal(Animal animal)
   {
	   if(animals.size()>5){animal.setRun(false);
	  
	   } 
	   animals.add(animal);
	   task = ((ExecutorService)threadPool).submit(animal);
	   animal.setFuture(task);
	   animal.addObserver(this.Controller);
   }
   public ArrayList getAnimals(){
	   return animals;
   }
	public void start()
	{
	    for(Animal an : animals)
	    	an.setResume();
   }

 	public void stop()
 	{
	    for(Animal an : animals)
	    	an.setSuspend();
   }
    synchronized public void clearMemento()
    {
 	   for(int i =0; i <animals.size();i++)
 	   {
 		   for(Animal an : animals)
 		   {
 				   an.interrupt();
 				   animals.remove(an);
 				   break;
 		   }
 	   }
 	   totalCount = 0;
 	   repaint();
    }
   synchronized public void clear()
   {
	    
	   for(int i =0; i <animals.size();i++)
	   {
		   for(Animal an : animals)
		   {
			   if(an.isRunning())
			   {
				   an.interrupt();
				   animals.remove(an);
				   break;
				}
			} 
	   }
	   totalCount = 0;
	   repaint();
   }


   synchronized public void preyEating(Animal predator, Animal prey)
   {
	   predator.eatInc();
	   totalCount -=(prey.getEatCount()-1);
   }

   
   synchronized public void addFood()
   {
	   if(Food == EFoodType.NOTFOOD){
		   Object[] options = {"Meat", "Cabbage", "Lettuce"}; 
		   int n = JOptionPane.showOptionDialog(frame, 
		   		"Please choose food", "Food for animals", 
		   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
		   		null, options, options[2]);
		   switch(n) {
		   case 0: // Meat
			   Food = EFoodType.MEAT;
			   break;
		   case 1: // Cabbage
			   Food = EFoodType.VEGETABLE;
			   forFood =Cabbage.getInstance(this);
			   break;
		   default: // Lettuce
			   Food = EFoodType.VEGETABLE;
			   forFood = Lettuce.getInstance(this);
			   break;
		   }
	   }
	   else {
		   Food = EFoodType.NOTFOOD;
		   forFood = null;
	   }
	   repaint();
  }
   public void info()
   {  	 
	   if(isTableVisible == false)
	   {
		  int i = 0;
		  int sz = animals.size();

		  String[] columnNames = {"Animal", "Color", "Weight", "Hor.speed","Ver.speed", "Eat counter"};
	      String [][] data = new String[sz+1][columnNames.length];
		  for(Animal an : animals)
	      {
	    	  data[i][0] = an.getName();
	    	  data[i][1] = an.getColor();
	    	  data[i][2] = new Integer((int)(an.getWeight())).toString();
		      data[i][3] = new Integer(an.getHorSpeed()).toString();
		      data[i][4] = new Integer(an.getVerSpeed()).toString();
	    	  data[i][5] = new Integer(an.getEatCount()).toString();
	    	  i++;
	      }
	      data[i][0] = "Total";
	      data[i][5] = new Integer(totalCount).toString();
	      
	      JTable table = new JTable(data, columnNames);
	      scrollPane = new JScrollPane(table);
	      scrollPane.setSize(450, table.getRowHeight() * (sz+1)+24);
	      add( scrollPane, BorderLayout.CENTER );
	      isTableVisible = true;
	   }
	   else
	   {
		   isTableVisible = false;
	   }
	   scrollPane.setVisible(isTableVisible);
       repaint();
   }

   public void destroy()
   { 
	  for(Animal an : animals)
		  an.interrupt();
      System.exit(0);
   }
   
   public void actionPerformed(ActionEvent e)
   {
	if(e.getSource() == b_num1[0]) //Add Animal
		addDialog();
	else if(e.getSource() == b_num1[1]) //Sleep
		stop();
	else if(e.getSource() == b_num1[2]) //Wake up
		start();
	else if(e.getSource() == b_num1[3]) //Clear
		clear();
	else if(e.getSource() == b_num1[4]) //Food
		addFood();
	else if(e.getSource() == b_num1[5]) //Info
		info();
	else if(e.getSource()==b_num2[0]){//Decorate
		checkDecorate();
	}
	else if(e.getSource()==b_num2[1]){ //Duplicate
		new DuplicateDialog (this).setVisible(true);
	}
	else if(e.getSource()==b_num2[2]){	//Save state
		if(this.mementos.size()<3){
			this.mementos.add(new ZooMemento(this.animals,this.Food,this.foodIndex));
		}
        
	}
	else if(e.getSource()==b_num2[3]){	//Restor state
		RestoreState();
	}	
	else if(e.getSource()==b_num2[4]){ //Exit
		destroy();
	}		
   }
   synchronized public  void RestoreState(){
	   if(this.mementos.size()>0)
	   {
		   Object[] options = new Object[]{"None"};
		   if(this.mementos.size()==1)
			   options = new Object[]{"Cancel","State1"};
		   else if(this.mementos.size() == 2)
			   options = new Object[]{"Cancel","State1","State2"};
		   else if(this.mementos.size() == 3)
			   options = new Object[]{"Cancel", "State1", "State2","State3"};
		 int n = JOptionPane.showOptionDialog(frame, "Please choose state for restore ",
	 "Saved states", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
	 null,options, options[0]);
	       if(n==-1||n==0)
	       return; 
		   ZooMemento array=this.mementos.remove(n-1);		   
		   clearMemento();
		   animals=new ArrayList<Animal>();
		   for(int i=0;i<array.getAnimalList().size();i++)
			   addAnimal(array.getAnimalList().get(i));
		   this.Food=array.getFood();
		   if(array.getIndexofFood()==2)
			  forFood = Cabbage.getInstance(this);
		   else if(array.getIndexofFood()==3)
			   forFood = Lettuce.getInstance(this); 
	   }
	   else JOptionPane.showMessageDialog(this, "You have not saved States"); 
   } 
   
   public void fillComboBox(JComboBox list, String text){
	   
	   if(text=="Natural"){
	   	for(int i=0;i<animals.size();i++)
	   	{
	   		if(animals.get(i).getColor()=="Natural")   			
	   			list.addItem((i+1)+".["+animals.get(i).getName()+":"+"running="+animals.get(i).isRunning()+",weight="+animals.get(i).getWeight()+", color="+animals.get(i).getColor()+"]");}
	     	
	    }
	    else if (text=="All"){
		   	 for(int i=0;i<animals.size();i++){
				 list.addItem((i+1)+".["+animals.get(i).getName()+":"+"running="+animals.get(i).isRunning()+",weight="+animals.get(i).getWeight()+", color="+animals.get(i).getColor()+"]");}	     	 }
   }
   
   public Animal getAnimal(int index){
	   if(index >=0)
		   return animals.get(index); 
	   else 
		   return null;
   }
   
   public void eatAn(){
		boolean preyEaten = false;
		synchronized(this) {
			
			for(Animal predator : animals) 
			{
				for(Animal prey : animals) 
				{
					if(predator != prey  && predator.getWeight()/prey.getWeight() >= 2 &&  predator.getDiet().canEat(prey)&&
					   (Math.abs(predator.getLocation().getX() - prey.getLocation().getX()) < prey.getSize()) &&
					   (Math.abs(predator.getLocation().getY() - prey.getLocation().getY()) < prey.getSize())) {
							preyEating(predator,prey);
							System.out.println("The "+predator+" cought up the "+prey+" ==> ");
							prey.interrupt();
							animals.remove(prey);
							repaint();
							preyEaten = true;
							break;
					}
				}
				if(preyEaten)
					break;
			}
		}
	} 
  
   public void checkDecorate(){
	   boolean chek=false;
	   for(Animal an : animals)
	   {
		   if(an.getColor()=="Natural")
			   chek=true;			   
	   }
	   if(chek==true){
		      decorator dDialog=new decorator(this);
		   dDialog.setVisible(true);

	   }
	   else
		   JOptionPane.showMessageDialog(this, "You have not animals for decoration");
   }
	
   public boolean isChange() {
		boolean flag = false;
		for(Animal an : animals) {
		    if(an.getChanges()){
		    	flag = true;
		    	an.setChanges(false);
			}
	    }
		return flag;
	}
		
} 