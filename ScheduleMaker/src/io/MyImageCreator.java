package io;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class MyImageCreator
{
  public static final int ICON_WIDTH = 10;
  public static final int ICON_HEIGHT = 10;
  
  public static BufferedImage getBufferedImage(String fileName)
  {
    BufferedImage bi = null;
    try
    {
      bi = ImageIO.read(new File(fileName));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return bi;
  }

  public static BufferedImage getResizedBufferedImage(String fileName, int width, int height)
  {
    BufferedImage image = getBufferedImage(fileName);
    //image = createCompatibleImage(image);
    image = resize(image, 100, 100);
    image = blurBI(image);
    image = resize(image, width, height);
    return image;
  }
  /*private BufferedImage createCompatibleImage(BufferedImage image)
  {
    GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
    int w = image.getWidth();
    int h = image.getHeight();
    BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
    Graphics2D g2 = result.createGraphics();
    g2.drawRenderedImage(image, null);
    g2.dispose();
    return result;
  }*/

  private static BufferedImage resize(BufferedImage image, int width, int height)
  {
    int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
    BufferedImage resizedImage = new BufferedImage(width, height, type);
    Graphics2D g = resizedImage.createGraphics();
    g.setComposite(AlphaComposite.Src);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.drawImage(image, 0, 0, width, height, null);
    g.dispose();
    return resizedImage;
  }

  private static BufferedImage blurBI(BufferedImage bi)
  {
    float ninth = 1.0f/9.0f;
    float[] blurKernel = {
        ninth, ninth, ninth,
        ninth, ninth, ninth,
        ninth, ninth, ninth
    };

    Map<Key, Object> map = new HashMap<Key, Object>();

    map.put(RenderingHints.KEY_INTERPOLATION,
    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    RenderingHints hints = new RenderingHints(map);
    BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
    return op.filter(bi, null);
  }
}
