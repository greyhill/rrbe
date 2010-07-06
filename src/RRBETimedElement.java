import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class RRBETimedElement extends RRBEElement {
    
    public RRBETimedElement(String typeId, String n,int l
			    , ImageIcon[] i, int r
			    , boolean requireWall
			    , boolean scaleToWall) {
	super(typeId, n,l,i,r,requireWall, scaleToWall);
    }

    public RRBETimedElement(String typeId, String n,int l, ImageIcon[] i, int r) {
	super(typeId, n,l,i,r,false, true);
    }

    
    public void drawTo(Graphics g, int x, int y
		       , int w, int h
		       , int ox, int oy, int ow, int oh
		       , ImageObserver i) {
	super.drawTo(g,x,y,w,h,ox,oy,ow,oh,i);

	if (scaleToWall)
	    for (int j = 0; j < 5; j++) {
		if (phases[j]) {
		    ImageIcon tI = image[4+j*4+rotation];
		    g.drawImage(tI.getImage(),
				x,y,w,h,0,0
				,tI.getIconWidth()
				,tI.getIconHeight()
				, i);
		}		
	    }
	else
	    for (int j = 0; j < 5; j++) {
		if (phases[j]) {
		    ImageIcon tI = image[4+j*4+rotation];
		    g.drawImage(tI.getImage(),
				ox,oy,ow,oh,0,0
				,tI.getIconWidth()
				,tI.getIconHeight()
				, i);
		}		
	    }
    }
}
