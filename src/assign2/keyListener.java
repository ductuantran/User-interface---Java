package assign2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class keyListener extends JPanel implements KeyListener {
	
    private String label=""                      ;
    private String[] labelArr = new String[10000];
    
    private DrawComponent dc                     ;
    private DrawComponent[] dcArr = new DrawComponent[10000];
    private int[] xArr = new int[10000]          ;
    private int[] yArr = new int[10000]          ;
    private Color[] colorArr = new Color[10000]  ;
    private int idx = 0                          ;
    
    private boolean isOn                         ;
    private BufferedImage img                    ;
    private BufferedImage fimg                   ;
    
    public Graphics2D g2d                        ;
    public DrawTools dt                          ;
    public MouseEvent me                         ;
    
    public keyListener(BufferedImage img, DrawTools dt, MouseEvent me) {
        this.setPreferredSize(new Dimension(500, 500));
        this.img       = img;
        this.fimg      = img;
        this.g2d       = (Graphics2D)(img.getGraphics());
        this.isOn      = false;
        this.dt        = dt;
        this.me        = me;
        addKeyListener(this);
    }
    
    public void addDrawComponentArr(DrawComponent[] dcArr, int nbElements)
    {
		this.idx=0;
    	for (int i=0; i<nbElements; i++)
	    	{
	    		this.dcArr[this.idx] = dcArr[i];
	    		this.idx++;
	    	}
    }
    
    public DrawComponent[] getDrawComponentArr()
    {
    	return this.dcArr;
    }
    
    public void setOnState(boolean val)
    {
    	this.isOn = val;
    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    
    public String getString()
    {
    	return this.label;
    }
    
    public int getIdx()
    {
    	return this.idx;
    }
    
    public boolean checkPrefix(String s1, String s2) // check if s1 is prefix of S2
    {
    	if (s1==null)
    		return true;
    	if (s2==null)
    		return false;
    	if (s1.length()>s2.length())
    		return false;
    	else
	    	{
	    		for (int i=0; i<s1.length(); i++)
		    		{
		    			if (s1.charAt(i)!=s2.charAt(i))
		    				return false;
		    		}
	    		return true;
	    	}
    }
    
    //divide the string into substrings to fit screen
    public List<String> divideString(String s, int xPos, int imgWidth, Graphics2D g2d)
    {
    	List<String> strVect = new ArrayList<String>();
    	String ss="";
    	int lastPos;
    	
    	if ((g2d.getFontMetrics().stringWidth(s) + xPos)<imgWidth)
    	{
    		strVect.add(s);
    		return strVect;
    	}
    	for (int i=0; i<s.length(); i++)
    	{
    		if (strVect.size()!=0)
    			xPos = 5;
    		
    		ss = ss + s.charAt(i); 
    		lastPos = g2d.getFontMetrics().stringWidth(ss) + xPos;
    		
    		if (lastPos>imgWidth)
    		{
    			int k;
    			for (k=ss.length()-1; k>=0; k--)
    			{
    				if (ss.charAt(k)==' ')
    					break;
    			}
    			if (k!=-1)
    			{
    				strVect.add(ss.substring(0, k)); 
    				i=i-(ss.length()-k)+1;
    			}
    			else
    			{
    				strVect.add(ss.substring(0, ss.length()));  
    				i--;
    			}		
    			ss="";   			
    		}
    		if ((lastPos<=imgWidth)&&(i==(s.length()-1)))
    		{
    			strVect.add(ss);
    			return strVect;
    		}
    	}
    	return strVect;
    }
    
    //draw components
    public void drawString()
	    {
	    	setFocusable(false);
	        this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        this.g2d.setColor(Color.white);
	        this.g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
	        this.g2d.setColor(this.dt.color);  
	        
	        if (this.dcArr!=null)
	        {
		        for (int i =0; i<this.idx; i++)
			        {					    
		        	    if (this.dcArr[i].isString==true)
			        	    {
				        		this.g2d.setColor(this.dcArr[i].color); 
				        		this.g2d.setFont(this.dcArr[i].font);	
				        		
				        		List<String> strL = divideString(this.dcArr[i].text, this.dcArr[i].x0, img.getWidth(), this.g2d);
				        		int maxX=0, minX=this.img.getWidth(), maxY=0, minY=this.img.getHeight();
				        		for (int cnt=0; cnt<strL.size(); cnt++)
				        		{
				        			if (cnt==0)
					        			{
					        				this.g2d.drawString(strL.get(cnt), this.dcArr[i].x0, this.dcArr[i].y0 + this.dcArr[i].size*cnt);
					        				this.dcArr[i].xmin = this.dcArr[i].x0;
					        			}
				        			else
					        			{
					        				this.g2d.drawString(strL.get(cnt), 5, this.dcArr[i].y0 + this.dcArr[i].size*cnt);
					        				this.dcArr[i].xmin = 5;
					        			}
				        			if ((g2d.getFontMetrics().stringWidth(strL.get(cnt)))>maxX)
				        				maxX = g2d.getFontMetrics().stringWidth(strL.get(cnt));
				        			if ((g2d.getFontMetrics().stringWidth(strL.get(cnt)))<minX)
				        				minX = g2d.getFontMetrics().stringWidth(strL.get(cnt));
				        		}
				        		//this.g2d.drawRect(this.dcArr[i].x0, this.dcArr[i].y0, maxX, 20*(strL.size()));
				        		this.dcArr[i].ymin = this.dcArr[i].y0;
				        		this.dcArr[i].xmax = this.dcArr[i].xmin + maxX;
				        		this.dcArr[i].ymax = this.dcArr[i].y0 + this.dcArr[i].size*(strL.size());
			        	    }
		        	    else
			        	    {
			        	    	this.g2d.setColor(this.dcArr[i].color);
						        BasicStroke bs = new BasicStroke(this.dt.strokeVal);
						        g2d.setStroke(bs);
						        Point start = new Point(this.dcArr[i].x0, this.dcArr[i].y0);
						        Point end   = new Point(this.dcArr[i].x1, this.dcArr[i].y1);
						        g2d.draw(new Line2D.Double(start, end));
			        	    }
			        }
			    repaint();
			    setFocusable(true);
			    requestFocusInWindow();
	        }
	    }
    
    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) {
    	if ((this.isOn == true)&&(e.getKeyChar()!='\n')&&(e.getKeyChar()!='\b'))
	    	{
		        this.label = this.label + e.getKeyChar();
		        int temp=0;
		        for (int i=0; i<this.idx; i++)
			        {
			        	if ((this.xArr[i]==this.me.getPoint().x)&&(this.yArr[i]==this.me.getPoint().y)&&((checkPrefix(this.label,this.labelArr[i])==true)||(checkPrefix(this.labelArr[i],this.label)==true)))
			        	{
			        		this.labelArr[i] = this.label;
			        		//add draw component
					    	this.dcArr[i] = new DrawComponent(this.me.getPoint().x, this.me.getPoint().y, this.label, this.dt.color, this.dt.fontForText.deriveFont((float)this.dt.textSize), this.dt.textSize);
			        		temp++;
			        	}
			        }
		        
		        if (temp==0)
			        {
				        this.xArr[this.idx]     = this.me.getPoint().x;
				    	this.yArr[this.idx]     = this.me.getPoint().y;
				    	this.labelArr[this.idx] = this.label;
				    	this.colorArr[this.idx] = this.dt.color;
				    	
				    	//add draw component
				    	this.dcArr[this.idx] = new DrawComponent(this.me.getPoint().x, this.me.getPoint().y, this.label, this.dt.color, this.dt.fontForText.deriveFont((float)this.dt.textSize), this.dt.textSize);
				    	this.idx++;
			        }
		        drawString();
	    	}
    	else if (e.getKeyChar()=='\n')
	    	{
	    		this.isOn = false;
	    	}
    	else if (e.getKeyChar()=='\b')
	    	{
    			if (this.label.length()>0)
	    			{
	    				this.label = this.label.substring(0, this.label.length()-1);
	    				int temp=0;
	    		        for (int i=0; i<this.idx; i++)
	    			        {
	    			        	if ((this.xArr[i]==this.me.getPoint().x)&&(this.yArr[i]==this.me.getPoint().y)&&((checkPrefix(this.label,this.labelArr[i])==true)||(checkPrefix(this.labelArr[i],this.label)==true)))
	    			        	{
	    			        		this.labelArr[i] = this.label;
	    			        		temp++;
	    			        		//add draw component	    			   
	    					    	this.dcArr[i] = new DrawComponent(this.me.getPoint().x, this.me.getPoint().y, this.label, this.dt.color, this.dt.fontForText.deriveFont((float)this.dt.textSize), this.dt.textSize);
	    			        	}
	    			        }
	    		        
	    		        if (temp==0)
	    			        {
	    				        this.xArr[this.idx]     = this.me.getPoint().x;
	    				    	this.yArr[this.idx]     = this.me.getPoint().y;
	    				    	this.labelArr[this.idx] = this.label;
	    				    	this.colorArr[this.idx] = this.dt.color;
	    				    	
	    				    	//add draw component
	    				    	this.dcArr[this.idx] = new DrawComponent(this.me.getPoint().x, this.me.getPoint().y, this.label, this.dt.color, this.dt.fontForText.deriveFont((float)this.dt.textSize), this.dt.textSize);
	    				    	this.idx++;
	    			        }
	    			}
    			drawString();
	    	}
    }
}
