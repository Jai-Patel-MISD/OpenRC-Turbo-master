package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorPracticeJai extends OpMode {
    DcMotorEx frontRight;
    DcMotorEx frontLeft;
    DcMotorEx backRight;
    DcMotorEx backLeft;
    private final DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};
    private final String[] motorsName = {"frontLeft", "frontRight", "backLeft", "backRight"};
    @Override
    public void init() {
        int i;
        for (i = 0; i < 4; i++ ){
            motors[i] = hardwareMap.get(DcMotorEx.class, motorsName[i]);
            motors[i].setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    @Override
    public void loop() {

    }
}
