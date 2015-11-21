package assign2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.sun.corba.se.spi.orbutil.fsm.Action;

public class MainFrame extends JFrame{
	
	private int windowWidth ;
	private int windowHeight;
	
	public MainFrame(){
		this.setTitle("TRAN Duc-Tuan - M2 Interaction - Assignment 2");
		this.windowWidth  = 640;
		this.windowHeight = 480;
		this.setSize(this.windowWidth, this.windowHeight);
	}
	
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

        	PhotoComponent pc = null;
        	Panel pn = null;
        	JScrollPane sp = null;
        	JButton btn = null;
        	keyListener kl = null;
        	
            @Override
            public void run() {
            	MainFrame frame = new MainFrame();
            	
                //create new menubar
                JMenuBar menuBar = new JMenuBar();
                
                //create and add "File" button to menubar
                FileButton fb = null;
				try {
					fb = new FileButton();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                fb.addToMenu(menuBar);
                fb.createMenuBar();
                
                //create and add "View" button to menubar
                ViewButton vb = new ViewButton();     
                vb.addToMenu(menuBar);
                vb.createMenuBar();
                
                //create and add "Save" button to menubar
                JButton sab = new JButton("Save Back Photo ...");
                sab.setOpaque(false);
                sab.setContentAreaFilled(false);
                sab.setBorderPainted(false);
                sab.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                    	JFileChooser fileChooser = new JFileChooser();
                    	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    	  File file = fileChooser.getSelectedFile();
                    	  File outputfile = new File(file.getAbsolutePath());
                    		try {
                    	        ImageIO.write((BufferedImage)pn.backPhoto, "png", outputfile);
            				} catch (IOException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
                    	}
                    }
                });
                
                //create and add "Save" button to menubar
                JButton sap = new JButton("Save Project ...");
                sap.setOpaque(false);
                sap.setContentAreaFilled(false);
                sap.setBorderPainted(false);
                sap.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                    	JFileChooser fileChooser = new JFileChooser();
						if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
						{
	                    	File file = fileChooser.getSelectedFile();           	
	                    	BufferedWriter output=null;
	                    	String outPath = file.getAbsolutePath() + ".dat";
	                    	File f=new File(outPath);
	                    	try {
								output = new BufferedWriter(new FileWriter(f));
								for (int i=0; i<pn.dcIndex; i++)
								{
									output.write(pn.dc[i].x0 + "," + pn.dc[i].y0 + "," + pn.dc[i].x1 + "," + pn.dc[i].color + "," + pn.dc[i].font + "," + pn.dc[i].text + "," + pn.dc[i].isString + "," + pn.dc[i].xmin + "," + pn.dc[i].ymin + "," + pn.dc[i].xmax + "," + pn.dc[i].ymax + "," + pn.dc[i].size);
									output.newLine();
								}
								output.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                    }
                    }
                });
                    
                
                //create the status bar panel and shove it down the bottom of the frame
                StatusBar sb = new StatusBar();
                vb.addListener(sb);
                sb.addToMenu(menuBar);
                frame.add(sb, BorderLayout.SOUTH);
                
                //create toogle for options
                ToolBar tb = new ToolBar();
                frame.add(tb, BorderLayout.WEST);
                
                //create a photocomponent
				try {
					pc = new PhotoComponent("images/background.jpg");
				} catch (IOException e) {
					e.printStackTrace();
				}
				fb.addListener(pc);
				pn = new Panel(pc);
				
				//create panel to show a photo
                pn.setVisible(true);
                pc.addListener(pn);
                
                //show to frame
                frame.add(pn, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                
                //scrollbar
                sp = new JScrollPane(pn);
                sp.getVerticalScrollBar().setUnitIncrement(1000);
                sp.getHorizontalScrollBar().setUnitIncrement(1000);
                frame.add(sp);
                       		
                //add draw tools
                DrawTools drTool = new DrawTools();
                drTool.addToMenu(menuBar);
                drTool.createOptions();
                pn.dt = drTool;
                
                menuBar.add(sab);  
                menuBar.add(sap);
                frame.setJMenuBar(menuBar);
                frame.setVisible(true);
                frame.addComponentListener(new ComponentAdapter() 
            	{  
            	  public void componentResized(ComponentEvent evt) {
            		  
            	        }
            	});
            }
        });
	}
}
