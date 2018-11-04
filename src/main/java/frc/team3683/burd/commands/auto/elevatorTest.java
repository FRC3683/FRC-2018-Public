package frc.team3683.burd.commands.auto;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.commands.ElevatorToScale;

public class elevatorTest extends CommandGroup {
    public elevatorTest(){
        addSequential(new ElevatorToScale());


    }
}