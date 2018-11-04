package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class PranitTwoCubeRightRightSideSwitchDR extends CommandGroup {

    public PranitTwoCubeRightRightSideSwitchDR(){

        addParallel(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        /*addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(40, 120),
                new Point(0, 155),
                new Point(-18, 145),
                3.0,
                1.0));*/

        addSequential(new DriveStraightAbsoluteDec(50, 1.0, 1.0, 0, 6.0, false, 0.8, 0.9));
        addSequential(new DriveAngleAbsolute(-90, 0.8, 1.0));
        addSequential(new DriveStraightAbsoluteDec(66, 1.0, 4.0, -90, 6.0, false, 0.8, 0.9));
        //addParallel(new AutoGearShift(false));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 20));
        addSequential(new DriveAngleAbsolute(0.0, 1.0, 1.0));
        addSequential(new DriveStraightAbsoluteDec(54, 1.0, 2.0, 0, 12.0, true, 0.4, 0.5));

        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addSequential(new WaitCommand(0.25));
        addSequential(new IntakeCommand(0.45, 0.5));
        //addSequential(new DriveStraight(-36, 0.8, 1.0));

        //addSequential(new DriveStraightAbsolute(-36, 0.8, 1.0, -135, 1.0, true, 0.0));



//copied from WhichWayRight
//WIP WIP WIP WIP
        //Score preload


        //prep to pickup second cube (1st from pyramid)
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getBottom(), 0.5));
        addSequential(new DriveStraightAbsoluteDec(-58, 1.0, 1.8, 0, 10.0, true, 0.8, 0.8)); //drive backward from fence
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() - 3));
        addSequential(new DriveAngleAbsolute(-40, 0.6, 0.6)); //turn towards pyramid
        addParallel(new DriveStraightAbsoluteDec(64, 1.0, 2.5, -40, 10.0, true, 0.6, 0.75)); //drive towards pyramid
        addSequential(new CurrentIntakeCommand(0.8));
        //addSequential(new WaitCommand(0.1));

        //Cube is in!
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addParallel(new IntakeCommand(-0.5, 2.5));
        addSequential(new DriveStraightAbsoluteDec(-38, 1.0, 2.0, -40, 4.0, true, 0.8, 0.8)); //drive backward from pyramid, changed from addSequential to addParallel (maybe gives a curve?)
        addSequential(new DriveAngleAbsolute(0, 1.0, 1.5)); //turn toward fence
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
        addSequential(new DriveStraightAbsoluteDec(51, 1.0, 1.0, 0, 1.0, true, 0.4, 0.5)); //drive toward fence
        //addSequential(new DriveStraight(12, 0.4, 0.25));

        //addSequential(new WaitCommand(0.35));
        addSequential(new IntakeCommand(0.4, 0.3));
        addSequential(new DriveStraight(-24, 0.8, 1.0));
        addParallel(new ElevatorCommand(0.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveAngleAbsolute(-90, 0.8, 0.8));
        addSequential(new DriveStraight(-50, 1.0, 3.0));


        }
}
