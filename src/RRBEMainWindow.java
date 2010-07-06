import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import javax.imageio.*;
import javax.imageio.stream.*;

/**
 * RRBE main window
 */
public class RRBEMainWindow extends JFrame {

    Container c;
    RRBEElementFactory currentElementFactory;
    RRBEBoard board;
    Vector elementChoosers;
    Vector rotationChoosers;
    private int rotation;
    RRBEToolBar toolBar;
    boolean[] activePhases;
    private Vector elementFactories;
    private RRBEThemeReader tr;
    private RRBEMenu menu;
    private String aboutText = "RoboRally Board Editor, by Niklas Lundstr�m";

    /**
     * Construct a main window.
     */
    public RRBEMainWindow() {
	elementChoosers = new Vector();
	rotationChoosers = new Vector();
	elementFactories = new Vector();
	toolBar = new RRBEToolBar(this);
	activePhases = new boolean[5];
	
	setTitle("RoboRally Board Editor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	c = getContentPane();

	c.setLayout(new BorderLayout());
	menu = new RRBEMenu(this);
	c.add(menu, BorderLayout.NORTH);
	c.add(toolBar, BorderLayout.WEST);

	registerElementChooser(toolBar);
	registerElementChooser(menu);

	newBoard(RRBEGlobal.stdBoardWidth
		 ,RRBEGlobal.stdBoardHeight);

	setRotation(0);	

	tr = new RRBEThemeReader(this);

    }

    /**
     * Closes the window and terminates the program
     */
    public void exit() {
	System.exit(0);
    }

    /**
     * Order themereader to load theme from global.themepath
     */
    public void changeTheme() {
	tr.readTheme();
	createTools();
	repaintAll();
    }

    /**
     * Creates a new board with specified width and height
     */
    public void newBoard(int width, int height) {
	RRBEGlobal.curFileName = "";
	createBoard(width, height);
    }

    private void createBoard(int width, int height) {
	
	float zoom = (float) 0.5;

	if (board != null) {
	    zoom = board.zoomFactor();
	    c.remove(board);
	}

	board = new RRBEBoard(this, width, height, zoom);
	Container c = getContentPane();
	c.add(board, BorderLayout.CENTER);

	repaintAll();
    }

    /**
     * Show save as dialogue
     */
    public void saveAs() {
	JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new RRBEFileFilter());
        int returnVal = jfc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            RRBEGlobal.curFileName = jfc.getSelectedFile().getPath();
            boardToFile();
        }
    }

    /**
     * Show load dialogue and loads selected file
     */
    public void load() {
	JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new RRBEFileFilter());
        int returnVal = jfc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            RRBEGlobal.curFileName = jfc.getSelectedFile().getPath();
            boardFromFile(true);
        }
	repaintAll();
    }

    public void repaintAll() {	
	board.setZoom(board.zoomFactor());
    }

    private class RRBEFileFilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;

            String name = f.getName();

            if (name.length() < 5)
                return false;

            return (name.substring(name.length() - 4,
                                   name.length()).equals(".rrb"));

        }

        public String getDescription() {
            return "RRBE Files (*.rrb)";
        }
    }

    /**
     * Save to file
     */
    public void save() {
	if (RRBEGlobal.curFileName.equals("")) {
	    saveAs();
	} else {
	    boardToFile();
	}
    }
    
    private void boardToFile() {
	try {
	    FileOutputStream fo = new FileOutputStream(RRBEGlobal.curFileName);
	    
	    fo.write(board.toString().getBytes());
	    
	    fo.close();
	} catch (Exception e) {
	    System.out.println("Error saving");
	}
    }

    private void boardFromFile(boolean changeTheme) {
	int oldRot = rotation;
	boolean[] oldActivePhases = new boolean[RRBEGlobal.phaseCount];

	for (int i = 0; i < RRBEGlobal.phaseCount; i++)
	    oldActivePhases[i] = activePhases[i];

	try {
	    FileInputStream fi = new FileInputStream(RRBEGlobal.curFileName);
	    int in = 0;
	    String t = "";
	    char c;

	    while (in >= 0) {
		in = fi.read();
		c = (char) in;
		if (in >= 0 && (c != '\n'))
		    t += (char) in;

		if (c == '\n') {
		    StringTokenizer st = new StringTokenizer(t, " ");
		    String nextToken = st.nextToken();
		    if (nextToken.equals("THEME")) {
			RRBEGlobal.curthemepath = t.substring(6);
		    } else if (nextToken.equals("BOARD")) {
			String w = st.nextToken();
			String h = st.nextToken();
			createBoard(Integer.parseInt(w), Integer.parseInt(h));
		    } else if (nextToken.equals("SQUARE")) {
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			RRBESquare s = board.getSquare(x,y);
			st.nextToken();
			for (int i = 0; i < 4; i++) {
			    if (Integer.parseInt(st.nextToken()) >= 0)
				s.setWall(i, true);
			}

			while (st.hasMoreTokens()) {
			    String t2 = st.nextToken();
			    RRBEElementFactory f = getElementFactory(t2);
			    t2 = st.nextToken();
			    int rot = Integer.parseInt(t2);
			    if (f != null) {
				for (int i = 0; 
				     i < RRBEGlobal.phaseCount
					 ; i++) {
				    if (Integer
					.parseInt(st.nextToken()) >= 0)
					activePhases[i] = true;
				    else
					activePhases[i] = false;
				}
				rotation = rot;
				f.activate(s);
			    } else {
			    }
			}
		    }
		    t = "";
		}

	    }

	    setRotation(oldRot);
	    for (int i = 0; i < RRBEGlobal.phaseCount; i++)
		activePhases[i] = oldActivePhases[i];

	    fi.close();
	} catch (Exception e) {
	    System.out.println("Error loading");
	    System.out.println(e);
	    e.printStackTrace();
	    
	}
    }

    /**
     * Add an element factory to the list of factories
     */
    public void addElementFactory(RRBEElementFactory f) {
	elementFactories.add(f);
    }

    /**
     * Remove all element factories from window
     */
    public void clearElementFactories() {
	elementFactories.clear();
    }

    /**
     * Get element factory with type typeId
     */
    public RRBEElementFactory getElementFactory(String typeId) {
	for (int i = 0; i < elementFactories.size(); i++) {
	    RRBEElementFactory f = (RRBEElementFactory)
		elementFactories.get(i);
	    if (f != null && f.typeId.equals(typeId))
		return f;
	}

	return null;
    }

    /**
     * Return an Object[] containting all element factories in this
     * window.
     */
    public Object [] getElementFactories() {
	return elementFactories.toArray();
    }

    /**
     * Initialize all element choosers, tool bar, menu etc.
     * to use all the registered element choosers.
     */
    public void createTools() {

	for (int j = 0; j < elementChoosers.size(); j++) {

	    RRBEElementChooser ec = (RRBEElementChooser)
		elementChoosers.get(j);

	    ec.removeFactories();

	    for (int i = 0; i < elementFactories.size(); i++) {
		RRBEElementFactory f = (RRBEElementFactory) 
		    elementFactories.get(i);
		ec.addElementFactory(f);
	    }
	}
    }

    /**
     * Register new element chooser
     */
    public void registerElementChooser(RRBEElementChooser e) {
	elementChoosers.add(e);
    }

    /**
     * Register rotation chooser
     */
    public void registerRotationChooser(RRBERotationChooser r) {
	rotationChoosers.add(r);
    }

    /**
     * Set rotation, if notify is set, notify the rotation choosers
     */
    public void setRotation(int r, boolean notify) {
	if (notify)
	    setRotation(r);
	else
	    rotation = r;
    }

    /**
     * Set rotation and notify rotation choosers.
     */
    public void setRotation(int r) {
	rotation = r;
	for (int i = 0; i < rotationChoosers.size(); i++) {
	    RRBERotationChooser e = (RRBERotationChooser)
		rotationChoosers.get(i);
	    e.setSelectedRotation(r);
	}
    }

    /**
     * Return the current rotatione
     */
    public int currentRotation() {
	return rotation;
    }

    /**
     * Set element factory and notify element choosers.
     */
    public void setElementFactory(RRBEElementFactory newElementFactory) {
	currentElementFactory = newElementFactory;
	for (int i = 0; i < elementChoosers.size(); i++) {
	    RRBEElementChooser e = (RRBEElementChooser) elementChoosers.get(i);
	    e.setSelected(newElementFactory);
	}
    }

    /**
     * return current element factory
     */
    public RRBEElementFactory getElementFactory() {
	return currentElementFactory;
    }

    /**
     * Return a reference to the board.
     */
    public RRBEBoard getBoard() {
	return board;
    }

    /**
     * Set phase i to v.
     * Ex. i = 1, v = true: activePhase[i] = true
     */
    public void setActivePhase(int i, boolean v) {
	activePhases[i] = v;
    }

    /**
     * Return true if phase i is set
     */
    public boolean getActivePhase(int i) {
	return activePhases[i];
    }

    /**
     * Show settings window.
     */
    public void showSettings() {
	RRBESettingsWindow sw = new RRBESettingsWindow(this);
	sw.show();
    }

    /**
     * Export to PNG using new ImageIO classes
     */
    public void exportToPNG(String fileName,RRBEExportPartition part) {
	for (int i = 0; i < part.countPartitions(); i++) {
	    int [] p = part.getPartition(i);
	    BufferedImage img; 
	    img = (BufferedImage) createImage((p[2]-p[0]) 
					      * RRBEGlobal.imageResolution,
					      (p[3]-p[1]) 
					      * RRBEGlobal.imageResolution);
	    Graphics G2D = (Graphics2D)img.getGraphics(); 
	    
	    board.paintOn(G2D, p[0],p[1],p[2],p[3],0,0); 
	    
	    try { 
		Iterator writers = ImageIO.getImageWritersByFormatName("png");
		ImageWriter writer = (ImageWriter)writers.next();
		File f;
		if (part.countPartitions() > 1)
		    f = new File(fileName + (i+1) + ".png"); 
		else
		    f = new File(fileName + ".npg");
		ImageOutputStream ios = ImageIO.createImageOutputStream(f);
		writer.setOutput(ios);
		writer.write(img);
	    } catch(IOException ioe) {
		System.out.println(ioe);
	    } 
	}
    
    }

    
    /**
     * Export the board to a JPEG file with name fileName
     * Using old JPEG library.
     */
    public void exportToJPG(String fileName, RRBEExportPartition part) {
	for (int i = 0; i < part.countPartitions(); i++) {
	    int [] p = part.getPartition(i);
	    BufferedImage img; 
	    img = (BufferedImage) createImage((p[2]-p[0]) 
					      * RRBEGlobal.imageResolution,
					      (p[3]-p[1]) 
					      * RRBEGlobal.imageResolution);
	    Graphics G2D = (Graphics2D)img.getGraphics(); 
	    
	    board.paintOn(G2D, p[0],p[1],p[2],p[3],0,0); 
	    
	    try {
		    if(part.countPartitions() > 1) {
		    	ImageIO.write(img, "jpg", new File(fileName + (i+1) + ".jpg"));
		    } else {
		    	ImageIO.write(img, "jpg", new File(fileName + ".jpg"));
		    }
	    } catch(Exception exc) {
	    	System.out.println(exc);
	    }
	    
	    /*try { 
		JPEGEncodeParam params = JPEGCodec
		    .getDefaultJPEGEncodeParam(img);
		params.setQuality((float) RRBEGlobal.exportQuality, true);
		if (part.countPartitions() > 1)
		    fos = new FileOutputStream(fileName + (i+1) + ".jpg"); 
		else
		    fos = new FileOutputStream(fileName + ".jpg");
		JPEGImageEncoder encoder = 
		    JPEGCodec.createJPEGEncoder(fos, params); 
		encoder.encode(img); 
		fos.flush(); 
		fos.close(); 
	    } 
	    catch(FileNotFoundException e) {
		System.out.println(e);
	    } 
	    catch(IOException ioe) {
		System.out.println(ioe);
	    } 
	    */
	}
    
    }

    /**
     * Show about screen
     */
    public void showAbout() {
	JOptionPane.showMessageDialog(this, aboutText, "About", 0);
    }
}






