package fr.gamagora.jponzo.rtrace4j.utils.interfaces;

public interface IVec3 {

	float getX();

	void setX(float x);

	float getY();

	void setY(float y);

	float getZ();

	void setZ(float z);

	/**
	 * Compute the sum of two vectors
	 * Warning : the return is a brand new object, not a reference
	 * @param v : the vector to add to this vector object
	 * @return : the sum this + v
	 */
	IVec3 sum(IVec3 v);
	
	/**
	 * Compute the subtraction of two vectors
	 * Warning : the return is a brand new object, not a reference
	 * @param v : the vector to subtract to this vector object
	 * @return : the subtraction this - v
	 */
	IVec3 sub(IVec3 v);
	
	/**
	 * Compute the term by term product of two vectors
	 * Warning : the return is a brand new object, not a reference
	 * @param v : the vector to multiply to this vector object
	 * @return : the product this * v
	 */
	IVec3 mult(IVec3 v);
	
	/**
	 * Perform the multiplication of this vector by the given scalar
	 * @param k : the given scalar
	 * @return the product k.this
	 */
	IVec3 mult(float k);
	
	/**
	 * Compute the term by term division of two vectors
	 * Warning : the return is a brand new object, not reference
	 * @param v : the vector dividing this vector object
	 * @return : the quotient this / v or null in case of by zero division
	 */
	IVec3 div(IVec3 v);

	/**
	 * Compute the square norme of this vector
	 * @return the square norme of the vector
	 */
	float norm2();

	/**
	 * Compute the norme of this vector
	 * @return the norme of the vector
	 */
	float norm();

	/**
	 * Compute the normalized version of this vector
	 * Warning : the return is a brand new object, not a reference
	 * @return this vector normalized or null if vector is (0, 0, 0)
	 */
	IVec3 normalized();

	/**
	 * Perform the dot product of this vector and the given v vector
	 * @param v : the given v vector
	 * @return : the dot product this.v
	 */
	float dot(IVec3 v);

	/**
	 * Perform the cross product of this vector and the given v vector
	 * Warning : the return is a brand new object, not a reference
	 * @param v : the given v vector
	 * @return : the cross product this.v
	 */
	IVec3 cross(IVec3 v);

	/**
	 * Test the equality of this vector with the given one (component diff compared sequentially to epsilon)
	 * @param v : the given vector
	 * @return true if equals, false otherwise
	 */
	boolean equals(IVec3 v);

	/**
	 * Test the equality of this vector with the given one (component diff compared sequentially to the given epsilon)
	 * @param v : the given vector
	 * @param epsilon the given epsilon
	 * @return true if equals, false otherwise
	 */
	boolean equals(IVec3 v, float epsilon);

	/**
	 * Test if this vector is collinear to the given v vector
	 * @param v the given vector
	 * @return true if they are collinear, false otherwise
	 */
	Boolean isCollinearTo(IVec3 v);
}
