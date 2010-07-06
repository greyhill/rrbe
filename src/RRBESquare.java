import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class RRBESquare extends JLabel implements MouseListener{

    private RRBEMainWindow window;
    public int x,y;
    private ElementHeap eh;
    private BeamElementHeap beamHeap;
    private JPopupMenu rightMenu;

    private JMenu wallSelect;
    private JCheckBoxMenuItem[] walls;
    private int[] supportWalls = new int[4];
    
    private int elementCount;

    public static int NONE = 0;
    public static int REMOVE = 1;
    public static int EDIT = 2;
    public static int ROTATELEFT = 3;
    public static int ROTATERIGHT = 4;
    public static int CLEARWALLS = 5;
    public static int CHANGEWALLS = 6;
    public static int TOGGLEACTIVEPHASE = 7; // the next 
                                             // phaseCount states are occupied!
    

    public RRBESquare (RRBEMainWindow w, int x, int y) {
	window = w;
	eh = new ElementHeap();
	beamHeap = new BeamElementHeap();
	wallSelect = new JMenu("Walls");
	JMenuItem clearWalls = new JMenuItem("Remove walls");
	clearWalls.addActionListener(new 
	    rightMenuHandler(window,
			     this,
			     null,
			     5));
	wallSelect.add(clearWalls);
	
	walls = new JCheckBoxMenuItem[4];
	walls[0] = new JCheckBoxMenuItem("Top");
	walls[1] = new JCheckBoxMenuItem("Right");
	walls[2] = new JCheckBoxMenuItem("Bottom");
	walls[3] = new JCheckBoxMenuItem("Left");

	for (int i = 0; i < 4; i++) {
	    wallSelect.add(walls[i]);	
	     walls[i].addActionListener(new 
		rightMenuHandler(window,
				 this,
				 null,
				 CHANGEWALLS));
	}


	this.x = x;
	this.y = y;

	this.addMouseListener(this);

	elementCount = 0;
    }

    public void setZoom(float ratio) {
	int side = (int) ((float) RRBEGlobal.themeImageResolution * ratio);
	Dimension d = new Dimension(side, side);
	setPreferredSize(d);
	setSize(d);
	setMinimumSize(d);
	setMaximumSize(d);
    }

    public void addElement(RRBEElement e) {
	elementCount += eh.add(e);
	if (e.requireWall) {
	    setSupportWall((e.getRotation()+2) % 4, true);
	}
    }

    public void removeElement(RRBEElement e) {
	if (e.requireWall)
	    setSupportWall((e.getRotation() + 2) % 4, false);
	if (e instanceof RRBEBeamElement)
	    beamHeap.remove((RRBEBeamElement) e);
	else
	    elementCount -= eh.remove(e);
	window.getBoard().invalidBeamState();
    }

    public void removeElement(int level) {
	RRBEElement e = eh.get(level);
	if (e != null) {
	    if (e.requireWall)
		setSupportWall((e.getRotation() + 2) % 4, false);
	    elementCount -= eh.remove(level);
	}
	window.getBoard().invalidBeamState();
    }
    
    public void addBeamElement(RRBEBeamElement e) {
	beamHeap.add(e);
	if (e.requireWall) {
	    setSupportWall((e.getRotation()+2) % 4, true);
	}

	window.getBoard().invalidBeamState();
    }

    public void removeBeamElement(RRBEBeamElement e) {
	if (e != null) {
	    if (e.requireWall)
		setSupportWall((e.getRotation() + 2) % 4, false);
	    beamHeap.remove(e);	    
	}
	window.getBoard().invalidBeamState();
    }

    public RRBEBeamElement[] getBeamElements() {
	RRBEBeamElement[] ret = new RRBEBeamElement[beamHeap.size()];

	for (int i = 0; i < beamHeap.size(); i++) {
	    ret[i] = beamHeap.get(i);
	}
	return ret;
    }

    public int count() {
	return elementCount;
    }

    public boolean getWall(int w) {
	return walls[w].getState();
    }

    public boolean getAnyWall(int w) {
	return walls[w].getState() || getSupportWall(w);
    }

    public void setWall(int w, boolean b) {
	walls[w].setState(b);
	window.getBoard().invalidBeamState();
    }

    public boolean getSupportWall(int w) {
	return (supportWalls[w] > 0);
    }

    public void setSupportWall(int w, boolean b) {
	if (b)
	    supportWalls[w]++;
	else
	    supportWalls[w]--;
	window.getBoard().invalidBeamState();
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	int w = this.getWidth();
	int h = this.getHeight();

	paint(g,w,h);
    }

    private void paint(Graphics g, int w, int h) {
	
	boolean elements = false;
	int ll, tt, hh, ww;
	double wth = (double) RRBEGlobal.wallThickness;
	double wtw = (double) RRBEGlobal.wallThickness;
	wth /= (double) RRBEGlobal.wall[0].getIconHeight(); 
	wtw /= (double) RRBEGlobal.wall[0].getIconWidth(); 
	wth *= (double) h; 
	wtw *= (double) w;
	
	ll = tt = 0;
	hh = h;
	ww = w;
	if (getAnyWall(0))
	    tt = (int) wth;
	
	if (getAnyWall(2))
	    hh -= (int) wth;

	if (getAnyWall(3)) 
	    ll = (int) wtw;
	
	if (getAnyWall(1))
	    ww -= (int) wtw;
	
	if (eh.get(0) == null) {
	    // draw pit

	    RRBEBoard b = window.getBoard();
	    boolean mxmy = x > 0 && y > 0 && b.getSquare(x-1,y-1).count() > 0;
	    boolean mxpy = x > 0 && y < b.height()-1 
		&& b.getSquare(x-1,y+1).count() > 0;
	    boolean mxy = x > 0 && b.getSquare(x-1,y).count() > 0;
	    boolean xmy = y > 0 && b.getSquare(x,y-1).count() > 0;
	    boolean xy = b.getSquare(x,y).count() > 0;
	    boolean pxy = x < b.width()-1 && b.getSquare(x+1,y).count() > 0;
	    boolean xpy = y < b.height()-1 && b.getSquare(x,y+1).count() > 0;
	    boolean pxpy = x < b.width()-1 && y < b.height()-1 
		&& b.getSquare(x+1,y+1).count() > 0;
	    boolean pxmy = x < b.width()-1 && y > 0 
		&& b.getSquare(x+1,y-1).count() > 0;
	    boolean x0 = x == 0;
	    boolean y0 = y == 0;
	    boolean xm = x == b.width()-1;
	    boolean ym = y == b.height()-1;

	    g.setColor(RRBEGlobal.pitColor);
	    g.fillRect(0,0,w,h);

	    if (xmy || y == 0) {
		g.drawImage(RRBEGlobal.pitwall[0].getImage()
			    ,0,0,w,h,0,0
			    ,RRBEGlobal.pitwall[0].getIconWidth()
			    ,RRBEGlobal.pitwall[0].getIconHeight(),this);
	    }
	    if (mxy || x == 0) {
		g.drawImage(RRBEGlobal.pitwall[3].getImage()
			    ,0,0,w,h,0,0
			    ,RRBEGlobal.pitwall[3].getIconWidth()
			    ,RRBEGlobal.pitwall[3].getIconHeight()
			    ,this);
	    }
	    if (pxy || x == b.width()-1) {
		g.drawImage(RRBEGlobal.pitwall[1].getImage()
			    ,0,0,w,h,0,0
			    ,RRBEGlobal.pitwall[1].getIconWidth()
			    ,RRBEGlobal.pitwall[1].getIconHeight()
			    ,this);
	    }
	    if (xpy || y == b.height()-1) {
		g.drawImage(RRBEGlobal.pitwall[2].getImage()
			    ,0,0,w,h,0,0
			    ,RRBEGlobal.pitwall[2].getIconWidth()
			    ,RRBEGlobal.pitwall[2].getIconHeight()
			    ,this);
	    }

	    // corners
	    boolean[] corners = new boolean[16];
	    
	    corners[0] = (x0 && y0) || (mxy && y0) 
		|| (x0 && xmy) || (mxy && xmy); //topleft90
	    corners[1] = (xm && y0) || (pxy && y0) 
		|| (xm && xmy) || (pxy && xmy); //topright90
	    corners[3] = (x0 && ym) || (x0 && xpy) 
		|| (mxy && ym) || (mxy && xpy); //bottomleft90
	    corners[2] = (xm && ym) || (pxy && ym) 
		|| (xm && xpy) || (pxy && xpy); //bottomright90

	    corners[4]  = (y0 && !(x0 || mxy)) || (xmy && !mxy && !x0); //tlt180
	    corners[5]  = (xm && !(y0 || xmy)) || (pxy && !xmy && !y0); //trr180
	    corners[6]  = (ym && !(xm || pxy)) || (xpy && !pxy && !xm); //brb180
	    corners[7]  = (x0 && !(ym || xpy)) || (mxy && !xpy && !ym); //bll180
	    
	    corners[8]  = (y0 && !(xm || pxy)) || (xmy && !pxy && !xm); //trt180
	    corners[9] = (xm && !(ym || xpy)) || (pxy && !xpy && !ym); //brr180
	    corners[10] = (ym && !(x0 || mxy)) || (xpy && !mxy && !x0); //blb180
	    corners[11] = (x0 && !(y0 || xmy)) || (mxy && !xmy && !y0); //tll180

	    corners[12] = (mxmy && !mxy && !xmy && !x0 && !y0); //tl270
	    corners[13] = (pxmy && !pxy && !xmy && !xm && !y0); //tr270
	    corners[14] = (pxpy && !pxy && !xpy && !xm && !ym); //br270
	    corners[15] = (mxpy && !mxy && !xpy && !x0 && !ym); //bl270

	    for (int i = 0; i < 16; i++) {
		if (corners[i])
		    g.drawImage(RRBEGlobal.pitcorner[i].getImage()
				,0,0,w,h,0,0
				,RRBEGlobal.pitcorner[i].getIconWidth()
				,RRBEGlobal.pitcorner[i].getIconHeight()
				,this);		    
		
	    }
	}

	for (int i = 0; i < eh.size(); i++)
	    if (eh.get(i) != null) {
		elements = true;
		    eh.get(i).drawTo(g,ll,tt,ww,hh,0,0,w,h,this);
	    }

	if (eh.get(0) != null) {
	    // draw edges
	    	    g.setColor(RRBEGlobal.borderColor);
		    g.drawRect(0,0,w-1,h-1);
	}

	// draw laser
	for (int i = 0; i < beamHeap.size(); i++)
	    if (beamHeap.get(i) != null) {
		beamHeap.get(i).drawTo(g,ll,tt,ww,hh,0,0,w,h,this);
	    }

	ImageIcon[] external = window.getBoard().getExternalImages(x,y);

	for (int i = 0; i < external.length; i++) {	    
	    g.drawImage(external[i].getImage()
			,0,0,w,h,0,0
			,external[i].getIconWidth()
			,external[i].getIconHeight()
			,this);
	}

	// draw walls
	
	for (int i = 0; i < 4; i++)
	    if (getAnyWall(i)) {
		g.drawImage(RRBEGlobal.wall[i].getImage(),
			    0,0,w,h,0,0
			    ,RRBEGlobal.wall[i].getIconWidth()
			    ,RRBEGlobal.wall[i].getIconHeight()
			    , this);
	    }

	for (int i = 0; i < 4; i++) {
	    if ((getAnyWall(i) && getAnyWall((i+1) % 4)))
		g.drawImage(RRBEGlobal.wallcorner[i].getImage(),
			    0,0,w,h,0,0
			    ,RRBEGlobal.wallcorner[i].getIconWidth()
			    ,RRBEGlobal.wallcorner[i].getIconHeight()
			    , this);
	}
    }

    public void paintOn(Graphics g, int x, int y, int w, int h) {
	g = g.create(x,y,w,h);
	paint(g,w,h);
    }

    public void mouseClicked(MouseEvent e) {
	if (e.getButton() == e.BUTTON1) {
	    // left click
	    RRBEElementFactory f = window.getElementFactory();
	    if (f != null) {
			//addElement(f.createElement());
		f.activate(this);
		window.repaint(0);
	    }
	} else if (e.getButton() == e.BUTTON2) {
	    // middle-click
	    
	} else if (e.getButton() == e.BUTTON3) {
	    // right-click
	    rightMenu = new JPopupMenu();
	    rightMenu.add(wallSelect);

	    for (int i = 0; i < eh.size() + beamHeap.size(); i++) {
		RRBEElement ehget;
		if (i <eh.size())
		    ehget = eh.get(i);
		else
		    ehget = beamHeap.get(i - eh.size());
		if (ehget != null) {
		    String s = ehget.getName();
		    JMenu t = new JMenu(s);
		    t.setIcon(ehget.getSmallIcon());
		    		    
		    JMenuItem remove = 
			new JMenuItem("Remove " + s);
		    remove.addActionListener(new 
			rightMenuHandler(window,
					 this,
					 ehget,
					 RRBESquare.REMOVE));
		    t.add(remove);

		    if (ehget instanceof RRBETimedElement) {
			JMenu phaseMenu = new JMenu("Active phases");
			JCheckBoxMenuItem phases[] = 
			    new JCheckBoxMenuItem[RRBEGlobal.phaseCount];

			for (int j = 0; j < RRBEGlobal.phaseCount; j++) {
			    phases[j] = 
				new JCheckBoxMenuItem((new 
				    Integer(j+1)).toString());
			    phases[j].setState(ehget.getActivePhase(j));
			    phaseMenu.add(phases[j]);
			    phases[j].addActionListener(new 
				rightMenuHandler(window,
						 this,
						 ehget,
						 RRBESquare
						 .TOGGLEACTIVEPHASE+j));
			}
			t.add(phaseMenu);
		    }

		    JMenuItem edit = new JMenuItem("Edit " + s);
		    edit.addActionListener(new 
			rightMenuHandler(window,
					 this,
					 ehget,
					 RRBESquare.EDIT));
		    //t.add(edit);
		    JMenuItem rotatel = new JMenuItem("Rotate " + s + " left");
		    rotatel.addActionListener(new 
			rightMenuHandler(window,
					 this,
					 ehget,
					 RRBESquare.ROTATELEFT));
		    t.add(rotatel);
		    JMenuItem rotater = new 
			JMenuItem("Rotate " + s + " right");
		    rotater.addActionListener(new 
			rightMenuHandler(window,
					 this,
					 ehget,
					 RRBESquare.ROTATERIGHT));
		    t.add(rotater);
		    rightMenu.add(t);
		}
	    }
	    rightMenu.show(e.getComponent(), e.getX(), e.getY());
	}
    }
    public void mouseEntered(MouseEvent e) {

	//if (e.getClickCount() > 0)
	//    mouseClicked(e);

    }
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public String toString() {
	String ret = "SQUARE " + x + " " + y;

	ret += " WALL";
	for (int i = 0; i < 4; i++)
	    if (getWall(i))
		ret += " " + i;
	    else
		ret += " -1"; 
		 
	for (int i = 0; i < eh.size() + beamHeap.size(); i++) {
	    RRBEElement e ;
	    if (i < eh.size())
		e = eh.get(i);
	    else
		e = beamHeap.get(i - eh.size());

	    if (e != null) {
		ret += " " + e.getTypeId() + " " + e.getRotation();
		for (int j = 0; j < RRBEGlobal.phaseCount; j++) {
		    if (e.getActivePhase(j))
			ret += " " + j;
		    else
			ret += " -1";
		}
	    }
	}
	return ret;
    }
  
    private class rightMenuHandler implements ActionListener {
	private RRBEMainWindow window;
	private RRBESquare square;
	private RRBEElement element;
	private int action;

	public rightMenuHandler(RRBEMainWindow w, RRBESquare s
				, RRBEElement e
				, int a) {
	    window = w;
	    square = s;
	    element = e;
	    action = a;
	}

	public void actionPerformed(ActionEvent e) {

	    if (action == RRBESquare.REMOVE) {
		square.removeElement(element);
	    } else if (action == RRBESquare.ROTATELEFT) {
		if (element.requireWall) {
		    setSupportWall((element.getRotation()+2) % 4, false);
		}
		element.rotateLeft();
		window.getBoard().invalidBeamState();
		if (element.requireWall) {
		    setSupportWall((element.getRotation()+2) % 4, true);
		}
	    } else if (action == RRBESquare.ROTATERIGHT) {
		if (element.requireWall) {
		    setSupportWall((element.getRotation()+2) % 4, false);
		}
		element.rotateRight();
		window.getBoard().invalidBeamState();
		if (element.requireWall) {
		    setSupportWall((element.getRotation()+2) % 4, true);
		}
	    } else if (action == RRBESquare.CLEARWALLS) {
		for (int i = 0; i < 4; i++)
		    square.setWall(i,false);

		window.getBoard().invalidBeamState();

	    } else if (action == RRBESquare.CHANGEWALLS) {
		window.getBoard().invalidBeamState();

	    } else if (action >= RRBESquare.TOGGLEACTIVEPHASE
		       && action < RRBESquare.TOGGLEACTIVEPHASE 
		       + RRBEGlobal.phaseCount) {
		int phase = action - RRBESquare.TOGGLEACTIVEPHASE;
		element.setActivePhase(phase
				       , !element.getActivePhase(phase));
	    }
	    window.repaint(0);
	}
    }

    private class BeamElementHeap {
	Vector s;

	public BeamElementHeap() {
	    s = new Vector();
	}

	public void add(RRBEBeamElement e) {
	    s.add(e);
	}

	public void remove(RRBEBeamElement e) {
	    s.remove(e);
	}

	public RRBEBeamElement get(int i) {
	    return (RRBEBeamElement) s.get(i);
	}

	public int size() {
	    return s.size();
	}
    }

    private class ElementHeap {
	Vector s;

	public ElementHeap() {
	    s = new Vector();
	    for (int i = 0; i < RRBEGlobal.maxLevel+1; i++)
		s.add(null);
	}

	public int add(RRBEElement e) {
	    int ret = 0;
	    if (s.get(e.level) == null)
		ret = 1;
	    s.setElementAt(e, e.level);
	    return ret;
	}

	public int remove(int i) {
	    int ret = 0;
	    if (s.get(i) != null)
		ret = 1;
	    s.setElementAt(null, i);
	    return ret;
	}

	public int remove(RRBEElement e) {
	    int ret = 0;
	    if (s.get(e.level) != null)
		ret = 1;
	    remove(e.level);
	    return ret;
	}

	public RRBEElement get(int i) {
	    return (RRBEElement) s.get(i);
	}

	public int size() {
	    return s.size();
	}
    }
}









