import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class RRBETwoLevelScaleBaseElement extends RRBEElement {
    
    public RRBETwoLevelScaleBaseElement(String typeId
					,String n,int l, ImageIcon[] i, int r
					, boolean requireWall
					, boolean scaleToWall) {
	super(typeId,n,l,i,r,requireWall,scaleToWall);
    }


    public void drawTo(Graphics g, int x, int y
		       , int w, int h
		       , int ox, int oy, int ow, int oh
		       , ImageObserver i) {
	g.drawImage(image[rotation].getImage(),
		    x,y,w,h,0,0
		    ,image[rotation].getIconWidth()
		    ,image[rotation].getIconHeight()
		    , i);
	g.drawImage(image[rotation+4].getImage(),
		    ox,oy,ow,oh,0,0
		    ,image[rotation+4].getIconWidth()
		    ,image[rotation+4].getIconHeight()
		    , i);

    }

    public ImageIcon getImageIcon() {
	return RRBEGlobal.combineImages(image[rotation],
				       image[rotation+4]);
    }

    public Image getImage() {
	return getImageIcon().getImage();
    }  

    public ImageIcon getTinyIcon() {
	return new ImageIcon(getImage()
			     .getScaledInstance(RRBEGlobal
						.tinyToolButtonIconWidth
						,RRBEGlobal
						.tinyToolButtonIconHeight
						,Image.SCALE_SMOOTH));
    }

    public ImageIcon getSmallIcon() {
	return new ImageIcon(getImage()
			     .getScaledInstance(RRBEGlobal.smallToolButtonIconWidth
						,RRBEGlobal
						.smallToolButtonIconHeight
						,Image.SCALE_SMOOTH));
    }   
}

