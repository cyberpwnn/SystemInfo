package com.volmit.sys.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONDataInput extends DataInput
{
	public void load(DataCluster cluster, File file) throws IOException
	{
		super.load(cluster, file);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line = null;
		JSONObject js = null;
		
		while((line = br.readLine()) != null)
		{
			sb.append(line);
		}
		
		br.close();
		
		try
		{
			js = new JSONObject(sb.toString());
		}
		
		catch(Exception e)
		{
			js = new JSONObject();
		}
		
		for(String i : js.keySet())
		{
			cluster.trySet(i, js.get(i));
		}
	}
}
