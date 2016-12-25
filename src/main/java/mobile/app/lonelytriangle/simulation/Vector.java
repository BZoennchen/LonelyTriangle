package mobile.app.lonelytriangle.simulation;

import android.util.FloatMath;

/**
 * A Vector is a helper class which offers some useful math methods. Its an mutalbe object.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Vector
{
    /** x-coordinate of this vector. */
    private float x;

    /** y-coordinate of this vector. */
    private float y;

    /** z-coordinate of this vector. */
    private float z;

    /**
     * Construct a new Vector object.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector(final float x, final float y, final float z)
    {
        this.set(x, y, z);
    }

    /**
     * Copy Constructor.
     * 
     * @param vector the original vector
     */
    public Vector(final Vector vector)
    {
        this(vector.x, vector.y, vector.z);
    }

    /**
     * vecotor addition.
     * 
     * @param vector the vector which will be added to this vector
     */
    public void add(final Vector vector)
    {
        this.set(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * vector subtraction.
     * 
     * @param vector the vector which will be subtracted from this vector
     */
    public void sub(final Vector vector)
    {
        this.set(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    /**
     * vector scalar multiolication.
     * 
     * @param vector the vector which will be scalar multiplicated to this vector
     * @return the scalar product of this vector and the parameter
     */
    public float scalarMul(final Vector vector)
    {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    /**
     * Calculates and returns the degree between two vectors.
     * 
     * @param vector the second vector which will be used to calculate the degree
     * @return the the degree between two vectors
     */
    public float degree(final Vector vector)
    {
        return (float) Math.acos(this.scalarMul(vector) / (this.length() * vector.length()));
    }

    /**
     * Returns the length of this vector |v|.
     * @return the length of this vector
     */
    public float length()
    {
        return FloatMath.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Returns the distance of this vector and the parameter.
     * @param vector the second vector for calculating the distance
     * @return the distance of this vector and the parameter
     */
    public float distance(final Vector vector)
    {
        final float a = vector.x - this.x;
        final float b = vector.y - this.y;
        final float c = vector.z - this.z;
        return FloatMath.sqrt(a * a + b * b + c * c);
    }

    /**
     * Convert this vector to a unit vector (length = 1).
     */
    public void toUnitVector()
    {
        final float length = this.length();
        this.set(this.x / length, this.y / length, this.z / length);
    }

    @Override
    public String toString()
    {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    /**
     * Sets x,y and z coordinates of this vector.
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     */
    public void set(final float x, final float y, final float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-coordinate of this vector.
     * @return the x-coordinate of this vector
     */
    public float getX()
    {
        return this.x;
    }

    /**
     * Returns the y-coordinate of this vector.
     * @return the y-coordinate of this vector
     */
    public float getY()
    {
        return this.y;
    }

    /**
     * Returns the z-coordinate of this vector.
     * @return the z-coordinate of this vector
     */
    public float getZ()
    {
        return this.z;
    }

    /**
     * Sets the x-coordinate of this vector.
     * @param x x-coordinate
     */
    public void setX(final float x)
    {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this vector.
     * @param y y-coordinate
     */
    public void setY(final float y)
    {
        this.y = y;
    }

    /**
     * Sets the z-coordinate of this vector.
     * @param z z-coordinate
     */
    public void setZ(final float z)
    {
        this.z = z;
    }
}
