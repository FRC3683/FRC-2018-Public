/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3683.burd.Robot;

/**
 * A straight 🔥🔥🔥 auto mode for 🅱ig 🅱allers only.
 */
public class Way_Which extends Command {
	int ticks;
	int ticksCount;
	public Way_Which() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.getmDriveBase());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ticks = (int) Math.ceil(Math.random() * 500);
		ticksCount = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.getmDriveBase().setLeft(-1);
		Robot.getmDriveBase().setRight(1);
		ticksCount++;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (ticksCount > ticks);
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
