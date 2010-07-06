/**
 * Interface for components that chooses active phases
 */
public interface RRBEActivePhaseChooser {

    /**
     * Called when another phase chooser changes an active phase
     * to update this chooser.
     */
    public void setPhase(int phase, boolean v);

    /**
     * Returns true if this chooser concidered phase to be active
     */
    public boolean getPhase(int phase);
}
