import java.awt.*;

/**
 * Robo Rally Board Editor
 */
public class RRBE {

    public static void main(String[] Args) {
	RRBEGlobal.readIniFile();
	RRBEMainWindow mw = new RRBEMainWindow();
	mw.changeTheme();
	mw.createTools();
	mw.setSize(new Dimension(800,600));
	mw.show();
    }
    
}
