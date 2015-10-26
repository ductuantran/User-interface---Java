package assign2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class keyListener extends JPanel implements KeyListener {
    private String label="";
    
    public keyListener() {
        this.setPreferredSize(new Dimension(500, 500));
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    
    public String getString()
    {
    	return this.label;
    }
    
    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) {
    	//System.out.println("Char pressed : " + e.getKeyChar());
        this.label = this.label + e.getKeyChar();
        System.out.println("String : " + this.label);
    }
}
