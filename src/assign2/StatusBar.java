package assign2;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

class StatusBar extends JPanel implements ViewStatusInterface
		{
			private JLabel statusLabel;
			
			public StatusBar()
				{
					setLayout(new BorderLayout(0, 25)); 
					statusLabel = new JLabel("Ready"); 
					statusLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
					statusLabel.setForeground(Color.black);
					add(BorderLayout.CENTER, statusLabel); 
					JLabel dummyLabel = new JLabel(" "); 
					dummyLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
					add(BorderLayout.EAST, dummyLabel); 
				} 
			
			public void setStatus(String status) 
				{
					if (status.equals("")) 
						statusLabel.setText("Ready"); 
					else 
						statusLabel.setText(status);
				} 
			public String getStatus()
				{
					return statusLabel.getText(); 
				}
			
			public void addToMenu(JMenuBar menubar){
				//Add "file" button to menu bar
			    menubar.add(this);
			}
			
			@Override
			public void writeMode(String mode) {
				statusLabel.setText(mode);
			}
		}