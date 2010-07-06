public class RRBEWallFactory extends RRBEElementFactory {
    public RRBEWallFactory(RRBEMainWindow w, 
			      String name) {
	super("Wall", w,name
	      ,RRBEGlobal.wallImageName
	      , 10
	      , false
	      , false);
    }

    public void activate(RRBESquare s) {
	int w = window.currentRotation();
	s.setWall(w, !s.getWall(w)); 
    }
}
