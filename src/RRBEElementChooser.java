/**
 * An interface for element choosers
 * tool bars etc.
 */
public interface RRBEElementChooser {
    /**
     * Add an element factry to the chooser.
     * Called from Main window when a new factory
     * is added.
     */
    public void addElementFactory(RRBEElementFactory f);

    /**
     * Called when a factory is selected elsewhere
     * and the chooser should know this.
     */
    public void setSelected(RRBEElementFactory f);

    /**
     * Clear the chooser from factories
     */
    public void removeFactories();
}
