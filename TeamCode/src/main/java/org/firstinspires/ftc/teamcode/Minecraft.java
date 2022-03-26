package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Minecraft")
//@Disabled
public class Minecraft extends OpMode {
    MusicMaker music = new MusicMaker();
    NotesLibrary notes = new NotesLibrary();
    public double BPM = 80;
    private final double quarterNote = 60/BPM;
    private final double halfNote = quarterNote * 2;
    private final double wholeNote = quarterNote * 4;
    private final double eighthNote = quarterNote/2;
    private final double sixteenthNote = quarterNote/4;
    private final double firstNote = 0;
    @Override
    public void init() {
        music.notesOrder = new Double[]{
                notes.g2, notes.a3, notes.b3, notes.c3,//1
                notes.g2, notes.a3, notes.b3, notes.d3,//2
                notes.c3, notes.b3, notes.c3, notes.d3,//3
                notes.g2, //whole note 4
                notes.g2, notes.a3, notes.b3, notes.c3,//5
                notes.g2, notes.a3, notes.b3, notes.d3,//6
                notes.c3, notes.b3, notes.c3, notes.d3,//7
                notes.g2, //dotted half treated whole 8
                notes.g2, notes.a3, notes.b3, notes.g2,//9
                notes.e2, notes.d2, notes.g2, notes.e2,//10
                notes.d2, notes.e2, notes.d2, notes.c2, notes.d2,//11
                notes.e2, notes.g2,//half note then dotted half 12
                notes.b3, notes.c3, notes.d3, //13+beat one 14 dotted half then eightnote, eight tied = make quarter
                notes.g2, notes.e2, notes.d2, notes.g2, notes.a3, notes.b3,//14+beat one 15complicated
                notes.c3, notes.b3, notes.d3, notes.b3, notes.a3, notes.b3,//15+16
                notes.b3, notes.g2, notes.a4, notes.g3,//17+beat one 18
                notes.d3, notes.g2, notes.d2, notes.a3, notes.b3,//18+beat one 19
                notes.c3, notes.b3, notes.d3, notes.b3, notes.d3, notes.e3, notes.g3, notes.b3, notes.a3, notes.b3,//19+20
                notes.d3, notes.c3, notes.b3, notes.g2,//21
                notes.f3, notes.e3, notes.g3, notes.e3,//22
                notes.g2, notes.b3, notes.d3, notes.g3,//23
                notes.b3,//24 whole note
                notes.f2, notes.a3, notes.a3, notes.b3,//25
                notes.f2, notes.e3,//26
                notes.f2, notes.a3, notes.a3, notes.b3,//27 r:25
                notes.f2, notes.e3,//28 r:26
                notes.f2, notes.g2, notes.a3, notes.b3, notes.a3, notes.g2, notes.d2,//29+firstnotes30
                notes.c2, notes.d2, notes.e2, notes.f2, notes.g2,//30
                notes.f2, notes.g2, notes.a3, notes.b3, notes.a3, notes.g2, notes.d2,//31 r:29+firstnotes30
                notes.c2, notes.d2, notes.e2, notes.f2, notes.g2,//32 r:30
                notes.g2, notes.b3, notes.d3, notes.e3, notes.c3, notes.b3, notes.g2,//33+firstnote34
                notes.c2, notes.d2, notes.e2, notes.f2, notes.g2,//34 r:30
                notes.f2, notes.g2, notes.a3, notes.b3, notes.a3, notes.g2, notes.d2,//35+firstnote36
                notes.c2, notes.d2, notes.e2, notes.f2, notes.g2, //36
                notes.f2, notes.g2, notes.a3, notes.b3, notes.a3, notes.g2, notes.d2,//37 r:35+firstnote36
                notes.c2, notes.g1

        };
        music.noteTime = new Double[]{
                firstNote, quarterNote, quarterNote, quarterNote,//1
                quarterNote, quarterNote, quarterNote, quarterNote,//2
                quarterNote, quarterNote, quarterNote, quarterNote,//3
                wholeNote,//4
                quarterNote, quarterNote, quarterNote, quarterNote,//5
                quarterNote, quarterNote, quarterNote, quarterNote,//6
                quarterNote, quarterNote, quarterNote, quarterNote,//7
                wholeNote,//8
                quarterNote, quarterNote, quarterNote, quarterNote,//9
                quarterNote, quarterNote, quarterNote, quarterNote,//10
                quarterNote, eighthNote, eighthNote, quarterNote, quarterNote,//11
                halfNote, halfNote*1.5,//12
                halfNote*1.5, eighthNote, quarterNote,//13+first14
                eighthNote, eighthNote, quarterNote, eighthNote, eighthNote, quarterNote,//14+first15
                sixteenthNote, quarterNote, sixteenthNote, quarterNote, eighthNote, halfNote*1.5 + eighthNote,//15+16
                halfNote, quarterNote, eighthNote, quarterNote,//17+first 18
                eighthNote, quarterNote, eighthNote, eighthNote, quarterNote,//18+19first
                sixteenthNote,sixteenthNote,sixteenthNote,eighthNote,eighthNote,sixteenthNote,sixteenthNote,sixteenthNote,eighthNote,wholeNote+eighthNote,//19+20
                quarterNote, quarterNote, quarterNote, quarterNote,//21
                quarterNote, quarterNote, quarterNote, quarterNote,//22
                quarterNote, eighthNote, eighthNote, quarterNote, quarterNote,//23
                wholeNote,//24
                quarterNote, quarterNote, quarterNote, quarterNote,//25
                halfNote,halfNote,//26
                quarterNote, quarterNote, quarterNote, quarterNote,//27
                halfNote,halfNote,//28
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,quarterNote+quarterNote*1.5,//29
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,//30
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,quarterNote+quarterNote*1.5,//30
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,//32
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,quarterNote+quarterNote*1.5,//33
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,//34
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,quarterNote+quarterNote*1.5,//35
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,//36
                eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,eighthNote,halfNote*1.5,//37
                quarterNote,quarterNote+wholeNote,
        };
        music.init();
    }

    @Override
    public void init_loop(){
        music.init_loop();
    }

    @Override
    public void start(){
        music.start();
    }

    @Override
    public void loop() {
        music.loop();
    }
}
