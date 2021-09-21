package org.firstinspires.ftc.teamcode;

public class PID
{
    public double kP;
    public double kI;
    public double kD;

    public double tau;

    public double limMin;
    public double limMax;

    public double limMinInt;
    public double limMaxInt;

    public double T;

    public double tolerance;

    private double integrator;
    private double prevError;
    private double differentiator;
    private double prevMeasurement;
    public double setPoint;

    public boolean atSetpoint;

    private double out;

    public PID()
    {
        integrator = 0.0;
        prevError = 0.0;
        differentiator = 0.0;
        prevMeasurement = 0.0;
        atSetpoint = false;
        out = 0.0;
    }

    public double calculate(double measurement)
    {
        /**
         * Error
         */
        double error = setPoint - measurement;

        /**
         * Setpoint check
         */
        if (Math.abs(error) <= tolerance)
        {
            atSetpoint = true;
            return 0.0;
        }
        else
        {
            atSetpoint = false;
        }

        /**
         * Proportional
         */
        double proportional = kP * error;

        /**
         * Integral
         */
        integrator = integrator + 0.5 * kI * T * (error + prevError);

        /**
         * Anti Wind up
         */
        if (integrator > limMaxInt)
        {
            integrator = limMaxInt;
        }
        else if (integrator < limMinInt)
        {
            integrator = limMinInt;
        }

        /**
         * Band limit derivative
         */
        differentiator = -(2.0 * kD * (measurement - prevMeasurement)
                + (2.0 * tau - T) * differentiator)
                / (2.0 * tau + T);

        /**
         * Compute
         */
        out = proportional + integrator + differentiator;

        /**
         * Clamp
         */
        if (out > limMax)
        {
            out = limMax;
        }
        else if (out < limMin)
        {
            out = limMin;
        }

        /**
         * Store variables
         */
        prevError = error;
        prevMeasurement = measurement;

        /**
         * Return final value
         */
        return out;
    }
}
