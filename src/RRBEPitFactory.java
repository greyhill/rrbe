import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * A element factory that makes a pit on selected square
 */
public class RRBEPitFactory extends RRBEElementFactory {
    public RRBEPitFactory(RRBEMainWindow w, 
			      String name) {
	typeId = "pit";
	window = w;
	this.images = new ImageIcon[4];
	this.images[0] = 
	    new ImageIcon(RRBEGlobal.curthemepath 
			  + RRBEGlobal.pitImageName);
	for (int i = 0; i < 3; i++)
	  this.images[i+1] = this.images[i];
	this.name = name;
	this.level = level;
    }

    /**
     * Remove floor element on square s
     */
    public void activate(RRBESquare s) {
	s.removeElement(0);
    }
}
