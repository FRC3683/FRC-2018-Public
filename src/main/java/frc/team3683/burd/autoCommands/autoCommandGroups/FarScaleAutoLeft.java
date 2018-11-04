package frc.team3683.burd.autoCommands.autoCommandGroups;

//import com.sun.corba.se.impl.orbutil.closure.Constant;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class FarScaleAutoLeft extends CommandGroup {

    public FarScaleAutoLeft(){
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addSequential(new AutoGearShift(true));

        addSequential(new DrivePathSlow(
                new Point(0, 0),
                new Point(-12, 188),//254 for realsies
                new Point(-3, 188),//254 for realsies
                new Point(20, 189), //263 for realsies
                5.5,
                0.8));
        //addSequential(new WaitCommand(0.25));
        addSequential(new DriveStraightAbsolute(145, 1.0, 2.0, 90, 3, false, 18));
        addSequential(new DrivePathElevator(//x and y are relative to robot i.e. they are backwards since robot is rotated 90
                new Point(0, 0),
                new Point(46*0.8, 65*0.8),
                new Point(-21*0.85, 58*0.8),
                new Point(-44*0.9, 24*0.85),
                1.4,
                0.65,
                100,
                Constants.INSTANCE.getHighScale(),
                600,
                Constants.INSTANCE.getPotMid() + 25,
                600));


        addSequential(new DriveStraightAbsolute(42, 0.8, 1.2, -35, 1.0, true, 6));

        addSequential(new WaitCommand(0.2));
        addSequential(new IntakeCommand(0.3, 0.25));
        addParallel(new DelayedWristCommand(Constants.INSTANCE.getPotUp() - 2, 0.3));
        addSequential(new DriveStraightAbsolute(-8, 0.4, 1.0, -15, 1.0, true, 0.0));
        addParallel(new ElevatorCommand(0.0));
        addSequential(new DriveStraightAbsolute(-20, 0.6, 1.0, -15, 1.0, true, 18.0));

        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveAngleAbsolute(-135, 1.0, 1.0));
        addParallel(new DriveStraightAbsolute(80.0, 1.0, 2.0, -135, 1.0, true, 24.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new WaitCommand(.1));
        addParallel(new IntakeCommand(-0.3, 1.0));
        addSequential(new DriveStraight(-32, 0.8, 1.0));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveAngleAbsolute(-20, 0.8, 0.5));
        addSequential(new DriveStraightAbsolute(40.0, 0.8, 1.0, -20, 1.0, true, 6.0));
        addSequential(new IntakeCommand(0.4, 0.5));
        addSequential(new DriveStraight(-8, 0.4, 0.5));
        addParallel(new ElevatorCommand(0.0));
        addSequential(new DriveStraight(-24, 0.5, 1.0));

      //  addSequential(new DriveStraightAbsolute(12, 0.8, 0.5, 60, 1));


    }
}
