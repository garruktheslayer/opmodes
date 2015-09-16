package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotAuto
//
/**
 * Extends the PushBotTelemetry and PushBotHardware classes to provide a custom made
 * auto mode. Currently, the push bot should make a square (forward, left, forward, left,
 * forward, left, forward)
 *
 * @author Dylan Siegler
 * @version 2015-09-13
 */
public class CustomPushBotAuto extends PushBotTelemetry

{
    //--------------------------------------------------------------------------
    //
    // v_state
    //
    //--------
    // This class member remembers which state is currently active.  When the
    // start method is called, the state will be initialized (0).  When the loop
    // starts, the state will change from initialize to state_1.  When state_1
    // actions are complete, the state will change to state_2.  This implements
    // a state machine for the loop method.
    //--------
    int v_state = 0;

    //--------------------------------------------------------------------------
    //
    // PushBotAuto
    //
    /**
     * Constructs the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public CustomPushBotAuto()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotAuto::PushBotAuto

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Performs any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders ();

    } // PushBotAuto::start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    static int[] l_times = new int [6];
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
            //
            // Synchronoize the state machine and hardware.
            //
            case 0:
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders ();

                //
                // Transition to the next state when this method is called again.
                //
                l_times[0] = 0;
                l_times[1] = 0;
                l_times[2] = 0;
                l_times[3] = 0;
                l_times[4] = 0;
                l_times[5] = 0;
                v_state++;

                break;
            //
            // Drive forward until the encoders exceed the specified values.
            //
            case 1:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders ();

                //
                // Start the drive wheel motors at full power.
                //
                set_drive_power (1.0f, 1.0f);

                //
                // Have the motor shafts turned the required amount?
                //
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (have_drive_encoders_reached (2880, 2880))
                {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders ();

                    //
                    // Stop the motors.
                    //
                    set_drive_power (0.0f, 0.0f);

                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 2:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[0]++;
                }
                break;
            //
            // Turn left until the encoders exceed the specified values.
            //
            case 3:
                run_using_encoders ();
                set_drive_power (-1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 4:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[1]++;
                }
                break;
            //
            // Go foward until the encoders exceed the specified values.
            //
            case 5:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 6:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[2]++;
                }
                break;

            //Turn left

            case 7:

                run_using_encoders ();
                set_drive_power (-1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;

            case 8:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[2]++;
                }
                break;

            //forward

            case 9:
                run_using_encoders();
                set_drive_power(1.0f, 1.0f);
                if(have_drive_encoders_reached(2800, 2800)){

                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;

            case 10:

                if(have_drive_encoders_reset()){

                    v_state++;

                }
                else{

                    l_times[3]++;

                }

                break;

            //left

                case 11:

                    run_using_encoders();
                    set_drive_power(-1.0f, 1.0f);
                    if(have_drive_encoders_reached(2800, 2800)){

                        reset_drive_encoders();
                        set_drive_power(0.0f, 0.0f);
                        v_state++;

                    }
                    break;

                case 12:
                    if(have_drive_encoders_reset()){

                        v_state++;

                    }
                    else{

                        l_times[4]++;

                    }
                    break;


            //forward

            case 13:
                run_using_encoders();
                set_drive_power(1.0f, 1.0f);
                if (have_drive_encoders_reached(1800, 1800)){

                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;

            case 14:
                if(have_drive_encoders_reset()){

                    v_state++;

                }
                else{

                    l_times[5] = 0;

                }
                break;

            //
            // Perform no action - stay in this case until the OpMode is stopped.
            // This method will still be called regardless of the state machine.
            //
            default:
                //
                // The autonomous actions have been accomplished (i.e. the state has
                // transitioned into its final state.
                //
                break;
            }

        //
        // Send telemetry data to the driver station.
        //
        update_telemetry (); // Update common telemetry
        telemetry.addData("Current ", "State (1, 3, 5 = moving; 2, 4, 6 = resetting): " + v_state);
        telemetry.addData ("Times Reset ", "(1): " + l_times[0]);
        telemetry.addData ("Times Reset ", "(2): " + l_times[1]);
        telemetry.addData ("Times Reset ", "(3): " + l_times[2]);
        telemetry.addData ("Times Reset ", "(4): " + l_times[3]);
        telemetry.addData ("Times Reset ", "(5): " + l_times[4]);
        telemetry.addData ("Times Reset ", "(6): " + l_times[5]);


    } // PushBotAuto::loop

} // PushBotAuto
