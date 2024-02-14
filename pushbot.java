package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "pushbot" )
  // motors and servos names

/* Controller Description:
    RT = accelerate (Planetary Gear speed)
    LT = lift motors and pixel servos speed
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
 */
public class pushbot extends OpMode {
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
    double robotSpeed,liftPixelSpeed,robotFrontBack,robotRightLeft,liftUpDown,pixelAngle;

    @Override
    public void init() {

        // Motors
        robotGearRight = hardwareMap.get(DcMotor.class, "GearMotorRight");
        robotGearLeft = hardwareMap.get(DcMotor.class, "GearMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "LiftRight");
        liftMotorLeft = hardwareMap.get(DcMotor.class, "LiftLeft");

        // Servos
        pixelArmAngle = hardwareMap.get(Servo.class,"PixelAngle");
        airPlaneServo = hardwareMap.get(Servo.class, "Airplane");
        pixelGrabberRight = hardwareMap.get(Servo.class, "PixelRight");
        pixelGrabberLeft = hardwareMap.get(Servo.class,"PixelLeft");

        // Set Motors Direction and Modes
        robotGearRight.setDirection(DcMotorSimple.Direction.FORWARD);
        robotGearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        //liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



      /*
        robotGearRight = hardwareMap.get(DcMotor.class, "driveR");
        pixelArmAngle = hardwareMap.get(Servo.class, "armS");
        pixelGrabberRight = hardwareMap.get(Servo.class, "graberS");
        pixelGrabberLeft = hardwareMap.get(Servo.class, "")
        liftLeftMotor = hardwareMap.get(DcMotor.class, "armL");
        liftLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        liftLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRightMotor = hardwareMap.get(DcMotor.class, "armR");
        liftRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robotGearLeft = hardwareMap.get(DcMotor.class, "driveL");
        robotGearLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //set start position
        pixelArmAngle.setPosition(0.9);
        pixelGrabberRight.setPosition(0);
*/
        telemetry.addData("PushBot", "Initialized");
    }
     //controls
    @Override
    public void loop() {
        telemetry.addData("PushBot", "run");
        robotSpeed = gamepad1.right_trigger;
        liftPixelSpeed = gamepad1.left_trigger;
        robotFrontBack = gamepad1.left_stick_y;
        robotRightLeft = gamepad1.left_stick_x;
        liftUpDown = gamepad1.right_stick_y;
        pixelAngle = gamepad1.left_stick_x;


        // Pixel Operation
        if (gamepad1.right_bumper) {
            pixelGrabberRight.setPosition(0.8);
            telemetry.addData("servo" , "right");
        }
        else {
            pixelGrabberRight.setPosition(0.75);
        }
        if (gamepad1.left_bumper)
        {
            pixelGrabberLeft.setPosition(0.79);
            telemetry.addData("servo" , "left");
        }
        else {
            pixelGrabberLeft.setPosition(0.8);
        }

        // Motor Operation:
        robotGearRight.setPower((robotRightLeft+robotFrontBack)*robotSpeed);
        robotGearLeft.setPower((-robotRightLeft+robotFrontBack)*robotSpeed);
        liftMotorRight.setPower(liftUpDown*liftPixelSpeed);
        liftMotorRight.setPower(liftUpDown*liftPixelSpeed);

        // Airplane Servo
        if (gamepad1.a) {
            airPlaneServo.setPosition(1);}
        else {
            airPlaneServo.setPosition(0.8);}

        //arm movment
        if (gamepad1.x) { // pixel arm position up
            pixelArmAngle.setPosition(0.4);
        }
        if (gamepad1.y) { // pixel arm position up
            pixelArmAngle.setPosition(1);
        }




/*
        forward = gamepad1.left_stick_y/2.0;
        side = gamepad1.left_stick_x/2.0;
        powerMultiplier = gamepad1.right_trigger/2.0;
        right_bump = gamepad1.right_trigger;
        left_bump = gamepad1.left_trigger;

        telemetry.addData("Controls","b/y for graber. x/a for servo arm");
        telemetry.addData("Controls","RB for powerAdjusting, RT for arm");
        telemetry.addData("RT",powerMultiplier);



        if (gamepad1.b) {
            pixelGrabberRight.setPosition(0.5);
        }
        if (gamepad1.y) {
            pixelGrabberRight.setPosition(0.1);
        }
        if (gamepad1.right_bumper) { // Controls Pixel arm Position - UP
            run_encoder(1.9, 0.7, liftRightMotor);
            run_encoder(1.9, 0.7, liftLeftMotor);
        }
        if (gamepad1.left_bumper){ // Control Pixel arm Position - DOWN
            run_encoder(0.0, 0.5, liftRightMotor);
            run_encoder(0.0, 0.5, liftLeftMotor);
        }


        telemetry.addData("forward value:",forward);

        telemetry.addData("side value:", side);

        robotGearRight.setPower((-side+forward)*0.7);
        robotGearLeft.setPower((side+forward)*0.7);
*/

    }


    public void run_encoder(double turnage, double power, DcMotor motor) {
        newTarget = ticks * turnage;
        motor.setTargetPosition((int) newTarget);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
