import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The settings window of this program
 */
public class RRBESettingsWindow extends JFrame
    implements ActionListener{

    RRBEMainWindow window;
    JButton ok;
    JButton cancel;
    JButton apply;
    
    JTextField curThemePath;
    JTextField graphicsPath;
    JSpinner exportResolution;

    JSpinner toolButtonIconWidth;
    JSpinner toolButtonIconHeight;
    JSpinner smallToolButtonIconWidth;
    JSpinner smallToolButtonIconHeight;
    JSpinner tinyToolButtonIconWidth;
    JSpinner tinyToolButtonIconHeight;
    JSpinner stdBoardWidth;
    JSpinner stdBoardHeight;


    public RRBESettingsWindow(RRBEMainWindow window) {
	this.window = window;
	setTitle("Preferences");

	setSize(250,230);

	Container c = getContentPane();
	c.setLayout(new BorderLayout());

	JTabbedPane tabs = new JTabbedPane();

	c.add(tabs, BorderLayout.CENTER);

	Container buttons = new Container();
	buttons.setLayout(new FlowLayout());
	ok = new JButton("OK");
	ok.addActionListener(this);
	buttons.add(ok);
	apply = new JButton("Apply");
	apply.addActionListener(this);
	buttons.add(apply);
	cancel = new JButton("Cancel");
	cancel.addActionListener(this);
	buttons.add(cancel);
	c.add(buttons, BorderLayout.SOUTH);

	JPanel paths = new JPanel();
	paths.setLayout(new GridLayout(4,1));
	tabs.add("Paths", paths);
	paths.add(new JLabel("Default theme path"));
	curThemePath = new JTextField(RRBEGlobal.curthemepath);
	paths.add(curThemePath);
	paths.add(new JLabel("Graphics path"));
	graphicsPath = new JTextField(RRBEGlobal.graphicspath);
	paths.add(graphicsPath);

	JPanel export = new JPanel();
	export.setLayout(new GridLayout(4,1));
	exportResolution = new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .imageResolution
							 ,1,2048,1));
	export.add(new JLabel("Default export resolution"));
	export.add(exportResolution);
	tabs.add("Export", export);

	JPanel GUI = new JPanel();
	GUI.setLayout(new GridLayout(6,1));
	JPanel c1 = new JPanel();
	c1.setLayout(new FlowLayout());
	toolButtonIconWidth = new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .toolButtonIconWidth
							 ,1,256,1));
	c1.add(new JLabel("Tool bar button width"));
	c1.add(toolButtonIconWidth);
	GUI.add(c1);
	c1 = new JPanel();
	c1.setLayout(new FlowLayout());
	toolButtonIconHeight = new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .toolButtonIconHeight
							 ,1,256,1));
	c1.add(new JLabel("Tool bar button height"));
	c1.add(toolButtonIconHeight);
	GUI.add(c1);
	c1 = new JPanel();
	c1.setLayout(new FlowLayout());

	smallToolButtonIconWidth = new JSpinner(new 
	    SpinnerNumberModel(RRBEGlobal
			       .smallToolButtonIconWidth
			       ,1,256,1));
	c1.add(new JLabel("Small tool bar button width"));
	c1.add(smallToolButtonIconWidth);
	GUI.add(c1);
	c1 = new JPanel();
	c1.setLayout(new FlowLayout());
	smallToolButtonIconHeight = new JSpinner(new 
	    SpinnerNumberModel(RRBEGlobal
			       .smallToolButtonIconHeight
			       ,1,256,1));
	c1.add(new JLabel("Small tool bar button height"));
	c1.add(smallToolButtonIconHeight);
	GUI.add(c1);
	c1 = new JPanel();
	c1.setLayout(new FlowLayout());

	tinyToolButtonIconWidth = new JSpinner(new 
	    SpinnerNumberModel(RRBEGlobal
			       .tinyToolButtonIconWidth
			       ,1,256,1));
	c1.add(new JLabel("Tiny tool bar button width"));
	c1.add(tinyToolButtonIconWidth);
	GUI.add(c1);
	c1 = new JPanel();
	c1.setLayout(new FlowLayout());
	tinyToolButtonIconHeight = new JSpinner(new 
	    SpinnerNumberModel(RRBEGlobal
			       .tinyToolButtonIconHeight
			       ,1,256,1));
	c1.add(new JLabel("Tiny tool bar button height"));
	c1.add(tinyToolButtonIconHeight);
	GUI.add(c1);

	tabs.add("GUI", GUI);

	JPanel board = new JPanel();
	paths.setLayout(new GridLayout(4,1));	
	stdBoardWidth= new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .stdBoardWidth
							 ,1,256,1));
	board.add(new JLabel("Default board width"));
	board.add(stdBoardWidth);
	stdBoardHeight = new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .stdBoardHeight
							 ,1,256,1));
	board.add(new JLabel("Default board height"));
	board.add(stdBoardHeight);

	tabs.add("Board", board);

    }

    private void apply() {

	// set all values

	RRBEGlobal.curthemepath = curThemePath.getText();
	RRBEGlobal.graphicspath = graphicsPath.getText();
	RRBEGlobal.imageResolution = ((Integer)exportResolution.getValue())
	    .intValue();
	
	RRBEGlobal.toolButtonIconWidth = ((Integer)toolButtonIconWidth
					  .getValue()).intValue();
	RRBEGlobal.toolButtonIconHeight = 
	    ((Integer)toolButtonIconHeight.getValue()).intValue();
	RRBEGlobal.smallToolButtonIconWidth = 
	    ((Integer)smallToolButtonIconWidth.getValue()).intValue();
	RRBEGlobal.smallToolButtonIconHeight = 
	    ((Integer)smallToolButtonIconHeight.getValue()).intValue();
	RRBEGlobal.tinyToolButtonIconWidth = 
	    ((Integer)tinyToolButtonIconWidth.getValue()).intValue();
	RRBEGlobal.tinyToolButtonIconHeight = 
	    ((Integer)tinyToolButtonIconHeight.getValue()).intValue();
	RRBEGlobal.stdBoardWidth = 
	    ((Integer)stdBoardWidth.getValue()).intValue();
	RRBEGlobal.stdBoardHeight = 
	    ((Integer)stdBoardHeight.getValue()).intValue();
	
	RRBEGlobal.writeIniFile();
    }

    private void ok() {
	apply();
	this.hide();
    }
    
    private void cancel() {
	this.hide();
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == ok) 
	    ok();
	else if (e.getSource() == cancel)
	    cancel();
	else if (e.getSource() == apply)
	    apply();
    }
}
