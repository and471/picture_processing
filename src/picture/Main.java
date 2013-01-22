package picture;

import picture.Process.*;

import java.util.Arrays;

public class Main {

    public static final String NOT_ENOUGH_ARGS = 
        "You did not give a valid input. Input should be of the form:\n" +
        "<transformation> <arguments> <image(s) to load> <image to save>";
    public static final String INCORRECT_ARG = 
        "The argument you supplied was not valid. Possible arguments:\n" +
        "rotation: 90 180 270\n" +
        "flip: H V";
    public static final String SAVE_ERROR = 
        "The transformed image could not be saved.";


    public static void main(String[] args) {
            Process.Transformation transformation = 
                Process.Transformation.valueOf(args[0].toUpperCase());

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
                    transformedPicture = Process.rotate(picture, 
                        Process.Angle.valueOf("_" + args[1]));
                    break;
                case FLIP:
                    transformedPicture = Process.flip(picture, 
                        Process.Direction.valueOf(args[1].toUpperCase()));
                    break;
                case BLUR:
                    transformedPicture = Process.blur(picture);
                    break;
                case BLEND:
                    Picture[] blendPictures = new Picture[args.length - 2];
                    for (int i = 1; i < args.length - 1; i++) {
                        blendPictures[i-1] = Utils.loadPicture(args[i]);
                    }
                    transformedPicture = Process.blend(blendPictures);
                    break;
                case MOSAIC:
                    Picture[] mosaicPictures = new Picture[args.length - 3];
                    for (int i = 2; i < args.length - 1; i++) {
                        mosaicPictures[i-2] = Utils.loadPicture(args[i]);
                    }
                    transformedPicture = Process.mosaic(
                        Integer.parseInt(args[1]), mosaicPictures);
                    break;
                default: break;
            }

            if (!Utils.savePicture(transformedPicture, args[args.length - 1])) {
                System.out.println(SAVE_ERROR);
            }

    }
}
