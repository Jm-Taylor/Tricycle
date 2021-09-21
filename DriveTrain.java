package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain
{
    // Hardware Map
    HardwareMap hardwareMap;

    // Objects
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor backDrive;
    private AHRS navX;
    private PID yawPID;
    private PID yawAlliancePID;

    // Alliance
    Utilities.Alliance alliance;

    // PID Constants
    private final double TARGET_ANGLE_DEFAULT = 0.0;
    private final double TARGET_ANGLE_BLUE = 90.0;
    private final double TARGET_ANGLE_RED = -90.0;
    private final double MIN_MOTOR_OUTPUT_VALUE = -1.0;
    private final double MAX_MOTOR_OUTPUT_VALUE = 1.0;
    private final double MIN_INTEGRATOR_VALUE = -0.5;
    private final double MAX_INTEGRATOR_VALUE = 0.5;
    private final double YAW_PID_P = 0.2;
    private final double YAW_PID_I = 0.0;
    private final double YAW_PID_D = 0.008;
    private final double TAU = 0.02;
    private final double TIME = 0.02;
    private final double TOLERANCE = 2.0;

    public DriveTrain(HardwareMap hardwareMap, Utilities.Alliance alliance)
    {
        this.hardwareMap = hardwareMap;
        this.alliance = alliance;

        // Motors
        leftDrive = hardwareMap.get(DcMotor.class, "leftMotor");
        rightDrive = hardwareMap.get(DcMotor.class, "rightMotor");
        backDrive = hardwareMap.get(DcMotor.class, "backMotor");

        // Motor flags
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        backDrive.setDirection(DcMotor.Direction.REVERSE);

        // Sensors
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"),
                AHRS.DeviceDataType.kProcessedData);

        // Controllers
        yawPID = new PID();
        yawAlliancePID = new PID();

        // Default Yaw PID (This is for the first gate)
        yawPID.setPoint = TARGET_ANGLE_DEFAULT;
        yawPID.kP = YAW_PID_P;
        yawPID.kI = YAW_PID_I;
        yawPID.kD = YAW_PID_D;
        yawPID.tau = TAU;
        yawPID.limMin = MIN_MOTOR_OUTPUT_VALUE;
        yawPID.limMax = MAX_MOTOR_OUTPUT_VALUE;
        yawPID.limMinInt = MIN_INTEGRATOR_VALUE;
        yawPID.limMaxInt = MAX_INTEGRATOR_VALUE;
        yawPID.T = TIME;
        yawPID.tolerance = TOLERANCE;

        // Alliance Yaw PID (This is for the second gate where the shared goal is)
        if (alliance == Utilities.Alliance.BLUE)
        {
            yawAlliancePID.setPoint = TARGET_ANGLE_BLUE;
        }
        else if (alliance == Utilities.Alliance.RED)
        {
            yawAlliancePID.setPoint = TARGET_ANGLE_RED;
        }
        yawAlliancePID.kP = YAW_PID_P;
        yawAlliancePID.kI = YAW_PID_I;
        yawAlliancePID.kD = YAW_PID_D;
        yawAlliancePID.tau = TAU;
        yawAlliancePID.limMin = MIN_MOTOR_OUTPUT_VALUE;
        yawAlliancePID.limMax = MAX_MOTOR_OUTPUT_VALUE;
        yawAlliancePID.limMinInt = MIN_INTEGRATOR_VALUE;
        yawAlliancePID.limMaxInt = MAX_INTEGRATOR_VALUE;
        yawAlliancePID.T = TIME;
        yawAlliancePID.tolerance = TOLERANCE;
    }

    /**
     * Holonomic Drive allows for the robot to be driven in a holonomic way
     * <p>
     * @param x axis (Left / Right movement)
     * @param y axis (Forward / Backward movement)
     * @param z axis (Rotational movement)
     */
    public void holonomicDrive(double x, double y, double z)
    {
        double leftPower;
        double rightPower;
        double backPower;

        backPower = Range.clip(-x + z, -1.0, 1.0);
        leftPower = Range.clip((x / 2) + (y * Math.sqrt(3) / 2) + z, -1.0, 1.0);
        rightPower = Range.clip((x / 2) + (-(y * (Math.sqrt(3) / 2))) + z, -1.0, 1.0);

        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        backDrive.setPower(backPower);
    }

    /**
     * Call to get the yaw angle from the navX2
     * <p>
     * @return the yaw angle ±180°
     */
    public double getYaw()
    {
        return navX.getYaw();
    }

    /**
     * Call to zero the yaw of the navX2
     */
    public void zeroYaw()
    {
        navX.zeroYaw();
    }

    /**
     * Call to close the navX2
     * <p>
     * This will help in limiting resources when its not required anymore
     */
    public void navXClose()
    {
        navX.close();
    }

    /**
     * Will turn the robot to the default starting angle aka 0.0 degrees or facing the other alliance
     */
    public void turnToDefault()
    {
        holonomicDrive(0.0,0.0, yawPID.calculate(navX.getYaw()));
    }

    /**
     * Will turn the robot to the angle required to drive into the shared object space aka facing the carousels
     */
    public void turnToSharedObject()
    {
        holonomicDrive(0.0, 0.0, yawAlliancePID.calculate(navX.getYaw()));
    }
}
