package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * Manual intake controls
 */
public class IntakeCommand extends Command {

    private OI oi;
    private double power;
    private double timeOut;
    /**
        runs the intake for a set power and time
        @param power
            run the intake at this power

        @param timeOut
            How long to run the intake for

     */
    public IntakeCommand(double power, double timeOut) {
        // Use requires() here to declare subsystem dependencies
        oi = OI.getInstantce();
        requires(Robot.getmIntake());
        this.power = power;
        this.timeOut = timeOut;
        setTimeout(timeOut);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //Robot.getmIntake().setHoldPosition();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.getmIntake().runIntake(power);
        Robot.getmIntake().PIDIntake();
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
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
