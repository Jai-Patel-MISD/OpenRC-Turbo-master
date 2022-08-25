package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Testing")
public class Testing extends OpMode {
    public Servo MooServo;
    @Override
    public void init() {
        MooServo = hardwareMap.get(Servo.class,"moo servo");
        MooServo.setPosition(0);
    }

    @Override
    public void loop() {

    }
}
