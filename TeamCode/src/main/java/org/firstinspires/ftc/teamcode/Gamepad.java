package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
public class Gamepad extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor towerMotor;
    Servo leftClaw;
    Servo rightClaw;
    double clawPosition = 0;
    private final double CLAWSPEED = 0.05;

    @Override

    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftWheel");
        rightMotor = hardwareMap.get(DcMotor.class, "rightWheel");
//        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
//        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
//        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        // naming everything

        telemetry.addData("Status", "waiting");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "Initialized");
        telemetry.update();//waiting for start
        telemetry.addData("Status", "running");
        telemetry.update();

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        towerMotor.setDirection(DcMotor.Direction.FORWARD);
        leftClaw.setDirection(Servo.Direction.FORWARD);
        rightClaw.setDirection(Servo.Direction.REVERSE);
        rightClaw.setPosition(Servo.MIN_POSITION);
        leftClaw.setPosition(Servo.MIN_POSITION);
        //setting direction for both servos and DC Motors (and not Marvel Motors XD)

        leftMotor.setPower(0);
        rightMotor.setPower(0);
        towerMotor.setPower(0);
        towerMotor.setPower(0);


        while (opModeIsActive()) {
            if (gamepad1.y) {
                leftMotor.setPower(-gamepad1.left_stick_y);
                rightMotor.setPower(gamepad1.left_stick_y);
                towerMotor.setPower(gamepad1.right_trigger);
                towerMotor.setPower(-gamepad1.left_trigger);
                leftMotor.setPower(gamepad1.right_stick_x);
                rightMotor.setPower(gamepad1.right_stick_x);
            }
            else if (gamepad1.b) {
                leftMotor.setPower(-gamepad1.left_stick_y * 0.4);
                rightMotor.setPower(gamepad1.left_stick_y * 0.4); 
                leftMotor.setPower(gamepad1.right_stick_x * 0.4);
                rightMotor.setPower(gamepad1.right_stick_x * 0.4);
            }
            // assigning los thingies
// used for controlling the servos, an if and else statement for both servos
            if (gamepad1.left_bumper) {
                clawPosition = clawPosition + CLAWSPEED;
            }
            if (gamepad1.right_bumper) {
                clawPosition = clawPosition - CLAWSPEED;
            }
            if (clawPosition > 1) {
                clawPosition = 1;
            }
            if (clawPosition < 0) {
                clawPosition = 0;
            }

            leftClaw.setPosition(clawPosition);
            rightClaw.setPosition(clawPosition);
// when both are pressed, then they go back, you must let go at the exact same time to make them clamp properly
            telemetry.addData("left_joystick", -gamepad1.left_stick_y);                    //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("left_joystick", gamepad1.left_stick_y);                     //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("left_trigger", gamepad1.left_trigger);                      //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("right_trigger", -gamepad1.right_trigger);                   //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("left_bumper", gamepad1.left_bumper);                        //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("right_bumper", gamepad1.right_bumper);                      //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.addData("dpad-up", gamepad1.dpad_up);                                //the telemetry updates, and I have no idea how this happened..... XD
            telemetry.update();
        }
    }

    private void closeClaws(Servo leftClaw, Servo rightClaw) {
        leftClaw.setPosition(1);
        rightClaw.setPosition(1);
    }

    private void openClaws(Servo leftClaw, Servo rightClaw) {
        leftClaw.setPosition(0);
        rightClaw.setPosition(0);
    }
}
