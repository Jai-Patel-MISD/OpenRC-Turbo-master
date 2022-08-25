package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name= "UrMom")

public class YeffreyAustin extends OpMode {
    Servo TheMooCowsGoMoo;
    DcMotorEx Name;
    @Override
    public void init() {
        TheMooCowsGoMoo = hardwareMap.get(Servo.class, "TheMooCowsGoMoo");
        Name = hardwareMap.get(DcMotorEx.class, "TheMooMotor");
        Name.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TheMooCowsGoMoo.setPosition(.5);
    }
    @Override
    public void loop() {
        Name.setTargetPosition(1000);
        Name.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Name.setVelocity(300);
    }
}
