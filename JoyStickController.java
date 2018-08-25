package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class JoyStickController extends LinearOpMode
{
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor towerMotor;
    Servo leftClaw;
    Servo rightClaw;
    double clawPosition = 0;
    private final double CLAWSPEED = 0.05;
    @Override
    public void runOpMode()
    {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        telemetry.addData("Status", "waiting");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "running");
        telemetry.update();
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        towerMotor.setDirection(DcMotor.Direction.FORWARD);
        leftClaw.setDirection(Servo.Direction.FORWARD);
        rightClaw.setDirection(Servo.Direction.REVERSE);
        rightClaw.setPosition(Servo.MIN_POSITION);
        leftClaw.setPosition(Servo.MIN_POSITION);
        while (opModeIsActive())
        {
            leftMotor.setPower(-gamepad1.left_stick_y*0.4); // Forward
            rightMotor.setPower(gamepad1.right_stick_y*0.4); // Right Stick
            towerMotor.setPower(gamepad1.right_trigger*0.5); // Moves the claw up
            towerMotor.setPower(-gamepad1.left_trigger*0.3); // Moves the claw down
            if (gamepad1.left_bumper)
            {
                clawPosition = clawPosition + CLAWSPEED;
            }
            if (gamepad1.right_bumper)
            {
                clawPosition = clawPosition - CLAWSPEED;
            }
            if (clawPosition > 1)
            {
                clawPosition = 1;
            }
            if (clawPosition < 0)
            {
                clawPosition = 0;
            }
            leftClaw.setPosition(clawPosition);
            rightClaw.setPosition(clawPosition);
            telemetry.addData("left_joystick", -gamepad1.left_stick_y);
            telemetry.addData("right_joystick", gamepad1.right_stick_y);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("right_trigger", -gamepad1.right_trigger);
            telemetry.addData("left_bumper", gamepad1.left_bumper);
            telemetry.addData("right_bumper", gamepad1.right_bumper);
            telemetry.update();
        }
    }
    private void closeClaws(Servo leftClaw, Servo rightClaw)
    {
        leftClaw.setPosition(1);
        rightClaw.setPosition(1);
    }
    private void openClaws(Servo leftClaw, Servo rightClaw)
    {
        leftClaw.setPosition(0);
        rightClaw.setPosition(0);
    }
}