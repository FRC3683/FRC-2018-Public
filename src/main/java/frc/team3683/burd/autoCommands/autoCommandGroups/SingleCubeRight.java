package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

@Deprecated
public class SingleCubeRight extends CommandGroup {

    public SingleCubeRight(){
        addSequential(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        //addSequential(new WaitCommand(0.1));

        //approach scale
        addSequential(new DriveStraightAbsoluteDec(268, 1.0, 6.0, 1.0, 1.0, true, 0.5, 0.75));

        //addSequential(new AutoGearShift(true));

        //turn into scale and prepare to shoot
        addParallel(new AutoGearShift(false));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveAngleAbsolute(-45, 0.6, 1.2));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 20));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new WaitCommand(0.8));


        //addSequential(new DriveStraightAbsolute(34, 0.6, 1.0, -30, 1.0, true, 0.0));

        //addSequential(new DriveStraight(14, 0.6, 0.5));
        //shoot
        addSequential(new IntakeCommand(0.3, 0.5));


        //back off from scale
        addSequential(new DriveStraightAbsoluteDec(-25, 0.4, 1.2, -45, 1.0, true, 0.5, 0.75));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.5)); //uncomment below to renable 2 cube

        //drive up to second cube
        addSequential(new DriveAngleAbsolute(-150, 0.65, 0.8));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveStraightAbsoluteDec(30, 1.0, 1.5, -150, 1.0, true, 0.5, 0.75));
        addParallel(new CurrentIntakeCommand(0.8));
        addSequential(new DriveStraightAbsoluteDec(65, 0.5, 1.0, -150, 1.0, true, 0.5, 0.75));
        //take second cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        //drive back to scale
        addParallel(new IntakeCommand(-0.4, 2.0));
        addSequential(new DriveStraightAbsoluteDec(-95, 1.0, 2.0, -150, 1.0, true, 0.5, 0.75));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new DriveAngleAbsolute(-30, 0.6, 1.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 10));
        //shoot again
        addSequential(new DriveStraightAbsoluteDec(25, 0.6, 1.0, -30, 1.0, true, 0.5, 0.75));
        addSequential(new IntakeCommand(0.4, 0.5));
        addSequential(new DriveStraight(-12, 0.5, 1.0));
        addSequential(new ElevatorCommand(0.0));


    }
}
