package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GoldAlignDetectorTest extends LinearOpMode {

    private GoldAlignDetector detector;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

        // Set up mineralDetector
        detector = new GoldAlignDetector(); // Create mineralDetector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set mineralDetector to use default settings

        // Optional tuning

        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //mineralDetector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the mineralDetector!

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("X Pos", detector.getXPosition()); // Gold X position.
            telemetry.addData("isAligned", detector.getAligned());
            telemetry.update();
        }


    }
}
