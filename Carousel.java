package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

public class Carousel
{
    // Hardware Map
    HardwareMap hardwareMap;

    // Alliance
    Utilities.Alliance alliance;

    // Objects
    private CRServo carouselMotor;

    public Carousel(HardwareMap hardwareMap, Utilities.Alliance alliance)
    {
        this.hardwareMap = hardwareMap;
        this.alliance = alliance;

        // Motors
        carouselMotor = hardwareMap.get(CRServo.class, "carousel");
    }

    /**
     * Spins the carousel
     * This method will know what alliance the robot is on and change the direction by default
     * <p>
     * @param speed to spin the carousel at.
     */
    public void spinCarousel(int speed)
    {
        if (alliance == Utilities.Alliance.BLUE)
        {
            carouselMotor.setDirection(CRServo.Direction.FORWARD);
            carouselMotor.setPower(Math.abs(speed));
        }
        else if (alliance == Utilities.Alliance.RED)
        {
            carouselMotor.setDirection(CRServo.Direction.REVERSE);
            carouselMotor.setPower(Math.abs(speed));
        }
    }
}
