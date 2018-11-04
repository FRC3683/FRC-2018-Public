package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.commands.Drive;
import frc.team3683.burd.configs.Constants;

public class RightSwitchDrop extends CommandGroup {

    public RightSwitchDrop(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new AutoGearShift(true));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveStraightAbsoluteDec(158, 1.0, 2.0, 0, 1.0, true, 0.5, 0.5));
        addSequential(new DriveAngleAbsolute(-90, 0.8, 1.0));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 10));
        addSequential(new DriveStraight(36, 0.4, 1.0));
        addSequential(new IntakeCommand(0.4, 0.5));
        addSequential(new DriveStraight(-18, 0.5, 1.0));
        addParallel(new ElevatorCommand(0.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.5));


    }
}
