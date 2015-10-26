package assign2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ToolBar extends JPanel{
	
	private int nbRows = 5;
	private int nbCols = 1;
	
	public String getCategory(int index)
		{
			switch(index)
				{
					case 1 : return "Family";
					case 2 : return "Vacation";
					case 3 : return "School";
					case 4 : return "Friends";
					case 5 : return "Selfies";
				}
			return null;
		}
	
	public ToolBar() {
	      ActionListener listener = new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	         }
	      };

	      setLayout(new GridLayout(nbRows, nbCols));
	      ButtonGroup btnGroup = new ButtonGroup();
	      for (int i = 0; i < nbRows * nbCols; i++) {
	         String text = getCategory(i+1);
	         JToggleButton btn = new JToggleButton(text);
	         btn.addActionListener(listener);
	         btnGroup.add(btn);
	         add(btn);
	      }
	   }
}
