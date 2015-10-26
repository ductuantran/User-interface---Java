package assign2;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

//class for "View" button
public class ViewButton extends JFrame {
	
	private int iconWidth            ;
	private int iconHeight           ;
	private int buttonWidth          ;
	private int buttonHeight         ;
	private JMenu view               ;
	private String mode              ;
	private ArrayList<ViewStatusInterface> listeners = new ArrayList<ViewStatusInterface>();
	
	public void addListener(ViewStatusInterface toAdd) {
        listeners.add(toAdd);
    }
	
	public ViewButton() {
		this.iconWidth    = 30;
		this.iconHeight   = 25;
		this.buttonWidth  = 160;
		this.buttonHeight = 25;
		this.view         = new JMenu("View");
		this.mode         = "Default photo viewer";
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
	
	public void createMenuBar() {
        //create icons (and resize them accordingly)
        BufferedImage exitImage = null, openImage = null, delImage = null;
        try{
        	exitImage = ImageIO.read(new File("images/exit.png"));
        	exitImage = getScaledImage(exitImage, this.iconWidth, this.iconHeight);
        	openImage = ImageIO.read(new File("images/open.png"));
        	openImage = getScaledImage(openImage, this.iconWidth, this.iconHeight);
        	delImage  = ImageIO.read(new File("images/del.png"));
        	delImage = getScaledImage(delImage, this.iconWidth, this.iconHeight);
        }
        catch (IOException ex) {
        }
        ImageIcon exitIcon = new ImageIcon(exitImage);
        ImageIcon openIcon = new ImageIcon(openImage);
        ImageIcon delIcon  = new ImageIcon(delImage);
        
        //button event
        view.setMnemonic(KeyEvent.VK_F);
        
        //Default photo viewer
        JRadioButton defaultViewer = new JRadioButton("Default photo viewer");
        defaultViewer.setMnemonic(KeyEvent.VK_E);
        defaultViewer.setToolTipText("Exit application"); //help message on hovering the cursor over the button
        defaultViewer.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
            	JOptionPane.showMessageDialog(null, "You've chosen default mode", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
            	for (ViewStatusInterface hl : listeners)
            		hl.writeMode("Default mode");
            	}
        });
        
        //browser — will eventually show a grid of thumbnails
        JRadioButton browserViewer = new JRadioButton("Grid of thumbnails");
        browserViewer.setMnemonic(KeyEvent.VK_E);
        browserViewer.setToolTipText("Exit application"); //help message on hovering the cursor over the button
        browserViewer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	JOptionPane.showMessageDialog(null, "You've chosen grid of thumbnails mode", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
            	for (ViewStatusInterface hl : listeners)
            		hl.writeMode("Thumbnails mode");
            }
        });
        
        //Split mode — will eventually show a single photo with a “film strip” of its neighbors
        JRadioButton splitViewer = new JRadioButton("Split mode");
        splitViewer.setMnemonic(KeyEvent.VK_E);
        splitViewer.setToolTipText("Exit application"); //help message on hovering the cursor over the button
        splitViewer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	JOptionPane.showMessageDialog(null, "You've chosen split mode", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
            	for (ViewStatusInterface hl : listeners)
            		hl.writeMode("Split mode");
            }
        });
        
        //Add buttons to a button group, so that only one can be selected at a time
        ButtonGroup group = new ButtonGroup();
        group.add(defaultViewer);
        group.add(browserViewer);
        group.add(splitViewer);
        
        //Add options to "file" button
        view.add(defaultViewer);
        view.add(browserViewer);
        view.add(splitViewer);
    }
	
	//Add "file" button to menu bar
	public void addToMenu(JMenuBar menubar){
	    menubar.add(this.view);
	    setJMenuBar(menubar);
	}
}
