package com.volmit.sys.util;

import com.volmit.sys.util.DataCluster.ClusterType;

/**
 * 
 * @author cyberpwn
 *
 */
public class ClusterDouble extends Cluster
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClusterDouble(Double value)
	{
		super(ClusterType.DOUBLE, value);
	}
	
	public double get()
	{
		return value.doubleValue();
	}
	
	public void set(double i)
	{
		value = i;
	}
}
