import javax.swing.*;
import java.awt.*;

/**
 * Element factory that removes the element
 * on the highest level.
 */
public class RRBERemoveFactory extends RRBEElementFactory {
    
    private ImageIcon image;

    public RRBERemoveFactory() {
	image = new ImageIcon(RRBEGlobal.graphicspath
			      +RRBEGlobal.removeImageName);
	typeId = "REMOVE";
    }   
    public void activate(RRBESquare s) {
	boolean removed = false;
	int eC = s.count();
	for (int i = RRBEGlobal.maxLevel; i >= 0 && !removed; i--) {
	    s.removeElement(i);
	    if (eC != s.count())
		removed = true;
	}
    }

    public ImageIcon getToolBarIcon() {
	return new ImageIcon(image.getImage()
			     .getScaledInstance(RRBEGlobal.toolButtonIconWidth
						,RRBEGlobal
						.toolButtonIconHeight
						,Image.SCALE_SMOOTH));
    }

    public ImageIcon getSmallIcon() {
	return new ImageIcon(image.getImage()
			     .getScaledInstance(RRBEGlobal
			       .smallToolButtonIconWidth
			       ,RRBEGlobal.smallToolButtonIconHeight
			       ,Image.SCALE_SMOOTH));
    }
}










