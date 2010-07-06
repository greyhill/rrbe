import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

/**
 * Static settings and functions
 */
public class RRBEGlobal {
    /** Ini file name */
    public static String iniFileName = ".rrbe";
    /** default theme path*/
    public static String themepath = "themes/";
    /** Default graphics path*/
    public static String graphicspath = "graphics/";
    /** Current theme path*/
    public static String curthemepath = "themes/160x160/";
    /** Current theme name*/
    public static String curthemename = "160x160";

    /** Current file name*/
    public static String curFileName = "";

    /** Tool button size*/
    public static int toolButtonIconWidth = 32;
    /** Tool button size*/
    public static int toolButtonIconHeight = 32;
    /** Small tool button size*/
    public static int smallToolButtonIconWidth = 16;
    /** Small tool button size*/
    public static int smallToolButtonIconHeight = 16;
    /** Tiny tool button size*/
    public static int tinyToolButtonIconWidth = 16;
    /** Tiny tool button size*/
    public static int tinyToolButtonIconHeight = 16;

    /** Selected tool button color*/
    public static Color selectedButtonColor = Color.red;
    /** Not selected tool button color*/
    public static Color unSelectedButtonColor = Color.lightGray;
    /** Line between squares color*/
    public static Color borderColor = Color.black;
    /** Bottom of pit color*/
    public static Color pitColor = Color.black;

    /** Export image resolution dpi*/
    public static int imageResolution = 160;
    /** Quality of JPeg export*/
    public static float exportQuality = (float) 1.0;
    /** DPI of this Theme*/
    public static int themeImageResolution = 160;

    public static String pitWallImageName = "";
    public static String pitCorner90ImageName = "";
    public static String pitCornerl180ImageName = "";
    public static String pitCornerr180ImageName = "";
    public static String pitCorner270ImageName = "";
    public static ImageIcon[] pitwall = new ImageIcon[4];
    public static ImageIcon[] pitcorner = new ImageIcon[16];
    public static String pitImageName = "4pit.png"; 

    public static String removeImageName = "removeicon.png"; 

    public static ImageIcon removeIcon;

    private static Vector themes = new Vector();;
    
    public static String wallImageName = "";
    public static String wallCornerImageName = "";
    public static ImageIcon[] wall = new ImageIcon[4];
    public static ImageIcon[] wallcorner = new ImageIcon[4];
    public static int wallThickness = 16;

    public static int stdBoardWidth = 4;
    public static int stdBoardHeight = 4;

    public static int maxLevel = 100;

    public static int phaseCount = 5;

    /**
     * Return java.color from string of name
     * Not working
     */
    public static Color renderColor(String s) {
	return Color.black;
    }

    /**
     * Add a theme to this program
     */
    public static void addTheme(String theme) {
	RRBETheme t = new RRBETheme(theme
				    , RRBEThemeReader.readThemeName(theme));
	themes.add(t);
    }

    /**
     * Remove all themes
     */
    public static void removeThemes() {
	themes.clear();
    }

    /**
     * Get array of themes
     */
    public static RRBETheme[] getThemes() {
	Object[] o = themes.toArray();
	RRBETheme[] ret = new RRBETheme[o.length]; 
	int j = 0;
	for (int i = 0; i < o.length; i++)
	    if (o[i] instanceof RRBETheme)
		ret[j++] = (RRBETheme) o[i];
	
	return ret;
    }

    /**
     * Write the ini file to disk
     */
    public static void writeIniFile() {
	try {
	    FileOutputStream fo = new FileOutputStream(iniFileName);
	    
	    fo.write(String.valueOf("CURTHEME").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(curthemepath.getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("GRAPHICS").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(graphicspath.getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("THEMEPATH").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(themepath.getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("IMAGERESOLUTION").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(imageResolution).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("TOOLBUTTONICONWIDTH").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(toolButtonIconWidth).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("TOOLBUTTONICONHEIGHT").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(toolButtonIconHeight).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("SMALLTOOLBUTTONICONWIDTH").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(smallToolButtonIconWidth).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("SMALLTOOLBUTTONICONHEIGHT").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(smallToolButtonIconHeight).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("TINYTOOLBUTTONICONWIDTH").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(tinyToolButtonIconWidth).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("TINYTOOLBUTTONICONHEIGHT").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(tinyToolButtonIconHeight).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("BOARDWIDTH").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(stdBoardWidth).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("BOARDHEIGHT").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(stdBoardHeight).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    fo.write(String.valueOf("MAXELEMENTLEVEL").getBytes());
	    fo.write(String.valueOf("=").getBytes());
	    fo.write(String.valueOf(maxLevel).getBytes());
	    fo.write(String.valueOf("\n").getBytes());
	    
	    RRBETheme[] t = getThemes();

	    for (int i = 0; i < t.length; i++) {
		fo.write(String.valueOf("THEME").getBytes());
		fo.write(String.valueOf("=").getBytes());
		fo.write(t[i].getPath().getBytes());
		fo.write(String.valueOf("\n").getBytes());
	    }
	    /*fo.write(String.valueOf("THEME").getBytes());
	      addTheme(t3);*/
		

	    fo.close();
	} catch (Exception e) {
	}
    }

    /**
     * Read ini file from file
     */
    public static void readIniFile() {	
	try {
	    //FileInputStream fi = new FileInputStream(iniFileName);

	    BufferedReader br = new BufferedReader(new
		FileReader(iniFileName));

	    for (String line = br.readLine(); line != null
		     ; line = br.readLine()) {
		// process ini line
		StringTokenizer st = new StringTokenizer(line, "=");
		if (st.hasMoreTokens()) {
		    String t2 = st.nextToken().toUpperCase();
		    if (st.hasMoreTokens()) {
			String t3 = st.nextToken();
			
			if (t2.equals("CURTHEME")) {
			    curthemepath = t3;
			} else if (t2.equals("GRAPHICS")) {
			    graphicspath = t3;
			} else if (t2.equals("THEMEPATH")) {
			    themepath = t3;
			} else if (t2.equals("IMAGERESOLUTION")) {
			    imageResolution = Integer.parseInt(t3);
			} else if (t2.equals("TOOLBUTTONICONWIDTH")) {
			    toolButtonIconWidth = Integer.parseInt(t3);
			} else if (t2.equals("TOOLBUTTONICONHEIGHT")) {
			    toolButtonIconHeight = Integer.parseInt(t3);
			} else if (t2.equals("SMALLTOOLBUTTONICONWIDTH")) {
			    smallToolButtonIconWidth = Integer
				.parseInt(t3);
			} else if (t2
				   .equals("SMALLTOOLBUTTONICONHEIGHT")) {
			    smallToolButtonIconHeight = Integer
				.parseInt(t3);
			} else if (t2.equals("TINYTOOLBUTTONICONWIDTH")) {
			    tinyToolButtonIconWidth = Integer.parseInt(t3);
			} else if (t2.equals("TINYTOOLBUTTONICONHEIGHT")) {
			    tinyToolButtonIconHeight = Integer
				.parseInt(t3);
			} else if (t2.equals("BOARDWIDTH")) {
			    stdBoardWidth = Integer.parseInt(t3);
			} else if (t2.equals("BOARDHEIGHT")) {
			    stdBoardHeight = Integer.parseInt(t3);
			} else if (t2.equals("MAXELEMENTLEVEL")) {
			    maxLevel = Integer.parseInt(t3);
			} else if (t2.equals("THEME")) {
			    addTheme(t3);
			}			    
		    }
		}
	    }
	} catch (Exception e) {
	    System.out.println("Error reading ini");
	    e.printStackTrace();
	}
    }

    /**
     * Initialize all global variables
     */
    public static void globalInit() {

	//	boardBackground = new ImageIcon(curthemepath + "background.png");

	pitwall[0] = new ImageIcon(curthemepath+pitWallImageName);
	for (int i = 0; i < 3; i++)
	    pitwall[i+1] = rotateImage(pitwall[i]);
	
	pitcorner[0] = new ImageIcon(curthemepath+pitCorner90ImageName);
	for (int i = 0; i < 3; i++)
	    pitcorner[i+1] = rotateImage(pitcorner[i]);

	pitcorner[4] = new ImageIcon(curthemepath+pitCornerl180ImageName);
	for (int i = 4; i < 7; i++)
	    pitcorner[i+1] = rotateImage(pitcorner[i]);

	pitcorner[8] = new ImageIcon(curthemepath+pitCornerr180ImageName);
	for (int i = 8; i < 11; i++) 
	    pitcorner[i+1] = rotateImage(pitcorner[i]);
	
	pitcorner[12] = new ImageIcon(curthemepath+pitCorner270ImageName);
	for (int i = 12; i < 15; i++) 
	    pitcorner[i+1] = rotateImage(pitcorner[i]);
	
	/*	wallImageNames[0] = "walltop.png";
	wallImageNames[1] = "wallright.png";
	wallImageNames[2] = "wallbottom.png";
	wallImageNames[3] = "wallleft.png";*/
	
	wall[0] = new ImageIcon(curthemepath+wallImageName);
	for (int i = 0; i < 3; i++)
	    wall[i+1] = rotateImage(wall[i]);

	wallcorner[0] = new ImageIcon(curthemepath+"wallcornertr90.png");
	for (int i = 0; i < 3; i++)
	    wallcorner[i+1] = rotateImage(wallcorner[i]);

	removeIcon = new ImageIcon(graphicspath + removeImageName);
    }

    /**
     * Combine paint image 2 on top of image 1 and return the result
     */
    public static ImageIcon combineImages(ImageIcon i1, ImageIcon i2) {
	int h = i1.getIconHeight();
	int w = i1.getIconWidth();
	BufferedImage ret = null;
	Graphics2D g = null;
	if (h > 0 && w > 0) {
	    ret = new BufferedImage(h,w
				  ,BufferedImage.TYPE_INT_ARGB);
	    g = ret.createGraphics();
	    g.drawImage(i1.getImage(),0,0,w,h,(ImageObserver) null);
	    g.drawImage(i2.getImage(),0,0,w,h,(ImageObserver) null);
	}
	if (ret != null)
	    return new ImageIcon(ret);
	else
	    return null;
    }

    /**
     * Rotate the image 90 degrees left and return result
     */
    public static ImageIcon rotateImage(ImageIcon image) {
	return rotateImage(image, null);
    }
    
    /**
     * Rotate the image 90 degrees left and return result.
     * With specified observer.
     */
    public static ImageIcon rotateImage(ImageIcon image, ImageObserver i) {
	int h = image.getIconHeight();
	int w = image.getIconWidth();
	BufferedImage img2 = null;
	Graphics2D rotg = null;

	if (h > 0 && w > 0) {
	    img2 = new BufferedImage(h,w
				  ,BufferedImage.TYPE_INT_ARGB);
	    rotg = img2.createGraphics();
	    rotg.rotate(Math.PI/2, w/2,h/2);
	    rotg.drawImage(image.getImage(),0,0,w,h,i);
	}

	if (img2 != null)
	    return new ImageIcon(img2);
	else
	    return null;
	
    }

    /**
     * Create a small button icon from an image file
     */
    public static ImageIcon smallButtonIcon(String fileName) {
	ImageIcon ret = new ImageIcon(fileName);

	ret = new ImageIcon(ret.getImage()
			    .getScaledInstance(smallToolButtonIconWidth
					       , smallToolButtonIconHeight
					       , Image.SCALE_SMOOTH));
	return ret;
    }

    
    /**
     * Create a small button icon from an image icon.
     */
    public static ImageIcon smallButtonIcon(ImageIcon i) {
	return new ImageIcon(i.getImage()
			    .getScaledInstance(smallToolButtonIconWidth
					       , smallToolButtonIconHeight
					       , Image.SCALE_SMOOTH));
    }
}
