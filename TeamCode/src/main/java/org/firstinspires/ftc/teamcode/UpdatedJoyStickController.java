package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class UpdatedJoyStickController extends LinearOpMode
{
    DcMotor elevatorMotor;
    Servo elevatorServo;
    double elevatorPosition=0;
    @Override                                            //counter clockwise up clockwise down
    public void runOpMode() throws InterruptedException
    {
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
        elevatorServo=hardwareMap.get(Servo.class,"boxPlate");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        elevatorMotor.setDirection(DcMotor.Direction.REVERSE);
        elevatorServo.setDirection(Servo.Direction.REVERSE);


        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPosition(1000);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setPower(0.5);
        while(elevatorMotor.isBusy())
        {
        }
        elevatorMotor.setPower(0.0);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive())
        {

            if (gamepad2.right_bumper)
            {
                elevatorPosition = elevatorPosition;
            }
            if (elevatorPosition > 1)
            {
                elevatorPosition = 1;
            }
            if (elevatorPosition < 0)
            {
                elevatorPosition = 0;
            }
            //elevatorServo.setDirection(gamepad1.right_trigger);
            telemetry.addData("Gamepad right value", gamepad2.right_bumper);
            telemetry.update();
        }
    }
}