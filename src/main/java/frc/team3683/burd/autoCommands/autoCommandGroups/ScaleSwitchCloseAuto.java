package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class ScaleSwitchCloseAuto extends CommandGroup {

    public ScaleSwitchCloseAuto(){
        addSequential(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getHighScale(), 2.45));
        addParallel(new DelayedWristCommand(Constants.INSTANCE.getPotMid() + 15, 2.45));
        //addSequential(new DriveStraight(240, 0.8, 5.0));
        addSequential(new DrivePathSlow(
                new Point(0, 0),
                new Point(12, 220),//254 for realsies
                new Point(9, 220),//254 for realsies
                new Point(6, 230), //263 for realsies
                5.5,
                0.8));

        addSequential(new WaitCommand(0.75));
        addSequential(new IntakeCommand(0.6, 0.5));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new DelayedElevatorCommand(1.0, 0.8));
        addSequential(new DriveStraight(-18, 0.5, 1.0));
        addSequential(new WaitCommand(1.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveAngleAbsolute(-135, 1.0, 1.0));

        addParallel(new DrivePathSlow(
                new Point(0, 0),
                new Point(-12, 28),//254 for realsies
                new Point(-6, 56),//254 for realsies
                new Point(0, 84), //263 for realsies
                2.0,
                0.4));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new IntakeCommand(-0.4, 0.5));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp() - 5));
        addSequential(new WaitCommand(0.04));
        addParallel(new IntakeCommand(-0.4, 1.5));

        addSequential(new DriveStraightAbsolute(-36, 0.5, 1.0, -135, 1.0, true, 0.0));
        addParallel(new IntakeCommand(-0.4, 1.5));

        addSequential(new DriveStraightAbsolute(-12, 0.5, 1.0, -180, 1.0, true, 0.0));
        addParallel(new IntakeCommand(-0.4, 1.5));

        addSequential(new DrivePathSlow(
                new Point(0, 0),
                new Point(-12, 40),//254 for realsies
                new Point(0, 65),//254 for realsies
                new Point(20, 65), //263 for realsies
                1.6,
                0.5));
        addParallel(new ElevatorCommand(5));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp() - 5));
        addSequential(new WaitCommand(0.15));
        addSequential(new IntakeCommand(0.3, 0.5));

    }
}
