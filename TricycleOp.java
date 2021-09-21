package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Tricycle Op Mode Blue", group = "OpMode")
public class TricycleOp extends OpMode
{
    private DriveTrain drive;
    private Carousel carousel;
    private Elevator elevator;

    // Debug
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * Set your Alliance color here
     * This will auto update subsystem code for the correct movements to be
     * done by the robot.
     */
    Utilities.Alliance alliance = Utilities.Alliance.BLUE;

    /**
     * Code that will run when init button is pressed
     */
    @Override
    public void init()
    {
        drive = new DriveTrain(hardwareMap, alliance);
        carousel = new Carousel(hardwareMap, alliance);
        elevator = new Elevator(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /**
     * Code that will run when the stop button is pressed or the robot is stopped
     */
    @Override
    public void stop()
    {
        drive.navXClose(); // Close the navX comms
        drive.holonomicDrive(0.0, 0.0, 0.0); // Make sure robot is stopped
        elevator.moveElevator(0.0); // Make sure elevator is stopped
        carousel.spinCarousel(0); // Make sure servo is stopped

        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

    /**
     * Code that will run when the op mode is first enabled
     */
    @Override
    public void init_loop()
    {
        runtime.reset();
        drive.zeroYaw(); // Zero the yaw at the start
    }

    /**
     * Code that will run every robot loop
     */
    @Override
    public void loop()
    {
        /**
         * Controller inputs from user
         */
        double y = -gamepad1.left_stick_y;
        double x =  gamepad1.left_stick_x;
        double z =  gamepad1.right_stick_x;

        // Drive
        if (gamepad1.right_bumper) // Strafe Right
        {
            drive.holonomicDrive(1, 0.0, 0.0);
        }
        else if (gamepad1.left_bumper) // Strafe Left
        {
            drive.holonomicDrive(-1, 0.0, 0.0);
        }
        else if (gamepad1.x) // Turn robot to default angle for first gate
        {
            drive.turnToDefault();
        }
        else if (gamepad1.y) // Turn robot to angle required for the other gate
        {
            drive.turnToSharedObject();
        }
        else // Default drive with joysticks
        {
            drive.holonomicDrive(x, y, z);
        }

        // Carousel
        if (gamepad1.a) // Spin Carousel
        {
            carousel.spinCarousel(1);
        }
        else
        {
            carousel.spinCarousel(0);
        }

        // Elevator
        if (gamepad1.dpad_up) // Move elevator up
        {
            elevator.moveElevator(0.5);
        }
        else if (gamepad1.dpad_down) // Move elevator down
        {
            elevator.moveElevator(-0.5);
        }
        else
        {
            elevator.moveElevator(0.0);
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
    }
}
