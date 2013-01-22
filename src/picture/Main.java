package picture;

import picture.Process.*;

import java.util.Arrays;

public class Main {

    public static final String NOT_ENOUGH_ARGS = 
        "You did not supply enough arguments for the specified transformation.\n" +
        "Arguments should be of the form:\n" +
        "<transformation> <optional transformation argument> <image to load> <image to save>";
    public static final String INCORRECT_ARG = 
        "The optional transformation argument you supplied was not valid. Possible arguments:\n" +
        "rotation: 90 180 270\n" +
        "flip: H V";
    public static final String SAVE_ERROR = 
        "The transformed image could not be saved.";


    public static void main(String[] args) {
        try {
            Process.Transformation transformation = Process.Transformation.valueOf(args[0].toUpperCase());

            Picture picture = Utils.loadPicture(args[args.length - 2]);
            Picture transformedPicture = null;

            switch (transformation) {
                case INVERT:
                    transformedPicture = Process.invert(picture);
                    break;
                case GRAYSCALE:
                    transformedPicture = Process.grayscale(picture);
                    break;
                case ROTATE:
                    transformedPicture = Process.rotate(picture, Process.Angle.valueOf("_" + args[1]));
                    break;
                case FLIP:
                    transformedPicture = Process.flip(picture, Process.Direction.valueOf(args[1].toUpperCase()));
                    break;
                case BLUR:
                    transformedPicture = Process.blur(picture);
                    break;
                case BLEND:
                    Picture[] pictures = new Picture[args.length - 2];
                    for (int i = 1; i < args.length - 1; i++) {
                        pictures[i-1] = Utils.loadPicture(args[i]);
                    }
                    transformedPicture = Process.blend(pictures);
                    break;
                default: break;
            }

            if (!Utils.savePicture(transformedPicture, args[args.length-1])) {
                System.out.println(SAVE_ERROR);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(NOT_ENOUGH_ARGS);
        } catch (IllegalArgumentException e) {
            System.out.println(INCORRECT_ARG);
        }
    }
}
