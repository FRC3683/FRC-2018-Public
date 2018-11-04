package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class PranitTwoCubeRightLeftSideSwitchDR extends CommandGroup {

    public PranitTwoCubeRightLeftSideSwitchDR(){
        addParallel(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        /*addSequential(new DrivePath(
                new Point(0, 0),
                new Point(-44, 50),
                new Point(-150, 55),
                new Point(-175, 95),
                7.0,
                1.0));*/

        addSequential(new DriveStraightAbsoluteDec(50, 1.0, 1.0, 0, 6.0, true, 0.8, 0.9));
        addSequential(new DriveStraightAbsoluteDec(170, 1.0, 4.0, -90, 6.0, true, 0.8, 0.9));
        addParallel(new AutoGearShift(false));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 20));
        addSequential(new DriveStraightAbsoluteDec(40, 1.0, 2.0, 0, 6.0, true, 0.4, 0.5));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
/*
        addParallel(new ElevatorCommand(5));
        addSequential(new WaitCommand(0.25));
        addSequential(new IntakeCommand(0.45, 0.5));
*/

//copied from WhichWayLeft
        //score preload
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
        addParallel(new DriveStraight(2, 0.25, 0.25));
        addSequential(new IntakeCommand(0.3, 0.5));
        //addSequential(new WaitCommand(0.1));

        //prep to pick up 2nd cube (1st cube from pyramid)
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getBottom(), 0.5));
        addSequential(new DriveStraightAbsoluteDec(-70, 1.0, 3, 0, 10.0, true, 0.8, 0.8));
        //back from switch fence

        /*
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveAngleAbsolute(44.0, 0.6, 0.6));//turn towards pyramid
        addParallel(new DriveStraightAbsoluteDec(47, 1.0, 3, 44.0, 2.0, true, 0.7, 0.8));//approach pyramid
        addSequential(new CurrentIntakeCommand(0.8));
        //addSequential(new WaitCommand(0.1));

        //Cube is in!
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        //addSequential(new WaitCommand(0.1));
        addParallel(new IntakeCommand(-0.5, 2.5));
        //drive back from pyramid
        addParallel(new DriveStraightAbsoluteDec(-46, 1.0, 2.0, 43, 10.0, false, 0.8, 0.8)); //changed from addSequential to addParallel (maybe gives curve?)
        addSequential(new DriveAngleAbsolute(0, 1.0, 0.75));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getSwitchHeight(), 0.75));
        addParallel(new DelayedWristCommand(Constants.INSTANCE.getPotMid(), 0.75));
        //drive to switch fence
        addSequential(new DriveStraightAbsoluteDec(70, 1.0, 1.0, 0, 1.0, true, 0.8, 0.8));
        //addSequential(new DriveStraight(12, 0.4, 0.25));
        //score cube
        //addSequential(new WaitCommand(0.1));
        addSequential(new IntakeCommand(0.4, 0.3));
        addParallel(new DelayedElevatorCommand(0.0, 0.5));

*/


        }
}
