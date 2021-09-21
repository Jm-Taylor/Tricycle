package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//TODO add limit switches and incorporate encoders
public class Elevator
{
    // Hardware Map
    HardwareMap hardwareMap;

    // Objects
    private DcMotor lift;

    public Elevator(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;

        // Motors
        lift = hardwareMap.get(DcMotor.class, "elevatorMotor");
        lift.setDirection(DcMotor.Direction.REVERSE);
    }

    /**
     * Moves the elevator up or down based on the speed
     * <p>
     * @param speed to move the elevator up or down
     */
    public void moveElevator(double speed)
    {
        lift.setPower(speed);
    }
}
