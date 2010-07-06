import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Menues for RRBE
 */
public class RRBEMenu extends JMenuBar 
    implements ActionListener, MenuListener, RRBEElementChooser {

    private RRBEMainWindow window;

    private String about = "";

    private JMenu file;
    private JMenu tools;
    private JMenu theme;
    private JMenu view;
    private JMenu chooseTheme;
    private JMenu help;

    private JMenuItem fileNew;
    private JMenuItem fileLoad;
    private JMenuItem fileSave;
    private JMenuItem fileSaveAs;
    private JMenuItem fileExport;
    private JMenuItem filePreferences;
    private JMenuItem fileExit;

    private JMenuItem viewZoomIn;
    private JMenuItem viewZoomOut;

    private JMenuItem addTheme;

    private JMenuItem helpAbout;
    private Vector factories;

    /**
     * Create the menues
     */
    public RRBEMenu(RRBEMainWindow w) {
	factories = new Vector();
	
	window = w;
	file = new JMenu("File");
	file.setMnemonic(KeyEvent.VK_F);

	fileNew = new JMenuItem("New Board", KeyEvent.VK_N);
	fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
						      ActionEvent.CTRL_MASK));
	fileLoad = new JMenuItem("Open Saved Board", KeyEvent.VK_L);
	fileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
						       ActionEvent.CTRL_MASK));
	fileSave = new JMenuItem("Save Board", KeyEvent.VK_L);
	fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						       ActionEvent.CTRL_MASK));
	fileSaveAs = new JMenuItem("Save Board As", KeyEvent.VK_L);
	fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12,
						       0));

	fileExport = new JMenuItem("Export...", KeyEvent.VK_E);
	fileExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11,
						       0));
							      
	filePreferences = new JMenuItem("Preferences...", KeyEvent.VK_L);
	filePreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
							      0));
	fileExit = new JMenuItem("Exit", KeyEvent.VK_X);
	fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
						       ActionEvent.ALT_MASK));


	tools = new JMenu("Tools");
	tools.setMnemonic(KeyEvent.VK_T);

	theme = new JMenu("Themes");
	theme.setMnemonic(KeyEvent.VK_T);
	chooseTheme = new JMenu("Choose Theme");
	chooseTheme.setMnemonic(KeyEvent.VK_C);
	chooseTheme.addMenuListener(this);
	theme.add(chooseTheme);

	addTheme = new JMenuItem("Add a theme");
	addTheme.addActionListener(this);
	theme.add(addTheme);

	view = new JMenu("View");
	viewZoomIn = new JMenuItem("Zoom In");
	viewZoomIn.addActionListener(this);
	viewZoomOut = new JMenuItem("Zoom Out");
	viewZoomOut.addActionListener(this);
	view.add(viewZoomIn);
	view.add(viewZoomOut);
	

	help = new JMenu("Help");
	help.setMnemonic(KeyEvent.VK_H);

	helpAbout = new JMenuItem("About", KeyEvent.VK_A);
	
	fileNew.addActionListener(this);
	fileLoad.addActionListener(this);
	fileSave.addActionListener(this);
	fileSaveAs.addActionListener(this);
	fileExport.addActionListener(this);
	filePreferences.addActionListener(this);
	fileExit.addActionListener(this);	
	
	helpAbout.addActionListener(this);
	
	this.add(file);
	file.add(fileNew);
	file.add(fileLoad);
	file.add(fileSave);
	file.add(fileSaveAs);
	file.addSeparator();
	file.add(fileExport);
	file.add(filePreferences);
	file.addSeparator();
	file.add(fileExit);
	
	this.add(tools);

	this.add(theme);
	
	this.add(view);

	this.add(help);
	help.add(helpAbout);
    }

    /**
     * Menues is an element chooser.
     * Adds a element factory to tools menu.
     */
    public void addElementFactory(RRBEElementFactory f) {

	ToolMenuItem i = new ToolMenuItem(f);
	factories.add(i);
	tools.add(i);
	i.addActionListener(this);

    }

    /**
     * Set selected element factory
     */
    public void setSelected(RRBEElementFactory f) {
	for (int i = 0; i < factories.size(); i++) {
	    ToolMenuItem ti = (ToolMenuItem) factories.get(i);
	    ti.setSelected(f == ti.factory);
	}
    }
    
    /**
     * Remove all factories from tool menu.
     */
    public void removeFactories() {
	factories.clear();
	tools.removeAll();
    }
    
    public void menuCanceled(MenuEvent e) {}
    public void menuDeselected(MenuEvent e) {}
    /**
     * Called then choose theme menu selected.
     * Updates themes.
     */
    public void menuSelected(MenuEvent e) {
	    chooseTheme.removeAll();
	    RRBETheme[] themes = RRBEGlobal.getThemes();
	    for (int i = 0; i < themes.length; i++) {
		JMenuItem themeMenu = new JMenuItem(themes[i].toString());
		themeMenu.addActionListener(new ThemeSelector(window, themes[i]));
		chooseTheme.add(themeMenu);
	    }
    }
    
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == fileExport) {
	    RRBEExportWindow ew = new RRBEExportWindow(window);
	    ew.pack();
	    ew.show();
	}
	else if (e.getSource() == fileNew) {
	    RRBENewBoardWindow nb = new RRBENewBoardWindow(window);
	    nb.pack();
	    nb.show();
	}
	else if (e.getSource() == fileLoad) {
	    window.load();
	}
	else if (e.getSource() == fileSave) {
	    window.save();
	}
	else if (e.getSource() == fileSaveAs) {
	    window.saveAs();
	}
	else if (e.getSource() == fileExit) {
	    window.exit();
	}
	else if (e.getSource() == helpAbout) {
	    window.showAbout();
	} 
	else if (e.getSource() == viewZoomIn) {
	    window.getBoard().zoom((float) 2);
	    window.repaintAll();
	} 
	else if (e.getSource() == viewZoomOut) {
	    window.getBoard().zoom((float) 0.5);
	    window.repaintAll();
	} 
	else if (e.getSource() == addTheme) {
	    String newPath;
	    newPath = JOptionPane
		.showInputDialog(window, "Enter the path to the theme");
	    if (!RRBEThemeReader.readThemeName(newPath)
		.equals(RRBEThemeReader.NONAME)) {
		RRBEGlobal.addTheme(newPath);
		RRBEGlobal.writeIniFile();
	    }
	}
	else if (e.getSource() == filePreferences) {
	    window.showSettings();
	} else {
	    for (int i = 0; i < factories.size(); i++) {
		ToolMenuItem ti = (ToolMenuItem) factories.get(i);
		if (e.getSource() == ti) {
		    window.setElementFactory(ti.factory);
		}
	    }
	}
    }

    private class ThemeSelector implements ActionListener {

	private RRBEMainWindow window;
	private RRBETheme theme;

	public ThemeSelector(RRBEMainWindow w, RRBETheme theme) {
	    window = w;
	    this.theme = theme;
	}

	public void actionPerformed(ActionEvent e) {
	    RRBEGlobal.curthemepath = theme.getPath();
	    window.changeTheme();
	}
    }

    private class ToolMenuItem extends JRadioButtonMenuItem {
	public RRBEElementFactory factory;
	public ToolMenuItem(RRBEElementFactory factory) {
	    super (factory.name, factory.getSmallIcon());
	    this.factory = factory;
	}

    }

}
