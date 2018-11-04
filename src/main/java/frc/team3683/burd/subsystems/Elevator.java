package frc.team3683.burd.subsystems;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3683.burd.commands.ElevatorControl;
import frc.team3683.burd.configs.Config;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.PID;

import static frc.team3683.burd.Robot.compBot;

/**
 *
 */
public class Elevator extends Subsystem {
	private DigitalInput atBottom;
	private DigitalInput atTop;
	private Victor[] mElevatorVictors;
	private Encoder mElevatorEncoder;
	private PID mElevatorPID;
	private double targetPos;
	private double elevatorTolerance;
	//private DoubleSolenoid mRatchet;
	public boolean climbed;

	public Elevator (Config config){
		mElevatorVictors = new Victor[5];
		mElevatorVictors[0] = config.getElevatorVictor1();
		mElevatorVictors[1] = config.getElevatorVictor2();
		mElevatorVictors[2] = config.getElevatorVictor3();
		mElevatorVictors[3] = config.getElevatorVictor4();
		mElevatorVictors[4] = config.getElevatorVictor5();
		mElevatorEncoder = config.getElevatorEncoder();
		mElevatorEncoder.setDistancePerPulse(1/config.getTicksPerInch());
		mElevatorPID = new PID(config.getElevatorP(), config.getElevatorI(), config.getElevatorD());
		targetPos = 0.0;
		elevatorTolerance = config.getElevatorTolerance();
		atBottom = config.getHoloflexDown();
		atTop = config.getHoloflexUp();
		//mRatchet = config.getRatchetSolenoid();

	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorControl());
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	void runMotors(double power){
		if(compBot){
			mElevatorVictors[0].set(-power);
			mElevatorVictors[1].set(-power); //invert on comp

			mElevatorVictors[2].set(power);
			mElevatorVictors[3].set(power);//invert this for practice
			mElevatorVictors[4].set(-power);
		} else {
			mElevatorVictors[0].set(-power);
			mElevatorVictors[1].set(power); //invert on comp

			mElevatorVictors[2].set(power);
			mElevatorVictors[3].set(-power);//invert this for practice
			mElevatorVictors[4].set(-power);
		}

		//TODO add config to invert mototors

	}

	public boolean getDown(){
		return !atBottom.get();
	}

	public boolean getUp(){
		return !atTop.get();
	}

	public void scoreSwitch(){
		targetPos = Constants.INSTANCE.getSwitchHeight();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}

	public boolean motorPID() {
		//mRatchet.set(DoubleSolenoid.Value.kReverse);

		if (!climbed) {
			if (targetPos > mElevatorEncoder.getDistance()) {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.7);
			} else {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.20);
			}
		}
		return (Math.abs(targetPos - mElevatorEncoder.getDistance()) < elevatorTolerance);

	}

	public boolean climbSetupPID() {
		//mRatchet.set(DoubleSolenoid.Value.kReverse);

		if (!climbed) {
			if (targetPos > mElevatorEncoder.getDistance()) {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.6);
			} else {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.0);
			}
		}
		return (Math.abs(targetPos - mElevatorEncoder.getDistance()) < elevatorTolerance);

	}

	public boolean autoPID() {
		//mRatchet.set(DoubleSolenoid.Value.kReverse);

		if (!climbed) {
			if (targetPos > mElevatorEncoder.getDistance()) {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.8);
			} else {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())) * 0.4);
			}
		}
		return (Math.abs(targetPos - mElevatorEncoder.getDistance()) < elevatorTolerance);

	}

	public void climbPID() {
		if (!climbed) {
			if (targetPos > mElevatorEncoder.getDistance()) {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())));
			} else {
				runMotors((mElevatorPID.iterate(targetPos, mElevatorEncoder.getDistance())));
			}
		}
	}

	public void scoreScaleLow(){
		targetPos = Constants.INSTANCE.getLowScale();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}

	public void scoreScaleNeutral(){
		targetPos = Constants.INSTANCE.getNeutralScale();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}

	public void scoreScaleHigh(){
		targetPos = Constants.INSTANCE.getHighScale();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}

	public void Pickup(){
		targetPos = Constants.INSTANCE.getBottom();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());

	}

	public void goToClimb(){
		targetPos = Constants.INSTANCE.getClimbPos();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}
	public void top(){
		targetPos = Constants.INSTANCE.getUp();
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());
	}

	public void ratchetControl(boolean isRatchet){
		if(isRatchet){
			if(mElevatorEncoder.getDistance() < 60 || climbed){
				//mRatchet.set(DoubleSolenoid.Value.kForward);
				climbed = true;
				if(mElevatorEncoder.get() < 60) {
					runMotors(0.0);
				}
			}

		} else {
			//mRatchet.set(DoubleSolenoid.Value.kReverse);
			climbed = false;
		}
	}

	public void forceRatchet(boolean isRathcet){
		if(isRathcet){
			//mRatchet.set(DoubleSolenoid.Value.kForward);
		} else {
			//mRatchet.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void giveData(){
		//System.out.println(mElevatorEncoder.getDistance());
	}

	public void PIDToPos(double pos){
		targetPos = pos;
		mElevatorPID.zero(targetPos, mElevatorEncoder.getDistance());

	}

	public double getHeight(){
		return mElevatorEncoder.getDistance();
	}
	public boolean isAtTop(){
		return atTop.get();
	}

	public boolean isAtBottom(){
		return atBottom.get();
	}

	public void zeroEncoder(){
		mElevatorEncoder.reset();
	}

	public void runMotor1(double power){
		mElevatorVictors[0].set(power);
	}
	//Caitlin make it go low here
	public void manualControl(double power){
		runMotors(power);
	}
}
