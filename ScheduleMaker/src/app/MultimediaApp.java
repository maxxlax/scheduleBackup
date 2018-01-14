package app;

/**
 * The requirements of a multimedia Applet/application
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software © 2011"
 * @version 1.0
 */
public interface MultimediaApp
{
    /**
     * Called to indicate that this MultimediaApp should destroy any 
     * resources that it has allocated
     */
    public abstract void destroy();
    
    /**
     * Called to indicate that this MultimediaApp has been loaded
     */
    public abstract void init();

    /**
     * Set the MultimediaRootPaneContainer for the MultimediaApp
     *
     * In most cases, the MultimediaRootPaneContainer will be either
     * a MultimediaApplication or a MultimediaApplet
     *
     * @param container   The RootPaneContainer for this MultimediaApp
     */
    public abstract void setMultimediaRootPaneContainer(
                        MultimediaRootPaneContainer container);    

    /**
     * Called to indicate that this MultimediaApp has been started
     */
    public abstract void start();
    
    /**
     * Called to indicate that this MultimediaApp has been stopped
     */
    public abstract void stop();

    public abstract boolean saveShit();    
}
