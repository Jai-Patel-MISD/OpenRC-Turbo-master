package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class VibeBotHardware
{
    /* Public OpMode members. */
    //public DcMotor  leftDrive   = null;
    public Servo    mallet1   = null;
    public DcMotorEx cart1 = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    private final ElapsedTime servoTime = new ElapsedTime();
    NotesLibrary notes = new NotesLibrary();
    /* Constructor */
    public VibeBotHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        //leftDrive  = hwMap.get(DcMotor.class, "left_drive");

        // Define and initialize ALL installed servos.
        mallet1  = hwMap.get(Servo.class, "mallet1");
        cart1 = hwMap.get(DcMotorEx.class, "cart1");
        cart1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cart1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mallet1.setPosition(.66);
    }


    public void drop(){
        while(servoTime.time() < 1){
            mallet1.setPosition(1);

        }
        mallet1.setPosition(.6);
        servoTime.reset();
    }
}