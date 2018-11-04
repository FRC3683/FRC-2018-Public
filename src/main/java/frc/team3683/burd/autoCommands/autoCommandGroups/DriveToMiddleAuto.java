package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class DriveToMiddleAuto extends CommandGroup {

    public DriveToMiddleAuto(){

        //Initial conditions
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addSequential(new AutoGearShift(true));

        //Drive forward
        addSequential(new DriveStraightAbsoluteDec(212, 1.0, 6.0, 0.0, 1.0, true, 0.5, 0.75));

        //turn 90
        addSequential(new DriveAngleAbsolute(-90, 0.8, 3.0));

        //Drive towards the far side auto
        addSequential(new DriveStraightAbsoluteDec(111, 1.0, 6.0, -90.0, 1.0, true, 0.5, 0.75));
/*
        //Turn back towards the scale
        addSequential(new DriveAngleAbsolute(0, 0.8, 3.0));

        //Drive towards the scale
        addSequential(new DriveStraightAbsoluteDec(61, 1.0, 6.0, 0.0, 1.0, true, 0.5, 0.75));

        //Turn towards a shooting position
        addSequential(new DriveAngleAbsolute(60, 0.8, 3.0));

        //Shooting stuff goes up and shoot, pew pew pew
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 25));
        addSequential(new WaitCommand(0.8));
        addSequential(new IntakeCommand(0.55, 0.35));

        //Bring the elevator and stuff down
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.1));

        //Turn towards the cube. Start to nom the cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveAngleAbsolute(150, 0.65, 0.8));
        addParallel(new DriveStraightAbsoluteDec(80, 1.0, 1.5, 150, 1.0, true, 0.4, 0.75));
        addSequential(new CurrentIntakeCommand(0.8));

        //Go back towards the scale
        addParallel(new IntakeCommand(-0.4, 2.0));
        addSequential(new DriveStraightAbsoluteDec(-80, 1.0, 1.5, 150, 1.0, true, 0.4, 0.75));

        //Turn back, and shoot
        addSequential(new DriveAngleAbsoluteElevator(60,
                0.6,
                1.5,
                Constants.INSTANCE.getHighScale(),
                30,
                Constants.INSTANCE.getPotMid() + 25,
                30));

        addSequential(new IntakeCommand(0.35, 0.25));

        //Go back to a dan friendly position
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveAngleAbsolute(-90, 0.6, 1.5));
        */
    }
}
