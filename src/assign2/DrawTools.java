package assign2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	public int textSize;   
	public String textFont;
	public int textStyle;
	public Font fontForText;

	
	public DrawTools()
		{
			this.colorMenu    = new JMenu("Graphics tools");
			this.buttonWidth  = 160;
			this.buttonHeight = 25;
			this.iconWidth    = 30;
			this.iconHeight   = 25;
			this.panel        = getColorPanel(4,4);
			this.stroke       = new BasicStroke(1f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER);
			this.color        = Color.BLACK;
			this.textSize     = 20;
			this.textFont     = "TimesRoman";
			this.textStyle    = Font.PLAIN;
			this.fontForText  = new Font(this.textFont, this.textStyle, this.textSize);
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
			BufferedImage colorImage = null, openImage = null, delImage = null, penImage = null, fontImage = null, sizeImage = null;
			try{
					colorImage = ImageIO.read(new File("images/color.png"));
					colorImage = getScaledImage(colorImage, this.iconWidth, this.iconHeight);
					penImage   = ImageIO.read(new File("images/pen.png"));
					penImage   = getScaledImage(penImage, this.iconWidth, this.iconHeight);
					fontImage  = ImageIO.read(new File("images/font.png"));
					fontImage  = getScaledImage(fontImage, this.iconWidth, this.iconHeight);
					sizeImage  = ImageIO.read(new File("images/size.png"));
					sizeImage  = getScaledImage(sizeImage, this.iconWidth, this.iconHeight);
				}
			catch (IOException ex) {
	        }
	        ImageIcon colorIcon = new ImageIcon(colorImage);
	        ImageIcon penIcon   = new ImageIcon(penImage);
	        ImageIcon fontIcon  = new ImageIcon(fontImage);
	        ImageIcon sizeIcon  = new ImageIcon(sizeImage);
			
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
	        		//System.out.println("Stroke = " + ((Integer)slider.getValue()).floatValue());
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
	        
	        //Font options
	        JMenuItem fontMenu = new JMenu("Text font");
	        fontMenu.setIcon(fontIcon);
	        fontMenu.setMnemonic(KeyEvent.VK_E);
	        fontMenu.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	        });
	        JMenuItem fontList = new JMenuItem ("FontList");
	        JMenuItem Serif         = new JMenuItem("Serif");
	        JMenuItem SansSerif     = new JMenuItem("Sans Serif");
	        JMenuItem Xfiles        = new JMenuItem("AlexBrush-Regular");
	        JMenuItem Pacifico      = new JMenuItem("Pacifico");
	        
	        Serif.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textFont = Font.SERIF;
					fontForText = new Font(textFont, textStyle, textSize);
				}
	        });
	        SansSerif.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textFont = Font.SANS_SERIF;
					fontForText = new Font(textFont, textStyle, textSize);
				}
	        });
	        Xfiles.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					 File f = new File("font/AlexBrush-Regular.ttf");
					    FileInputStream in = null;
						try {
							in = new FileInputStream(f);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    Font dynamicFont = null;
						try {
							dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
						} catch (FontFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    Font dynamicFont32Pt = dynamicFont.deriveFont(32f);
						fontForText = dynamicFont32Pt;
					  }	
	        });
	        Pacifico.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					 File f = new File("font/Pacifico.ttf");
					    FileInputStream in = null;
						try {
							in = new FileInputStream(f);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    Font dynamicFont = null;
						try {
							dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
						} catch (FontFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    Font dynamicFont32Pt = dynamicFont.deriveFont((float)textSize);
					    System.out.println("Size = " + textSize);
						fontForText = dynamicFont32Pt;
					  }	
	        });
	        
	        fontMenu.add(Serif);
	        fontMenu.add(SansSerif);
	        fontMenu.add(Xfiles);
	        fontMenu.add(Pacifico);
	        
	        //Size options
	        JMenuItem sizeMenu = new JMenuItem("Text size");
	        sizeMenu.setIcon(sizeIcon);
	        sizeMenu.setMnemonic(KeyEvent.VK_E);
	        sizeMenu.addActionListener(new ActionListener(){
	        	@Override
	        	public void actionPerformed(ActionEvent event) {
	        		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	        		JOptionPane pane = new JOptionPane(slider);
	        		slider.setMajorTickSpacing(10);
	                slider.setMinorTickSpacing(5);
	                slider.setPaintTicks(true);
	                slider.setPaintLabels(true);
	        		final JDialog dialog = pane.createDialog(getParent(), "Adjust font size");
	        		dialog.setSize(300,300);
	        		dialog.show();
	        		//System.out.println("Stroke = " + ((Integer)slider.getValue()).floatValue());
	        		textSize = (int) ((Integer)slider.getValue()).floatValue();
	        		System.out.println("Size = " + textSize);
	    	        slider.addChangeListener(new ChangeListener()
	    	        {
	    	            public void stateChanged(ChangeEvent e)
	    	            {
	    	                textSize = (int) ((Integer)slider.getValue()).floatValue();
	    	            }
	    	        });
	        	}
	        });
	     
	        //Color panel
	        colorMenu.add(selectMenu);
	        colorMenu.add(thickMenu);
	        colorMenu.add(fontMenu);
	        colorMenu.add(sizeMenu);
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
}
