package frc.team3683.burd.configs

import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj.*
import edu.wpi.first.wpilibj.interfaces.Potentiometer
import frc.team3683.burd.utils.BNO055
import frc.team3683.burd.utils.TCS34725

interface Config {
    //drivebase
    val driveVictorLeft1 : VictorSPX
    val driveVictorLeft2 : VictorSPX
    val driveVictorRight1 : VictorSPX
    val driveVictorRight2 : VictorSPX
    val driveEncoderLeft : Encoder
    val driveEncoderRight : Encoder
    val driveTicksPerInchLeft: Double
    val driveTicksPerInchRight : Double
    val driveSolenoid : DoubleSolenoid
    val driveGyro : BNO055
    val colorSensor : TCS34725
    val driveP : Double
    val driveI : Double
    val driveD : Double
    val gyroP : Double
    val gyroI : Double
    val gyroD : Double

    //bezier
    val xMultiplier : Double
    val yMultiplier : Double

    //intake
    val intakeVictorLeft : Victor
    val intakeVictorRight : Victor
    val wristVictor1 : Victor
    val wristVictor2 : Victor
   // val intakeSolenoidLeft : DoubleSolenoid
   // val intakeSolenoidRight : DoubleSolenoid
    val intakeP : Double
    val intakeI : Double
    val intakeD : Double
    val Pot : AnalogPotentiometer
    val LightSensor : AnalogInput
    val ticksPerDegree : Double
    val zeroDegreeTicks : Double
    val LEDLeft : Solenoid
    val LEDRight : Solenoid
    //elevator
    val elevatorVictor1 : Victor
    val elevatorVictor2 : Victor
    val elevatorVictor3 : Victor
    val elevatorVictor4 : Victor
    val elevatorVictor5 : Victor
    val elevatorEncoder : Encoder
    val elevatorP : Double
    val elevatorI : Double
    val elevatorD : Double
    val ticksPerInch : Double
    val elevatorTolerance : Double
    val holoflexDown : DigitalInput
    val holoflexUp : DigitalInput
    //val ratchetSolenoid : DoubleSolenoid
    //wrist
}