package assign2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

public class PhotoComponent extends JComponent implements FilePanelInterface
{
	private int photoState   ;
	private Image photo  =null;
	private boolean isFlipped;
	private int width        ;
	private int height       ;
	private int preferred_w  ;
	private int preferred_h  ;
	private String imagePath ;
	
	
	private ArrayList<PhotoPanelInterface> ppi = new ArrayList<PhotoPanelInterface>();
	
	public void addListener(PhotoPanelInterface toAdd)
		{
			ppi.add(toAdd);
		}
	
	public PhotoComponent(String imagePath) throws IOException
		{
			this.width       = 340;
			this.height      = 280;
			this.preferred_w = 200;
			this.preferred_h = 100;
			this.photo       = ImageIO.read(new File(imagePath));
			this.setSize(width, height);
			this.setBackground(Color.RED);
			this.imagePath   = imagePath;
			this.isFlipped   = false;
		}
	
	public Image getPhoto()
		{
			return this.photo;
		}
	
	public void setFlipped(boolean value)
		{
			this.isFlipped = value;
		}
	
	public boolean getFlippedState()
		{
			return this.isFlipped;
		}
	
	//resize a BufferedImage
	public BufferedImage getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	public void setPhotoSize(int width, int height)
		{
			this.photo = getScaledImage(this.photo, width, height);
			for (PhotoPanelInterface hl : ppi)
				hl.repaintPanel();
		}
	
	public String getImagePath()
		{
			return this.imagePath;
		}
	
	public void setImagePath(String imagePath)
		{
			this.imagePath = imagePath;
			try {
				this.photo     = ImageIO.read(new File(imagePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			repaint();
			for (PhotoPanelInterface hl : ppi)
				hl.repaintPanel();
		}

}
