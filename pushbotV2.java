package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "pushbotV2" )


public class pushbotV2 extends OpMode {
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
    double robotSpeed, liftPixelSpeed, robotFrontBack, robotRightLeft, liftUpDown, pixelAngle;


    @Override
    public void init() {
        robotGearRight = hardwareMap.get(DcMotor.class, "GearMotorRight");
        robotGearLeft = hardwareMap.get(DcMotor.class, "GearMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "LiftRight");
        liftMotorLeft = hardwareMap.get(DcMotor.class, "LiftLeft");

        pixelArmAngle = hardwareMap.get(Servo.class, "PixelAngle");
        airPlaneServo = hardwareMap.get(Servo.class, "Airplane");
        pixelGrabberRight = hardwareMap.get(Servo.class, "PixelRight");
        pixelGrabberLeft = hardwareMap.get(Servo.class, "PixelLeft");

        robotGearRight.setDirection(DcMotorSimple.Direction.FORWARD);
        robotGearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //liftMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);
        //liftMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        //liftMotorLeft = hardwareMap.get(DcMotor.class, "armL");
        liftMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //liftMotorRight = hardwareMap.get(DcMotor.class, "armR");
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);





        robotGearLeft = hardwareMap.get(DcMotor.class, "driveL");
        robotGearLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //set start position
        pixelArmAngle.setPosition(0.9);
        pixelGrabberRight.setPosition(0);

        telemetry.addData("PushBot", "Initialized");
    }


    @Override
    public void loop() {
        telemetry.addData("PushBot", "run");
        robotSpeed = gamepad1.right_trigger;
        liftPixelSpeed = gamepad1.left_trigger;
        robotFrontBack = gamepad1.left_stick_y;
        robotRightLeft = gamepad1.left_stick_x;
        liftUpDown = gamepad1.right_stick_y;
        pixelAngle = gamepad1.left_stick_x;

        if (gamepad1.right_bumper) {
            pixelGrabberRight.setPosition(0.8);
            telemetry.addData("servo", "right");
        } else {
            pixelGrabberRight.setPosition(0.75);
        }
        if (gamepad1.left_bumper) {
            pixelGrabberLeft.setPosition(0.79);
            telemetry.addData("servo", "left");
        } else {
            pixelGrabberLeft.setPosition(0.8);
        }

        robotGearRight.setPower((robotRightLeft + robotFrontBack) * robotSpeed);
        robotGearLeft.setPower((-robotRightLeft + robotFrontBack) * robotSpeed);
        liftMotorRight.setPower(liftUpDown * liftPixelSpeed);
        liftMotorRight.setPower(liftUpDown * liftPixelSpeed);
        //airplane
        if (gamepad1.a) {
            airPlaneServo.setPosition(1);
        } else {
            airPlaneServo.setPosition(0.8);
        }


        if (gamepad1.right_bumper) { // Controls Pixel arm Position - UP for release
            run_encoder(1.9, 0.7, liftMotorRight);
            run_encoder(1.9, 0.7, liftMotorLeft);
            pixelArmAngle.setPosition(0.9);
        }
        if (gamepad1.left_bumper){ // Control Pixel arm Position - DOWN for pick
            run_encoder(0.0, 0.5, liftMotorRight);
            run_encoder(0.0, 0.5, liftMotorLeft);
            pixelArmAngle.setPosition(0.9);
        }




    }

    public void run_encoder(double turnage, double power, DcMotor motor) {
        newTarget = ticks * turnage;
        motor.setTargetPosition((int) newTarget);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}

