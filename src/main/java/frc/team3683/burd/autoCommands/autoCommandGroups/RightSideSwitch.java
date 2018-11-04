package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class RightSideSwitch extends CommandGroup {

    public RightSideSwitch(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(40, 120),
                new Point(0, 155),
                new Point(-18, 145),
                3.0,
                1.0));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addParallel(new ElevatorCommand(5));
        addSequential(new WaitCommand(0.25));
        addSequential(new IntakeCommand(0.45, 0.5));
        addSequential(new DriveStraight(-12, 0.8, 1.0));
        addSequential(new DriveStraightAbsolute(-36, 0.8, 1.0, -135, 1.0, true, 0.0));
        }
}
