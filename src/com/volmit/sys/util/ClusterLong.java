package com.volmit.sys.util;

import com.volmit.sys.util.DataCluster.ClusterType;

/**
 * 
 * @author cyberpwn
 *
 */
public class ClusterLong extends Cluster
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClusterLong(Long value)
	{
		super(ClusterType.LONG, (double) value);
	}
	
	public long get()
	{
		return value.longValue();
	}
	
	public void set(long i)
	{
		value = (double) i;
	}
}
