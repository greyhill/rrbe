import java.io.*;

/**
 * A class for reading text files.
 * Returning lines delimited by a delimiter.
 */ 
public class RRBEFileReader {

    private String bufferLeft;
    private BufferedReader in;
    private char delimiter;
    private boolean whiteSpace;

    /**
     * Create a RRBE File Reader from file wicth name fileName.
     * Tokens will be delimited by delimiter.
     * If whiteSpace is set, all line breaks, feeds and tabs will be replaced
     * by space.
     */
    public RRBEFileReader(String fileName, char delimiter,
			  boolean whiteSpace) {
	bufferLeft = new String("");
	this.delimiter = delimiter;
	this.whiteSpace = whiteSpace;
	try {
	    in = new BufferedReader(new FileReader(fileName));
	} catch (IOException e) {
	}
    }

    /**
     * Change the delimiter
     */
    public void setDelimiter(char delimiter) {
	this.delimiter = delimiter;
    }

    private String whiteSpaceFixer(String s) {

	s = s.replace('\n', ' ');
	s = s.replace('\t', ' ');
	s = s.replace('\r', ' ');
	
	return s;
    }

    /**
     * Returns the next token from the file.
     * Reads if neceseary and fixes whitespace if told to.
     */
    public String nextToken() {
	int pos = bufferLeft.indexOf(delimiter);
	String read = " ";

	try {
	    while (pos < 0 && read != null) {
		read = in.readLine();
		if (read != null) { 
		    bufferLeft += " " + read;
		    pos = bufferLeft.indexOf(delimiter);
		} else {
		    pos = bufferLeft.length()-1;
		}

	    }
	} catch (IOException e) {
	    pos = bufferLeft.length()-1;
	}
	
	String retVal = bufferLeft.substring(0,pos);
	bufferLeft = bufferLeft.substring(pos+1);

	if (whiteSpace)
	    retVal = whiteSpaceFixer(retVal);

	return retVal;
    }

    /**
     * Returns true if there is more to be read
     * False otherwise
     */
    public boolean hasMoreTokens() {
	try {
	    return in.ready();
	} catch (IOException e) {
	}
	
	return false;
    }
}
