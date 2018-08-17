package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public  class MyFIRSTJavaOpMode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor towerMotor;


    @Override
    public void runOpMode() throws InterruptedException {

        towerMotor = hardwareMap.dcMotor.get("towerMotor");


        towerMotor.setDirection(DcMotor.Direction.FORWARD);


        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        runtime.reset();

        telemetry.addData("Mode", "running");
        telemetry.update();
        towerMotor.setPower(0.25);

        sleep(2000);

        towerMotor.setPower(0.0);



        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }


    }


}
