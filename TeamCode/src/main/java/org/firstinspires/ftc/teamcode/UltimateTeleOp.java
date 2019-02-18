package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

// You get to use the quality wannnabe PS4 game controller... wait what?


public class UltimateTeleOp extends LinearOpMode {
    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor armMotor;
    private DcMotor dumpMotor;
    private DcMotor flyWheel;

    @Override
    public void runOpMode() {
        leftWheel = hardwareMap.get(DcMotor.class, "leftWheel");
        rightWheel = hardwareMap.get(DcMotor.class, "rightWheel");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        dumpMotor = hardwareMap.get(DcMotor.class, "dumpMotor");
        flyWheel = hardwareMap.get(DcMotor.class, "flyWheel");
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Status", "Initialized");
        telemetry.update();//waiting for start
        telemetry.addData("Status", "running");
        telemetry.update();

        leftWheel.setDirection(DcMotor.Direction.REVERSE);
        rightWheel.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        dumpMotor.setDirection(DcMotor.Direction.FORWARD);
        flyWheel.setPower(Servo.MIN_POSITION);


        telemetry.addData("Status", "People have directions");
        leftWheel.setPower(0);
        rightWheel.setPower(0);
        armMotor.setPower(0);
        armMotor.setPower(0);
        dumpMotor.setPower(0);
        dumpMotor.setPower(0); //meep
        flyWheel.setPower(0);
        while (opModeIsActive()) {
            leftWheel.setPower(-gamepad1.left_stick_y);
            rightWheel.setPower(gamepad1.right_stick_y);
            leftWheel.setPower(gamepad1.left_stick_x);
            rightWheel.setPower(gamepad1.right_stick_x);


            if (gamepad1.dpad_left == true) {
                pushMineral();
            }
            if (gamepad1.dpad_right == true) {
                takeMineral();
            }
            telemetry.addData("Status", "Lifted");
            telemetry.addData("Lifted up: ", gamepad1.left_trigger);
            telemetry.addData("Camed back down", gamepad1.right_trigger);
            telemetry.update();

            if (gamepad1.dpad_up == true) {
                forwardArmMotor();
            }
            if (gamepad1.dpad_down == true) {
                backwardArmMotor();
            }

            if (gamepad1.left_bumper == true) {
                moveDumperUp();
            }
            else if (gamepad1.right_bumper == true) {
                moveDumperDown();
            }
        }
    }


    private void moveDumperUp() {
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dumpMotor.setDirection(DcMotor.Direction.FORWARD);
        dumpMotor.setTargetPosition(430);
        dumpMotor.setPower(0.6);
        while (dumpMotor.isBusy()) {
            telemetry.addData("Status", "Lifting");
            telemetry.addData("dumpMotor", dumpMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + dumpMotor.isBusy());
            telemetry.update();
        }

    }


    private void moveDumperDown() {
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dumpMotor.setDirection(DcMotor.Direction.REVERSE);
        dumpMotor.setTargetPosition(30);
        dumpMotor.setPower(0.3);

        while (dumpMotor.isBusy()) {
            telemetry.addData("Status", "Lifting");
            telemetry.addData("dumpMotor", dumpMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + dumpMotor.isBusy());
            telemetry.update();
        }
        dumpMotor.setPower(0);
    }
    private void pushMineral(){
        if (gamepad1.dpad_left == true) {
            flyWheel.setDirection(DcMotor.Direction.FORWARD);
            flyWheel.setPower(1);
            telemetry.addData("Status", "Pushing Out The Mineral");
            telemetry.update();
        } else {
            flyWheel.setPower(0);
        }
    }

    private void takeMineral(){
        if (gamepad1.dpad_right == true) {
            flyWheel.setDirection(DcMotor.Direction.REVERSE);
            flyWheel.setPower(1);
            telemetry.addData("Status", "Take Mineral");
            telemetry.update();
        } else {
            flyWheel.setPower(0);
        }
    }
    private void forwardArmMotor(){
        if (gamepad1.dpad_up == true) {
            armMotor.setDirection(DcMotor.Direction.REVERSE);
            armMotor.setPower(0.5);
            telemetry.addData("Condition", "Arm Extending");
            telemetry.update();
        } else {
            armMotor.setPower(0);
        }
    }
    private void backwardArmMotor(){
        if (gamepad1.dpad_down == true) {
            armMotor.setDirection(DcMotor.Direction.FORWARD);
            armMotor.setPower(0.5);
            telemetry.addData("Condition", "Arm Backing up");
            telemetry.update();
        } else {
            armMotor.setPower(0);
        }
    }

}