import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
public class RRBEElement {

    /**
     * The level on which this element is placed
     */
    public int level;
    protected int rotation;
    protected ImageIcon[] image;
    private String name;
    protected boolean[] phases;
    /**
     * True is this element requires a wall
     */
    public boolean requireWall;
    /**
     * True if this elements image is scaled
     * if a wall is present
     */
    public boolean scaleToWall;
    /**
     * A string with this element type id
     */
    public String typeId;

    /**
     * Construc a standard element.
     */
    public RRBEElement(String typeId, String n,int l
		       , ImageIcon[] i, int r
		       , boolean requireWall
		       , boolean scaleToWall) {
	init (typeId,n,l,i,r,requireWall, scaleToWall);
    }
    
    private void init(String typeId, String n,int l
		      , ImageIcon[] i, int r
		       , boolean requireWall
		       , boolean scaleToWall) {
	this.typeId = typeId;
	image = new ImageIcon[i.length];
	phases = new boolean[5];
	level = l;

	for (int j = 0; j < i.length; j++) {
	    image[j] = i[j];
	}
	rotation = r;
	rotatemod();
	name = n;
	this.requireWall = requireWall;
	this.scaleToWall = scaleToWall;
    }   

    /**
     * Return type id
     */
    public String getTypeId() {
	return typeId;
    }

    /**
     * Draw this square to g
     * Destination: (x,y) to (w,h) if scaled to wall
     * (ox,oy) to (ow, oh) if not scaled to wall
     */
    public void drawTo(Graphics g, int x, int y
		       , int w, int h
		       , int ox, int oy, int ow, int oh
		       , ImageObserver i) {
	if (scaleToWall)
	    g.drawImage(getImage(),
			x,y,w,h,0,0
			,getImageIcon().getIconWidth()
			,getImageIcon().getIconHeight()
			, i);
	else {
	    g.drawImage(getImage(),
			ox,oy,ow,oh,0,0
			,getImageIcon().getIconWidth()
			,getImageIcon().getIconHeight()
			, i);	    
	}
    }

    /**
     * Return rotation
     */
    public int getRotation() {
	return rotation;
    }

    /**
     * Set phase p to active if v true
     * false otherwise
     */
    public void setActivePhase(int p, boolean v) {
	phases[p] = v;
    }

    /**
     * Returns true if phase p is active
     */
    public boolean getActivePhase(int p) {
	return phases[p];
    }

    /**
     * Return the image
     */
    public Image getImage() {
	return image[rotation].getImage();
    }

    /**
     * Get an image icon of this element
     */
    public ImageIcon getImageIcon() {
	return image[rotation];
    }

    /**
     * Return a scaled version of the image as an image icon
     */
    public ImageIcon getSmallIcon() {
	return new ImageIcon(image[rotation].getImage()
			 .getScaledInstance(RRBEGlobal.smallToolButtonIconWidth
					    ,RRBEGlobal
					    .smallToolButtonIconHeight
					    ,Image.SCALE_SMOOTH));
    }

    /**
     * Return a smaller scaled version of the image as an image icon
     */
    public ImageIcon getTinyIcon() {
	return new ImageIcon(image[rotation].getImage()
			 .getScaledInstance(RRBEGlobal.tinyToolButtonIconWidth
					    ,RRBEGlobal
					    .tinyToolButtonIconHeight
					    ,Image.SCALE_SMOOTH));
    }

    /**
     * Rotate the element left
     */
    public void rotateLeft() {
	rotation--;
	rotatemod();
    }
    
    /**
     * Rotate the element right
     */
    public void rotateRight() {
	rotation++;
	rotatemod();
    }

    /**
     * Return the name of the element
     */
    public String getName() {
	return name;
    }
    
    private void rotatemod(){
	int i = rotation;
	if (i >= 0)
	    i = i % 4;
	else 
	    while (i < 0)
		i += 4;

	rotation = i;
    }
}






