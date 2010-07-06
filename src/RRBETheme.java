public class RRBETheme {
    private String path;
    private String name;

    public RRBETheme (String path, String name) {
	this.path = path;
	this.name = name;
    }

    public String toString() {
	return name;
    }

    public String getPath() {
	return path;
    }
}
