import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Export window
 */
public class RRBEExportWindow extends JFrame implements ActionListener {
    RRBEMainWindow window;

    JTextField fileName;
    JSpinner resolution;
    JButton ok;
    JButton cancel;
    JCheckBox partition;

    /**
     * Construct an export window connected to window w
     */
    public RRBEExportWindow(RRBEMainWindow w) {
	Container c = getContentPane();
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	ok = new JButton("OK");
	cancel = new JButton("Cancel");
	partition = new JCheckBox("Partition images");
	cancel.addActionListener(this);
	ok.addActionListener(this);

	window = w;

	setTitle("Export board to PNG");
	
	fileName = new JTextField(30);
	resolution = new JSpinner(new SpinnerNumberModel(RRBEGlobal
							 .imageResolution
							 ,1,2048,1));

	p1.setLayout(new FlowLayout());
	p1.add(new JLabel("Export filename: "));
	p1.add(fileName);

	p2.setLayout(new FlowLayout());
	p2.add(new JLabel("Image resolution"));
	p2.add(resolution);
	p2.add(new JLabel("dpi"));
	p2.add(partition);

	p3.add(ok);
	p3.add(cancel);
	
	c.setLayout(new BorderLayout());
	c.add(p1, BorderLayout.NORTH);
	c.add(p2, BorderLayout.CENTER);
	c.add(p3, BorderLayout.SOUTH);
	
    }   

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == ok) {
	    int width = window.getBoard().width();
	    int height = window.getBoard().height();
	    RRBEExportPartition part = new
		RRBEExportPartition(width,
				    height);
	    if (partition.isSelected()) {
		for (int i = 8; i < width; i += 8)
		    part.addXDivisor(i);
		for (int i = 10; i < height; i += 10)
		    part.addYDivisor(i);
	    }
	    String name = fileName.getText();
	    int res = ((Integer)resolution.getValue()).intValue();
	    if (name != ""
		&& res > 0) {
		RRBEGlobal.imageResolution = res;
		window.exportToPNG(name,part);
		this.hide();
	    }
	} else if (e.getSource() == cancel)
	    this.hide();
    }
}

