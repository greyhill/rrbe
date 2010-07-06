import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class RRBENewBoardWindow extends JFrame implements ActionListener {

    RRBEMainWindow window;
    JSpinner width;
    JSpinner height;
    JButton ok;
    JButton cancel;

    /**
     * Construct a new board window
     */
    public RRBENewBoardWindow(RRBEMainWindow w) {
	window = w;

	setTitle("New board");
	
	JPanel heightPanel = new JPanel();
	JLabel hLabel = new JLabel("Height: ");
	JLabel wLabel = new JLabel("Width: ");
	ok = new JButton("OK");
	cancel = new JButton("Cancel");
	Container c = getContentPane();
	Container cButtons = new Container();
	width = new JSpinner(new SpinnerNumberModel(RRBEGlobal.stdBoardWidth
						    ,1,24,1));
	height = new JSpinner(new SpinnerNumberModel(RRBEGlobal.stdBoardHeight
						     ,1,24,1));

	heightPanel.setLayout(new GridLayout(2,2));
	
	heightPanel.add(hLabel);
	heightPanel.add(height);
	heightPanel.add(wLabel);
	heightPanel.add(width);

	cButtons.setLayout(new FlowLayout());
	cancel.addActionListener(this);
	ok.addActionListener(this);
	cButtons.add(cancel);
	cButtons.add(ok);

	c.setLayout(new BorderLayout());
	c.add(heightPanel, BorderLayout.CENTER);
	c.add(cButtons, BorderLayout.SOUTH);
    }
    
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == ok) {
	    window.newBoard(((Integer) height.getValue()).intValue()
			    , ((Integer) width.getValue()).intValue());
	}
	this.hide();
    }
}
