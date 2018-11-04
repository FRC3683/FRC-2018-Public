package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

@Deprecated
public class StraightScaleAuto extends CommandGroup {

    public StraightScaleAuto(){
        //Initial setup code
        addSequential(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));

//        //Drive to scale
//        addSequential(new DrivePathElevator(
//                new Point(0, 0),
//                new Point(24, 220),//254 for realsies
//                new Point(21, 220),//254 for realsies
//                new Point(18, 230), //263 for realsies
//                5.5,
//                0.8,
//                250,
//                Constants.INSTANCE.getHighScale(),
//                600,
//                Constants.INSTANCE.getPotMid() + 25,
//                600));
//
//
//        //Raise up intake and turn
//        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
//        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
//        addSequential(new DriveAngleAbsolute(-55, 0.6, 0.8));
//
//        //SPITTT
//        addSequential(new WaitCommand(0.1));
//        addSequential(new IntakeCommand(0.45, 0.5));

        // I imagine this is the same code but in a simple drive straight format

        addSequential(new DriveStraightAbsolute(245, 1.0, 6.0, 0.0, 4.0, true, 36.0, false, 200));
        addSequential(new AutoGearShift(true));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
        addSequential(new DriveStraightAbsolute(12, 0.6, 2.0, 0.0, 2.0, true, 0.0));
        addSequential(new DriveAngleAbsolute(-45, 1.0, 1.0));
        //addSequential(new DriveStraight(14, 0.6, 0.5));
        addSequential(new IntakeCommand(0.45, 0.5));



        //Pull wrist up and back up
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveStraightAbsolute(-18, 0.7, 0.8, -50, 2.0, true, 12.0));

        //pull intake down
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.3)); //uncomment below to renable 2 cube

        //Turn towards 2nd cube
        addSequential(new DriveAngleAbsolute(-155, 1.0, 1.0));

        //Put wrist down and start driving towards the cubd
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveStraightAbsolute(38.0, 1.0, 3.0, -155, 5.0, true, 18.0));

        //Intake the cube
        addParallel(new DriveStraightAbsolute(32.0, 0.5, 3.0, -155, 1.0, true, 24.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new IntakeCommand(-0.4, 0.5));

        //Bumb wrist up and drive backward
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp() - 5));
        addSequential(new WaitCommand(0.04));
        addParallel(new IntakeCommand(-0.4, 1.5));
        addSequential(new DriveStraight(-42, 0.8, 1.0));

        //Turn towards the scale
        addSequential(new WaitCommand(0.05));
        addParallel(new IntakeCommand(-0.4, 1.5));
        addSequential(new DriveAngleAbsolute(0, 0.8, 0.9));

        //Drive towards the scale
        addParallel(new IntakeCommand(-0.3, 1.5));
        addSequential(new DriveStraightAbsolute(42, 0.9, 1.5, 0, 1.0, true, 0.0));

        //Raise the intake
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 25));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new DriveAngleAbsolute(-80, 0.6,  0.8));
       /* addSequential(new DrivePathSlow(
                new Point(0, 0),
                new Point(-9, 15),//254 for realsies
                new Point(-14.5, 30),//254 for realsies
                new Point(-18, 45), //263 for realsies
                1.3,
                0.75));*/

       //SPITTTT
        addSequential(new IntakeCommand(0.3, 0.5));
        addSequential(new DriveStraight(-6, 0.6, 1.0));
        addSequential(new WaitCommand(0.1));
        //addSequential(new DriveStraight(-24, 0.4, 1.0));

        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new ElevatorCommand(1));
        /*addParallel(new WristCommand(-105));
        addSequential(new DriveStraight(12, 0.8, 1.0));
        addParallel(new CurrentIntakeCommand(0.8));
        addSequential(new DriveStraight(24, 0.2, 5.0));
        */


        /*addParallel(new WristCommand(-105));
        addParallel(new CurrentIntakeCommand(0.8));
        addSequential(new DrivePath(
                new Point(0, 0),
                new Point(0, 20),
                new Point(10, 20),
                new Point(20, 40),
                5.5,
                1.0));

*/

    }
}
