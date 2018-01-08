package app;

import javax.swing.*;

/**
 * Requirements of a RootPaneContainer in a MultimediaApp
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software © 2011"
 * @version 1.0
 */
public interface MultimediaRootPaneContainer 
       extends   RootPaneContainer
{
    
    /**
     * Returns the value of the "named" parameter
     *
     * @param name  The name/index of the parameter
     */
    public abstract String getParameter(String name);
}
