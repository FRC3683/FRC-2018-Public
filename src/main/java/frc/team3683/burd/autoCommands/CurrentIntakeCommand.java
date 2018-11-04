package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * Manual intake controls
 */
public class CurrentIntakeCommand extends Command {

    private OI oi;
    private double power;
    private double ticks;
    /**
        runs the intake for a set power and time
        @param power
            run the intake at this power

        @param timeOut
            How long to run the intake for

     */
    public CurrentIntakeCommand(double power) {
        // Use requires() here to declare subsystem dependencies
        oi = OI.getInstantce();
        requires(Robot.getmIntake());
        this.power = power;

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        ticks = 0;
        //Robot.getmIntake().setHoldPosition();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.getmIntake().runIntake(-power);
        Robot.getmIntake().PIDIntake();
        if(Robot.getmIntake().getCurrentLeft() > 21 || Robot.getmIntake().getCurrentRight() > 21){
            ticks++;
        } else {
            ticks = 0;
        }
        System.out.println("Left current: " + Robot.getmIntake().getCurrentLeft());
        System.out.println("Right Current: " + Robot.getmIntake().getCurrentRight());
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (ticks > 22);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.getmIntake().runIntake(0.0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.getmIntake().runIntake(0.0);

    }
}
