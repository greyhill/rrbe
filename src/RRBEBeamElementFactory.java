import javax.swing.*;

/**
 * A factory for creating beam elements
 */
public class RRBEBeamElementFactory extends RRBEElementFactory {

    private ImageIcon[] beamImages;
    private ImageIcon[] beamEndImages;
    private int range;

    /**
     * Construct a beam element factory
     */
    public RRBEBeamElementFactory(String typeId
				  , RRBEMainWindow w
				  , String name
				  , String imageName
				  , String beamImageName
				  , String beamEndImageName
				  , int range
				  , boolean requireWall
				  , boolean scaleToWall) {
	super(typeId, w,name,imageName,0,requireWall,scaleToWall);
	beamImages = new ImageIcon[4];
	beamEndImages = new ImageIcon[4];
	beamImages[0] = new ImageIcon(RRBEGlobal.curthemepath + beamImageName);
	for (int i = 0; i < 3; i++)
	  this.beamImages[i+1] = rotateImage(this.beamImages[i]);

	beamEndImages[0] = new ImageIcon(RRBEGlobal.curthemepath + beamEndImageName);
	for (int i = 0; i < 3; i++)
	  this.beamEndImages[i+1] = rotateImage(this.beamEndImages[i]);

	this.range = range;
    }

    /**
     * Activate this factory on square s.
     */
    public void activate(RRBESquare s) {
	RRBEBeamElement e = createBeamElement();
	s.addBeamElement(e);	
    }

    /**
     * Create a beam element
     */
    public RRBEBeamElement createBeamElement() {
	RRBEBeamElement e = new RRBEBeamElement(typeId, name, level, images
						, beamImages
						, beamEndImages
						, range
						, window.currentRotation()
						, requireWall
						, scaleToWall);
	for (int i = 0; i < 5; i++)
	    e.setActivePhase(i,false);
	return e;
    }
}
