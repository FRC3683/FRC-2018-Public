/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.Robot;
import frc.team3683.burd.utils.MathUtils;

/**
 * Drive the robot to a specific distance, in inches
 */
public class DriveStraightAbsoluteDec extends Command {
	private double distance;
	private double speed;
	private double timeOut;
	private double targetAngle;
	private double targetPos;
	private double tolerance;
	private boolean brake;
	private double initalSpeed;
	private double finalSpeed;
	private double percentFactor;


	public DriveStraightAbsoluteDec(double distance, double initalSpeed, double timeOut, double angle, double tolerance,
                                    boolean brake, double finalSpeed, double percentFactor) {
		// Use requires() here to declare subsystem dependencies
		this.speed = speed;
		this.distance = distance;
		this.timeOut = timeOut;
		targetAngle = angle;
		this.tolerance = tolerance;
		this.brake = brake;
		this.initalSpeed = initalSpeed;
		this.finalSpeed = finalSpeed;
		this.percentFactor = percentFactor;
		requires(Robot.getmDriveBase());
	}


	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.getmDriveBase().resetEncoders();
		//Robot.getmDriveBase().zeroGyro();
		targetPos = distance + Robot.getmDriveBase().getDistance();
		//targetAngle = Robot.getmDriveBase().getHeading();
		Robot.getmDriveBase().getmDrivePID().zero(targetPos, Robot.getmDriveBase().getDistance());
		Robot.getmDriveBase().getmGyroPID().zero(targetAngle, Robot.getmDriveBase().getAbsoluteHeading());
		setTimeout(timeOut);
		if(brake){
			Robot.getmDriveBase().brakeMode();
		} else {
			Robot.getmDriveBase().coastMode();
		}
		//Robot.getmDriveBase().highGear(highGear);


	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		double distanceTravlled = Math.abs(Robot.getmDriveBase().getDistance());
		double beta = MathUtils.beta(distanceTravlled, targetPos);
		double alpha = MathUtils.alpha(beta, percentFactor);

		speed = MathUtils.lerp(alpha, initalSpeed, finalSpeed);

		SmartDashboard.putNumber("DB/Slider 0", speed);


		Robot.getmDriveBase().driveStraightAbsolute(targetPos, speed, targetAngle);
		//SmartDashboard.putString(String.format("DB/String 0"), String.valueOf(Robot.getmDriveBase().getDistance()));
		//SmartDashboard.putString(String.format("DB/String 1"), String.valueOf(Robot.getmDriveBase().getmDrivePID().iterate(targetPos, Robot.getmDriveBase().getDistance())));

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (isTimedOut() || ((Math.abs(Robot.getmDriveBase().getDistance() - targetPos) < tolerance) && Math.abs(Robot.getmDriveBase().getSpeed()) < (5.0 * tolerance)));
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.getmDriveBase().setRight(0.0);
		Robot.getmDriveBase().setLeft(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
