package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class TyAutoClose extends CommandGroup {

    public TyAutoClose(){
        addSequential(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        //approach scale
        addSequential(new DriveStraightAbsoluteDec(320, 0.7, 6.0, 4, 1.0, true, 0.5, 0.75));

        //addSequential(new AutoGearShift(true));

        //turn into scale and prepare to shoot
//      addParallel(new AutoGearShift(false));
//      addSequential(new WaitCommand(0.1));
 //     integrated turn and elevator ^^^
        addSequential(new DriveAngleAbsolute(-95, 0.6, 1.2));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 25));
        //addSequential(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new WaitCommand(0.8));

        //addSequential(new DriveStraightAbsolute(34, 0.6, 1.0, -30, 1.0, true, 0.0));

        //addSequential(new DriveStraight(14, 0.6, 0.5));
        //shoot
        addSequential(new IntakeCommand(0.75, 0.5));

        //back off from scale
        addSequential(new DriveStraightAbsolute(-6, 0.4, 1.2, -95, 1.0, true, 0.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.5)); //uncomment below to renable 2 cube
    }
}
