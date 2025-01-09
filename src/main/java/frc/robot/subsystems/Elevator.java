package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfigAccessor;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.spark.SparkBase.ResetMode;

public class Elevator extends SubsystemBase{

    private final SparkMax leftSparkMax;
    private final SparkMax rightSparkMax;

    private final SparkBaseConfig leftConfig;
    private final SparkBaseConfig rightConfig;

    private final AbsoluteEncoder absoluteEncoder; 

    private final PIDController pid;

    public ElevatorState elevatorState;

    public Elevator() {
        leftSparkMax = new SparkMax(Constants.ElevatorConstants.leftSparkMaxID, MotorType.kBrushless);
        rightSparkMax = new SparkMax(Constants.ElevatorConstants.leftSparkMaxID, MotorType.kBrushless);

        leftConfig = new SparkMaxConfig();
        rightConfig = new SparkMaxConfig();

        rightConfig.follow(leftSparkMax);

        leftConfig.inverted(Constants.ElevatorConstants.leftSparkMaxInverted);
        rightConfig.inverted(Constants.ElevatorConstants.rightSparkMaxInverted);

        leftSparkMax.configure(leftConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightSparkMax.configure(rightConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        absoluteEncoder = leftSparkMax.getAbsoluteEncoder();

        pid = new PIDController(Constants.ElevatorConstants.PID.kP, Constants.ElevatorConstants.PID.kI, Constants.ElevatorConstants.PID.kD, Constants.ElevatorConstants.PID.kF);
    
        elevatorState = ElevatorState.DOWN;
    }

 

    //pidloop
    private double goal; 
    @Override
    public void periodic() {
        setOutput(pid.calculate(getEncoderAbsolutePosition(), goal));
         
    }


   //elevator states
    public enum ElevatorState {
        DOWN,
        L1,
        L2,
        L3,
        L4
    }


/*FUNCTIONS*/

    //set motor outputs
    public void setOutput(double output) {
        leftSparkMax.set(output);
        rightSparkMax.set(output);
    }

    //get absolute encoder rotations 
    public double getEncoderAbsolutePosition() {
    return absoluteEncoder.getPosition();
    }

 
    //move elevator to DOWN position
    public void goToDOWN() {
        elevatorState = ElevatorState.DOWN;
        goal = Constants.ElevatorConstants.SetpointRotations.DOWN;
    }


    //move elevator to L1 position 
    public void goToL1() {
        elevatorState = ElevatorState.L1;
        goal = Constants.ElevatorConstants.SetpointRotations.L1;
    }


    //move elevator to L2 position
    public void goToL2() {
        elevatorState = ElevatorState.L2;
        goal = Constants.ElevatorConstants.SetpointRotations.L2;
    }

    //move elevator to L3 position
    public void goToL3() {
        elevatorState = ElevatorState.L3;
        goal = Constants.ElevatorConstants.SetpointRotations.L3;
    }

    //move elevator to L4 position
    public void goToL4() {
        elevatorState = ElevatorState.L4;
        goal = Constants.ElevatorConstants.SetpointRotations.L4;
    }

    
/*LOGGERS*/
}
