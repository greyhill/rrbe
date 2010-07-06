import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class RRBETimedElementFactory extends RRBEElementFactory {
    
    public RRBETimedElementFactory(String typeId
				   , RRBEMainWindow w
				   , String name
				   , String imageName
				   , String timeImageNameBase
				   , String timeImageNameSufix
				   , int level
				   , boolean requireWall
				   , boolean scaleToWall) {
	this.typeId = typeId;
	window = w;
	this.images = new ImageIcon[24];
	this.images[0] = new ImageIcon(RRBEGlobal.curthemepath + imageName);
	for (int i = 0; i < 3; i++)
	  this.images[i+1] = rotateImage(this.images[i]);
	
	for (int t = 1; t <= 5; t++) {
	    this.images[t*4] = new ImageIcon(RRBEGlobal.curthemepath 
					       + timeImageNameBase
					       + Integer.toString(t)
					       + timeImageNameSufix);
	    for (int i = 0; i < 3; i++)
		this.images[t*4+i+1] = rotateImage(this.images[t*4+i]);
	}
	
	this.name = name;
	this.level = level;
	this.requireWall = requireWall;
	this.scaleToWall = scaleToWall;
    }

    public RRBEElement createElement() {
	RRBEElement e = new RRBETimedElement(typeId, name, level, images
					     , window.currentRotation()
					     , requireWall
					     , scaleToWall);
	for (int i = 0; i < 5; i++)
	    e.setActivePhase(i,window.getActivePhase(i));
	return e;
    }
}










