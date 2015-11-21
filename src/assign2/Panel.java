package assign2;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

class Panel extends JPanel implements PhotoPanelInterface, FilePanelInterface2{
	
	private int panelWidth  = 3000;
	private int panelHeight = 1000;
	private PhotoComponent photoCom;	
	private boolean isBackground;
	private boolean moveText;
	private Point start, drawPos;
	private char keyPressed='a';
	private keyListener[] keyList = new keyListener[100];	
	private String label="";
	private int keyListIdx = 0;
	private int drawIdx    = 0;
	private int drawIndexTemp=0;
	private int dragX;
	private int dragY;
	
	public DrawComponent[] dc = new DrawComponent[10000];
	public int dcIndex=0;
	public Image photoBack= null, img = null, imgback = null, backPhoto = null;
	public JButton btn = new JButton();
	public Color colorDraw;
	public DrawTools dt= new DrawTools();
	public boolean needToDraw = true;
	private keyListener keyListItem = null;
	
	//resize a BufferedImage
	public BufferedImage getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	//constructor
	public Panel(PhotoComponent photocom) {
        setPreferredSize(new Dimension(this.panelWidth, this.panelHeight));
        this.isBackground = true;
        this.photoCom = photocom;
        ImageIcon ic = null;
		if (photoCom.getPhoto()!=null)
        	ic = new ImageIcon(photoCom.getPhoto());
		this.btn = new JButton(ic);
		//this.btn.setSize(photoCom.getPhoto().getWidth(null), photoCom.getPhoto().getHeight(null));
		this.btn.setBorder(BorderFactory.createEmptyBorder());
		this.btn.setContentAreaFilled(false);
		this.btn.setFocusPainted(false);
		this.btn.setContentAreaFilled(false);
		this.colorDraw = Color.black;
		
		//this.keyList.add(this);
		
		
		//background
		Image imgback = null;
		try {
			this.imgback = ImageIO.read(new File("images/background.jpg"));
			this.imgback = getScaledImage(this.imgback, panelWidth, panelHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//back of photo (when it is flipped)
		try {
			this.backPhoto = ImageIO.read(new File("images/white.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//set button to the left corner of panel
		setLayout(new FlowLayout(FlowLayout.LEFT));
        add(btn,BorderLayout.CENTER);
        add(btn,BorderLayout.NORTH);
		//add(btn, BorderLayout.WEST);
		
        
		btn.addMouseListener(new MouseAdapter(){
	        @Override public void mouseClicked(MouseEvent me){
	        	if (me.getClickCount() == 2 && !me.isConsumed() && isBackground == false)
		        	photoCom.setFlipped(!photoCom.getFlippedState());
	        	else if ((me.getClickCount() == 1 || moveText==true)&& photoCom.getFlippedState()==true)
		        	{
	        			DrawComponent drCom[] = null;
	        			if (keyListItem!=null)
	        			{
		        				drCom = keyListItem.getDrawComponentArr();
		        				for (int k=0; k<keyListItem.getIdx(); k++)
		        				{
		        					dc[dcIndex] = drCom[k];
		        					dcIndex++;
		        				}
		        				
		        				//move text
		        				if (dc!=null)
		        				{
				        			for (int cntp=0; cntp<dcIndex; cntp++)
				        			{
				        				if (dc[cntp].isString==true)
				        				{
				        					if ((me.getX()>=dc[cntp].xmin)&&(me.getX()<=dc[cntp].xmax)&&(me.getY()>=dc[cntp].ymin)&&(me.getY()<=dc[cntp].ymax)&&(me.getButton()==MouseEvent.BUTTON1))
				        						{
				        							moveText      = true;
				        							drawIndexTemp = cntp;
				        							dragX         = me.getX();
				        							dragY         = me.getY();
				        						}
				        					if (me.getButton()==MouseEvent.BUTTON3)
					        					{
					        						dc[cntp].color = dt.color;
					        						dc[cntp].font  = dt.fontForText;
					        						dc[cntp].size  = dt.textSize;
					        						keyListItem.drawString();
					        					}
				        				}
				        			}
		        				}
	        			}
	        			
	        			
	        			keyListItem = new keyListener((BufferedImage)backPhoto, dt, me); 
	        			keyListItem.addDrawComponentArr(dc, dcIndex);
	        			
	        			keyListItem.setOnState(true);
		        		addNotify();
		        		add(keyListItem);
		        		
		        		File outputfile = new File("D:\\Master_Paris_Sud_11\\AdvancedPIS\\Assignment_2\\image.jpg");
		        		try {
		        	        ImageIO.write((BufferedImage)backPhoto, "png", outputfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
	        }
	        
	        @Override public void mousePressed(MouseEvent e) {
	        	start = e.getPoint();
	         }
	        
	        @Override public void mouseDragged(MouseEvent e)
			    {
			    }
	        
	        @Override public void mouseReleased(MouseEvent e) {
	        	Point end = e.getPoint();
	        	moveText = false;
	         }
	    });
		
		btn.addMouseMotionListener(new MouseAdapter(){
			@Override public void mouseDragged(MouseEvent e)
			    {
					if (moveText==false)
						{
					        Point p = e.getPoint();
					        Graphics2D g2d = (Graphics2D) ((BufferedImage)backPhoto).getGraphics();					       
					        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                            RenderingHints.VALUE_ANTIALIAS_ON);
					        g2d.setColor(dt.color);
					        BasicStroke bs = new BasicStroke(dt.strokeVal);
					        g2d.setStroke(bs);
					        
					        //save draw component
					        DrawComponent dcCom = new DrawComponent(start.x, start.y, p.x, p.y, dt.color);
					        dc[dcIndex] = dcCom;
					        dcIndex++;
					        
					        //draw stroke
					        g2d.draw(new Line2D.Double(start, p));
					        g2d.dispose();
					        repaint();
					        start = p;
						}
					else
						{
							Point p = e.getPoint();
							dc[drawIndexTemp].x0 = dc[drawIndexTemp].x0 + e.getPoint().x - dragX;
							dc[drawIndexTemp].y0 = dc[drawIndexTemp].y0 + e.getPoint().y - dragY;
							if (dc[drawIndexTemp].x0<0)
								dc[drawIndexTemp].x0=0;
							if (dc[drawIndexTemp].x0>backPhoto.getWidth(null))
								dc[drawIndexTemp].x0=backPhoto.getWidth(null);
							if (dc[drawIndexTemp].y0<0)
								dc[drawIndexTemp].y0=0;
							if (dc[drawIndexTemp].y0>backPhoto.getHeight(null))
								dc[drawIndexTemp].y0=backPhoto.getHeight(null);
							dragX = e.getPoint().x;
							dragY = e.getPoint().y;
							
							keyListItem.drawString();
						}
			    }
	    });
    }

	public void resizePanel(int width, int height)
		{
			setPreferredSize(new Dimension(width, height));
			this.panelHeight = height;
			this.panelWidth  = width;
			repaint();
		}
	
	public void setComponent(PhotoComponent photocom)
		{
			this.photoCom = photocom;
		}
	
	public PhotoComponent getPhotocom()
		{
			return this.photoCom;
		}
	
	public int getPanelWidth()
		{
			return this.panelWidth;
		}
	
	public int getPanelHeight()
		{
			return this.panelHeight;
		}

    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Image img = null, backPhoto = null;
		try {
			img  = ImageIO.read(new File(this.photoCom.getImagePath()));
			if ((img.getWidth(null) > panelWidth) || ((img.getHeight(null) > panelHeight)))
				{
					if (img.getWidth(null) > img.getHeight(null))
						img = getScaledImage(img, panelWidth, img.getWidth(null)*panelWidth/img.getHeight(null));
					else
						img = getScaledImage(img, img.getWidth(null)*panelHeight/img.getHeight(null), panelHeight);
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//draw background
		g.drawImage(imgback, 5, 5, imgback.getWidth(null), imgback.getHeight(null), this);
		this.btn.setSize(img.getWidth(null), img.getHeight(null));
		
		
		//flipped state
		if ((this.isBackground==false)&&(photoCom.getFlippedState()==false)&&(needToDraw==true))
			g.drawImage(img, 5, 5, img.getWidth(null), img.getHeight(null), this);
		else if ((this.isBackground==false)&&(photoCom.getFlippedState()==true))
			{
				g.drawImage(this.backPhoto, 5, 5, img.getWidth(null), img.getHeight(null), this);
			}
		g.dispose();
    }
  
    //repaint
    public void repaintPanel()
	    {
    		this.isBackground = false;
    		this.img = this.photoCom.getPhoto();
    		if ((this.img.getWidth(null) > panelWidth) || ((this.img.getHeight(null) > panelHeight)))
				{
					if (this.img.getWidth(null) > img.getHeight(null))
						this.img = getScaledImage(this.img, panelWidth, this.img.getWidth(null)*panelWidth/this.img.getHeight(null));
					else
						this.img = getScaledImage(this.img, this.img.getWidth(null)*panelHeight/this.img.getHeight(null), panelHeight);
				}
    		this.btn = new JButton(new ImageIcon(this.photoCom.getPhoto()));
    		this.btn.setSize(this.photoCom.getPhoto().getWidth(null), this.photoCom.getPhoto().getHeight(null));
    		this.backPhoto = getScaledImage(this.backPhoto, this.img.getWidth(null), this.img.getHeight(null));
    		revalidate();
	    	repaint();
	    }
    
    public void drawBack()
	    {
	    	this.isBackground = true;
	    	revalidate();
	    	repaint();
	    	this.needToDraw = true;
	    }
    
    public BufferedImage getBackImage()
    {
    	return (BufferedImage)this.backPhoto;
    }
}