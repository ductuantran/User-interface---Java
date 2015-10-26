package assign2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

public class DrawTools extends JFrame {
	
	public Color color;
	private JButton buttonPressed=null;
	private JMenu colorMenu ;
	private int buttonWidth ;
	private int buttonHeight;
	private int iconWidth   ;
	private int iconHeight  ;
	public JPanel panel    ;
	public Stroke stroke;
	public float strokeVal;
	
	public DrawTools()
		{
			this.colorMenu = new JMenu("Draw tools");
			this.buttonWidth  = 160;
			this.buttonHeight = 25;
			this.iconWidth    = 30;
			this.iconHeight   = 25;
			this.panel        = getColorPanel(4,4);
			this.stroke       = new BasicStroke(1f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER);
		}
	
	//resize a BufferedImage
	private BufferedImage getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	//generate color palette
	public Color[] generateRandomColor(int nbColors) {
		int nbPoint = (int) Math.cbrt(nbColors);
		int idx=0;
	    Color[] colorPal = new Color[nbColors];
	    for (int ired=0; ired<256; ired=ired+(255/nbPoint))
	    	for (int igreen=0; igreen<256; igreen=igreen+(255/nbPoint))
	    		for (int iblue=0; iblue<256; iblue=iblue+(255/nbPoint))
		    		{
	    				if (idx<(nbColors-1))
	    					{
	    						colorPal[idx] = new Color(ired, igreen, iblue);
	    						idx++;
	    					}
	    				else if (idx==(nbColors-1))
		    				{
	    						colorPal[idx] = new Color(255, 255, 255);
		    					return colorPal;
		    				}
		    		}
	    return colorPal;
	}
	
	public void createOptions()
		{
			//icons for options
			BufferedImage colorImage = null, openImage = null, delImage = null, penImage = null;
			try{
					colorImage = ImageIO.read(new File("images/color.png"));
					colorImage = getScaledImage(colorImage, this.iconWidth, this.iconHeight);
					penImage   = ImageIO.read(new File("images/pen.png"));
					penImage   = getScaledImage(penImage, this.iconWidth, this.iconHeight);
				}
			catch (IOException ex) {
	        }
	        ImageIcon colorIcon = new ImageIcon(colorImage);
	        ImageIcon penIcon   = new ImageIcon(penImage);
			
			//Color selection option
	        JMenuItem selectMenu = new JMenuItem("Select a color");
	        selectMenu.setIcon(colorIcon);
	        selectMenu.setMnemonic(KeyEvent.VK_E);
	        selectMenu.addActionListener(new ActionListener(){
	        	@Override
	        	public void actionPerformed(ActionEvent event) {
	        		JOptionPane pane = new JOptionPane(panel);
	        		JDialog dialog = pane.createDialog(getParent(), "Select one color");
	        		dialog.setSize(300,300);
	        		dialog.show();
	        	}
	        });
	        
	        //Pen thickness option
	        JMenuItem thickMenu = new JMenuItem("Adjust pen thickness");
	        thickMenu.setIcon(penIcon);
	        thickMenu.setMnemonic(KeyEvent.VK_E);
	        thickMenu.addActionListener(new ActionListener(){
	        	@Override
	        	public void actionPerformed(ActionEvent event) {
	        		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
	        		JOptionPane pane = new JOptionPane(slider);
	        		slider.setMajorTickSpacing(1);
	                slider.setMinorTickSpacing(1);
	                slider.setPaintTicks(true);
	                slider.setPaintLabels(true);
	        		final JDialog dialog = pane.createDialog(getParent(), "Adjust pen thickness");
	        		dialog.setSize(300,300);
	        		dialog.show();
	        		System.out.println("Stroke = " + ((Integer)slider.getValue()).floatValue());
	        		strokeVal = ((Integer)slider.getValue()).floatValue();
	    	        slider.addChangeListener(new ChangeListener()
	    	        {
	    	            public void stateChanged(ChangeEvent e)
	    	            {
	    	                float value = ((Integer)slider.getValue()).floatValue();
	    	                stroke = new BasicStroke(value, BasicStroke.CAP_BUTT,
	    	                                                BasicStroke.JOIN_MITER);
	    	            }
	    	        });
	        	}
	        });
	        	        
	        //Color panel
	        colorMenu.add(selectMenu);
	        colorMenu.add(thickMenu);
		}
	
	//color panel for color selection
	public JPanel getColorPanel(int nbRows, int nbCols)
	    {
	        Color[] colors = generateRandomColor(nbRows*nbCols);
	        ActionListener l = new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
		            {
	            		Border emptyBorder = BorderFactory.createEmptyBorder();
	            	    if (buttonPressed!=null)
	            	    	buttonPressed.setBorder(emptyBorder);
		                JButton button = (JButton)e.getSource();
		                color = button.getBackground();
		                button.setBorder(BorderFactory.createBevelBorder(0, color.red, color.red));
		                buttonPressed = button;
		                System.out.println("Color selected = " + color.toString());
		            }
	        };
	        JPanel panel = new JPanel(new GridLayout(nbRows,nbCols));
	        for(int j = 0; j < colors.length; j++)
		        {
		            JButton button = new JButton();
		            button.setSize(5,5);
		            button.setFocusPainted(false);
		            button.setBackground(colors[j]);
		            button.addActionListener(l);
		            panel.add(button);
		        }
	        return panel;
	    }
	
	//Add "Color" button to menu bar
	public void addToMenu(JMenuBar menubar){
	    menubar.add(this.colorMenu);
	    setJMenuBar(menubar);
	}
	
	//draw function
	/*public void draw(Point start, Point end)
	    {
	        Graphics2D g2 = image.createGraphics();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(color);
	        g2.setStroke(stroke);
	        g2.draw(new Line2D.Double(start, end));
	        g2.dispose();
	        repaint();
	    }*/
}
