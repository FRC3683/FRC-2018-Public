package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.commands.Drive;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class WhichWayLeft extends CommandGroup {

    public WhichWayLeft(){
        addParallel(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        addSequential(new WaitCommand(0.1));

        addSequential(new DrivePathElevator(
                new Point(0, 0),
                new Point(0, 75),
                new Point(-50, 38),
                new Point(-47, 102),
                2.0,
                0.65,
                100,
                Constants.INSTANCE.getSwitchHeight(),
                800,
                Constants.INSTANCE.getPotMid(),
                800));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
        addParallel(new DriveStraight(2, 0.25, 0.25));
        addSequential(new IntakeCommand(0.3, 0.5));
        addSequential(new WaitCommand(0.1));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getBottom(), 0.5));
        addSequential(new DriveStraightAbsolute(-82, 1.0, 1.8, 0, 1.0, true, 12.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() - 3));
        addSequential(new DriveAngleAbsolute(42, 0.6, 0.6));
        addParallel(new DriveStraightAbsolute(65, 0.8, 2.5, 43, 1.0, true, 36.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addParallel(new IntakeCommand(-0.5, 2.5));
        addSequential(new DriveStraight(-46, 1.0, 1.25));
        addSequential(new DriveAngleAbsolute(0, 0.8, 0.75));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getSwitchHeight(), 0.75));
        addParallel(new DelayedWristCommand(Constants.INSTANCE.getPotMid(), 0.75));
        addSequential(new DriveStraightAbsolute(49, 1.0, 1.0, 0, 1.0, true, 0.0));
        addSequential(new DriveStraight(12, 0.4, 0.25));
        addSequential(new WaitCommand(0.1));
        addSequential(new IntakeCommand(0.4, 0.3));
        addParallel(new DelayedElevatorCommand(0.0, 0.5));
        addParallel(new DelayedWristCommand(Constants.INSTANCE.getPotMid() - 10, 0.5));
        addSequential(new DriveStraightAbsolute(-36, 1.0, 1.0, 0, 2.0, true, 0.0));
        addParallel(new ElevatorCommand(0.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() - 3));
        addSequential(new DriveAngleAbsolute(45, 0.6, 0.6));
        addParallel(new DriveStraightAbsolute(48, 0.9, 2.5, 45, 1.0, true, 18.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addParallel(new IntakeCommand(-0.4, 0.5));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveStraight(-12, 0.6, 1.0));
        /*addParallel(new DriveStraightAbsolute(66, 0.8, 2.5, 45, 1.0, true, 12.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addParallel(new IntakeCommand(-0.4, 2.0));
        addSequential(new DriveStraight(-42, 0.75, 1.25));
        addSequential(new DriveAngleAbsolute(0, 0.8, 0.75));
        addSequential(new DriveStraight(36, 0.6, 1.0));
        addSequential(new IntakeCommand(0.6, 0.5));*/



       /*addSequential(new DriveStraightAbsolute(-36, 0.8, 1.0, 0, 1.0, true, 6.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveAngleAbsolute(-40, 0.6, 0.6));
       addSequential(new DriveStraightAbsolute(-60, 0.8, 1.5, -45, 1.0, true, 12.0));
       addSequential(new DriveAngleAbsolute(0, 0.8, 0.5));
       addSequential(new WaitCommand(0.1));
       addParallel(new DriveStraight(60, 0.6, 1.5));
       addSequential(new CurrentIntakeCommand(0.8));
       addSequential(new WaitCommand(0.1));
       addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
       addSequential(new WaitCommand(0.1));
       addParallel(new IntakeCommand(-0.4, 2.0));
       addSequential(new DriveStraight(-18, 0.6, 0.6));
       addSequential(new DriveAngleAbsolute(-50, 0.8, 0.8));
       addSequential(new DriveStraight(54, 0.8, 1.0));
       addSequential(new DriveAngleAbsolute(0, 0.8, 0.6));
       addSequential(new DriveStraight(24, 0.35, 0.8));
       addSequential(new IntakeCommand(0.7, 1.0));
       addSequential(new DriveStraight(-12, 0.8, 1.0));*/

        }
}
