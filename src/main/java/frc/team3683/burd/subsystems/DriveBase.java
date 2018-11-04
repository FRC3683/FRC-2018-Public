package frc.team3683.burd.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.Robot;
import frc.team3683.burd.commands.Grind;
import frc.team3683.burd.configs.Config;
import frc.team3683.burd.commands.Drive;
import frc.team3683.burd.utils.BNO055;
import frc.team3683.burd.utils.PID;
import frc.team3683.burd.utils.TCS34725;

import java.text.DecimalFormat;

import static frc.team3683.burd.Robot.compBot;

/**
 *
 */
public class DriveBase extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private VictorSPX mLeft1;
	private VictorSPX mLeft2;
	private VictorSPX mRight1;
	private VictorSPX mRight2;
	private Encoder mEncoderLeft;
	private Encoder mEncoderRight;
	private DoubleSolenoid mShifter;
	private BNO055 mGyro;
	private static TCS34725 mLightSensor;
	private static PID mDrivePID;
	private static PID mBangBang;
	private static PID mGyroPID;
	private double gyroZero;
	private boolean gyroDied;
	private double absoluteOffset;
	private DoubleSolenoid.Value currentShift = DoubleSolenoid.Value.kForward;
	private DecimalFormat df2 = new DecimalFormat("##.##");

	public DriveBase(Config config){
		mLeft1 = config.getDriveVictorLeft1();
		mLeft2 = config.getDriveVictorLeft2();
		mRight1 = config.getDriveVictorRight1();
		mRight2 = config.getDriveVictorRight2();

		mEncoderRight = config.getDriveEncoderRight();
		mEncoderLeft = config.getDriveEncoderLeft();
		mShifter = config.getDriveSolenoid();
		mGyro = config.getDriveGyro();


		mLightSensor = config.getColorSensor();

		mDrivePID = new PID (config.getDriveP(), config.getDriveI(), config.getDriveD());
		mGyroPID = new PID (config.getGyroP(), config.getGyroI(), config.getGyroD());
		mBangBang = new PID(1, 0, 0);
		mShifter.set(currentShift);
		gyroDied = false;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
		// Set the default command for a subsystem here.
	}

	//All of the motor methods

	public void setLeft(double power){
		if(compBot) {
			mLeft1.set(ControlMode.PercentOutput, -power);
			mLeft2.set(ControlMode.PercentOutput, -power);

		} else {
			mLeft1.set(ControlMode.PercentOutput, power);
			mLeft2.set(ControlMode.PercentOutput, power);
		}
	}

	public void setRight(double power){
		if(compBot){
			mRight1.set(ControlMode.PercentOutput, power);
			mRight2.set(ControlMode.PercentOutput, power);
		} else {
			mRight1.set(ControlMode.PercentOutput, -power);
			mRight2.set(ControlMode.PercentOutput, -power);
		}
	}

	//Shifters
	public void toggleShift(){
		if(currentShift == DoubleSolenoid.Value.kForward){
			currentShift = DoubleSolenoid.Value.kReverse;
		}else{
			currentShift = DoubleSolenoid.Value.kForward;
		}
		mShifter.set(currentShift);
	}


	public void highGear(boolean isHigh){
		if(!isHigh){
			mShifter.set(DoubleSolenoid.Value.kForward);
		} else {
			mShifter.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void setShifterHigh(){
		currentShift = DoubleSolenoid.Value.kForward;
		mShifter.set(currentShift);
	}

	public void setShifterLow(){
		currentShift = DoubleSolenoid.Value.kReverse;
		mShifter.set(currentShift);
	}
	//All of the encoder methods

	public void resetEncoders(){
		mEncoderLeft.reset();
		mEncoderRight.reset();
	}

	public double getDistance(){

		return ((mEncoderLeft.getDistance() + mEncoderRight.getDistance())/2);
	}

	public void coastMode(){
		mLeft1.setNeutralMode(NeutralMode.Coast);
		mLeft2.setNeutralMode(NeutralMode.Coast);
		mRight1.setNeutralMode(NeutralMode.Coast);
		mRight2.setNeutralMode(NeutralMode.Coast);
	}

	public void brakeMode(){
		mLeft1.setNeutralMode(NeutralMode.Brake);
		mLeft2.setNeutralMode(NeutralMode.Brake);
		mRight1.setNeutralMode(NeutralMode.Brake);
		mRight2.setNeutralMode(NeutralMode.Brake);
	}
	public double getSpeed(){
		return (mEncoderLeft.getRate() + mEncoderRight.getRate()) / 2;
	}

	public int getTicksRight(){
		return (mEncoderRight.getRaw());
	}

	public int getTicksLeft(){
		return (mEncoderLeft.getRaw());
	}

	public double getHeading(){
		return mGyro.getHeading() - gyroZero;
	}

	public double getAbsoluteHeading(){
		return mGyro.getHeading() - absoluteOffset;
	}

	public void setAbsoluteOffset(double offset){
		absoluteOffset = offset;
	}

	public void driveStraight(double setPoint, double speed, double setAngle) {
		//SmartDashboard.putString("DB/String 7", Double.valueOf(getHeading()).toString());

		double output = mDrivePID.iterate(setPoint, getDistance());
		double angle = mGyroPID.iterate(setAngle, getHeading());
		System.out.println(output);
		System.out.println("speed" + speed);
		System.out.println("angle" + angle);
		//SmartDashboard.putString("DB/String 2", String.valueOf(((output + angle) * speed)));
		//SmartDashboard.putString("DB/String 3", String.valueOf(((output - angle) * speed)));

		if(compBot){
			setLeft((output - angle) * speed); //invert foir practice
			setRight((output + angle) * speed); //invert foir practice
		} else {
			setLeft((output + angle) * speed); //invert foir practice
			setRight((output - angle) * speed); //invert foir practice
		}
	}

	public void driveStraightAbsolute(double setPoint, double speed, double setAngle) {
		SmartDashboard.putString("DB/String 9", Double.valueOf(getHeading()).toString());

		double output = mDrivePID.iterate(setPoint, getDistance());
		double angle = mGyroPID.iterate(setAngle, getAbsoluteHeading());
		System.out.println(output);
		System.out.println("speed" + speed);
		System.out.println("angle" + angle);
		SmartDashboard.putString("DB/String 4", "left: " + df2.format(((output + angle) * speed)));
		SmartDashboard.putString("DB/String 6", "right: " + df2.format(((output - angle) * speed)));
		//output = output * 0.9;



		if(compBot){
			setLeft((output - angle) * speed); //invert foir practice
			setRight((output + angle) * speed); //invert foir practice
		} else {
			setLeft((output + angle) * speed); //invert foir practice
			setRight((output - angle) * speed); //invert foir practice
		}
	}

	public void BangBang(double setPoint, double speed, double setAngle) {
		SmartDashboard.putString("DB/String 9", Double.valueOf(getHeading()).toString());

		double output = mBangBang.iterate(setPoint, getDistance());
		double angle = mGyroPID.iterate(setAngle, getAbsoluteHeading());
		System.out.println(output);
		System.out.println("speed" + speed);
		System.out.println("angle" + angle);
		//SmartDashboard.putString("DB/String 2", String.valueOf(((output + angle) * speed)));
		//SmartDashboard.putString("DB/String 3", String.valueOf(((output - angle) * speed)));

		if(compBot){
			setLeft((output - angle) * speed); //invert foir practice
			setRight((output + angle) * speed); //invert foir practice
		} else {
			setLeft((output + angle) * speed); //invert foir practice
			setRight((output - angle) * speed); //invert foir practice
		}
	}

	public void driveAngle(double targetAngle, double turningSpeed){
		SmartDashboard.putString("DB/String 9", Double.valueOf(getHeading()).toString());

		double angle = mGyroPID.iterate(targetAngle, getHeading());
		//SmartDashboard.putString(String.format("DB/String 1"), String.valueOf(angle));

		if(compBot){
			setLeft(-angle * turningSpeed); //invert foir practice
			setRight(angle * turningSpeed); //invert foir practice
		} else {
			setLeft(angle * turningSpeed); //invert foir practice
			setRight(-angle * turningSpeed); //invert foir practice
		}
	}

	public void driveAngleAbsolute(double targetAngle, double turningSpeed){
		SmartDashboard.putString("DB/String 9", Double.valueOf(getHeading()).toString());

		double angle = mGyroPID.iterate(targetAngle, getAbsoluteHeading());
		//SmartDashboard.putString(String.format("DB/String 1"), String.valueOf(angle));

		if(compBot){
			setLeft(-angle * turningSpeed);
			setRight(angle * turningSpeed);
		} else {
			setLeft(angle * turningSpeed);
			setRight(-angle * turningSpeed);
		}
	}
	public void zeroGyro(){
		gyroZero = mGyro.getHeading();
	}

	public boolean isGyro(){
		return mGyro.isSensorPresent();
	}

	public boolean checkGyro(){
		if(!gyroDied){
			if(!isGyro()) {
				gyroDied = true;
			}
		}
		return gyroDied;
	}

	public double getGyroAbsolute(){
		return mGyro.getHeading();
	}

	public double getSpeedLeft(){
		return mEncoderLeft.getRate();
	}

	public double getSpeedRight(){
		return mEncoderRight.getRate();
	}

	public double getLeftDistance(){
		return mEncoderLeft.getDistance();
	}

	public double getRightDistance(){
		return mEncoderRight.getDistance();
	}

	public static PID getmDrivePID(){
		return mDrivePID;
	}

	public static PID getmGyroPID(){
		return mGyroPID;
	}

	public static TCS34725 getmColourSensor() {return mLightSensor;}


	public void clearSticky(){
		mLeft1.clearStickyFaults(10);
		mLeft2.clearStickyFaults(10);
		mRight1.clearStickyFaults(10);
		mRight2.clearStickyFaults(10);

	}
}
