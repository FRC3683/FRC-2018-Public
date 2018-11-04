/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * The elevator is manually controlled here.
 */
public class ElevatorControl extends Command {
	OI oi;
	boolean manual;
	boolean idle;
	boolean climbPower;
	public static boolean climbTime;
	public static boolean climbSetup;
	double maxCurrent;
	private boolean coastDown;
	private int dannyTicks;
	public ElevatorControl() {
		oi = OI.getInstantce();
		manual = false;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.getmElevator());
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		coastDown = false;
		climbTime = false;
		climbSetup = false;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//			SmartDashboard.putString(String.format("DB/String 4"), "current 9: " + String.valueOf(Robot.getmPDP().getCurrent(12)));
//			SmartDashboard.putString(String.format("DB/String 5"), "current 10: " + String.valueOf(Robot.getmPDP().getCurrent(13)));
//			SmartDashboard.putString(String.format("DB/String 6"), "current 11: " + String.valueOf(Robot.getmPDP().getCurrent(14)));
//			SmartDashboard.putString(String.format("DB/String 7"), "current 12: " + String.valueOf(Robot.getmPDP().getCurrent(15)));
			//SmartDashboard.putString(String.format("DB/String 8"), "max current: " + String.valueOf(maxCurrent));
			if (Robot.getmPDP().getCurrent(12) > maxCurrent) {
				maxCurrent = Robot.getmPDP().getCurrent(12);
			}
			if (Robot.getmPDP().getCurrent(13) > maxCurrent) {
				maxCurrent = Robot.getmPDP().getCurrent(13);
			}
			if (Robot.getmPDP().getCurrent(14) > maxCurrent) {
				maxCurrent = Robot.getmPDP().getCurrent(14);
			}
			if (Robot.getmPDP().getCurrent(15) > maxCurrent) {
				maxCurrent = Robot.getmPDP().getCurrent(15);
			}


			if (oi.getBButton()) {
				Robot.getmElevator().scoreScaleLow();
				manual = false;
				climbPower = false;
				dannyTicks = 0;
			} else if(oi.getXButton()){
				Robot.getmElevator().scoreScaleNeutral();
				manual = false;
				dannyTicks = 0;
				climbPower = false;
			}  else if (oi.getYButton()) {

				Robot.getmElevator().scoreScaleHigh();
				manual = false;
				climbPower = false;
				dannyTicks = 0;


			} else if (oi.getBackButton()){
				Robot.getmElevator().goToClimb();
				manual = false;
				climbTime = false;
				climbPower = true;
				//Robot.getmElevator().ratchetControl(false);

			} else if(oi.getRightBumper() && !(oi.getXButton() || oi.getBButton() || oi.getYButton())){
				if(dannyTicks > 10) {
					Robot.getmElevator().scoreSwitch();
					manual = false;
					climbPower = false;
				} else {
					dannyTicks ++;
				}
			}


			else if (oi.getStartButton()) {
				Robot.getmElevator().top();

				manual = false; //caitlin's code
				climbPower = false;
				climbTime = false;
				climbSetup = true;

			}  else if (climbTime){
				Robot.getmElevator().manualControl(0.0);
			} else if(!climbTime) {
				Robot.getmElevator().Pickup();
				climbPower = false;



			}
		Robot.getmElevator().giveData();
		//win_worlds(now please)//
		if(!coastDown) {
			if (oi.getRightBumper()) {
				//Robot.getmElevator().manualControl(-0.30);
				//manual = true;
			} else if (oi.getLeftBumper()) {
				//Robot.getmElevator().manualControl(0.69);
				//manual = true;
			} else if (manual) {
				Robot.getmElevator().manualControl(0.0);
				//Robot.getmElevator().ratchetControl(false);



				//manual = false;
			}

			if(!Robot.getmElevator().isAtBottom()){
				Robot.getmElevator().zeroEncoder();
			}

			if (!manual) {
				if(climbPower) {
					Robot.getmElevator().climbPID();
					//Robot.getmElevator().ratchetControl(true);
					if (Robot.getmElevator().climbed){
						//Robot.getmElevator().forceRatchet(true);
					}
				} else if(climbSetup) {
					Robot.getmElevator().climbSetupPID();
					//Robot.getmElevator().ratchetControl(false);
				}else {
					Robot.getmElevator().motorPID();
					//Robot.getmElevator().ratchetControl(false);
				}
			}
			if(Robot.getmElevator().getUp()){
				coastDown = true;
			}
			if(Robot.getmElevator().getDown()){
				climbSetup = false;
			}
		} else if (coastDown){
			Robot.getmElevator().manualControl(0.0);
			if(Robot.getmElevator().getDown()){
				coastDown = false;
			}
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
