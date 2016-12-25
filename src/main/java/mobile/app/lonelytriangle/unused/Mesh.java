package mobile.app.lonelytriangle.unused;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import mobile.app.lonelytriangle.rendering.IDrawableMesh;

/**
 * UNUSED! A Mesh is defined by a number of Vertices (coordinates) and a type (Triangle, Point, and so on). It is rendered directly by the GPU by using OpenGL. It is a helper to draw simple geometry graphical Objects.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Mesh implements IDrawableMesh
{
	/** the buffer of the vertices */
	private final FloatBuffer	verticesBuffer;

	private final ShortBuffer	indiciesBuffer;

	/** the vertices [x1,y1,z1,x2,y2,z2,...] */
	private final float[]		vertices;

	/** the number of vertices */
	private final int			numberOfVertices;

	/** the number of indicies */
	private final int			numberOfIndicies;

	/** the type of the mesh */
	private final MeshType		type;

	/** helper index to avoid wrong number of vertices */
	private int					index		= 0;

	private short[]				indicies	= null;

	public Mesh(final int numberOfVertices, final MeshType type)
	{
		this(numberOfVertices, 0, type);
	}

	public Mesh(final int numberOfVertices, final int numberOfIndicies, final MeshType type)
	{
		this.numberOfVertices = numberOfVertices;
		this.numberOfIndicies = numberOfIndicies;
		this.verticesBuffer = this.allocateFloatBuffer(numberOfVertices * 3);
		this.indiciesBuffer = this.allocateShortBuffer(numberOfIndicies * 2);
		this.type = type;
		this.vertices = new float[3 * numberOfVertices];
	}

	/**
	 * 
	 * @param gl
	 * @param width width of the viewport
	 * @param height height of the viewport
	 */
	public void render(final GL10 gl)
	{
		this.verticesBuffer.put(this.vertices);
		this.verticesBuffer.position(0);

		gl.glVertexPointer(this.numberOfVertices, GL10.GL_FLOAT, 0, this.verticesBuffer);
		if (this.indicies != null)
		{
			this.indiciesBuffer.put(this.indicies);
			this.indiciesBuffer.position(0);
			gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			gl.glDrawElements(this.getOpenGLType(), this.numberOfIndicies, GL10.GL_UNSIGNED_SHORT, this.indiciesBuffer);
		}
		else
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDrawArrays(this.getOpenGLType(), 0, this.numberOfVertices);
		}
	}

	/**
	 * Allocates and return a new FloatBuffer for the vertices.
	 * 
	 * @param size gernerally the number of vertices
	 * @return a new FloatBuffer for the vertices
	 */
	protected FloatBuffer allocateFloatBuffer(final int size)
	{
		final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 4);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asFloatBuffer();
	}

	protected ShortBuffer allocateShortBuffer(final int size)
	{
		final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 2);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asShortBuffer();
	}

	/**
	 * Add a new vertices to this mesh.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void addVertix(final float x, final float y)
	{
		this.addVertix(x, y, 0);
	}

	/**
	 * Add a new vertices to this mesh.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate (= 0 for 2d)
	 */
	public void addVertix(final float x, final float y, final float z)
	{
		if (this.index == this.getNumberOfVertices())
		{
			throw new IllegalArgumentException("more vertices than expected!");
		}
		final int offset = this.index * 3;

		this.vertices[offset] = x;
		this.vertices[offset + 1] = y;
		this.vertices[offset + 2] = z;
		this.index++;
	}

	public void addIndicies(final short[] indicies)
	{
		if (indicies.length != this.numberOfIndicies)
		{
			throw new IllegalArgumentException("more or less indicies than expected!");
		}
		this.indicies = indicies;
	}

	public int getNumberOfVertices()
	{
		return this.numberOfVertices;
	}

	protected MeshType getMeshType()
	{
		return this.type;
	}

	protected int getOpenGLType()
	{
		return meshTypeToOpenGLType(this.getMeshType());
	}

	private static int meshTypeToOpenGLType(final MeshType type)
	{
		switch (type)
		{
			case TRIANGLE:
				return GL10.GL_TRIANGLES;
			case POINT:
				return GL10.GL_POINTS;
			case TRIANGLE_STRIP:
				return GL10.GL_TRIANGLE_STRIP;
			case TRIANGLE_FAN:
				return GL10.GL_TRIANGLE_FAN;
			case LINE_LOOP:
				return GL10.GL_LINE_LOOP;
			case LINE:
				return GL10.GL_LINES;
			default:
				throw new IllegalArgumentException("wrong MeshType!");
		}
	}
}
