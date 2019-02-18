package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


// DO NOT CHANGE THIS PROGRAM. CHANGING THIS PROGRAM WILL RESULT IN A THRASHING, SO NO TOUCHY TOUCHY WITHOUT PERMISSION.
public class ittings extends LinearOpMode {
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor hangerMotor;
    DcMotor armMotor;
    Servo claw;
    BNO055IMU imu;
    Orientation angles;
    /* Controls:
     * GAMEPAD 1:
     * MOVEMENT: LS
     * TURNING: RS
     * STRAFING: DPAD LEFT AND RIGHT
     * ACTUATOR: DPAD UP AND DOWN
     * CLAW: RT AND LT (Right open, Left close)
     * ARM:LB AND RB (Right up, Left down)
     * */



    public void runOpMode() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        hangerMotor = hardwareMap.get(DcMotor.class, "hangerMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        claw = hardwareMap.get(Servo.class, "claw");



        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        hangerMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        imu.initialize(parameters);




        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        hangerMotor.setPower(0);
        armMotor.setPower(0);
        claw.setPosition(.5);

        telemetry.addData("Status", "Waiting");
        telemetry.update();
        float power;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        telemetry.addData("Status", "Waiting");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            float rightPower = 1;
            float leftPower = 1;
            float otherLeftPower = gamepad1.right_trigger;
            float otherRightPower = gamepad1.left_trigger;


            telemetry.addData("left Power", leftPower);
            telemetry.addData("right power", rightPower);
            telemetry.update();

            telemetry.addData("Left Trigger", gamepad1.left_trigger);
            telemetry.addData("Right Trigger", gamepad1.right_trigger);

            if (gamepad1.left_stick_y != 0) { //analog movement
                backLeft.setPower(gamepad1.left_stick_y);
                frontLeft.setPower(gamepad1.left_stick_y);
                backRight.setPower(-gamepad1.left_stick_y);
                frontRight.setPower(-gamepad1.left_stick_y);
                telemetry.addData("gamepad1.left_stick_y", gamepad1.left_stick_y);
                telemetry.update();

                telemetry.addData("angle", angles);
            } else {
                backLeft.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0); // public void init(){ extends public class undeuninit}
            }


            if (gamepad1.right_stick_x != 0) { //analog turning
                backLeft.setPower(-gamepad1.right_stick_x);
                frontLeft.setPower(-gamepad1.right_stick_x);
                backRight.setPower(-gamepad1.right_stick_x);
                frontRight.setPower(-gamepad1.right_stick_x);
                telemetry.addData("gamepad1.right_stick_x", gamepad1.right_stick_x);
                telemetry.addData("Angles", angles);
            } else {
                backLeft.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
            }
            //to strafe
//            if (gamepad1.right_stick_x>0){
//                frontRight.setPower(-0.5);
//                backRight.setPower(power);
//                frontLeft.setPower(power);
//                backLeft.setPower(-power);
//            }
//            if (gamepad1.right_stick_x<0){
//                frontRight.setPower(power);

//                backRight.setPower(-power);
//                frontLeft.setPower(-power);
//                backLeft.setPower(power);
//            }
//            to go back and forwards
            if (gamepad1.dpad_right) { //digital strafe left
                telemetry.addData("Status", " To the left");
                backLeft.setPower(rightPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(-rightPower);
                frontRight.setPower(-rightPower);
                telemetry.addData("backLeft", backLeft.getPower());
                telemetry.addData("Left Trigger", gamepad1.left_trigger);
                telemetry.addData("Right Trigger", gamepad1.right_trigger);
                telemetry.addData("Angles", angles);

            } else {
                backLeft.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
            }


            if (gamepad1.dpad_left) { //digital strafe right
                frontRight.setPower(leftPower);
                backRight.setPower(-leftPower);
                frontLeft.setPower(leftPower);
                backLeft.setPower(-leftPower);
                telemetry.addData("backLeft Motor: ", backLeft.getPower());
                telemetry.addData("Left Trigger", gamepad1.left_trigger);
                telemetry.addData("Right Trigger", gamepad1.right_trigger);
                telemetry.addData("Angles", angles);
            } else {
                backLeft.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
            }

            if (gamepad1.dpad_up) { //hanger up
                hangerMotor.setPower(0.5);
                telemetry.addData("The encoder value", hangerMotor.getCurrentPosition());
            } else {
                hangerMotor.setPower(0);
            }

            if (gamepad1.dpad_down) { //hanger down
                hangerMotor.setPower(-0.5);
                telemetry.addData("The encoder value", hangerMotor.getCurrentPosition());
            } else {
                hangerMotor.setPower(0);

            }
            if (gamepad1.right_bumper) { //arm forward
                telemetry.addData("Status", "Left Bumper Pressed");
                telemetry.addData("The encoder value: ", armMotor.getCurrentPosition());
                telemetry.update();
                armMotor.setDirection(DcMotor.Direction.FORWARD);
                armMotor.setPower(1);
            } else {
                armMotor.setPower(0.0);
            }

            if (gamepad1.left_bumper) { //arm backwards
                telemetry.addData("Status", "Right Bumper Pressed");
                telemetry.addData("The encoder value: ", armMotor.getCurrentPosition());
                telemetry.update();
                armMotor.setDirection(DcMotor.Direction.REVERSE);
                armMotor.setPower(1);
            } else {
                armMotor.setPower(0.0);
            }

            if (gamepad1.right_trigger > 0) {//claw closed
                telemetry.addData("Status", "Dpad Up Pressed");
                telemetry.update();
                claw.setDirection(Servo.Direction.FORWARD);
                claw.setPosition(0);
            }
            if (gamepad1.left_trigger > 0) { //claw open
                telemetry.addData("Status", "Dpad down Pressed");
                telemetry.update();
                claw.setDirection(Servo.Direction.REVERSE);
                claw.setPosition(0.5);
            }
            /*if (gamepad2.left_trigger!=0) {
                frontRight.setPower(otherLeftPower);
                backRight.setPower(-otherLeftPower);
                frontLeft.setPower(otherLeftPower);
                backLeft.setPower(-otherLeftPower);
                telemetry.addData("backLeft Motor: ", backLeft.getPower());
                telemetry.addData("Left Trigger", gamepad1.left_trigger);
                telemetry.addData("Right Trigger", gamepad1.right_trigger);
                telemetry.addData("Angles", angles);
            }else{
                backLeft.setPower(0);
                frontLeft.setPower (0);
                backRight.setPower(0);
                frontRight.setPower(0);
            }
            if (gamepad2.right_trigger != 0) {
                frontRight.setPower(otherRightPower);
                backRight.setPower(-otherRightPower);
                frontLeft.setPower(otherRightPower);
                backLeft.setPower(-otherRightPower);
                telemetry.addData("backLeft Motor: ", backLeft.getPower());
                telemetry.addData("Left Trigger", gamepad1.left_trigger);
                telemetry.addData("Right Trigger", gamepad1.right_trigger);
                telemetry.addData("Angles", angles);
            }else{
                backLeft.setPower(0);
                frontLeft.setPower (0);
                backRight.setPower(0);
                frontRight.setPower(0);
            }*/

//            to go left/right
//            if (gamepad1.left_stick_x<0){
//                frontRight.setPower(power);
//                backRight.setPowe\`r(power);
//                frontLeft.setPower(power);
//                backLeft.setPower(power);
//            }
//            if (gamepad1.left_stick_x>0){
//                frontRight.setPower(-power);
//                backRight.setPower(-power);
//                frontLeft.setPower(-power);
//                backLeft.setPower(-power);
//            }
//            to go left/right
//            if (gamepad1.left_stick_x<0){
//                frontRight.setPower(power);
//                backRight.setPowe\`r(power);
//                frontLeft.setPower(power);
//                backLeft.setPower(power);
//            }
//            if (gamepad1.left_stick_x>0){
//                frontRight.setPower(-power);
//                backRight.setPower(-power);
//                frontLeft.setPower(-power);
//                backLeft.setPower(-power);
//            }


        }
        telemetry.update();
//        private void moveFront () {
//            backLeft.setDirection(DcMotor.Direction.REVERSE);
//            frontLeft.setDirection(DcMotor.Direction.REVERSE);
//            backRight.setDirection(DcMotor.Direction.FORWARD);
//            frontRight.setDirection(DcMotor.Direction.FORWARD); //THIS IS FOR STRAIGHT LIN
//            backLeft.setPower(0.5);
//            backRight.setPower(0.5);
//            frontLeft.setPower(0.5);
//            frontRight.setPower(0.5);
//        }
//
//        private void moveBack () {
//            backLeft.setDirection(DcMotor.Direction.REVERSE);
//            frontLeft.setDirection(DcMotor.Direction.REVERSE);
//            backRight.setDirection(DcMotor.Direction.FORWARD);
//            frontRight.setDirection(DcMotor.Direction.FORWARD); //This is for back
//            backLeft.setPower(0.5);
//            backRight.setPower(0.5);
//            frontLeft.setPower(0.5);
//            frontRight.setPower(0.5);
//
//        }
//
//        private void moveLeft () {
//            backLeft.setDirection(DcMotor.Direction.FORWARD);
//            frontLeft.setDirection(DcMotor.Direction.REVERSE);
//            backRight.setDirection(DcMotor.Direction.FORWARD);
//            frontRight.setDirection(DcMotor.Direction.REVERSE); //this is left
//            backLeft.setPower(0.5);
//            backRight.setPower(0.5);
//            frontLeft.setPower(0.5);
//            frontRight.setPower(0.5);
//        }
//
//        private void moveRight () {
//            backLeft.setDirection(DcMotor.Direction.REVERSE);
//            frontLeft.setDirection(DcMotor.Direction.FORWARD);
//            backRight.setDirection(DcMotor.Direction.REVERSE);
//            frontRight.setDirection(DcMotor.Direction.FORWARD); //this is right
//            backLeft.setPower(0.5);
//            backRight.setPower(0.5);
//            frontLeft.setPower(0.5);
//            frontRight.setPower(0.5);
//        }


    }
}