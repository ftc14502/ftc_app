import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
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
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        // naming everything

        telemetry.addData("Status", "waiting");
        telemetry.update();
        waitForStart();
        //waiting for start
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

        while (opModeIsActive()) {
            leftMotor.setPower(-gamepad1.left_stick_y*0.7); //Both forward and backward
            rightMotor.setPower(gamepad1.left_stick_y*0.7); //Both forward and backward
            leftMotor.setPower(gamepad1.right_stick_x*0.4); //Makes a right or left turn
            rightMotor.setPower(gamepad1.right_stick_x*0.4); //Makes a right or left turn
            towerMotor.setPower(gamepad1.right_trigger*0.5); //Moves the hand down
            towerMotor.setPower(-gamepad1.left_trigger*0.3); //Moves the hand up

            // assigning los thingies
// used for controlling the servos, an if and else statement for both servos
            if (gamepad1.left_bumper) {
                clawPosition = clawPosition + CLAWSPEED;
            }                              // yajeeeeeeeeeeeeeeeeeeeeeeeeeetinguus
            if (gamepad1.right_bumper) {
                clawPosition = clawPosition - CLAWSPEED;
            }                                  // i suck at Java, am a piece of poop when it comes to java:)
            if (clawPosition > 1) {
                clawPosition = 1;
            }
            if (clawPosition < 0) {
                clawPosition = 0;
            }
            leftClaw.setPosition(clawPosition); //Opens the claw
            rightClaw.setPosition(clawPosition); //Closes the claw

//            if (gamepad1.right_bumper) {
//                rightClaw.setPosition(-1);
//                leftClaw.setPosition(-1);
//            } else {
//                rightClaw.setPosition(0);
//                leftClaw.setPosition(0);

// when both are pressed, then they go back, you must let go at the exact same time to make them clamp properly
            telemetry.addData("left_joystick", -gamepad1.left_stick_y);
            telemetry.addData("right_joystick", gamepad1.right_stick_y);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("right_trigger", -gamepad1.right_trigger);
            telemetry.addData("left_bumper", gamepad1.left_bumper);
            telemetry.addData("right_bumper", gamepad1.right_bumper);
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