package frc.team3683.burd.commands.auto;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class DriveTest extends CommandGroup {
    public DriveTest(){
        addSequential(new DriveAngleAbsolute(180, 0.8, 5.0));
        addSequential(new DriveStraightAbsolute(24, 1.0, 1.0, 180, 1.0, true, 0.0));
    }
}