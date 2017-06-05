package com.volmit.sys.util;

public class TFRemove implements TextFilter
{
	private String grep;
	
	public TFRemove(String grep)
	{
		this.grep = grep;
	}
	
	@Override
	public String onFilter(String initial)
	{
		return initial.replaceAll(grep, "");
	}
}
