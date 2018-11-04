package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class SwitchAuto extends CommandGroup {

    public SwitchAuto(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(0, 90),
                new Point(60, 50),
                new Point(56, 125),
                3.0,
                1.0));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addSequential(new WaitCommand(0.25));
        addSequential(new IntakeCommand(0.3, 0.5));
        }
}
