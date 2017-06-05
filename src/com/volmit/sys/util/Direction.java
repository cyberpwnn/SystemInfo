package com.volmit.sys.util;

import org.bukkit.util.Vector;
import com.volmit.sys.util.Cuboid.CuboidDirection;

/**
 * Directions
 * 
 * @author cyberpwn
 */
public enum Direction
{
	U(0, 1, 0, CuboidDirection.Up),
	D(0, -1, 0, CuboidDirection.Down),
	N(0, 0, -1, CuboidDirection.North),
	S(0, 0, 1, CuboidDirection.South),
	E(1, 0, 0, CuboidDirection.East),
	W(-1, 0, 0, CuboidDirection.West);
	
	private static GMap<GBiset<Direction, Direction>, DOP> permute = null;
	
	private int x;
	private int y;
	private int z;
	private CuboidDirection f;
	
	public boolean isVertical()
	{
		return equals(D) || equals(U);
	}
	
	public static Direction closest(Vector v, Direction... d)
	{
		double m = Double.MAX_VALUE;
		Direction s = null;
		
		for(Direction i : d)
		{
			Vector x = i.toVector();
			double g = x.distance(v);
			
			if(g < m)
			{
				m = g;
				s = i;
			}
		}
		
		return s;
	}
	
	public Vector toVector()
	{
		return new Vector(x, y, z);
	}
	
	public boolean isCrooked(Direction to)
	{
		if(equals(to.reverse()))
		{
			return false;
		}
		
		if(equals(to))
		{
			return false;
		}
		
		return true;
	}
	
	private Direction(int x, int y, int z, CuboidDirection f)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.f = f;
	}
	
	public Vector angle(Vector initial, Direction d)
	{
		calculatePermutations();
		
		for(GBiset<Direction, Direction> i : permute.k())
		{
			if(i.getA().equals(this) && i.getB().equals(d))
			{
				return permute.get(i).op(initial);
			}
		}
		
		System.out.println("Failed to find vector angle permutation for " + this + " to " + d);
		return initial;
	}
	
	public Direction reverse()
	{
		switch(this)
		{
			case D:
				return U;
			case E:
				return W;
			case N:
				return S;
			case S:
				return N;
			case U:
				return D;
			case W:
				return E;
			default:
				break;
		}
		
		return null;
	}
	
	public int x()
	{
		return x;
	}
	
	public int y()
	{
		return y;
	}
	
	public int z()
	{
		return z;
	}
	
	public CuboidDirection f()
	{
		return f;
	}
	
	public static GList<Direction> news()
	{
		return new GList<Direction>().qadd(N).qadd(E).qadd(W).qadd(S);
	}
	
	public static Direction getDirection(Vector v)
	{
		Vector k = VectorMath.triNormalize(v.clone().normalize());
		
		for(Direction i : udnews())
		{
			if(i.x == k.getBlockX() && i.y == k.getBlockY() && i.z == k.getBlockZ())
			{
				return i;
			}
		}
		
		return Direction.N;
	}
	
	public static GList<Direction> udnews()
	{
		return new GList<Direction>().qadd(U).qadd(D).qadd(N).qadd(E).qadd(W).qadd(S);
	}
	
	/**
	 * Get the directional value from the given byte from common directional
	 * blocks (MUST BE BETWEEN 0 and 5 INCLUSIVE)
	 * 
	 * @param b
	 *            the byte
	 * @return the direction or null if the byte is outside of the inclusive
	 *         range 0-5
	 */
	public static Direction fromByte(byte b)
	{
		if(b > 5 || b < 0)
		{
			return null;
		}
		
		if(b == 0)
		{
			return D;
		}
		
		else if(b == 1)
		{
			return U;
		}
		
		else if(b == 2)
		{
			return N;
		}
		
		else if(b == 3)
		{
			return S;
		}
		
		else if(b == 4)
		{
			return W;
		}
		
		else
		{
			return E;
		}
	}
	
	/**
	 * Get the byte value represented in some directional blocks
	 * 
	 * @return the byte value
	 */
	public byte byteValue()
	{
		switch(this)
		{
			case D:
				return 0;
			case E:
				return 5;
			case N:
				return 2;
			case S:
				return 3;
			case U:
				return 1;
			case W:
				return 4;
			default:
				break;
		}
		
		return -1;
	}
	
	public static void calculatePermutations()
	{
		if(permute != null)
		{
			return;
		}
		
		permute = new GMap<GBiset<Direction, Direction>, DOP>();
		
		for(Direction i : udnews())
		{
			for(Direction j : udnews())
			{
				GBiset<Direction, Direction> b = new GBiset<Direction, Direction>(i, j);
				
				if(i.equals(j))
				{
					permute.put(b, new DOP("DIRECT")
					{
						@Override
						public Vector op(Vector v)
						{
							return v;
						}
					});
				}
				
				else if(i.reverse().equals(j))
				{
					if(i.isVertical())
					{
						permute.put(b, new DOP("R180CCZ")
						{
							@Override
							public Vector op(Vector v)
							{
								return VectorMath.rotate90CCZ(VectorMath.rotate90CCZ(v));
							}
						});
					}
					
					else
					{
						permute.put(b, new DOP("R180CCY")
						{
							@Override
							public Vector op(Vector v)
							{
								return VectorMath.rotate90CCY(VectorMath.rotate90CCY(v));
							}
						});
					}
				}
				
				else if(getDirection(VectorMath.rotate90CX(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CX")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CX(v);
						}
					});
				}
				
				else if(getDirection(VectorMath.rotate90CCX(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CCX")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CCX(v);
						}
					});
				}
				
				else if(getDirection(VectorMath.rotate90CY(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CY")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CY(v);
						}
					});
				}
				
				else if(getDirection(VectorMath.rotate90CCY(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CCY")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CCY(v);
						}
					});
				}
				
				else if(getDirection(VectorMath.rotate90CZ(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CZ")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CZ(v);
						}
					});
				}
				
				else if(getDirection(VectorMath.rotate90CCZ(i.toVector())).equals(j))
				{
					permute.put(b, new DOP("R90CCZ")
					{
						@Override
						public Vector op(Vector v)
						{
							return VectorMath.rotate90CCZ(v);
						}
					});
				}
				
				else
				{
					permute.put(b, new DOP("FAIL")
					{
						@Override
						public Vector op(Vector v)
						{
							return v;
						}
					});
				}
			}
		}
	}
	
	public Axis getAxis()
	{
		switch(this)
		{
			case D:
				return Axis.Y;
			case E:
				return Axis.X;
			case N:
				return Axis.Z;
			case S:
				return Axis.Z;
			case U:
				return Axis.Y;
			case W:
				return Axis.X;
		}
		
		return null;
	}
}
