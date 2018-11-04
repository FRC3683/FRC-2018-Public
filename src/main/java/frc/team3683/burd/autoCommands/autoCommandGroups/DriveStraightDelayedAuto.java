package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class DriveStraightDelayedAuto extends CommandGroup {

    public DriveStraightDelayedAuto(){
       // addSequential(new WaitCommand(Constants.INSTANCE.getAutoWaitTime()));
        addParallel(new AutoGearShift(true));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveStraightAbsolute(10, 1.0, 6.0, 0.0, 0.5,
                true, 0.0));

    }
}
