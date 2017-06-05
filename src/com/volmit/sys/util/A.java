package com.volmit.sys.util;

public abstract class A
{
	public A()
	{
		Wraith.poolManager.queue(new Execution()
		{
			@Override
			public void run()
			{
				async();
			}
		});
	}
	
	public abstract void async();
}
