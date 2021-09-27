package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import static android.os.SystemClock.sleep;

@Autonomous(name="Tricycle Auto", group="OpMode")
public class TricycleAuto extends OpMode
{
    BarcodePipeline visionPipeline;
    WebcamName webcamName;
    OpenCvCamera camera;
    FtcDashboard dashboard;
    TelemetryPacket packet;

    /**
     * Code that will run when init button is pressed
     */
    @Override
    public void init()
    {
        dashboard = FtcDashboard.getInstance();
        packet = new TelemetryPacket();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcamName = hardwareMap.get(WebcamName.class, "camera"); // Make sure this matches the config you created
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        visionPipeline = new BarcodePipeline();
        camera.setPipeline(visionPipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                camera.stopStreaming();
            }
        });

        dashboard.startCameraStream(camera, 0);
    }

    /**
     * Code that will run when the stop button is pressed or the robot is stopped
     */
    @Override
    public void stop()
    {

    }

    /**
     * Code that will run when the op mode is first enabled
     */
    @Override
    public void init_loop()
    {

    }

    /**
     * Code that will run every robot loop
     */
    @Override
    public void loop()
    {
        //telemetry.addData("Raw:", "{%d}, {%d}, {%d}",visionPipeline.bar1, visionPipeline.bar2, visionPipeline.bar3);
        //telemetry.addData("Position", visionPipeline.position);
        //telemetry.update();

        packet.put("Barcode 1:", visionPipeline.bar1);
        packet.put("Barcode 2:", visionPipeline.bar2);
        packet.put("Barcode 3:", visionPipeline.bar3);
        packet.put("Barcode Position:", visionPipeline.position);
        dashboard.sendTelemetryPacket(packet);

        sleep(50); // Not really needed but a good idea as there is no other code running.
    }

}
