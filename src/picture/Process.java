package picture;

import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import picture.Picture.Pixel;


public class Process {

    public enum Transformation {
        INVERT,
        GRAYSCALE,
        ROTATE,
        FLIP,
        BLUR
    }

    public enum Angle {
        _90, _180, _270
    }

    public enum Direction {
        H, V
    }

    public static Picture invert(Picture picture) {
        Picture newPicture = Utils.createPicture(picture.getWidth(), picture.getHeight());

        for (Pixel pixel : picture) {
            pixel.color.invert();
            newPicture.setPixel(pixel.x, pixel.y, pixel.color);
        }

        return newPicture;
    }

    public static Picture grayscale(Picture picture) {
        Picture newPicture = Utils.createPicture(picture.getWidth(), picture.getHeight());

        for (Pixel pixel : picture) {
            pixel.color.normalise();
            newPicture.setPixel(pixel.x, pixel.y, pixel.color);
        }

        return newPicture;
    }

    public static Picture rotate(Picture picture, Angle angle) {
        Picture newPicture;
        if (angle == Angle._180) {
            newPicture = Utils.createPicture(picture.getWidth(), picture.getHeight());
        } else {
            newPicture = Utils.createPicture(picture.getHeight(), picture.getWidth());
        }

        int width = picture.getWidth() - 1;
        int height = picture.getHeight() - 1;

        for (Pixel pixel : picture) {
            switch (angle) {
                case _90:
                    newPicture.setPixel(pixel.y, width - pixel.x, pixel.color);
                    break;
                case _180:
                    newPicture.setPixel(width - pixel.x, height - pixel.y, pixel.color);
                    break;
                case _270:
                    newPicture.setPixel(height - pixel.y, pixel.x, pixel.color);
                    break;
                default: break;
            }
        }

        return newPicture;
    }

    public static Picture flip(Picture picture, Direction direction) {
        Picture newPicture = Utils.createPicture(picture.getWidth(), picture.getHeight());

        int width = picture.getWidth() - 1;
        int height = picture.getHeight() - 1;

        for (Pixel pixel : picture) {
            switch (direction) {
                case H:
                    newPicture.setPixel(width - pixel.x, pixel.y, pixel.color);
                    break;
                case V:
                    newPicture.setPixel(pixel.x, height - pixel.y, pixel.color);
                    break;
                default: break;
            }
        }

        return newPicture;
    }

    public static Picture blur(Picture picture) {
        Picture newPicture = Utils.createPicture(picture.getWidth(), picture.getHeight());

        for (Pixel pixel : picture) {
            boolean hasNeighbours = pixel.x > 0 && 
                                    pixel.y > 0 && 
                                    pixel.x < picture.getWidth() - 1 &&
                                    pixel.y < picture.getHeight() - 1;

            if (hasNeighbours) {
                newPicture.setPixel(pixel.x, pixel.y, averageNeighbours(picture, pixel.x, pixel.y));
            } else {
                newPicture.setPixel(pixel.x, pixel.y, pixel.color);
            }
        }

        return newPicture;
    }

    private static Color averageNeighbours(Picture picture, int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (int x_ = x - 1; x_ <= x + 1; x_++) {
            for (int y_ = y - 1; y_ <= y + 1; y_++) {
                Color color = picture.getPixel(x_, y_);
                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
            }
        }
        return new Color(red / 9, green / 9, blue / 9);
    }

}
