package app;

import java.awt.*;
import javax.swing.*;

/**
 * A MultimediaApplet is a JApplet that delegates all calls
 * to "transition" methods to a MultimediaApp.  The calls
 * to the MultimediaApp object's "transition" methods will
 * be made in the event dispatch thread.
 *
 * Note: The browser/appletviewer is not supposed to call the
 * "transition" methods in the event dispatch thread.  This class
 * checks to make sure [since SwingUtilities.invokeAndWait() must not
 * be called in the event dispatch thread].  While there is a small
 * performance penalty incurred as a result, the "transition" methods
 * are not called frequently enough for this to matter.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software © 2011"
 * @version 1.0
 */
public abstract class      MultimediaApplet 
                extends    JApplet
                implements MultimediaRootPaneContainer
{
    private MultimediaApp          app;
    
    /**
     * Explicit Value Constructor
     *
     * @param app   The MultimediaApp to delegate to
     */
    public MultimediaApplet(MultimediaApp app)
    {
       super();
       this.app = app;
       setLayout(null);       
       app.setMultimediaRootPaneContainer(this);       
    }
    
    /**
     * Called to indicate that this Applet should destroy any 
     * resources that it has allocated
     */
    public void destroy()
    {
       if (SwingUtilities.isEventDispatchThread()) app.destroy();          
       else
       {
          try {SwingUtilities.invokeAndWait(new DestroyRunnable());}
          catch (Exception e) {}
       }
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
     * Called to indicate that this Applet has been
     * loaded
     */
    public void init()
    {
       if (SwingUtilities.isEventDispatchThread()) app.init();          
       else
       {
          try {SwingUtilities.invokeAndWait(new InitRunnable());}
          catch (Exception e) {e.printStackTrace();}
       }
    }

    /**
     * Called to indicate that this Applet has been
     * started
     */
    public void start()
    {
       if (SwingUtilities.isEventDispatchThread()) app.start();          
       else
       {
          try {SwingUtilities.invokeAndWait(new StartRunnable());}
          catch (Exception e) {e.printStackTrace();}
       }
    }

    /**
     * Called to indicate that this Applet has been
     * stopped
     */
    public void stop()
    {
       if (SwingUtilities.isEventDispatchThread()) app.stop();          
       else
       {
          try {SwingUtilities.invokeAndWait(new StopRunnable());}
          catch (Exception e) {e.printStackTrace();}
       }
    }

    /**
     * A Runnable that delegates to the MultimediaApp object's
     * destroy() method
     */
    private class DestroyRunnable implements Runnable
    {
        public void run()
        {
           app.destroy();           
        }        
    }

    /**
     * A Runnable that delegates to the MultimediaApp object's
     * init() method
     */
    private class InitRunnable implements Runnable
    {
        public void run()
        {
           app.init();           
        }        
    }

    /**
     * A Runnable that delegates to the MultimediaApp object's
     * start() method
     */
    private class StartRunnable implements Runnable
    {
        public void run()
        {
           app.start();           
        }        
    }

    /**
     * A Runnable that delegates to the MultimediaApp object's
     * stop() method
     */
    private class StopRunnable implements Runnable
    {
        public void run()
        {
           app.stop();           
        }        
    }
}
