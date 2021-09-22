package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class BarcodePipeline extends OpenCvPipeline
{
    /*
     * Enum to define barcode positions
     */
    public enum BarcodePosition
    {
        ONE,
        TWO,
        THREE,
        NONE
    }

    /*
     * Constants
     */
    static final Scalar BLUE = new Scalar(0, 0, 255);

    /*
     * Locations
     * This is the top left corner of the ROI box
     * Adjust for different Camera positions
     */
    static final Point BARCODE_1_TOP_LEFT = new Point(10, 275);
    static final Point BARCODE_2_TOP_LEFT = new Point(300, 275);
    static final Point BARCODE_3_TOP_LEFT = new Point(539, 250);

    /**
     * Width and Height of the ROI box
     * Don't want this to be that big as it can mess with averaging
     * when calling Core.mean later
     */
    static final int BARCODE_WIDTH = 100;
    static final int BARCODE_HEIGHT = 75;

    // Threshold to see if a object is there
    final int OBJECT_THERE_THRESHOLD = 125;

    Point barcode_1_pointA = new Point(
            BARCODE_1_TOP_LEFT.x,
            BARCODE_1_TOP_LEFT.y);
    Point barcode_1_pointB = new Point(
            BARCODE_1_TOP_LEFT.x + BARCODE_WIDTH,
            BARCODE_1_TOP_LEFT.y + BARCODE_HEIGHT);
    Point barcode_2_pointA = new Point(
            BARCODE_2_TOP_LEFT.x,
            BARCODE_2_TOP_LEFT.y);
    Point barcode_2_pointB = new Point(
            BARCODE_2_TOP_LEFT.x + BARCODE_WIDTH,
            BARCODE_2_TOP_LEFT.y + BARCODE_HEIGHT);
    Point barcode_3_pointA = new Point(
            BARCODE_3_TOP_LEFT.x,
            BARCODE_3_TOP_LEFT.y);
    Point barcode_3_pointB = new Point(
            BARCODE_3_TOP_LEFT.x + BARCODE_WIDTH,
            BARCODE_3_TOP_LEFT.y + BARCODE_HEIGHT);

    /*
     * Variables
     */
    Mat barcode_1_Cb;
    Mat barcode_2_Cb;
    Mat barcode_3_Cb;
    Mat ycrcb = new Mat();
    Mat cb = new Mat();
    int bar1;
    int bar2;
    int bar3;

    public volatile BarcodePosition position = BarcodePosition.NONE;

    @Override
    public void init(Mat firstFrame)
    {
        Imgproc.cvtColor(firstFrame, ycrcb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(ycrcb, cb, 1);
        barcode_1_Cb = cb.submat(new Rect(barcode_1_pointA, barcode_1_pointB));
        barcode_2_Cb = cb.submat(new Rect(barcode_2_pointA, barcode_2_pointB));
        barcode_3_Cb = cb.submat(new Rect(barcode_3_pointA, barcode_3_pointB));
    }

    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.cvtColor(input, ycrcb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(ycrcb, cb, 1);

        // Calculate the Cb mean of all three ROI's
        bar1 = (int) Core.mean(barcode_1_Cb).val[0];
        bar2 = (int) Core.mean(barcode_2_Cb).val[0];
        bar3 = (int) Core.mean(barcode_3_Cb).val[0];

        // These rectangles are more just for debugging and setting up locations
        Imgproc.rectangle(
                input, // Image to draw on
                barcode_1_pointA, // First point
                barcode_1_pointB, // Second point
                BLUE, // Color
                5); // Thickness of lines

        Imgproc.rectangle(
                input, // Image to draw on
                barcode_2_pointA, // First point
                barcode_2_pointB, // Second point
                BLUE, // Color
                5); // Thickness of lines

        Imgproc.rectangle(
                input, // Image to draw on
                barcode_3_pointA, // First point
                barcode_3_pointB, // Second point
                BLUE, // Color
                5); // Thickness of lines

        // Check to see which ROI has the duck in it
        position = BarcodePosition.NONE;
        if (bar1 > OBJECT_THERE_THRESHOLD)
        {
            position = BarcodePosition.ONE;
        }
        else if (bar2 > OBJECT_THERE_THRESHOLD)
        {
            position = BarcodePosition.TWO;
        }
        else if (bar3 > OBJECT_THERE_THRESHOLD)
        {
            position = BarcodePosition.THREE;
        }
        else
        {
            position = BarcodePosition.NONE;
        }
        return input;
    }
}
