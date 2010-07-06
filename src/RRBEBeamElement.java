import javax.swing.*;

/**
 * Element of laser etc.
 */
public class RRBEBeamElement extends RRBEElement {
    
    private ImageIcon[] beamImages;
    private ImageIcon[] beamEndImages;
    private int range;

    /**
     * Construct a Beam element
     */
    public RRBEBeamElement(String typeId
			   , String n,int l, ImageIcon[] i
			   , ImageIcon[] beamImages
			   , ImageIcon[] beamEndImages
			   , int range
			   , int r
			   , boolean requireWall
			   , boolean scaleToWall) {
	super(typeId, n,l,i,r,requireWall,scaleToWall);
	this.beamImages = beamImages;
	this.beamEndImages = beamEndImages;
	this.range = range;
    }


    /**
     * Get beam image.
     */
    public ImageIcon getBeamImage() {
	return beamImages[rotation];
    }

    /**
     * The last image in a beam
     */
    public ImageIcon getBeamEndImage() {
	return beamEndImages[rotation];
    }

    /**
     * Return the range of this beam element
     */
    public int getRange() {
	return range;	
    }
}
