package frc.team3683.burd.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.Robot;
import frc.team3683.burd.commands.IntakeControl;
import frc.team3683.burd.configs.Config;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.PID;

import static frc.team3683.burd.Robot.compBot;

public class Intake extends Subsystem {
    private Victor mLeft;
    private Victor mRight;
    //private DoubleSolenoid pynchyBoiLeft;
    //private DoubleSolenoid pynchyBoiRight;
    private Victor mWrist1;
    private Victor mWrist2;
    private AnalogPotentiometer mPot;
    private AnalogInput mLightSensor;
    private PID  mIntakePID;
    private double targetPOS;
    private double degreesPerTick;
    private double zeroDegree;
    private boolean isPinched;
    private double prevDegree;
    private Solenoid mLEDLeft;
    private Solenoid mLEDRight;
    public Intake(Config config){
        mLeft = config.getIntakeVictorLeft();
        mRight = config.getIntakeVictorRight();
        //pynchyBoiLeft = config.getIntakeSolenoidLeft();
        //pynchyBoiRight = config.getIntakeSolenoidRight();
        mLEDLeft = config.getLEDLeft();
        mLEDRight = config.getLEDRight();
        mWrist1 = config.getWristVictor1();
        mWrist2 = config.getWristVictor2();
        mPot = config.getPot();
        mIntakePID = new PID(config.getElevatorP(), config.getElevatorI(), config.getElevatorD());
        degreesPerTick = config.getTicksPerDegree();
        zeroDegree = config.getZeroDegreeTicks();
        mLightSensor = config.getLightSensor();
        //unpinch();

    }
    /*public void pinch(){
        pynchyBoiLeft.set(DoubleSolenoid.Value.kForward);
        pynchyBoiRight.set(DoubleSolenoid.Value.kForward);
        isPinched = true;
    }
    public void unpinch(){
        pynchyBoiLeft.set(DoubleSolenoid.Value.kReverse);
        pynchyBoiRight.set(DoubleSolenoid.Value.kReverse);
        isPinched = false;

    }
*/    public void runIntake(double power){
        if(compBot) {
            if (Math.abs(power) > 0.05) {
                mLeft.set(-power);
                mRight.set(power); //invert on comp
            } else {
                mLeft.set(0.0);
                mRight.set(0.0);
            }
        } else {
            if (Math.abs(power) > 0.05) {
                mLeft.set(power);
                mRight.set(power); //invert on comp
            } else {
                mLeft.set(0.0);
                mRight.set(0.0);
            }
        }
    }
    public void runLeft(double power){
        mLeft.set(power);
    }

    public void runRight(double power){
        mRight.set(power);
    }
    public void moveWrist(double power){
        mWrist1.set(power);
        mWrist2.set(power);
    }

   /* public void togglePinch(){
        if(isPinched){
            unpinch();
        } else {
            pinch();
        }
    }*/

    public double getTilt(){
        return -(mPot.get() - zeroDegree)/degreesPerTick;
    }

    public void setHoldPosition(){
        if(Math.abs(targetPOS - getTilt()) > .2) {
            targetPOS = getTilt();
            mIntakePID.zero();
        }
    }

    public void setWrist(double pos){
        targetPOS = pos;
    }

    public void giveData(){
        System.out.println("degrees: " + getTilt());
    }

    public void outputLight(){
        //SmartDashboard.putString("DB/String 6","light: " + Integer.valueOf(mLightSensor.getValue()).toString());
    }

    public void PIDIntake(){
        if(Math.abs(targetPOS - getTilt()) > 1.5){
            moveWrist(mIntakePID.iterate(targetPOS, getTilt()) * 0.65 );
        } else if ((Math.abs(targetPOS - getTilt()) < 1.5) && (Math.abs(prevDegree - getTilt()) * 50 < 5.0)){
            moveWrist(mIntakePID.iterate(targetPOS, getTilt()) * 0.4);
        }
        prevDegree = getTilt();
    }

    public void wristUp(){
        targetPOS = Constants.INSTANCE.getPotUp();
    }

    public void wristStraight(){
        targetPOS = Constants.INSTANCE.getPotMid();
    }

    public void wristDanny(){
        targetPOS = Constants.INSTANCE.getPotDanny();
    }

    public void wristDown(){
        targetPOS = Constants.INSTANCE.getPotDown();
    }

    public void climbPosition(){
        targetPOS = Constants.INSTANCE.getPotClimb();
    }

    public void initDefaultCommand() {
       // setDefaultCommand(new IntakeControl());
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public double getCurrentLeft(){
        return Robot.getmPDP().getCurrent(11);
    } // 11 practice, 11 comp

    public double getCurrentRight(){
        return Robot.getmPDP().getCurrent(4);
    } //10 practice, 4 comp

    public void leftOn(){
        mLEDLeft.set(true);
    }

    public void leftOff(){
        mLEDLeft.set(false);
    }

    public void rightOn(){
        mLEDRight.set(true);
    }

    public void rightOff(){
        mLEDRight.set(false);
    }




}