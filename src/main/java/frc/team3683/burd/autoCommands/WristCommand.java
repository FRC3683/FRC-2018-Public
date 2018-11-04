package frc.team3683.burd.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.OI;
import frc.team3683.burd.Robot;

/**
 * Manual intake controls
 */
public class WristCommand extends Command {

    private OI oi;
    private double pos;
    /**
     runs the intake for a set power and time
     @param pos
        angle for the intake

     */
    public WristCommand(double pos) {
        // Use requires() here to declare subsystem dependencies
        oi = OI.getInstantce();
        requires(Robot.getmIntake());
        this.pos = pos;


    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.getmIntake().setWrist(pos);
        //Robot.getmIntake().runIntake(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Robot.getmIntake().moveWrist(0.0);
        Robot.getmIntake().PIDIntake();
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.getmIntake().moveWrist(0.0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.getmIntake().moveWrist(0.0);

    }
}
