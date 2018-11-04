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

/**
 * Turn the robot to a specific angle, in degrees
 */
public class DriveAngleAbsolute extends Command {
	private double speed;
	private double timeOut;
	private double targetAngle;
	private double targetPos;
	private double angle;
	private double ticks;

	public DriveAngleAbsolute(double angle, double speed, double timeOut) {
		// Use requires() here to declare subsystem dependencies
		this.speed = speed;
		this.angle = angle;
		this.timeOut = timeOut;
		requires(Robot.getmDriveBase());
		ticks = 0;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.getmDriveBase().zeroGyro();
		targetAngle = angle + Robot.getmDriveBase().getHeading();
		Robot.getmDriveBase().getmDrivePID().zero(targetPos, Robot.getmDriveBase().getDistance());
		Robot.getmDriveBase().getmGyroPID().zero(targetAngle, Robot.getmDriveBase().getAbsoluteHeading());
		setTimeout(timeOut);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.getmDriveBase().driveAngleAbsolute(angle, speed);
		//TODO: change tolerance back to 1
		if(((Math.abs(Robot.getmDriveBase().getAbsoluteHeading() - targetAngle) < 3.0) && (Math.abs(Robot.getmDriveBase().getSpeedLeft()) < 2.0))){
			ticks ++;
		} else {
			ticks = 0;
		}
		//SmartDashboard.putString(String.format("DB/String 0"), String.valueOf(Robot.getmDriveBase().getDistance()));
		//SmartDashboard.putString(String.format("DB/String 1"), String.valueOf(Robot.getmDriveBase().getmGyroPID().iterate(targetAngle, Robot.getmDriveBase().getHeading())));

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (isTimedOut() || ticks > 2);
		//return false;
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
