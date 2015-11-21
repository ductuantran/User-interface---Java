package assign2;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.io.File;
import java.util.ArrayList;

//class for "File" button
public class FileButton extends JFrame {
	
	private int iconWidth   ;
	private int iconHeight  ;
	private int buttonWidth ;
	private int buttonHeight;
	private JMenu file      ;
	private Panel pan       ;
	private JScrollPane sp  ;
	private ArrayList<FilePanelInterface> fpi = new ArrayList<FilePanelInterface>();
	private ArrayList<FilePanelInterface2> fpi2 = new ArrayList<FilePanelInterface2>();
	
	public void addListener(FilePanelInterface toAdd) {
        fpi.add(toAdd);
    }
	
	public void addListener(FilePanelInterface2 toAdd) {
        fpi2.add(toAdd);
    }
	
	public FileButton() throws IOException {
		this.iconWidth    = 30;
		this.iconHeight   = 25;
		this.buttonWidth  = 160;
		this.buttonHeight = 25;
		this.file         = new JMenu("File");
		this.pan          = new Panel(new PhotoComponent(System.getProperty("user.dir") + "\\images\\background.jpg"));
		this.sp           = new JScrollPane(this.pan.getPhotocom());
    }
	
	public Panel getPanel()
		{
			return this.pan;
		}
	
	public JScrollPane getScrollPane()
		{
			return this.sp;
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
        BufferedImage exitImage = null, openImage = null, delImage = null, saveImage = null;
        try{
        	exitImage = ImageIO.read(new File("images/exit.png"));
        	exitImage = getScaledImage(exitImage, this.iconWidth, this.iconHeight);
        	openImage = ImageIO.read(new File("images/open.png"));
        	openImage = getScaledImage(openImage, this.iconWidth, this.iconHeight);
        	delImage  = ImageIO.read(new File("images/del.png"));
        	delImage  = getScaledImage(delImage, this.iconWidth, this.iconHeight);
        }
        catch (IOException ex) {
        }
        ImageIcon exitIcon = new ImageIcon(exitImage);
        ImageIcon openIcon = new ImageIcon(openImage);
        ImageIcon delIcon  = new ImageIcon(delImage);    
        
        //button event
        file.setMnemonic(KeyEvent.VK_F);
        
        //Import option
        JMenuItem eMenuItemOpen = new JMenuItem("Import", openIcon);
        //eMenuItemOpen.setPreferredSize(new Dimension(this.buttonWidth,this.buttonHeight));
        eMenuItemOpen.setMnemonic(KeyEvent.VK_E);
        eMenuItemOpen.setToolTipText("File browser");
        eMenuItemOpen.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		
        		//get file path
        		JFileChooser chooser= new JFileChooser();
        		int choice = chooser.showOpenDialog(null);
        		if (choice != JFileChooser.APPROVE_OPTION) return;
        		File chosenFile = chooser.getSelectedFile();
        		PhotoComponent pcn = null;
        		
        		//open file
				try {
					pcn = new PhotoComponent(chosenFile.getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//trigger events
				for (FilePanelInterface hl : fpi)
            		hl.setImagePath(chosenFile.getPath());
        	}
        });
        
        //Delete option
        JMenuItem eMenuItemDel = new JMenuItem("Delete", delIcon);
        eMenuItemDel.setMnemonic(KeyEvent.VK_E);
        eMenuItemDel.setToolTipText("Delete photo"); //help message on hovering the cursor over the button
        eMenuItemDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//trigger events
            	System.out.println("Inside call");
				for (FilePanelInterface2 hl2 : fpi2)
            		hl2.drawBack();
            	
            }
        });
        
        
        //Exit option
        JMenuItem eMenuItem = new JMenuItem("Quit", exitIcon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application"); //help message on hovering the cursor over the button
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        //Add options to "file" button
        file.add(eMenuItemOpen);
        file.add(eMenuItemDel);
        file.add(eMenuItem);
    }
	
	public void addToMenu(JMenuBar menubar){
		//Add "file" button to menu bar
	    menubar.add(this.file);
	    setJMenuBar(menubar);
	}
}
