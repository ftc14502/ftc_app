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



public class DepotRobotCode extends LinearOpMode {
    NormalizedColorSensor colorSensor;
    @Override
    public void runOpMode() {

        waitForStart();
        AutonomousRobot robot = new AutonomousRobot(hardwareMap, telemetry); // calling autonomous robot class
        robot.forward(19,0.3f);
        robot.rightTurn(100);
        robot.backward(9, 0.2f);// specifying the amount of degree the robo wants to turn.
        robot.depotColourProgramme(59);
        robot.leftTurn(62);
        robot.forward(10, .6f);
        robot.moveDumperDown();//dump team marker
        sleep(300);
        robot.moveDumperUp();
        robot.backward(75, 0.6f);//move to crater
    }
}