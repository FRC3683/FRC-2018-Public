package frc.team3683.burd.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;
import frc.team3683.burd.configs.Constants;

import static frc.team3683.burd.Robot.compBot;
import static frc.team3683.burd.commands.ElevatorControl.climbSetup;
import static frc.team3683.burd.commands.ElevatorControl.climbTime;

/**
 * Manual intake controls
 */
public class IntakeControl extends Command {

    private OI oi;
    private int ticks;
    private boolean climbing;
    public IntakeControl() {
        // Use requires() here to declare subsystem dependencies
        oi = OI.getInstantce();
        requires(Robot.getmIntake());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        ticks = 0;
        climbing = false;
        Robot.getmIntake().setHoldPosition();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(oi.getYButtonDown()){
            //Robot.getmIntake().togglePinch();
        }
       /* if(oi.getDPadUp()){
            if(oi.getRightTrigger() > 0.2){
                Robot.getmIntake().runLeft(oi.getRightTrigger());
                Robot.getmIntake().runRight(oi.getRightTrigger());
            } else if(oi.getLeftTrigger() > 0.2){
                Robot.getmIntake().runLeft(-oi.getLeftTrigger());
                Robot.getmIntake().runRight(-oi.getLeftTrigger());
            } else {
                Robot.getmIntake().runIntake(0.0);
            }
        } else if (oi.getDPadDown()){
            if(oi.getRightTrigger() > 0.2){
                Robot.getmIntake().runLeft(-oi.getRightTrigger());
                Robot.getmIntake().runRight(-oi.getRightTrigger());
            } else if(oi.getLeftTrigger() > 0.2){
                Robot.getmIntake().runLeft(oi.getLeftTrigger());
                Robot.getmIntake().runRight(oi.getLeftTrigger());
            } else {
                Robot.getmIntake().runIntake(0.0);
            }
        } else {*/

        if (oi.getRightStickButton()){
            if(compBot) {
                Robot.getmIntake().runRight(-1.0);
                Robot.getmIntake().runLeft(0);
            } else {
                Robot.getmIntake().runRight(0);
                Robot.getmIntake().runLeft(1.0);
            }
        } else {
            if (oi.getYButton() || oi.getXButton() || oi.getBButton() || oi.getRightBumper()) {
                if (oi.getRightTrigger() > 0.2) {
                    Robot.getmIntake().runIntake(oi.getRightTrigger() * oi.getRightTrigger() * -.4);
                } else if (oi.getLeftTrigger() > 0.2) {
                    Robot.getmIntake().runIntake(-oi.getLeftTrigger() * oi.getLeftTrigger() * -.4);
                } else {
                    Robot.getmIntake().runIntake(0.0);
                }
            } else {
                if (oi.getRightTrigger() > 0.2) {
                    Robot.getmIntake().runIntake(oi.getRightTrigger() * oi.getRightTrigger() * -1.0);
                } else if (oi.getLeftTrigger() > 0.2) {
                    Robot.getmIntake().runIntake(-oi.getLeftTrigger() * oi.getLeftTrigger() * -1.0);
                } else {
                    Robot.getmIntake().runIntake(0.0);
                }
            }
        }



        if(oi.getDPadUp()){
            Robot.getmIntake().moveWrist(0.8);
            Robot.getmIntake().setHoldPosition();

        } else if(oi.getDPadDown()){
            Robot.getmIntake().moveWrist(-0.8);
            Robot.getmIntake().setHoldPosition();

        } else if (oi.getAButton()){
            Robot.getmIntake().wristDown();

        } else if(oi.getRightBumper()) {
            Robot.getmIntake().wristStraight();
        } else{
            Robot.getmIntake().wristUp();
            //Robot.getmIntake().moveWrist(0.0);

        }
        if(oi.getStartButton()){
            climbing = true;
        } else if(climbTime || climbSetup){
            climbing = true;
        } else {
            climbing = false;
        }

        if(climbing){
            Robot.getmIntake().climbPosition();
        }
            //Robot.getmIntake().moveWrist(0.0);
//          );

        //System.out.println("Left current: " + Robot.getmIntake().getCurrentLeft());
        //System.out.println("Right Current: " + Robot.getmIntake().getCurrentRight());
        Robot.getmIntake().moveWrist(0.0); //I know this looks useless bus it makes it stable DO NOT DELETE.
        if(!Constants.INSTANCE.getReadPot()) {
            Robot.getmIntake().PIDIntake();
        } else {
            Robot.getmIntake().giveData();
        }


        if(Robot.getmIntake().getCurrentRight() > 25.0 || Robot.getmIntake().getCurrentLeft() > 25.0){
            oi.rumble(0.6);
        } else {
            oi.rumble(0.0);
        }
        if(oi.getLeftStickButton()) {
            if (ticks < 3.45) {
                Robot.getmIntake().leftOn(); //works
                Robot.getmIntake().rightOff();
            } else if (ticks < 6.9) {
                Robot.getmIntake().rightOn();
                Robot.getmIntake().leftOff(); //works
            } else {
                ticks = 0;
            }
        } else {
            Robot.getmIntake().rightOff();
            Robot.getmIntake().leftOff();
            ticks = 0;
        }
        //SmartDashboard.putString("DB/String 5", String.valueOf(Robot.getmintake().getTilt()));
        //Robot.getmIntake().outputLight();
        ticks ++;
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
