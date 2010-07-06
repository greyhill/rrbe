import java.util.*;

public class RRBEExportPartition {

    private Vector xDivisors;
    private Vector yDivisors;
    private int width;
    private int height;

    public RRBEExportPartition(int width, int height) {
	xDivisors = new Vector();
	yDivisors = new Vector();
	addXDivisor(0);
	addXDivisor(width);
	addYDivisor(0);
	addYDivisor(height);
	this.width = width;
	this.height = height;
    }

    /**
     * Add a divisor on X = x
     */
    public void addXDivisor(int x) {
	xDivisors.add(new Integer(x));
	sort(xDivisors);
    }
    
    /**
     * Add a divisor on Y = y
     */
    public void addYDivisor(int y) {
	yDivisors.add(new Integer(y));
	sort(yDivisors);
    }

    /**
     * Return the number of partitions
     */
    public int countPartitions() {
	return (xDivisors.size() - 1) * (yDivisors.size() - 1);
    }

    private void sort(Vector v) {
	Object o;
	for (int i = 0; i < v.size(); i++)
	    for (int  j = 0 ; j < v.size()-1; j++)
		if (((Integer) v.get(j)).compareTo((Integer) v.get(j+1)) > 0) {
		    o = v.get(j);
		    v.set(j, v.get(j+1));
		    v.set(j+1, o);
		}
	    
    }

    /**
     * Return partion no n
     */
    public int[] getPartition(int n) {

	int[] ret = new int[4];
	int xs = xDivisors.size()-1;
	int ys = yDivisors.size();
	ret[0] = ((Integer) xDivisors.get(n % xs)).intValue();
	ret[1] = ((Integer) yDivisors.get(n / xs)).intValue();
	ret[2] = ((Integer) xDivisors.get((n % xs)+1)).intValue();
	ret[3] = ((Integer) yDivisors.get((n / xs)+1)).intValue();

	return ret;
    }
    
}
