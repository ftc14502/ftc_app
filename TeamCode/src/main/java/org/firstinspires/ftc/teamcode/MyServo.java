package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MyServo extends LinearOpMode {
    private Servo leftClaw;
    private Servo rightClaw;
    private DcMotor towerMotor;

    public void runOpMode(){
        telemetry.addData("Status", "Starting to do the movings of the tower arm");
        telemetry.update();
        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
        towerMotor.setDirection(DcMotor.Direction.REVERSE);
        towerMotor.setPower(0.25);
        sleep(1000);
        towerMotor.setPower(0);
        telemetry.addData("Status", "Completed");
        telemetry.update();
// Varun is bad at basketball
        leftClaw = hardwareMap.servo.get("leftClaw");
        rightClaw = hardwareMap.servo.get("rightClaw");
        leftClaw.setDirection(Servo.Direction.FORWARD);
        rightClaw.setDirection(Servo.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftClaw.setPosition(0.4);
        rightClaw.setPosition(0.4);
        sleep(550);


        telemetry.addData("Status", "Starting to do the movings of the tower arm");
        telemetry.update();
        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
        towerMotor.setDirection(DcMotor.Direction.FORWARD);
        towerMotor.setPower(0.25);
        sleep(525);
        towerMotor.setPower(0);
        telemetry.addData("Status", "Completed");
        telemetry.update();


        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
