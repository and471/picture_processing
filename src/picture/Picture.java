package picture;

import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * A class that encapsulates and provides a simplified interface for
 * manipulating an image. The internal representation of the image is based on
 * the RGB direct colour model. Refer to <tt>picture.PictureTool</tt> for
 * information on how to create instances of this class.
 * 
 * @see picture.Utils PictureTool
 */
public class Picture implements Iterator<Picture.Pixel>, Iterable<Picture.Pixel>
{

    public class Pixel {
        public final int x, y;
        public final Color color;

        public Pixel(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    /** The internal image representation of this picture. */
    private final BufferedImage image;

    private Pixel currentPixel;

    /**
     * Construct a new Picture object from the specified image.
     * 
     * @param image
     *                    the internal representation of the image.
     */
    protected Picture(BufferedImage image) {
        this.image = image;
    }

    /**
     * Return the internal image represented by the Picture.
     * 
     * @return the <tt>BufferedImage</tt> associated with this <tt>Picture</tt>.
     */
    protected BufferedImage getImage() {
        return image;
    }

    /**
     * Return the width of the <tt>Picture</tt>.
     * 
     * @return the width of this <tt>Picture</tt>.
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Return the height of the <tt>Picture</tt>.
     * 
     * @return the height of this <tt>Picture</tt>.
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Return the colour components (red, green, then blue) of the pixel-value
     * located at (x,y).
     * 
     * @param x
     *                    x-coordinate of the pixel value to return
     * @param y
     *                    y-coordinate of the pixel value to return
     * @return the RGB components of the pixel-value located at (x,y).
     * @throws ArrayIndexOutOfBoundsException
     *                     if the specified pixel-location is not contained within the
     *                     boundaries of this picture.
     */
    public Color getPixel(int x, int y) {
        int rgb = image.getRGB(x, y);
        return new Color((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
    }

    /**
     * Update the pixel-value at the specified location.
     * 
     * @param x
     *                    the x-coordinate of the pixel to be updated
     * @param y
     *                    the y-coordinate of the pixel to be updated
     * @param rgb
     *                    the RGB components of the updated pixel-value
     * @throws ArrayIndexOutOfBoundsException
     *                     if the specified pixel-location is not contained within the
     *                     boundaries of this picture.
     */
    public void setPixel(int x, int y, Color rgb) {

        image.setRGB(x, y, 0xff000000 | (((0xff & rgb.getRed()) << 16)
                | ((0xff & rgb.getGreen()) << 8) | (0xff & rgb.getBlue())));
    }

    /**
     * Test if the specified point lies within the boundaries of this picture.
     * 
     * @param x
     *                    the x co-ordinate of the point
     * @param y
     *                    the y co-ordinate of the point
     * @return <tt>true</tt> if the point lies within the boundaries of the
     *                 picture, <tt>false</tt> otherwise.
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    // Don't worry too much about the following two methods for the moment
    // They are needed for the testsuite to work.

    /**
     * Returns true if this Picture is graphically identical to the other one.
     * 
     * @param other
     *                    The other picture to compare to.
     * @return true iff this Picture is graphically identical to other.
     */
    public boolean equals(Object otherO) {
        if (otherO == null) {
            return false;
        }
        if (!(otherO instanceof Picture)) {
            return false;
        }

        Picture other = (Picture) otherO;

        if (image == null || other.image == null) {
            return image == other.image;
        }
        if (image.getWidth() != other.image.getWidth()
                || image.getHeight() != other.image.getHeight()) {
            return false;
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) != other.image.getRGB(i, j)) {
                    return false;
                }

            }
        }
        return true;
    }

    public int hashCode() {
        if (image == null) {
            return -1;
        }
        int hashCode = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                hashCode = 31 * hashCode + image.getRGB(i, j);
            }
        }
        return hashCode;
    }


    /* Gets the color values of a group of pixels (tile) */
    public Color[] getTile(int startX, int startY, int tileSize) {
        Color[] colors = new Color[tileSize * tileSize];
        int i = 0;
        for (int x = startX; x < startX + tileSize; x++) {
            for (int y = startY; y < startY + tileSize; y++) {
                colors[i] = getPixel(x, y);
                i++;
            }
        }
        return colors;
    }

    /* Sets the color values of a group of pixels (tile) */
    public void setTile(int startX, int startY, int tileSize, Color[] colors) {
        int i = 0;
        for (int x = startX; x < startX + tileSize; x++) {
            for (int y = startY; y < startY + tileSize; y++) {
                setPixel(x, y, colors[i]);
                i++;
            }
        }
    }

    /* Iterable Methods */
    public Iterator<Pixel> iterator() {
        currentPixel = new Pixel(-1, 0, null);
        return this;
    }

    /* Iterator Methods */
    @Override
    public boolean hasNext() {
        return !((currentPixel.x == getWidth() - 1) &&
                 (currentPixel.y == getHeight() - 1));
    }

    @Override
    public Pixel next() {
        int x = currentPixel.x + 1;
        int y = currentPixel.y;
        if (currentPixel.x == getWidth() - 1) {
            x = 0;
            y++;
        }

        currentPixel = new Pixel(x, y, getPixel(x, y));
        return currentPixel;
    }

    @Override
    public void remove() {}

}
