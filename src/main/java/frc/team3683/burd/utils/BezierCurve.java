package frc.team3683.burd.utils;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class that generates a Bezier Curve based of off 4 coordinates given by the
 * client
 *
 * Reference Material: https://www.desmos.com/calculator/cahqdxeshd
 *
 */

public class BezierCurve {

    /** Used to save number of points generated */
    private static int size = 1000;

    /** Used to store inputed coordinates */
    public Point[] mVector = new Point[4];

    /** Stores all x values of points on the curve */
    public List<Double> mXPoints = new ArrayList<Double>();

    /** Stores all y values of points on the curve */
    public List<Double> mYPoints = new ArrayList<Double>();

    /** Stores distanced between coordinates */
    public List<Double> mHypotenuse = new ArrayList<Double>();

    /** Used to store difference between x and x+1 */
    public List<Double> mXDelta = new ArrayList<Double>();

    /** Used to store difference between y and y+1 */
    public List<Double> mYDelta = new ArrayList<Double>();

    /** Used to store angles between two coordinates */
    public List<Double> mAngle = new ArrayList<Double>();

    /** Stores x values from inputed coordinates */
    public double[] mXValues = new double[4];

    /** Stores y values from inputed coordinates */
    public double[] mYValues = new double[4];

    /** Used to store total arc length */
    public double mDistance;

    /**
     * Requires 4 points to generate Bezier Curves, inputed as Points.
     *
     * @param startPoint
     *            the start point
     * @param controlPoint1
     *            the control point 1
     * @param controlPoint2
     *            the control point 2
     * @param endPoint
     *            the end point
     */
    public BezierCurve(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint) {
        mVector[0] = startPoint;
        mVector[1] = controlPoint1;
        mVector[2] = controlPoint2;
        mVector[3] = endPoint;
        putPoints();
        findPoints();
        calcPoints();
    }

    /**
     * Instantiates a new bezier curve.
     *
     * @param startPoint
     *            the start point
     * @param controlPoint1
     *            the control point 1
     * @param controlPoint2
     *            the control point 2
     * @param endPoint
     *            the end point
     * @param size
     *            the number points to be generated
     */
    public BezierCurve(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint, int size) {
        mVector[0] = startPoint;
        mVector[1] = controlPoint1;
        mVector[2] = controlPoint2;
        mVector[3] = endPoint;
        this.size = size;
        putPoints();
        findPoints();
        calcPoints();
    }

    /**
     * Update given coordinates.
     *
     * @param startPoint
     *            the start point
     * @param controlPoint1
     *            the control point 1
     * @param controlPoint2
     *            the control point 2
     * @param endPoint
     *            the end point
     */
    public void changePoints(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint) {
        mVector[0] = startPoint;
        mVector[1] = controlPoint1;
        mVector[2] = controlPoint2;
        mVector[3] = endPoint;
        putPoints();
        findPoints();
        calcPoints();
    }

    /**
     * Generates the points on the Bezier Curve.
     *
     * @return Returns a ArrayList consisting of Points
     */
    public ArrayList<Point> findPoints() {
        //System.out.println("Entering Find Points");
        ArrayList<Point> points = new ArrayList<Point>();
        double xVal;
        double yVal;
        mXPoints.clear();
        mYPoints.clear();

       /* Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("PointLog.txt"), "utf-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        for (double i = 0; i <= 1; i += 1.0 / size) {
            xVal = useFunctionX(mXValues[0], mXValues[1], mXValues[2], mXValues[3], i);
            yVal = useFunctionY(mYValues[0], mYValues[1], mYValues[2], mYValues[3], i);
            mXPoints.add(xVal);
            mYPoints.add(yVal);
            points.add(new Point(xVal, yVal));
           /* try {
                //writer.write("xVal:" + Double.valueOf(xVal).toString() + "    " + "yVal:" + Double.valueOf(yVal).toString() + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        /*try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        return points;
    }

    /**
     * Generates x points for Bezier Curve.
     *
     * @param x0
     * @param x1
     * @param x2
     * @param x3
     * @param counter
     * @return Returns the x values for the coordinate
     */
    public double useFunctionX(double x0, double x1, double x2, double x3, double counter) {
        double cx = 3 * (x1 - x0);
        double bx = 3 * (x2 - x1) - cx;
        double ax = x3 - x0 - cx - bx;
        double xVal = ax * Math.pow(counter, 3) + bx * Math.pow(counter, 2) + cx * counter + x0;

        return xVal;
    }

    /**
     * Generates y points for Bezier Curve.
     *
     * @param y0
     * @param y1
     * @param y2
     * @param y3
     * @param counter
     * @return Returns the y values for the coordinate
     */
    public double useFunctionY(double y0, double y1, double y2, double y3, double counter) {
        double cy = 3 * (y1 - y0);
        double by = 3 * (y2 - y1) - cy;
        double ay = y3 - y0 - cy - by;
        double yVal = ay * Math.pow(counter, 3) + by * Math.pow(counter, 2) + cy * counter + y0;

        return yVal;
    }

    /**
     * Separates coordinates into x and y values
     */
    public void putPoints() {
        Point point;

        for (int i = 0; i < mVector.length; i++) {
            point = mVector[i];
            mXValues[i] = point.getX();
            mYValues[i] = point.getY();
        }
    }

    /**
     * Used to calculate angles and arc length.
     */
    public void calcPoints() {
        for (int i = 0; i < mXPoints.size() - 1; i++) {
            mXDelta.add((mXPoints.get(i + 1) - mXPoints.get(i)));
            mYDelta.add((mYPoints.get(i + 1) - mYPoints.get(i)));

//			if (mXDelta.get(i) == 0) {
//				if (mYDelta.get(i) > 0)
//					mAngle.add(i, 0.0);
//				else if (mYDelta.get(i) < 0)
//					mAngle.add(i, 180.0);
//			} else if (mYDelta.get(i) == 0) {
//				if (mXDelta.get(i) > 0)
//					mAngle.add(i, 90.0);
//				else if (mXDelta.get(i) < 0)
//					mAngle.add(i, -90.0);
//			} else
            mAngle.add(Math.toDegrees(Math.atan2(mXDelta.get(i), mYDelta.get(i))));


            mDistance += Math.sqrt(Math.pow(mXDelta.get(i), 2) + Math.pow(mYDelta.get(i), 2));
            mHypotenuse.add(i, mDistance);
        }

        try {

            final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Angles.txt"), "utf-8"));
            mAngle.forEach(la -> {
                try {
                    writer.write(la.toString() + "\n");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * @return The arc length of the curve
     */
    public double findArcLength() {
        return mDistance;
    }

    /**
     * Find the angle at an index
     *
     * @param index
     *            Index of angle (0 to number of coordinates)
     * @return Returns angle in degrees
     */
    public double findAngle(int index) {
        return mAngle.get(index);
    }

    /**
     * Find the hypotenuse length between two coordinates at an index.
     *
     * @param index
     *            Index of hypotenuse segment
     * @return Returns the hypotenuse length in inches
     */
    public double findHypotenuse(int index) {
        return mHypotenuse.get(index);
    }

    /**
     * @return Returns all the x values on the Bezier Curve
     */
    public List<Double> getXPoints() {
        return mXPoints;
    }

    /**
     * @return Returns all the y values on the Bezier Curve
     */
    public List<Double> getYPoints() {
        return mYPoints;
    }

    /**
     * @return Returns the number of points generated
     */
    public int size() {
        return size;
    }

}