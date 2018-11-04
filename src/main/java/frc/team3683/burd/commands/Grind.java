/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * Test code used to grind gear boxes
 */
public class Grind extends Command {

	private OI oi;
	private int ticks;
	private boolean inverted;
	public Grind() {
		// Use requires() here to declare subsystem dependencies
		oi = OI.getInstantce();
		requires(Robot.getmDriveBase());
		//requires(Robot.getmElevator());
		ticks = 0;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (ticks < 50){
			Robot.getmDriveBase().setLeft(0);
			Robot.getmDriveBase().setRight(0);
			//Robot.getmElevator().runMotor1(0);
		}
		 else if(inverted) {
			Robot.getmDriveBase().setLeft(.6);
			Robot.getmDriveBase().setRight(.6);
			//Robot.getmElevator().runMotor1(.8);
		} else {
			Robot.getmDriveBase().setLeft(-.6);
			Robot.getmDriveBase().setRight(-.6);
			//Robot.getmElevator().runMotor1(-.8);
		}
		ticks++;
		if (ticks > 1500){
			inverted = !inverted;
			ticks = 0;
			Robot.getmDriveBase().setLeft(0);
			Robot.getmDriveBase().setRight(0);
			//Robot.getmElevator().runMotor1(0);
		}
		if(oi.getBButtonDown()){
			Robot.getmDriveBase().toggleShift();
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
