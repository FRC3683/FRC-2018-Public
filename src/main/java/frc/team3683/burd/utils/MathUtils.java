package frc.team3683.burd.utils;

public class MathUtils {

    public static double square(double x){
        return x * x;
    }

    public static double cube(double x){
        return x * x * x;
    }

    public static double beta(double x, double totalDistance){
        return x / totalDistance;
    }

    public static double alpha(double beta, double p){
        return (beta - p) / (1.0 - p);
    }

    //@TODO fix this, as it is can currently spit out valuese greater than the starting velocity!!!!!
    public static double lerp(double alpha, double startingVelocity, double finalVelocity){
        double lerp = (1.0 - alpha) * startingVelocity + (finalVelocity * alpha);
        if(lerp > startingVelocity){
            lerp = startingVelocity;
        } else if(lerp < finalVelocity){
            lerp = finalVelocity;
        }
        return lerp;
    }

    public static double cubic_bezier(double start, double c1, double c2, double end, double alpha){
        double result = start * cube(1.0 - alpha) + 3.0 * c1 * square(1.0 - alpha) * alpha + 3.0 * c2 * (1.0 - alpha) * square(alpha) + end * cube(alpha);
        return result;
    }

}
