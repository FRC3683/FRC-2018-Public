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
 * Drive the robot to a specific distance, in inches
 */
public class AutoGearShift extends Command {
	private boolean isHigh;
	private int ticks;
	public AutoGearShift(boolean isHigh) {
		// Use requires() here to declare subsystem dependencies
		this.isHigh = isHigh;
		//requires(Robot.getmDriveBase());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.getmDriveBase().highGear(isHigh);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.getmDriveBase().highGear(isHigh);
		ticks++;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (ticks > 1);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
