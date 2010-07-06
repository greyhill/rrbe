import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RRBEThemeElementChooser extends JFrame 
    implements ActionListener {

    private RRBEMainWindow window;
    private boolean active;
    private static RRBEThemeElementChooser instance;
    private static boolean initiated = false;
    private RRBEElementFactory selectedElement;
    private JList destination;

    public RRBEThemeElementChooser(RRBEMainWindow window) {
	this.window = window;

	setTitle("Error in theme conversion");

	destination = new JList();
	getContentPane().add(destination);
    }

    public void actionPerformed(ActionEvent e) {
	
    }

    public RRBEElementFactory getConversion(String elementName) {
	return null;
    }

    public RRBEElementFactory activate(String elementName) {
	active = true;

	this.show();
	Object[] f = window.getElementFactories();
	
	for (int i = 0; i < f.length; i++) {
	    //destination.add((RRBEElementFactory) f[i]);
	}

	while(active)
	    try {Thread.sleep(1);} catch (Exception e) {}
	this.hide();
	return selectedElement;
    }

    public static RRBEElementFactory getConverted(RRBEMainWindow window
						  , String elementName) {
	if (!initiated) {
	    instance = new RRBEThemeElementChooser(window);
	    initiated = true;
	}

	RRBEElementFactory ret = instance.activate(elementName);

	if (ret == null) {
	    ret = instance.activate(elementName);
	}

	return ret;
    }
}








