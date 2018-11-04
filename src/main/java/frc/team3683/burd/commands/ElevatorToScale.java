/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3683.burd.Robot;

/**
 * Elevator preset to raise the elevator up to the scale
 */
public class ElevatorToScale extends Command {
	private boolean finished;
	public ElevatorToScale() {
		// Use requires() here to declare subsystem dependencies

		requires(Robot.getmExampleSubsystem());
		finished = false;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.getmElevator().scoreScaleNeutral();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		finished = Robot.getmElevator().motorPID();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.getmElevator().manualControl(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
