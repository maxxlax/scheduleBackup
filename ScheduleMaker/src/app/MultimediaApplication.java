package app;
import java.util.*;

/**
 * A MultimediaApplication is a JApplication that delegates all calls
 * to "transition" methods to a MultimediaApp.  The calls
 * to the MultimediaApp object's "transition" methods will
 * be made in the event dispatch thread.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software © 2011"
 * @version 1.0
 */
public abstract class      MultimediaApplication
                extends    JApplication
                implements MultimediaRootPaneContainer
{
    private   MultimediaApp app;
    private   Properties    params;

    /**
     * Explicit Value Constructor
     *
     * @param args    The command-line aguments
     * @param app     The MultimediaApp to delegate to
     * @param width   The width (in pixels) of the main window
     * @param height  The width (in pixels) of the main window
     */
    public MultimediaApplication(String[] args,
                                 MultimediaApp app, 
                                 int width, int height)
    {
       super(width, height);
       
       this.app    = app;
       app.setMultimediaRootPaneContainer(this);       

       params      = new Properties();
       for (int i=0; i<args.length; i++)
       {
          params.put(Integer.toString(i), args[i]);          
       }
    }

    /**
     * This method is called just after the main window is
     * disposed.
     *
     * The default implementation does nothing.
     */
    public void destroy()
    {
       app.destroy();       
    }

    /**
     * Get a reference to the MultimediaApp that is
     * being delegated to
     *
     * @return  The MultimediaApp
     */
    protected MultimediaApp getMultimediaApp()
    {
       return app;       
    }
    
    /**
     * Returns the value of the "named" parameter
     * (required by MultimediaRootPaneContainer)
     *
     * @param name  The name/index of the parameter
     */
    public String getParameter(String name)
    {
       return params.getProperty(name);       
    }

    /**
     * This method is called just before the main window
     * is first made visible
     */
    public void init()
    {
       app.init();       
    }

    /**
     * This method is called when the main window is first
     * made visible and then each time it is de-iconified.
     *
     * The default implementation does nothing.
     */
    public void start()
    {
       app.start();       
    }

    /**
     * This method is called each time the main window is iconified
     * and just before it is disposed.
     *
     * The default implementation does nothing.
     */
    public void stop()
    {
       app.stop();       
    }
    
    
}
