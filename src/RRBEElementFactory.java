import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class RRBEElementFactory{

    protected RRBEMainWindow window;
    public ImageIcon[] images;
    public String name;
    public int level;
    public boolean requireWall;
    public boolean scaleToWall;
    public String typeId;

    public RRBEElementFactory() {
    }

    public RRBEElementFactory(String typeId
			      , RRBEMainWindow w
			      , String name
			      , String imageName
			      , int level
			      , boolean wall
			      , boolean scaleToWall) {
	init(typeId, w, name, imageName
	     , level, wall, scaleToWall);
    }

    private void init(String typeId,
		      RRBEMainWindow w
		      , String name
		      , String imageName
		      , int level
		      , boolean wall
		      , boolean scaleToWall) {
	this.typeId = typeId;
	window = w;
	this.images = new ImageIcon[4];
	this.images[0] = new ImageIcon(RRBEGlobal.curthemepath + imageName);
	for (int i = 0; i < 3; i++)
	  this.images[i+1] = rotateImage(this.images[i]);
	this.name = name;
	this.level = level;
	requireWall = wall;
	this.scaleToWall = scaleToWall;
    }

    public ImageIcon rotateImage(ImageIcon image) {
	return RRBEGlobal.rotateImage(image, window);	
    }

    public void activate(RRBESquare s) {
	RRBEElement e = createElement();
	s.addElement(e);	
    }

    public String toString() {
	return name + " (" + typeId + ")";
    }

    public ImageIcon getToolBarIcon() {
	return new ImageIcon(images[window.currentRotation()].getImage()
			 .getScaledInstance(RRBEGlobal.toolButtonIconWidth
					    ,RRBEGlobal.toolButtonIconHeight
					    ,Image.SCALE_SMOOTH));
    }

    public ImageIcon getSmallIcon() {
	return new ImageIcon(images[window.currentRotation()].getImage()
			 .getScaledInstance(RRBEGlobal.smallToolButtonIconWidth
					    ,RRBEGlobal
					    .smallToolButtonIconHeight
					    ,Image.SCALE_SMOOTH));
    }

    public RRBEElement createElement() {
	RRBEElement e = new RRBEElement(typeId, name, level, images
					, window.currentRotation()
					, requireWall
					, scaleToWall);
	for (int i = 0; i < 5; i++)
	    e.setActivePhase(i,false);
	return e;
    }
}











