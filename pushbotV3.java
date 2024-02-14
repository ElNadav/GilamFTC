package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "pushbotV3" )
// motors and servos names

/* Controller Description:
    Left_Joystick = robot direction controls
        up - forward
        down - backwards
        left - left
        right - right
    Right_Joystick = arm and pixel control
        up - lift up
        down - lower down
        right - pixel angle up
        left - pixel angle down
    RB = right pixel grab and release
    LB = left pixel grab and release
    A = airplane Release
    B = boost increase to robot speed
    Y = boost increase to lift speed
 */
public class pushbotV3 extends OpMode {
    public Servo pixelArmAngle;
    public Servo pixelGrabberRight;
    public Servo pixelGrabberLeft;
    public Servo airPlaneServo;
    DcMotor robotGearRight;
    DcMotor robotGearLeft;
    DcMotor liftMotorRight;
    DcMotor liftMotorLeft;
    double ticks = 288;
    double newTarget;
    double robotFrontBack, robotRightLeft, liftUpDown, pixelAngle = 0;
    double[] speedBoost = {0.35, 0.7, 1};
    int count = 0;
    int liftCount = 0;

    @Override
    public void init() {
        // Motors
        robotGearRight = hardwareMap.get(DcMotor.class, "GearMotorRight"); // Port 0
        robotGearLeft = hardwareMap.get(DcMotor.class, "GearMotorLeft"); // Port 1
        liftMotorRight = hardwareMap.get(DcMotor.class, "LiftRight"); // Port 2
        liftMotorLeft = hardwareMap.get(DcMotor.class, "LiftLeft"); // Port 3

        // Servos
        pixelArmAngle = hardwareMap.get(Servo.class, "PixelAngle"); // Port 0
        airPlaneServo = hardwareMap.get(Servo.class, "Airplane"); // Port 1
        pixelGrabberRight = hardwareMap.get(Servo.class, "PixelRight"); // Port 2
        pixelGrabberLeft = hardwareMap.get(Servo.class, "PixelLeft"); // Port 3

        // Set Motors Direction and Modes
        robotGearRight.setDirection(DcMotorSimple.Direction.FORWARD);
        robotGearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        run_encoder(0, 0.7, liftMotorLeft);
        run_encoder(0, 0.7, liftMotorRight);
        pixelArmAngle.setPosition(0.8);

    }

    public void loop() {
        telemetry.addData("PushBot", "run");
        if (gamepad1.b) { // *controls robot driving speed
            count = (count + 1) % 3;
        }
        if (gamepad1.y) { // controls lift speed
            liftCount = (liftCount + 1) % 3;
        }
        robotFrontBack = gamepad1.left_stick_y;
        robotRightLeft = gamepad1.left_stick_x;
        //liftUpDown = gamepad1.right_stick_y;

        // Motor Operation:
        robotGearRight.setPower((robotRightLeft + robotFrontBack) * speedBoost[count]);
        robotGearLeft.setPower((-robotRightLeft + robotFrontBack) * speedBoost[count]);
        //liftMotorRight.setPower(liftUpDown * speedBoost[liftCount]);
        //liftMotorRight.setPower(liftUpDown * speedBoost[liftCount]);

        if (gamepad1.dpad_up) { // Controls Pixel arm Position - UP
            run_encoder(2, speedBoost[liftCount], liftMotorRight);
            run_encoder(2, speedBoost[liftCount], liftMotorLeft);
            pixelArmAngle.setPosition(1);//armServo position - UP
        }
        if (gamepad1.dpad_down) { // Controls Pixel arm Position - UP
            run_encoder(0, speedBoost[liftCount], liftMotorRight);
            run_encoder(0, speedBoost[liftCount], liftMotorLeft);
            pixelArmAngle.setPosition(0);//armServo position - DOWN
        }
        if (gamepad1.dpad_left) {
            run_encoder(0.8, speedBoost[liftCount],liftMotorLeft);
            run_encoder(0.8, speedBoost[liftCount],liftMotorRight);
            pixelArmAngle.setPosition(0.3);
        }
        if (gamepad1.dpad_right) {
            run_encoder(1.5, speedBoost[liftCount],liftMotorLeft);
            run_encoder(1.5, speedBoost[liftCount],liftMotorRight);
            pixelArmAngle.setPosition(0.5);
        }
        telemetry.addData("Current Lift Speed (press Y to change):", speedBoost[liftCount]);
        telemetry.addData("Current Robot Speed (press B to change):", speedBoost[count]);



        // Airplane Servo
        if (gamepad1.a) {
            airPlaneServo.setPosition(0.8);
        } else {
            airPlaneServo.setPosition(1);
        }

        //Pixel Operation:
        if (gamepad1.right_bumper) {
            pixelGrabberRight.setPosition(0);
            telemetry.addData("servo", "right");
        } else {
            pixelGrabberRight.setPosition(0.3);
        }
        if (gamepad1.left_bumper) {
            pixelGrabberLeft.setPosition(0.3);
            telemetry.addData("servo", "left");
        } else {
            pixelGrabberLeft.setPosition(0);
        }
        liftMotorLeft.setTargetPosition((int)(gamepad1.right_stick_x * ticks));
        liftMotorRight.setTargetPosition((int)(gamepad1.right_stick_x * ticks));
        liftMotorLeft.setPower(1);
        liftMotorRight.setPower(1);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void run_encoder(double turnage, double power, DcMotor motor) {
        newTarget = ticks * turnage;
        motor.setTargetPosition((int) newTarget);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
