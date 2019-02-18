package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class VuforiaOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "Ac3oZIX/////AAABmS6XJUcGck8Em1EneeAfzY8c5SshgBWfNGjae8pxIKY8j6ItXyZ2F+/qYH6I6Uhf3/nEWEjaOFRvRIk57HXDz93cuOy+impcdqE5sx7Mgn2DPfB/lsI6r9zb5vXd4PDApS75E01JKk31wc23Q1xN9UR9yzS4rJGx9qqrBWervKTs78ldf295k6yOrIQCvw8cvU6BpKE5jRXQsIdVd9E5SRbDo5UMVAmHKV5GJsR81pC/Ilo1tD4Ei/JP1RD7W/ss7fUrFeKNvMV5qoetllrdRiIBlLxb7d2ZEOeoYZwWkPvvWSOFrutJNLoEhdqXS8aPM9J59ZESS4XY2Iqb4S1/Ja2zduiJAzZz0c4XlIlZFM2r";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables gold = vuforia.loadTrackablesFromAsset("FTC_2018-2019");
        gold.get(0).setName("Wheels");
        gold.get(1).setName("Tools");
        gold.get(0).setName("Legos");
        gold.get(0).setName("Gears");

        waitForStart();

        gold.activate();

        while (opModeIsActive()){
            for(VuforiaTrackable goldy : gold){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) goldy.getListener()).getPose();

                if (pose != null){
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(goldy.getName() + "- Translation ", translation);
                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(0), translation.get(2)));

                    telemetry.addData(goldy.getName() + "-degrees-", degreesToTurn);


                }

            }
        }
        telemetry.update();




    }
}
