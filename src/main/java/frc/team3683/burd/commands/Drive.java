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
import frc.team3683.burd.configs.Constants;

import static frc.team3683.burd.Robot.compBot;
import static frc.team3683.burd.Robot.getmElevator;

/**
 * Driving and encoders happen here.
 */
public class Drive extends Command {

	private OI oi;
	private double xPower;
	private double yPower;
	private double rampConstant;
	public Drive() {
		// Use requires() here to declare subsystem dependencies
		oi = OI.getInstantce();
		requires(Robot.getmDriveBase());
		rampConstant = 0.1;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	Robot.getmDriveBase().brakeMode();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(compBot) {
			if(Robot.getmElevator().getHeight() < Constants.INSTANCE.getSwitchHeight()) {
				if (oi.getYLeft() > 0) {
					if (oi.getXRight() > 0) {
						Robot.getmDriveBase().setLeft(-oi.getYLeft() * oi.getYLeft() - (oi.getXRight() * oi.getXRight()));//invert y for practice
						Robot.getmDriveBase().setRight(-oi.getYLeft() * oi.getYLeft() + (oi.getXRight()) * oi.getXRight());//invert y for practice
					} else {
						Robot.getmDriveBase().setLeft(-oi.getYLeft() * oi.getYLeft() - (-oi.getXRight() * oi.getXRight()));//invert y for practice
						Robot.getmDriveBase().setRight(-oi.getYLeft() * oi.getYLeft() + (-oi.getXRight()) * oi.getXRight());//invert y for practice
					}
				} else {
					if (oi.getXRight() > 0) {
						Robot.getmDriveBase().setLeft(oi.getYLeft() * oi.getYLeft() - (oi.getXRight() * oi.getXRight()));//invert y for practice
						Robot.getmDriveBase().setRight(oi.getYLeft() * oi.getYLeft() + (oi.getXRight()) * oi.getXRight());//invert y for practice
					} else {
						Robot.getmDriveBase().setLeft(oi.getYLeft() * oi.getYLeft() - (-oi.getXRight() * oi.getXRight()));//invert y for practice
						Robot.getmDriveBase().setRight(oi.getYLeft() * oi.getYLeft() + (-oi.getXRight()) * oi.getXRight());//invert y for practice
					}
				}
			} else {
				if (oi.getYLeft() > 0) {
					if (oi.getXRight() > 0) {
						Robot.getmDriveBase().setLeft(0.6*(-oi.getYLeft() * oi.getYLeft() - (oi.getXRight() * oi.getXRight())));//invert y for practice
						Robot.getmDriveBase().setRight(0.6*(-oi.getYLeft() * oi.getYLeft() + (oi.getXRight()) * oi.getXRight()));//invert y for practice
					} else {
						Robot.getmDriveBase().setLeft(0.6*(-oi.getYLeft() * oi.getYLeft() - (-oi.getXRight() * oi.getXRight())));//invert y for practice
						Robot.getmDriveBase().setRight(0.6*(-oi.getYLeft() * oi.getYLeft() + (-oi.getXRight()) * oi.getXRight()));//invert y for practice
					}
				} else {
					if (oi.getXRight() > 0) {
						Robot.getmDriveBase().setLeft(0.6*(oi.getYLeft() * oi.getYLeft() - (oi.getXRight() * oi.getXRight())));//invert y for practice
						Robot.getmDriveBase().setRight(0.6*(oi.getYLeft() * oi.getYLeft() + (oi.getXRight()) * oi.getXRight()));//invert y for practice
					} else {
						Robot.getmDriveBase().setLeft(0.6*(oi.getYLeft() * oi.getYLeft() - (-oi.getXRight() * oi.getXRight())));//invert y for practice
						Robot.getmDriveBase().setRight(0.6*(oi.getYLeft() * oi.getYLeft() + (-oi.getXRight()) * oi.getXRight()));//invert y for practice
					}
				}
			}
		} else {
			if (oi.getYLeft() > 0) {
				if (oi.getXRight() > 0) {
					Robot.getmDriveBase().setLeft(-oi.getYLeft() * oi.getYLeft() + (oi.getXRight() * oi.getXRight()));//invert y for practice
					Robot.getmDriveBase().setRight(-oi.getYLeft() * oi.getYLeft() - (oi.getXRight()) * oi.getXRight());//invert y for practice
				} else {
					Robot.getmDriveBase().setLeft(-oi.getYLeft() * oi.getYLeft() + (-oi.getXRight() * oi.getXRight()));//invert y for practice
					Robot.getmDriveBase().setRight(-oi.getYLeft() * oi.getYLeft() - (-oi.getXRight()) * oi.getXRight());//invert y for practice
				}
			} else {
				if (oi.getXRight() > 0) {
					Robot.getmDriveBase().setLeft(oi.getYLeft() * oi.getYLeft() + (oi.getXRight() * oi.getXRight()));//invert y for practice
					Robot.getmDriveBase().setRight(oi.getYLeft() * oi.getYLeft() - (oi.getXRight()) * oi.getXRight());//invert y for practice
				} else {
					Robot.getmDriveBase().setLeft(oi.getYLeft() * oi.getYLeft() + (-oi.getXRight() * oi.getXRight()));//invert y for practice
					Robot.getmDriveBase().setRight(oi.getYLeft() * oi.getYLeft() - (-oi.getXRight()) * oi.getXRight());//invert y for practice
				}
			}
		}
		Robot.getmDriveBase().highGear(oi.getLeftBumper());


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
