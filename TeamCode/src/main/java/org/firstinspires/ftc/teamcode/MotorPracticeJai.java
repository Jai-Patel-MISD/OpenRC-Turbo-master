package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorPracticeJai extends OpMode {
    DcMotorEx frontRight;
    DcMotorEx frontLeft;
    DcMotorEx backRight;
    DcMotorEx backLeft;
    //private final DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};
    private final DcMotorEx[] noahThing = new DcMotorEx[4];
    private final String[] motorsName = {"frontLeft", "frontRight", "backLeft", "backRight"};
    @Override
    public void init() {

        for (int i = 0; i < noahThing.length; i++ ){
            noahThing[i] = hardwareMap.get(DcMotorEx.class, motorsName[i]);
            noahThing[i].setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    @Override
    public void loop() {

    }
}
