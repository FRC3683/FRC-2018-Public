/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * The elevator is manually controlled here.
 */
public class DelayedElevatorCommand extends Command {
	OI oi;
	private double pos;
	private double delay;
	private double ticks;
	public DelayedElevatorCommand(double pos, double delay) {
		oi = OI.getInstantce();
		this.pos = pos;
		this.delay = delay;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.getmElevator());
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.getmElevator().PIDToPos(pos);
		ticks = 0;

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(ticks > delay * 50) {
			Robot.getmElevator().autoPID();
		} else {
			ticks++;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
		Robot.getmElevator().manualControl(0.0);
	}
}
