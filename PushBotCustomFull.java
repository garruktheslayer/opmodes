package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotManual
//
/**
 * Extends the PushBotTelemetry and PushBotHardware classes to provide a basic
 * manual operational mode for the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class PushBotCustomFull extends PushBotTelemetry

{
    //--------------------------------------------------------------------------
    //
    // PushBotManual
    //
    //--------
    // Constructs the class.
    //
    // The system calls this member when the class is instantiated.
    //--------
    public PushBotCustomFull()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotManual::PushBotManual

    //--------------------------------------------------------------------------
    //
    // loop
    //
    //--------
    // Initializes the class.
    //
    // The system calls this member repeatedly while the OpMode is running.
    //--------
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // DC Motors
        //
        // Obtain the current values of the joystick controllers.
        //
        // Note that x and y equal -1 when the joystick is pushed all of the way
        // forward (i.e. away from the human holder's body).
        //
        // The clip method guarantees the value never exceeds the range +-1.
        //
        //Functionality:
        //-Game Pad 1's left stick (y axis) acts as a throttle
        //while the x axis acts and turning.
        //-Pressing the right trigger will put the robot in fine
        //control mode, making the max speed 10%
        //-Right and left bumpers turn the robot by little amounts (5% speed?)
        //and the DPAD up and down do the same for forwards and backwards
        //-Right stick (y axis) controls arm while Y (grip) and A (ungrip) control
        //the gripper (no fine controls on gp1), only while right stick, Y button,
        //or A button is being used
        //-Game pad 2 will act as normal for now with the equivalent fine control and will
        //using X (grip) and B (ungrip) and the DPAD up and down for up
        //and down 5%?
        //Potential features:
        //-the more you hold down the trigger the more fine the control
        //-DPAD selector for the amount of fine control
        //-Make a debug OP Mode (telementary on everything)
        //Finished features:
        //-GP2 (fully finished)
        //GP1???
        //
        // The setPower methods write the motor power values to the DcMotor
        // class, but the power levels aren't applied until this method ends.
        //

        //
        // Manage the drive wheel motors.
        //
        //Regular (not fine) control mode

        float l_gp1_left_stick_y = -gamepad1.left_stick_y;
        float l_drive_power = l_gp1_left_stick_y;
        float r_drive_power = l_gp1_left_stick_y;
        float l_gp1_left_stick_x = gamepad1.left_stick_x;
        l_drive_power += l_gp1_left_stick_x;
        r_drive_power += -l_gp1_left_stick_x;



        //fine control mode

        if(gamepad1.right_trigger > 0.01){

            l_drive_power = l_gp1_left_stick_y / 10;
            r_drive_power = l_gp1_left_stick_y / 10;
            l_drive_power += l_gp1_left_stick_x / 10;
            r_drive_power += -l_gp1_left_stick_x / 10;

        }

        if (l_drive_power > 1) {

            l_drive_power = 1;

        }

        if (l_drive_power < -1) {

            l_drive_power = -1;

        }

        if (r_drive_power > 1) {

            r_drive_power = 1;

        }

        if (r_drive_power < -1) {

            r_drive_power = -1;

        }

        //DPAD fine movement (front and back) and bumpers (turning)

        if(gamepad1.dpad_up){

            l_drive_power = 0.05f;
            r_drive_power = 0.05f;

        }

        if(gamepad1.dpad_down){

            l_drive_power = -0.05f;
            r_drive_power = -0.05f;

        }
        if(gamepad1.right_bumper){

            l_drive_power = 0.05f;
            r_drive_power = -0.05f;

        }
        if(gamepad1.left_bumper){

            l_drive_power = -0.05f;
            r_drive_power = 0.05f;

        }

        set_drive_power(l_drive_power, r_drive_power);

        //----------------------------------------------------------------------
        //
        // Servo Motors and the arm
        //
        // Obtain the current values of the gamepad 'x' and 'b' buttons.
        //
        // Note that x and b buttons have boolean values of true and false.
        //
        // The clip method guarantees the value never exceeds the allowable range of
        // [0,1].
        //
        // The setPosition methods write the motor power values to the Servo
        // class, but the positions aren't applied until this method ends.
        //

        //If GP1 Y button, B button, or y axis of left stick is on,
        //GP1 can control arm and gripper

        if(gamepad1.y || gamepad1.b || gamepad1.left_stick_y != 0){

            float gp1_left_stick_y = -gamepad1.left_stick_y;
            float sec_left_arm_power = gp1_left_stick_y;
            v_motor_left_arm.setPower(sec_left_arm_power);

            if(gamepad1.y){

                m_hand_position (a_hand_position () + 0.05);

            }

            if (gamepad1.a){

                m_hand_position(a_hand_position() - 0.05);

            }

        }

        //
        // Manage the arm motor.
        //
        float gp2_left_stick_y = -gamepad2.left_stick_y;

        //Fine control on GP2 (right trigger pressed)

        if(gamepad2.right_bumper){

            if (gamepad2.y) {
                m_hand_position(a_hand_position() + 0.01);
            } else if (gamepad2.a) {
                m_hand_position(a_hand_position() - 0.01);
            }

            float main_left_arm_power = gp2_left_stick_y / 10;
            v_motor_left_arm.setPower (main_left_arm_power);

        }
        //regular control
        else {

            if (gamepad2.y) {
                m_hand_position(a_hand_position() + 0.05);
            } else if (gamepad2.a) {
                m_hand_position(a_hand_position() - 0.05);
            }

            float main_left_arm_power = gp2_left_stick_y;
            v_motor_left_arm.setPower (main_left_arm_power);

        }

        //use GP2 DPAD up and down to fine control the arm

        if(gamepad2.dpad_up){

            v_motor_left_arm.setPower(0.05);

        }

        if(gamepad2.dpad_down){

            v_motor_left_arm.setPower(-0.05);

        }

        //
        // Send telemetry data to the driver station.
        //
        update_telemetry (); // Update common telemetry
        telemetry.addData ("GP1 ", "Left (y) : " + l_gp1_left_stick_y);
        telemetry.addData ("GP1 ", "Left (x) : " + l_gp1_left_stick_x);
        telemetry.addData ("GP2 ", "Left: " + gp2_left_stick_y);
        telemetry.addData ("GP2 ", "Y: " + gamepad2.y);
        telemetry.addData ("GP2 ", "A: " + gamepad2.a);

    } // PushBotManual::loop

} // PushBotManual
