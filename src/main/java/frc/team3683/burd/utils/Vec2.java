package frc.team3683.burd.utils;

/**
 * Base class representing 2D vector or point, for use in spline following
 * @author Hsifeulbhsifder 
 */
public class Vec2 {
    private double x;
    private double y;

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 other){
        x = other.x;
        y = other.y;
    }

    public Vec2(){
        this(0,0);
    }

    public Vec2 Add(Vec2 other){
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 Sub(Vec2 other){
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 Scale(double s){
        return new Vec2(s*x, s*y);
    }

    public double Dot(Vec2 other){
        return x * other.x + y * other.y;
    }

    public double Length(){
        return Math.sqrt(x*x + y*y);
    }

    public double Distance(Vec2 other){
        double dx = other.x - x;
        double dy = other.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Linear Interpolation between two points
     * @param alpha value from 0 to 1 representing percentage completion
     * @param start starting point
     * @param end ending point
     * @return point on the line between starting and ending point at alpha percentage
     */
    public static Vec2 Lerp(double alpha, Vec2 start, Vec2 end){
        double newx = (1.0 - alpha) * start.x + alpha * end.x;
        double newy = (1.0 - alpha) * start.y + alpha * end.y;
        return new Vec2(newx, newy);
    }


}
