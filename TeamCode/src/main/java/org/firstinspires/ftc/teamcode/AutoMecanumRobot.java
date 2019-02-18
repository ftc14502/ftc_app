package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static java.lang.Thread.sleep;

public class AutoMecanumRobot {
    public GoldAlignDetector mineralDetector;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor hangerMotor;
    private DcMotor armMotor;

    BNO055IMU imu;
    Orientation angles;
    Direction pathTakenToPushMineral = Direction.STRAIGHT;
    private Servo claw;
    float netAngle = 0;
    private Telemetry telemetry;
    public boolean mineralFound;


    LinearOpMode depotAutonomous;


    public AutoMecanumRobot(LinearOpMode dp) {

        telemetry = dp.telemetry;
        depotAutonomous = dp;
        initialize();
    }

    private void initialize() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = depotAutonomous.hardwareMap.get(BNO055IMU.class, "imu");
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        imu.initialize(parameters);

        backLeft = depotAutonomous.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = depotAutonomous.hardwareMap.get(DcMotor.class, "backRight");

        frontLeft = depotAutonomous.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = depotAutonomous.hardwareMap.get(DcMotor.class, "frontRight");

        hangerMotor = depotAutonomous.hardwareMap.get(DcMotor.class, "hangerMotor");
        armMotor = depotAutonomous.hardwareMap.get(DcMotor.class, "armMotor");
        claw = depotAutonomous.hardwareMap.get(Servo.class, "claw");

        // Setting default value
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        hangerMotor.setPower(0);
        armMotor.setPower(0);
        claw.setPosition(0.5);

        // Set up mineralDetector
        //initializeMineralDetector();

        telemetry.addData("IMU first angle", angles.firstAngle);
        telemetry.update();

    }

    public void initializeMineralDetector() {
        mineralDetector = new GoldAlignDetector(); // Create mineralDetector
        mineralDetector.init(depotAutonomous.hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        mineralDetector.useDefaults(); // Set mineralDetector to use default settings

        // Optional tuning
        mineralDetector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        mineralDetector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        mineralDetector.downscale = 0.4; // How much to downscale the input frames
        // soyuz nerushimy rezpublic sbovodyk splotila naveshi velikaya rus'!
        mineralDetector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA, but who cares
        //mineralDetector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring, nope
        mineralDetector.maxAreaScorer.weight = 0.005; // not actual weight

        mineralDetector.ratioScorer.weight = 5; //
        mineralDetector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        mineralDetector.enable(); //// Start the mineralDetector!
    }


    public void independenceForward() {
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        backLeft.setPower(0.5);
        backRight.setPower(0.5);
        depotAutonomous.sleep(300);
        telemetry.addData("Go", "!!!");
        telemetry.update();
    }

    public void closeClaw() {
        claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(1);
    }

    public void dumpMarker() {
        // 1. make the armMotor come down using the ways of the encoder
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(1600);
        armMotor.setPower(1.0);
        while (armMotor.isBusy() && depotAutonomous.opModeIsActive()) {
            telemetry.addData("Moving Down", " to place the team Marker");
        }
        armMotor.setPower(0);

        //2. make the claw open
        claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(0);
        claw.setPosition(0);
        depotAutonomous.sleep(750);
        moveForward(-5, 0.5);
        //3. put the armMotor up
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(1600);
        armMotor.setPower(1);
        while (armMotor.isBusy() && depotAutonomous.opModeIsActive()) { /* */
            telemetry.addData("waitng", " for the arm to go up");
        }

        //4. close the claws
        claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(0.5);
        depotAutonomous.sleep(750);
        telemetry.addData("Status:", " Done with this method");
    }

    public void comeDown() {
        telemetry.addData("Coming down", " right now chico");
        hangerMotor.setDirection(DcMotor.Direction.FORWARD);
        hangerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hangerMotor.setTargetPosition(13 * 1440 + 300);
        hangerMotor.setPower(0.7);

        while (hangerMotor.isBusy() && depotAutonomous.opModeIsActive()) { // tengo miedo
            telemetry.addData("Still coming down,", hangerMotor.getCurrentPosition());
            telemetry.update();
        }
        hangerMotor.setPower(0);
    }


    public void moveForward(int inches, double power) {


        telemetry.addData("Status", "calling the moveFORWARD METHOD");


        int tick = 92 * (inches); // going inches
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD); //THIS IS FOR STRAIGHT LINES


        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeft.setTargetPosition(tick);
        backRight.setTargetPosition(tick);
        frontLeft.setTargetPosition(tick);
        frontRight.setTargetPosition(tick);
        telemetry.addData("tick", tick);
        telemetry.addData("back right current position", backRight.getCurrentPosition());
        telemetry.addData("back left current position", backLeft.getCurrentPosition());
        telemetry.addData("front right current position", frontRight.getCurrentPosition());
        telemetry.addData("front left current position", frontLeft.getCurrentPosition());

        telemetry.update();
        backLeft.setPower(power); //goin forward
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
        telemetry.addData("Status", "entering is busy loop");
        while (backLeft.isBusy() && frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && depotAutonomous.opModeIsActive()) {
            // while (backLeft.getCurrentPosition() < backLeft.getTargetPosition()  ){
            telemetry.addData("backRight", backRight.getCurrentPosition() + "value" + backRight.isBusy());
            telemetry.addData("backLeft", backLeft.getCurrentPosition() + "value" + backLeft.isBusy());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition() + "value" + frontLeft.isBusy());
            telemetry.addData("frontRight", frontRight.getCurrentPosition() + "value" + frontRight.isBusy());
            telemetry.addData("Tick:", tick);
            telemetry.update();

            backLeft.setPower(power); //goin forward again
            backRight.setPower(power);
            frontLeft.setPower(power);
            frontRight.setPower(power);
        }


        stop();
        telemetry.addData("Move forward done and Robot Stopped", "");
        telemetry.update();
    }

    public void strafeLeft(int time) {
        telemetry.addData("The robot ", "Moved to the Left");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD); //this is left

        backLeft.setPower(0.2);
        backRight.setPower(0.2);
        frontLeft.setPower(0.2);
        frontRight.setPower(0.2);
        depotAutonomous.sleep(time);
        stop();
        telemetry.update();

    }

    public void strafeRight(int time) {
        telemetry.addData("The robot ", "has moved to the Right");
        telemetry.update();
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE); //this is right

        backLeft.setPower(0.2);
        backRight.setPower(0.2);
        frontLeft.setPower(0.2);
        frontRight.setPower(0.2);

        depotAutonomous.sleep(time);
        stop();
    }


    public void stop() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void goToDepot() {
        //check which path was taken and turn appropritately
        if (pathTakenToPushMineral == Direction.STRAIGHT) {
            moveForward(21, 0.6); // inside depot
        } else if (pathTakenToPushMineral == Direction.RIGHT) {
            turnLeftOnCenterAxis(/*Y U DO DIS */60);
            depotAutonomous.sleep(500);
            moveForward(23, 0.2); // inside depot

        } else if (pathTakenToPushMineral == Direction.LEFT) {
            turnRightOnCenterAxis(40);

            moveForward(23, 0.2); // inside depot
        }
        dumpMarker();
    }

    public float turnRightOnCenterAxis(int turnAngle) {

        telemetry.addData("turnRightOnCenterAxis", "Turning Right");
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        float currentAngle = angles.firstAngle;
        float startAngle = angles.firstAngle;

        float desiredAngle = currentAngle - turnAngle;

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        backLeft.setPower(0.3);
        backRight.setPower(0.3);
        frontLeft.setPower(0.3); // poggers my doggers/(-_-)\_ ;
        frontRight.setPower(0.3);


        while (currentAngle > desiredAngle && depotAutonomous.opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            currentAngle = angles.firstAngle; // resetting values
            // meep
            telemetry.addData("Current Angle", currentAngle);

            telemetry.addData("getAligned", mineralDetector.getAligned());
            telemetry.addData("desiredAngle", desiredAngle);
            telemetry.addData("XPOS", mineralDetector.getXPosition());
            telemetry.update();



        }
        netAngle = netAngle - currentAngle;
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

        telemetry.addData("netAngle", netAngle);
        telemetry.addData("turnRightOnCenterAxis", "returning");

        return Math.abs(currentAngle - startAngle);
    }

    public float turnLeftOnCenterAxis(int turnAngle) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        float currentAngle = angles.firstAngle;
        float startAngle = angles.firstAngle;
        float desiredAngle = currentAngle + turnAngle;
        telemetry.addData("turnLeftOnCenterAxis", "Turning Left");
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("current Angle before loop", currentAngle);
        telemetry.addData("desired Angle before ", desiredAngle);

        backLeft.setPower(0.3);
        backRight.setPower(0.3);
        frontLeft.setPower(0.3);
        frontRight.setPower(0.3);
        while (currentAngle < desiredAngle && depotAutonomous.opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            currentAngle = angles.firstAngle;
            telemetry.addData("Current Angle LEFT", currentAngle);
            telemetry.addData("getAligned", mineralDetector.getAligned());
            telemetry.addData("desiredAngle", desiredAngle);
            telemetry.addData("XPOS", mineralDetector.getXPosition());
            telemetry.update();


            //if (mineralDetector.getAligned()){
            //mineralFound=true;
            //break;
            depotAutonomous.sleep(10);


        }


        netAngle = netAngle + currentAngle;

        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

        telemetry.addData("current Angle", currentAngle);
        telemetry.addData("desired Angle", desiredAngle);
        telemetry.addData("netAngle", netAngle);
        telemetry.addData("turnLeftOnCenterAxis", "Finished Turning Left");
        telemetry.update();
        return Math.abs(currentAngle - startAngle);

    }


}
