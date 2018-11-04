package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3683.burd.Robot;
import frc.team3683.burd.subsystems.Intake;

public class IntakeAuto extends Command {
    public enum Action{
        PINCHED, UNPINCHED, SHOOT, PICKUP
    }
    private Action currentAction;

    public IntakeAuto(Action action) {
        requires(Robot.getmIntake());
        currentAction = action;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
       switch (currentAction){
           case SHOOT:
               Robot.getmIntake().runIntake(1.0);
               break;
           case PICKUP:
               Robot.getmIntake().runIntake(-1.0);
               break;
           case PINCHED:
               //Robot.getmIntake().pinch();
               break;
           case UNPINCHED:
               //Robot.getmIntake().unpinch();
               break;
           default:
               System.out.println("Intake auto shouldn't be here");
       }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        switch (currentAction){
            case PINCHED:
            case UNPINCHED:
                return true;
            case SHOOT:
                return true;
            case PICKUP:
                return true;
            default:
                return true;
        }
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
