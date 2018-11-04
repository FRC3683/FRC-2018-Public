package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.Robot;
import frc.team3683.burd.utils.BezierCurve;
import frc.team3683.burd.utils.Point;

/**
 * Command used to allow robot to travel a path generated using Bezier curves
 *
 * @author Mahrus Kazi
 */
public class DrivePathElevator extends Command {

    // Create a Bezier curve object
    private BezierCurve curve;

    //NOTE: Reverse = true makes the robot drive the curve from sta
    // Variables to store parameter information
    private int counter;
    private double distance;
    private double timeOut;
    private double speed;
    private boolean reverse;
    private boolean targetPos;
    private double elevatorPos;
    private double wristPos;
    private double slowDist;
    private double elevatorDist;
    private double wristDist;
    private boolean elevatorSet;
    private boolean wristSet;
    /**
     * Instantiates a new getmDriveBase() path.
     *
     * @param startPoint
     *            The start point
     * @param controlPoint1
     *            The control point 1
     * @param controlPoint2
     *            The control point 2
     * @param endPoint
     *            The end point
     * @param timeOut
     *            The time out in seconds
     * @param speed
     *            The speed the robot will travel at (0.0 - 1.0)
     * @param slowDist
     *            Reduce speed by half for this many ticks
     * @param elevatorDist
     *            Move the elevator after this many ticks
     * @param elevatorPos
     *            Move the elevator to this position
     * @param wristDist
     *            Move the wrist after this many ticks
     * @param wristPos
     *            Move the wrist to this position
     *
     */
    public DrivePathElevator(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint, double timeOut,
                             double speed, double slowDist, double elevatorPos, double elevatorDist, double wristPos, double wristDist) {

        curve = new BezierCurve(startPoint, controlPoint1, controlPoint2, endPoint);
        distance = curve.findArcLength();
        this.timeOut = timeOut;
        this.speed = speed;
        this.reverse = false;
        this.slowDist = slowDist;
        this.elevatorPos = elevatorPos;
        this.elevatorDist = elevatorDist;
        this.wristPos = wristPos;
        this.wristDist = wristDist;
        elevatorSet = false;
        wristSet = false;
        requires(Robot.getmDriveBase());
        requires(Robot.getmIntake());
        requires(Robot.getmElevator());
        Robot.getmDriveBase().checkGyro();    }





    /*public DrivePathSlow(Point[] controlPoints, double timeOut, double speed) {
        this(controlPoints[0],controlPoints[1],controlPoints[2],controlPoints[3], timeOut, speed);
    }*/

    // Initialize the command by reseting encoders and setting time out
    protected void initialize() {
        Robot.getmDriveBase().brakeMode();
        counter = 0;
        setTimeout(timeOut);
        Robot.getmDriveBase().resetEncoders();
        Robot.getmDriveBase().zeroGyro();
        //Robot.getmDriveBase().Shift(false);
        setTimeout(timeOut);

    }

    // Give set distance for robot to travel, at each point change angle to
    // point towards next coordinate
    protected void execute() {
        if(counter > 999) counter = 999;
        if (counter > (999-slowDist) && counter  < 999){
            if (reverse) {
                if (-Robot.getmDriveBase().getDistance() > curve.findHypotenuse(counter) && counter < curve.size() - 1)
                    counter =  Double.valueOf((Math.abs((Robot.getmDriveBase().getDistance()/curve.findArcLength()))*1000)).intValue();

                if (counter < 999)Robot.getmDriveBase().driveStraight(-distance, speed * .5, curve.findAngle(Math.abs(counter - 998)));
            } else {
                if (Robot.getmDriveBase().getDistance() > curve.findHypotenuse(counter) && counter < curve.size()- 1)
                    counter =  Double.valueOf(((Robot.getmDriveBase().getDistance()/curve.findArcLength())*1000)).intValue();

                if(counter < 999) Robot.getmDriveBase().driveStraight(distance, speed * .5, curve.findAngle(counter));
            }
        }
        else if(counter < 999){
            if (reverse) {
                if (-Robot.getmDriveBase().getDistance() > curve.findHypotenuse(counter) && counter < curve.size() - 1)
                    counter =  Double.valueOf((Math.abs((Robot.getmDriveBase().getDistance()/curve.findArcLength()))*1000)).intValue();

                if (counter < 999)Robot.getmDriveBase().driveStraight(-distance, speed, curve.findAngle(Math.abs(counter - 998)));
            } else {
                if (Robot.getmDriveBase().getDistance() > curve.findHypotenuse(counter) && counter < curve.size()- 1)
                    counter =  Double.valueOf(((Robot.getmDriveBase().getDistance()/curve.findArcLength())*1000)).intValue();

                if(counter < 999) Robot.getmDriveBase().driveStraight(distance, speed, curve.findAngle(counter));
            }
        }


        //control the elevator and wrist based on time through curve
        if((counter > elevatorDist)){
            Robot.getmElevator().PIDToPos(elevatorPos);
        }
        if((counter > wristDist)){
            Robot.getmIntake().setWrist(wristPos);
        }
        Robot.getmIntake().PIDIntake();
        Robot.getmElevator().autoPID();

    }

    // Command is finished when average distance = total distance or command
    // times out
    protected boolean isFinished() {
        SmartDashboard.putString(
                "DB/String 2",
                String.valueOf(Robot.getmDriveBase().getLeftDistance()));



        SmartDashboard.putString(
                "DB/String 3",
                String.valueOf(Robot.getmDriveBase().getDistance())
        );

        SmartDashboard.putString(
                "DB/String 4",
                String.valueOf(Robot.getmDriveBase().getRightDistance())
        );
        return Math.abs(Robot.getmDriveBase().getDistance()) >= distance ||

                isTimedOut() ||
                counter  >= 999 ||
                (Math.abs(Robot.getmDriveBase().getSpeed()) < 0.05 &&
                        Math.abs(Robot.getmDriveBase().getDistance()) >= 1.0);
    }

    // At the end, stop getmDriveBase() motors
    protected void end() {
        Robot.getmDriveBase().setLeft(0);
        Robot.getmDriveBase().setRight(0);
        Robot.getmElevator().manualControl(0.0);
    }

    protected void interrupted() {
        end();
    }
}
