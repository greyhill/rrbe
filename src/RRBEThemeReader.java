import java.util.*;
import java.io.*;

public class RRBEThemeReader {

    /**
     * If no theme definition is found, the theme is given this name
     */
    public static String NONAME = "No Theme Found";
    
    private RRBEMainWindow window;

    /**
     * Construct a Theme reader cnnected to a certain window.
     */
    public RRBEThemeReader(RRBEMainWindow w) {
	window = w;
    }

    /**
     * Takes a theme path and returns the name of the theme.
     */
    public static String readThemeName(String path) {	
	try {
	    RRBEFileReader fi = new RRBEFileReader(path + "theme", 
						   ';', true);
	    String t;
	    
	    while (fi.hasMoreTokens()) {
		t = fi.nextToken();
		if (t.indexOf('=') >= 0) {
		    StringTokenizer st = new StringTokenizer(t, "=");
		    String t2 = st.nextToken().toUpperCase().trim();
		    String t3 = st.nextToken().trim();
		    
		    if (t2.equals("NAME"))
			return t3;
		}
	    }
	}catch (Exception e) {
  
	    return NONAME;
	}
	return NONAME;
    } 

    /**
     * Read a theme from a Global.themepath and set the window to using it.
     */
    public void readTheme() {

	window.clearElementFactories();

	try {

	    RRBEFileReader fi = new RRBEFileReader(RRBEGlobal
						   .curthemepath + "theme", 
						   ';', true);
	    String t;

	    while (fi.hasMoreTokens()) {
		t = fi.nextToken();
		if (t.indexOf('=') >= 0) {
		    StringTokenizer st = new StringTokenizer(t, "=");
		    String t2 = st.nextToken().toUpperCase().trim();
		    String t3 = st.nextToken().trim();
		
		    if (t2.equals("NAME")) {
			RRBEGlobal.curthemename = t3;
		    } else if (t2.equals("IMAGERESOLUTION")) {
			RRBEGlobal.themeImageResolution = Integer.parseInt(t3);
		    } else if (t2.equals("WALL")) {
			RRBEGlobal.wallImageName = t3;
		    } else if (t2.equals("WALLTHICKNESS")) {
			RRBEGlobal.wallThickness = Integer.parseInt(t3);
		    } else if (t2.equals("WALLCORNER")) {
			RRBEGlobal.wallCornerImageName = t3;
		    } else if (t2.equals("PITICON")) {
			RRBEGlobal.pitImageName = t3;
		    } else if (t2.equals("PITWALL")) {
			RRBEGlobal.pitWallImageName = t3;
		    } else if (t2.equals("PITCORNER90")) {
			RRBEGlobal.pitCorner90ImageName = t3;
		    } else if (t2.equals("PITCORNERL180")) {
			RRBEGlobal.pitCornerl180ImageName = t3;
		    } else if (t2.equals("PITCORNERR180")) {
			RRBEGlobal.pitCornerr180ImageName = t3;
		    } else if (t2.equals("PITCORNER270")) {
			RRBEGlobal.pitCorner270ImageName = t3;
		    } else if (t2.equals("ELEMENT")) {
			try {
			    StringTokenizer st2 = new StringTokenizer(t3," ");
			    Hashtable ht = new Hashtable(30);
			
			    while (st2.hasMoreTokens()) {
				String par = st2.nextToken().toUpperCase();
				if (st2.hasMoreTokens()) {
				    String val = st2.nextToken();
				    ht.put(par, val);
				}
			    }
			
			    if (((String) ht.get("FACTORY")).toUpperCase().equals("ELEMENT")) 
				readElement(ht);
			    else if (((String) ht.get("FACTORY")).toUpperCase().equals("TIMEDELEMENT")) 
				readTimedElement(ht);
			    else if (((String) ht.get("FACTORY")).toUpperCase()
				     .equals("SCALEBASEELEMENT")) 
				readScaleBaseElement(ht);
			    else if (((String) ht.get("FACTORY")).toUpperCase()
				     .equals("BEAMELEMENT")) 
				readBeamElement(ht);
			} catch (Exception e) {
			    System.out.println("Error reading element");
			    e.printStackTrace();
			}
		    }
		
		    t = "";
		}
	    }
	} catch (Exception e) {
	    System.out.println("Error reading theme");
	    e.printStackTrace();
	}

	RRBEGlobal.globalInit();

	window.addElementFactory(new RRBERemoveFactory());
	window.addElementFactory(new RRBEWallFactory(window,"Wall"));
	window.addElementFactory(new RRBEPitFactory(window,"Pit"));
    }

    /*
     * Read a theme element
     */
    private void readElement(Hashtable ht) throws Exception {
	String name = "";
	String typeId = "";
	String imageName = ""; 
	int level = 0;
	boolean requireWall = false;
	boolean scaleToWall = false;
	if (ht.get("LEVEL") != null) level = Integer.parseInt((String) ht.get("LEVEL"));

	if (ht.get("NAME") != null) 
	    name = ((String) ht.get("NAME")).replace('_' , ' ');;

	if (ht.get("TYPEID") != null) 
	    typeId = (String) ht.get("TYPEID");
	else
	    throw new Exception("No type ID");

	if (ht.get("IMAGE") != null) 
	    imageName = (String) ht.get("IMAGE");
	else
	    throw new Exception("No image");

	if (ht.get("REQUIREWALL") != null) 
	    requireWall = Integer.parseInt((String) ht.get("REQUIREWALL")) != 0;
	if (ht.get("SCALETOWALL") != null) 
	    scaleToWall = Integer.parseInt((String) ht.get("SCALETOWALL")) != 0;

	window.addElementFactory(new RRBEElementFactory(typeId
							, window
							, name
							, imageName
							, level
							, requireWall
							, scaleToWall));
    }    

    /*
     * Read scale base element
     */
    private void readScaleBaseElement(Hashtable ht) throws Exception {
	String name = "";
	String typeId = "";
	String imageName = ""; 
	String baseImageName = ""; 
	int level = 0;
	boolean requireWall = false;
	if (ht.get("LEVEL") != null) level = Integer.parseInt((String) ht.get("LEVEL"));

	if (ht.get("NAME") != null) 
	    name = ((String) ht.get("NAME")).replace('_' , ' ');;

	if (ht.get("TYPEID") != null) 
	    typeId = (String) ht.get("TYPEID");
	else
	    throw new Exception("No type ID");

	if (ht.get("BASEIMAGE") != null) 
	    baseImageName = (String) ht.get("BASEIMAGE");

	if (ht.get("IMAGE") != null) 
	    imageName = (String) ht.get("IMAGE");
	else
	    throw new Exception("No image");

	if (ht.get("REQUIREWALL") != null) 
	    requireWall = Integer.parseInt((String) ht.get("REQUIREWALL")) != 0;

	window.addElementFactory(new RRBETwoLevelScaleBaseElementFactory(typeId
									 , window
									 , name
									 , baseImageName
									 , imageName
									 , level
									 , requireWall));
    }    

    private void readBeamElement(Hashtable ht) throws Exception {
	String name = "";
	String typeId = "";
	String imageName = ""; 
	String beamImageName = ""; 
	String beamEndImageName = ""; 
	int range = 0;
	boolean requireWall = false;
	boolean scaleToWall = false;
	if (ht.get("RANGE") != null) range = Integer.parseInt((String) ht.get("RANGE"));

	if (ht.get("NAME") != null) 
	    name = ((String) ht.get("NAME")).replace('_' , ' ');;

	if (ht.get("TYPEID") != null) 
	    typeId = (String) ht.get("TYPEID");
	else
	    throw new Exception("No type ID");

	if (ht.get("BEAMIMAGE") != null) 
	    beamImageName = (String) ht.get("BEAMIMAGE");
	else
	    throw new Exception("No image");

	if (ht.get("BEAMENDIMAGE") != null) 
	    beamEndImageName = (String) ht.get("BEAMENDIMAGE");
	else
	    beamEndImageName = beamImageName;

	if (ht.get("IMAGE") != null) 
	    imageName = (String) ht.get("IMAGE");
	else
	    throw new Exception("No image");

	if (ht.get("REQUIREWALL") != null) 
	    requireWall = Integer.parseInt((String) ht.get("REQUIREWALL")) != 0;
	if (ht.get("SCALETOWALL") != null) 
	    scaleToWall = Integer.parseInt((String) ht.get("SCALETOWALL")) != 0;

	window.addElementFactory(new RRBEBeamElementFactory(typeId
							    , window
							    , name
							    , imageName
							    , beamImageName
							    , beamEndImageName
							    , range
							    , requireWall
							    , scaleToWall));
    }

    private void readTimedElement(Hashtable ht) throws Exception {
	String name = "";
	String typeId = "";
	String imageName = ""; 
	String timeImageNameBase = "";
	String timeImageNameSufix = "";
	int level = 0;
	boolean requireWall = false;
	boolean scaleToWall = false;
	if (ht.get("LEVEL") != null) level = Integer.parseInt((String) ht.get("LEVEL"));

	if (ht.get("NAME") != null) 
	    name = ((String) ht.get("NAME")).replace('_' , ' ');;

	if (ht.get("TYPEID") != null) 
	    typeId = (String) ht.get("TYPEID");
	else
	    throw new Exception("No type ID");

	if (ht.get("IMAGE") != null) 
	    imageName = (String) ht.get("IMAGE");
	else
	    throw new Exception("No image");

	if (ht.get("TIMEIMAGEBASE") != null) 
	    timeImageNameBase = (String) ht.get("TIMEIMAGEBASE");
	else
	    throw new Exception("No time image base");

	if (ht.get("TIMEIMAGESUFIX") != null) 
	    timeImageNameSufix = (String) ht.get("TIMEIMAGESUFIX");
	else
	    throw new Exception("No time image sufix");

	if (ht.get("REQUIREWALL") != null) 
	    requireWall = Integer.parseInt((String) ht.get("REQUIREWALL")) != 0;
	if (ht.get("SCALETOWALL") != null) 
	    scaleToWall = Integer.parseInt((String) ht.get("SCALETOWALL")) != 0;

	window.addElementFactory(new RRBETimedElementFactory(typeId
							     , window
							     , name
							     , imageName
							     , timeImageNameBase
							     , timeImageNameSufix
							     , level
							     , requireWall
							     , scaleToWall));
    }
}
