package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

public class FlyWheel extends LinearOpMode {
    private CRServo pushColor;

    public void runOpMode() {
       float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;
        pushColor = hardwareMap.get(CRServo.class, "pushColour");
        telemetry.addData("Staetus", "Waiting");
        waitForStart();
        resetStartTime();

        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                pushColor.setDirection(CRServo.Direction.FORWARD);
                pushColor.setPower(1);
                telemetry.addData("The Servo on the Robot", " goes round and round.");
                telemetry.update();
            }
            if (gamepad1.right_bumper) {
                pushColor.setDirection(CRServo.Direction.REVERSE);
                pushColor.setPower(0);
                telemetry.addData("Status", "The Bus is a bully");
                telemetry.update();
            }
        }
    }
}



