package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.DriveStraight;
import frc.team3683.burd.autoCommands.WaitCommand;
import frc.team3683.burd.autoCommands.WristCommand;
import frc.team3683.burd.configs.Constants;

public class DoNothingAuto extends CommandGroup {

    public DoNothingAuto(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(15.0));

    }
}
