package com.volmit.sys.util;

import com.volmit.sys.util.DataCluster.ClusterType;

/**
 * 
 * @author cyberpwn
 *
 */
public class ClusterBoolean extends Cluster
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClusterBoolean(boolean value)
	{
		super(ClusterType.BOOLEAN, (double) (value ? 1 : 0));
	}
	
	public boolean get()
	{
		return value == 1;
	}
	
	public void set(boolean b)
	{
		value = (double) (b ? 1 : 0);
	}
}
