package ldjp.jassistant.util;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 *
 */

public class ImageManager implements Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public static ImageIcon createImageIcon(String filename) {
	    String dirPath = "/ldjp/jassistant/util/resource/images/";
	    return createImageIcon(dirPath, filename);
    }

    public static ImageIcon createImageIcon(String dirName, String filename) {
	    String path = dirName + filename;
	    return new ImageIcon(ImageManager.class.getResource(path));
    }
}
