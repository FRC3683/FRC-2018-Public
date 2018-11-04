package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.DrivePath;
import frc.team3683.burd.autoCommands.DriveStraight;
import frc.team3683.burd.autoCommands.WristCommand;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class DriveStraightAuto extends CommandGroup {

    public DriveStraightAuto(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveStraight(30, 0.5, 2.0));

    }
}
