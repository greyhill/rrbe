import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class RRBEToolBar extends JToolBar 
    implements MouseListener 
    ,RRBEElementChooser, RRBERotationChooser {

    RRBEMainWindow window;
    Vector buttons;
    Container arrowContainer;
    Container elementContainer;
    JButton[] arrowButtons = new JButton[4];
    ActivateChooserPanel activatePanel;
    RRBEToolButton selectedButton;

    public RRBEToolBar(RRBEMainWindow w) {
	arrowContainer = new Container();
	elementContainer = new Container();
	window = w;
	buttons = new Vector();
	activatePanel = new ActivateChooserPanel(window);

	setLayout(new BorderLayout());
	window.registerRotationChooser(this);

	arrowContainer.setLayout(new FlowLayout());
	elementContainer.setLayout(new GridLayout(0,5));

	JScrollPane elementScrollPane = new JScrollPane(elementContainer);
	elementScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	add(arrowContainer, BorderLayout.NORTH);
	add(elementScrollPane, BorderLayout.CENTER);
	add(activatePanel, BorderLayout.SOUTH);

	arrowButtons[0] = new 
	    JButton(RRBEGlobal.smallButtonIcon
		    (RRBEGlobal.graphicspath+"uparrow.png"));

	arrowButtons[1] = new 
	    JButton(RRBEGlobal.smallButtonIcon
		    (RRBEGlobal.graphicspath+"rightarrow.png"));
	arrowButtons[2] = new 
	    JButton(RRBEGlobal.smallButtonIcon
		    (RRBEGlobal.graphicspath+"downarrow.png"));
	arrowButtons[3] = new 
	    JButton(RRBEGlobal.smallButtonIcon
		    (RRBEGlobal.graphicspath+"leftarrow.png"));

	for (int i = 0; i < 4; i++) {
	    arrowContainer.add(arrowButtons[i]);
	    arrowButtons[i].addMouseListener(this);
	}
    }

    public void removeFactories() {
	elementContainer.removeAll();
	buttons.clear();
    }

    public void resetElementButtons() {
	window.createTools();
    }

    public void addElementFactory(RRBEElementFactory f) {
	RRBEToolButton b = new RRBEToolButton(f);
	b.addMouseListener(this);
	elementContainer.add(b);
	buttons.add(b);
    }

    public void updateButtonImages() {	
	for (int i = 0; i < buttons.size(); i++) {
	    RRBEToolButton b = (RRBEToolButton) buttons.get(i);
	    b.updateImage();
	}
    }

    public void setSelected(RRBEElementFactory f) {
	for (int i = 0; i < buttons.size(); i++) {
	    RRBEToolButton b = (RRBEToolButton) buttons.get(i);
	    if (b.factory == f) {
		b.updateImage();
		b.setBackground(RRBEGlobal.selectedButtonColor);
		selectedButton = b;
	    } else {
		b.setBackground(RRBEGlobal.unSelectedButtonColor);			    }
	}
    }

    public void setSelectedRotation(int r) {
	for (int i = 0 ; i < 4; i++) {
	    if (i == r) {
		if (selectedButton != null)
		    selectedButton.updateImage();
		arrowButtons[i].setBackground(RRBEGlobal.selectedButtonColor);
	    } else {
		arrowButtons[i].setBackground(RRBEGlobal
					      .unSelectedButtonColor);		
	    }
	}
	//	updateButtonImages();
    }

    public void mouseClicked(MouseEvent e) {
	boolean arrow = false;
                     
	for (int i = 0; i < 4; i++)
	    if (e.getSource() == arrowButtons[i]) {
		arrow = true;
		window.setRotation(i);
	    }
	if (!arrow) {
	    RRBEToolButton sender = (RRBEToolButton) e.getSource();
	    window.setElementFactory(sender.factory);
	    if (e.getButton() == e.BUTTON1) {
		// left-click
	    }  else if (e.getButton() == e.BUTTON2) {
		// middle-click
		
	    } else if (e.getButton() == e.BUTTON3) {
		// right-click
		RightMenu rm = new RightMenu(window);
		rm.show(e.getComponent(), e.getX(), e.getY());
	    }
	}

    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
  
    private class ActivateChooserPanel extends JPanel 
	implements ActionListener, RRBEActivePhaseChooser {
	RRBEMainWindow window;
	JButton[] phases;
	boolean[] sel;
	public ActivateChooserPanel(RRBEMainWindow w) {
	    window = w;
	    phases = new JButton[RRBEGlobal.phaseCount];
	    sel = new boolean[RRBEGlobal.phaseCount];
	    Container captionContainer = new Container();
	    Container buttonContainer = new Container();
	    captionContainer.setLayout(new FlowLayout());
	    buttonContainer.setLayout(new FlowLayout());
	    
	    captionContainer.add(new JLabel("Active phases:"));
	    for (int i = 0; i < RRBEGlobal.phaseCount; i++) {
		phases[i] = new JButton(Integer.toString(i+1));
		phases[i].addActionListener(this);
		buttonContainer.add(phases[i]);
	    }
	    
	    setLayout(new BorderLayout());
	    add(captionContainer, BorderLayout.NORTH);
	    add(buttonContainer, BorderLayout.SOUTH);
	    
	}

	public void setPhase(int phase, boolean v) {
	    sel[phase] = v;
	    window.setActivePhase(phase,v);
	    if (v) 
		phases[phase].setBackground(RRBEGlobal.selectedButtonColor);
	    else
		phases[phase].setBackground(RRBEGlobal.unSelectedButtonColor);
	}

	public boolean getPhase(int phase) {
	    return sel[phase];
	}

	public void actionPerformed(ActionEvent e) {
	    for (int i = 0; i < RRBEGlobal.phaseCount; i++) {
		if (e.getSource() == phases[i])
		    setPhase(i, !getPhase(i));
	    }
	}
    }
    
    private class RightMenu extends JPopupMenu implements ActionListener{
	RRBEMainWindow window;
	JMenuItem all;
	JMenuItem other;

	public RightMenu(RRBEMainWindow w) {
	    window = w;
	    all = new JMenuItem("Place on all squares");
	    all.addActionListener(this);
	    add(all);
	    other = new JMenuItem("Place on all square, random orientation");
	    other.addActionListener(this);
	    add(other);
	}
	
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == all) {
		RRBEElementFactory f = window.getElementFactory();
		RRBEBoard b = window.getBoard();
		if (f != null) {
		    for (int y = 0; y < b.height(); y++)
			for (int x = 0; x < b.width(); x++) {
			    RRBESquare s = b.getSquare(x,y);
			    f.activate(s);
			}
		    window.repaint();
		}
	    } else if (e.getSource() == other) {
		RRBEElementFactory f = window.getElementFactory();
		RRBEBoard b = window.getBoard();
		int r = window.currentRotation();
		if (f != null) {
		    for (int y = 0; y < b.height(); y++)
			for (int x = 0; x < b.width(); x++) {
			    window.setRotation((int) (Math.random() * 4.0), false);
			    RRBESquare s = b.getSquare(x,y);
			    f.activate(s);
			}
		    window.repaint();
		}
		window.setRotation(r);
	    }
	}
    }
    
    private class RRBEToolButton extends JButton{
	public RRBEElementFactory factory;
	public RRBEToolButton(RRBEElementFactory f) {
	    factory = f;
	    setIcon(f.getToolBarIcon());
	    setToolTipText(f.name);
	}

	public void updateImage() {
	    setIcon(factory.getToolBarIcon());	    
	}
    }
}
