package frc.team3683.burd.commands.auto;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.DriveAngle;
import frc.team3683.burd.autoCommands.DriveStraight;

public class shittySquare extends CommandGroup {
    public shittySquare(){
        addSequential(new DriveStraight(60, 1.0, 1.0));
        addSequential(new DriveAngle(90, .8, 1.0));
        addSequential(new DriveStraight(60, 1.0, 1.0));
        addSequential(new DriveAngle(90, .8, 1.0));
        addSequential(new DriveStraight(60, 1.0, 1.0));
        addSequential(new DriveAngle(90, .8, 1.0));
        addSequential(new DriveStraight(60, 1.0, 1.0));
        addSequential(new DriveAngle(90, .8, 1.0));

    }
}