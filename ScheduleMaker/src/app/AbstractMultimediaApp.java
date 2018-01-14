package app;

/**
 * An abstract implementation of the MultimediaApp interface
 * that includes empty implementations of the "transition"
 * methods
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software © 2011"
 * @version 1.0
 */
public abstract class      AbstractMultimediaApp 
                implements MultimediaApp
{
    protected MultimediaRootPaneContainer rootPaneContainer;

    /**
     * Called to indicate that this MultimediaApp should destroy any 
     * resources that it has allocated
     */
    public void destroy()
    {
    }
    
    /**
     * Called to indicate that this MultimediaApp has been loaded
     */
    public void init()
    {
    }
    
    /**
     * Set the MultimediaRootPaneContainer for the MultimediaApp
     *
     * In most cases, the MultimediaRootPaneContainer will be either
     * a MultimediaApplication or a MultimediaApplet
     *
     * @param container   The RootPaneContainer for this MultimediaApp
     */
    public void setMultimediaRootPaneContainer(
                             MultimediaRootPaneContainer container)
    {
       rootPaneContainer = container;
    }
    
    /**
     * Called to indicate that this MultimediaApp has been started
     */
    public void start()
    {
    }
    
    /**
     * Called to indicate that this MultimediaApp has been stopped
     */
    public void stop()
    {
    }
    
    @Override
    public boolean saveShit()
    {
      return false;
    }
}
