package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.AutonomousRobot;


public class CraterRobotCode extends LinearOpMode {

    @Override
    public void runOpMode() {
        waitForStart();
        AutonomousRobot robot = new AutonomousRobot(hardwareMap, telemetry); // calling autonomous robot class
        robot.forward(18,0.2f);
        robot.rightTurn(100); // specifying the amount of degree the robo want4s to turn.
        robot.forward(9,0.2f);

        //assumption - you are right in front of a mineral
        robot.craterColourProgramme(59);
        //getting to depot
        robot.rightTurn(152);
        robot.forward(30, 0.6f);
        //dumping team marker
        robot.moveDumperDown();
        robot.moveDumperUp();
        //going to crater
        robot.backward(75, .6f);



    }
}

