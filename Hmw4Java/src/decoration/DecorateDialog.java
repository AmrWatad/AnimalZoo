package decoration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import animals.Animal;
import graphics.ZooPanel;

/**
 * class to decorate
 * @author Abdalla Wattawda
 *
 */
public class DecorateDialog extends JDialog implements ActionListener
{
	
	JPanel leftPanel,rightPanel;
    protected JRadioButton[] rb;
    protected JButton okBtn;
    protected ZooPanel zooPanel;
	private JComboBox cb;
	protected ArrayList<Animal> animals;
	protected String[] colors = {"Red", "Blue"};
	protected int[] arr;
	public DecorateDialog(ZooPanel pan, String title)
	{
    	
		super(new JFrame(), title,true);
    	 this.zooPanel=pan;
    	setSize(800,300);
		rb = new JRadioButton[colors.length];

        JPanel panel = new JPanel(new GridLayout(1,2));
        JPanel panelOK=new JPanel();
        JPanel panelcompBoxOfAnimal=new JPanel();

        JPanel left=new JPanel(new GridLayout(3,1));
        JPanel right=new JPanel(new GridLayout(1,2));
        JPanel pTR=new JPanel();
        JPanel pTL=new JPanel();

        pTR.add(new JLabel("Choose decoration color :"));
        pTL.add(new JLabel("Select Animal to decorate :"));

	    cb=new JComboBox();  
        okBtn=new JButton("OK");
        
        okBtn.addActionListener(this);
        okBtn.setBackground(Color.lightGray);
        cb.addItem("No animal");
        left.add(pTL);
        right.add(pTR);
        panelcompBoxOfAnimal.add(cb);
        left.add(panelcompBoxOfAnimal);
        panelOK.add(okBtn);
        left.add(panelOK);


		for(int i = 0; i < colors.length; i++)
		{
		    rb[i] = new JRadioButton(colors[i], false);
		    rb[i].addActionListener(this);
		    right.add(rb[i]);
		}
        panel.add(left);
        panel.add(right);
        add(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource()==okBtn)
			{
				if(!rb[0].isSelected() && !rb[1].isSelected()){
					JOptionPane.showMessageDialog(this, "No changes has been maded!");
					dispose();
					return;
				}
			    
				if(cb.getSelectedItem()=="No animal")
				{
					setVisible(false);
					return;
				}
				
				int index=cb.getSelectedIndex()-1;
				Animal an=animals.get(arr[index]); 
				JOptionPane.showMessageDialog(this, "You have choosed index:"+index+"animal name:"+an.getName());

				
				String selectedcolor;
				ColoredAnimalDecorator dec = new ColoredAnimalDecorator(an);
				
				
				if(rb[0].isSelected())
					selectedcolor="Red";
				else if (rb[1].isSelected())
					selectedcolor="Blue";
				else
					selectedcolor="null";
				
				
				dec.PaintAnimal(selectedcolor);
				
				setVisible(false);
			}
	}




}
