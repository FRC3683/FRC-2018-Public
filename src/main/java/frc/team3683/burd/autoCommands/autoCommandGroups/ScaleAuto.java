package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class ScaleAuto extends CommandGroup {

    public ScaleAuto(){
        addParallel(new AutoGearShift(true));
        addParallel(new WristCommand(-20));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getHighScale(), 2.0));
        addParallel(new DelayedWristCommand(-55, 2.0));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(12, 169),
                new Point(-21, 205),
                new Point(-23, 232), //272 for realsies
                5.5,
                1.0));
        addSequential(new WaitCommand(0.3));
        addSequential(new IntakeCommand(0.3, 0.5));
        addParallel(new WristCommand(-20));
        addParallel(new DelayedElevatorCommand(1.0, 0.8));
        addSequential(new DriveStraight(-36, 0.5, 1.0));
        addSequential(new WaitCommand(0.25));

        addSequential(new WaitCommand(1.0));
        addParallel(new WristCommand(-105));
        addSequential(new DriveAngle(180, 0.8, 1.5));
        //addParallel(new IntakeCommand(-0.8, 5.0));
        addSequential(new DriveStraight(21, 0.5, 2.0));
        addSequential(new DriveAngle(30, 0.5, 1.0));
        addParallel(new CurrentIntakeCommand(0.6));
        addSequential(new DriveStraight(12, 0.2, 2.0));

    }
}
