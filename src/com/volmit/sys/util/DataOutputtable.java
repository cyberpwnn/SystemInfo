package com.volmit.sys.util;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author cyberpwn
 *
 */
public interface DataOutputtable
{
	public void save(DataCluster cluster, File file) throws IOException;
}
