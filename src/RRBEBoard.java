import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * A class handeling a board
 */
public class RRBEBoard extends JPanel{

    private int gridHeight, gridWidth;
    private RRBESquare[][] squares;
    private boolean beamconfig;
    private Vector[][] beamImages;
    private JScrollPane scroll;
    private JPanel panel;
    private float zoomRatio;
    private RRBEMainWindow window;

    /**
     * Constrct a board connected to window w, with dimenstions width*height
     * Zoom level set to Zoom.
     */
    public RRBEBoard (RRBEMainWindow w, int width, int height, float zoom) {
	window = w;
	gridHeight = height;
	gridWidth = width;
	panel = new JPanel();
	setLayout(new GridLayout(1,1));
	panel.setLayout(new GridLayout(gridHeight, gridWidth));

	squares = new RRBESquare[gridWidth][gridHeight];
	beamImages = new Vector[gridWidth][gridHeight];
	
	beamconfig = false;

	for (int y = 0; y < gridHeight; y++) {
	    for (int x = 0; x < gridWidth; x++) {
		squares[x][y] = new RRBESquare(w,x,y);
		panel.add(squares[x][y]);
		beamImages[x][y] = new Vector();
	    }
	}
	setZoom(zoom);

	scroll = new JScrollPane(panel);
	add(scroll);
    }

    /**
     * Paint this board on graphics g
     */
    public void paintOn(Graphics g) {
	paintOn(g,0,0,
		gridWidth,
		gridHeight,
		0,0);
    }

    /**
     * Return zoom factor
     */
    public float zoomFactor() {
	return zoomRatio;
    }
    
    /**
     * Zoom zoom by factor
     */
    public void zoom(float factor) {
	setZoom(zoomRatio * factor);
    }

    /**
     * Zoom the board
     */
    public void setZoom(float ratio) {
	zoomRatio = ratio;
	for (int y = 0; y < gridHeight; y++) 
	    for (int x = 0; x < gridWidth; x++) { 
		squares[x][y].setZoom(ratio);
	    }
	//invalidate();
	//repaint();
	//doLayout();
    }

    /**
     * Paint this board on graphics g.
     * Draws squares (x1,y1) to (not including) (x2,y2)
     * On place xoff,yoff.
     * All in square coordinates.
     */
    public void paintOn(Graphics g, int x1, int y1,
			int x2, int y2, int xoff, int yoff) {
	int w = this.getWidth();
	int h = this.getHeight();
	/*g.drawImage(RRBEGlobal.boardBackground.getImage()
		    , 0,0,w,h,0,0
		    ,RRBEGlobal.boardBackground.getIconWidth()
		    ,RRBEGlobal.boardBackground.getIconHeight()
		    , this);*/
	for (int y = y1; y < y2; y++) {
	    for (int x = x1; x < x2; x++) {
		squares[x][y].paintOn(g,
				      (xoff + x - x1) 
				      * RRBEGlobal.imageResolution
				      ,(yoff + y - y1)
				      * RRBEGlobal.imageResolution
				      ,RRBEGlobal.imageResolution
				      ,RRBEGlobal.imageResolution);
	    }
	}
    }

    /**
     * Return a square
     */
    public RRBESquare getSquare(int x, int y) {
	return squares[x][y];
    }

    /**
     * Set a square
     */
    public void setSquare(int x, int y, RRBESquare s) {
	squares[x][y] = s;
    }

    /**
     * Return the height of the board
     */
    public int height() {
	return gridHeight;
    }

    /**
     * Return the width of the board
     */
    public int width() {
	return gridWidth;
    }

    /**
     * Should be called then something that might effect the 
     * beam configuration of the board is changed.
     * Such as walls changed or beam elements changed.
     */
    public void invalidBeamState() {
	beamconfig = false;
    }
    
    private int dx(int rotation) {
	if (rotation == 0)
	    return 0;
	if (rotation == 1)
	    return 1;
	if (rotation == 2)
	    return 0;

	return -1;
    }

    private int dy(int rotation) {
	if (rotation == 0)
	    return -1;
	if (rotation == 1)
	    return 0;
	if (rotation == 2)
	    return 1;
	
	return 0;
    }

    private void calcBeamImages() {
	for (int sx = 0; sx < gridWidth; sx++)
	    for (int sy = 0; sy < gridHeight; sy++){
		beamImages[sx][sy].clear();
	    }

	for (int sx = 0; sx < gridWidth; sx++)
	    for (int sy = 0; sy < gridHeight; sy++){
		RRBEBeamElement[] be = squares[sx][sy].getBeamElements();
		
		for (int i = 0; i < be.length; i++) {
		    int r = be[i].getRotation();
		    int range = be[i].getRange();
		    if (range == 0)
			range = gridWidth * gridHeight;
		    int length = 0;
		    int x = sx;
		    int y = sy;
		    boolean cont = true;
		    while (x >= 0 && y >= 0
			   && x < gridWidth && y < gridHeight
			   && cont) {
			if (squares[x][y].getAnyWall(r))
			    cont = false;
			
			if (squares[x][y].getAnyWall((r+2)%4)
			    && !(x == sx && y == sy))
			    cont = false;
			else {
			    if (x != sx || y != sy)
				beamImages[x][y].add(be[i].getBeamImage());
			    x += dx(r);
			    y += dy(r);
			    length++;
			    if (length >= range)
				cont = false;
			}
		    }
		    beamImages[x-dx(r)][y-dy(r)].add(be[i].getBeamEndImage());
		}
	    }

	beamconfig = true;
    }

    /**
     * Called from squares.
     * Returns images painted in squares due to 
     * things outside of the squares scope
     * such as a beam element in a neiughbouring
     * square.
     */
    public ImageIcon[] getExternalImages(int x, int y) {
	if (!beamconfig) {
	    calcBeamImages();
	}

	ImageIcon[] ret = new ImageIcon[beamImages[x][y].size()];

	for (int i = 0; i < beamImages[x][y].size(); i++) {
	    ret[i] = (ImageIcon) beamImages[x][y].get(i);
	}

	return ret;
    }

    /**
     * Convert board into a string
     * for saving
     */
    public String toString() {
	String ret = "";
	ret += "THEME " + RRBEGlobal.curthemepath + "\n";
	ret += "BOARD " + gridWidth + " " + gridHeight + "\n";

	for (int y = 0; y < gridHeight; y++)
	    for (int x = 0; x < gridWidth; x++)
		ret += squares[x][y].toString() + "\n";

	return ret;
    }
}





