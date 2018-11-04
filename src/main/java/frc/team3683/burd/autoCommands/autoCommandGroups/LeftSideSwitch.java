package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class LeftSideSwitch extends CommandGroup {

    public LeftSideSwitch(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(-11, 64),
                new Point(-184, 22),
                new Point(-169, 121),
                7.0,
                1.0));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addParallel(new ElevatorCommand(5));
        addSequential(new WaitCommand(0.25));
        addSequential(new IntakeCommand(0.45, 0.5));
        }
}
