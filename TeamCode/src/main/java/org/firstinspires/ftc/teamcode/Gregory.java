package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.Dogeforia;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;


public class Gregory extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private static final float mmPerInch = 25.4f;
    private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
    // Valid choices are:  BACK or FRONT
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    //Vuforia variables
    private OpenGLMatrix lastLocation = null;
    boolean targetVisible;
    Dogeforia vuforia;
    WebcamName webcamName;
    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    private double robotX; // X displacement from target center
    private double robotY; // Y displacement from target center
    private double robotBearing; // Robot's rotation around the Z axis (CCW is positive)
    private double targetRange; // Range from robot's center to target in mm
    private double targetBearing; // Heading of the target , relative to the robot's unrotated center
    private double relativeBearing;// Heading to the target from the robot's current bearing.
    // eg: a Positive RelativeBearing means the robot must turn CCW to point at the target image.
    //Detector object
    GoldAlignDetector detector;

    @Override
    public void runOpMode() throws InterruptedException {
        // Setup camera and Vuforia parameters
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        // Set Vuforia parameters
        parameters.vuforiaLicenseKey = " Ac4mtxr/////AAABmWAzUiEltU3kmfcnc/JNu/U+lKfx1wbVLWPzHhShJqFN1q6Ays+yInrsE87Xp/wZIeH6rvqYngTEJ4r7r+597yydg2zWeMMlvrgCbeqSShy9qu4j0PktHfZ5UDYNvteyqLBuUzENN5DsuIvpsjKWqMwUsLww2RYiXEpEPmPZboFzfFu3YNssYNa93iRTH4+GkmcAPKkfKVCIIKA/ZwfMrgr6OV4GEFY6IEV35EAb5WAsncfUuytzBLPDmkQHlXE2iQ9HrMlKs2CiOQTVFusLpclkgqCbVpIGA/E9cmUzovBcJ/n8f0j/ZvOWVeWOOOAkcjdIQ1UsXbNI45f+zY3qtsPBCmwwpDDtBKmpW4X/szKw ";
        parameters.fillCameraMonitorViewParent = true;

        // Init Dogeforia
        vuforia = new Dogeforia(parameters);
        vuforia.enableConvertFrameToBitmap();

        // Set target names
        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsRoverRuckus);

        // Set trackables' location on field
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);


        //Set camera displacement
        final int CAMERA_FORWARD_DISPLACEMENT = 110;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        // Set phone location on robot
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        //Set info for the trackables
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        //Activate targets
        targetsRoverRuckus.activate();

        detector = new GoldAlignDetector(); // Create a gold aligndetector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 0, true);

        detector.yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 100); // Create new filter
        detector.useDefaults(); // Use default settings
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //mineralDetector.perfectAreaScorer.perfectArea = 10000; // Uncomment if using PERFECT_AREA scoring

        //Setup Vuforia
        vuforia.setDogeCVDetector(detector); // Set the Vuforia mineralDetector
        vuforia.enableDogeCV(); //Enable the DogeCV-Vuforia combo
        vuforia.showDebug(); // Show debug info
        vuforia.start(); // Start the mineralDetector


        telemetry.addData("vufroria ", "initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            //Assume we can't find a target
            targetVisible = false;

            //Loop through trackables - if we find one, get the location
            for (VuforiaTrackable trackable : allTrackables) {
                telemetry.addData("looping trackable", trackable.getName());
                telemetry.update();
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    //We found a target! Print data to telemetry
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;
                    telemetry.update();
                    sleep(5000);
                    //yeet meat skeet fleet eat delete repeat
                    // getUpdatedRobotLocation() will return null if no new information is available since the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }
            telemetry.addData("finished looping","all trackables");
            telemetry.update();// Provide feedback as to where the robot is located (if we know).
            sleep(3000);
            if (targetVisible) {
                // Express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
                sleep(6000);

                // Express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);

                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                sleep(5000);
            } else {
                //No visible target
                telemetry.addData("Visible Target", "none");
                telemetry.addData("Gold Mineral X position", detector.getXPosition());
                telemetry.addData("isAlighned", detector.getAligned());
                sleep(5000);
                // this is so we know how far the robot threw, and this will make the robot stop throwing
            }
            // Update telemetry
            telemetry.update();
        }

    }
}
// 19 8 9 18, 9 18, 19 8 5, 5 14 4! (Numerical code for "this is the end!");