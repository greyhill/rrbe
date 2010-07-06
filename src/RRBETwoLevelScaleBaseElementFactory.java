import javax.swing.*;
import java.awt.*;

public class RRBETwoLevelScaleBaseElementFactory extends RRBEElementFactory {
    
    private ImageIcon[] combined;
    
    public RRBETwoLevelScaleBaseElementFactory(String typeId
					       , RRBEMainWindow w
					       , String name
					       , String baseImageName
					       , String topImageName
					       , int level
					       , boolean wall) {	
        this.typeId = typeId;
	window = w;
	this.images = new ImageIcon[8];
	this.images[0] = new ImageIcon(RRBEGlobal.curthemepath 
				       + baseImageName);
	for (int i = 0; i < 3; i++)
	  this.images[i+1] = rotateImage(this.images[i]);

	this.images[4] = new ImageIcon(RRBEGlobal.curthemepath + topImageName);
	for (int i = 4; i < 7; i++)
	  this.images[i+1] = rotateImage(this.images[i]);

	this.name = name;
	this.level = level;
	requireWall = wall;

	combined = new ImageIcon[4];
	for (int i = 0; i < 4; i++)
	    combined[i] = null;
    }

    private ImageIcon getImageIcon() {
	if (combined[window.currentRotation()] == null)	    
	    combined[window.currentRotation()] = RRBEGlobal
		.combineImages(images[window.currentRotation()],
			       images[window.currentRotation()+4]);

	return combined[window.currentRotation()];
    }

    private Image getImage() {
	return getImageIcon().getImage();
    }

    public ImageIcon getToolBarIcon() {
	return new ImageIcon(getImage()
			     .getScaledInstance(RRBEGlobal
						.toolButtonIconWidth
						,RRBEGlobal
						.toolButtonIconHeight
						,Image.SCALE_SMOOTH));
    }

    public ImageIcon getSmallIcon() {
	return new ImageIcon(getImage()
			     .getScaledInstance(RRBEGlobal
						.smallToolButtonIconWidth
						,RRBEGlobal
						.smallToolButtonIconHeight
						,Image.SCALE_SMOOTH));
    }

    public RRBEElement createElement() {
	RRBEElement e = new RRBETwoLevelScaleBaseElement(typeId, name
							 , level, images
							 , window
							 .currentRotation()
							 , requireWall
							 , scaleToWall);
	for (int i = 0; i < 5; i++)
	    e.setActivePhase(i,window.getActivePhase(i));
	return e;
    }
}










