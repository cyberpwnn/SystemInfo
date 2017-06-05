package com.volmit.sys.util;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ControllablePlugin extends JavaPlugin implements Controllable
{
	private Controller base;
	
	@Override
	public void onLoad()
	{
		destroyOldThreads();
		readCurrentTick();
	}
	
	@Override
	public void onEnable()
	{
		Wraith.instance = this;
		setupTicker();
		
		base = new Controller(null)
		{
			@Override
			public void onStop()
			{
				ControllablePlugin.this.onStop();
			}
			
			@Override
			public void onStart()
			{
				ControllablePlugin.this.onStart();
			}
			
			@Override
			public void onTick()
			{
				ControllablePlugin.this.onTick();
			}
		};
		
		start();
	}
	
	@SuppressWarnings("deprecation")
	private void destroyOldThreads()
	{
		for(Thread i : new GList<Thread>(Thread.getAllStackTraces().keySet()))
		{
			if(i.getName().startsWith("CT Parallel Tick Thread "))
			{
				try
				{
					i.interrupt();
					i.join(100);
				}
				
				catch(InterruptedException e)
				{
					
				}
				
				catch(Throwable e)
				{
					e.printStackTrace();
				}
				
				if(i.isAlive())
				{
					try
					{
						i.stop();
					}
					
					catch(Throwable e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void readCurrentTick()
	{
		long ms = System.currentTimeMillis();
		File prop = new File("server.properties");
		TICK.tick = (ms - prop.lastModified()) / 50;
		System.out.println("Setting Tick to " + TICK.tick);
		Wraith.poolManager = new ParallelPoolManager(2);
	}
	
	private void setupTicker()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			@Override
			public void run()
			{
				TICK.tick++;
			}
		}, 0, 0);
		
		Wraith.poolManager.start();
	}
	
	public abstract void onStart();
	
	public abstract void onStop();
	
	public void onTick()
	{
		
	}
	
	@Override
	public void onDisable()
	{
		stop();
	}
	
	@Override
	public void tick()
	{
		
	}
	
	public abstract void onConstruct();
	
	@Override
	public void start()
	{
		onConstruct();
		preStart();
		base.start();
	}
	
	@Override
	public void stop()
	{
		base.stop();
	}
	
	@Override
	public Controllable getParent()
	{
		return base.getParent();
	}
	
	@Override
	public GList<Controllable> getChildren()
	{
		return base.getChildren();
	}
	
	@Override
	public boolean isRoot()
	{
		return true;
	}
	
	@Override
	public boolean isActive()
	{
		return base.isActive();
	}
	
	@Override
	public void register(Controllable controllable)
	{
		base.register(controllable);
	}
	
	@Override
	public boolean isTicked()
	{
		return base.isTicked();
	}
	
	@Override
	public double getTickRate()
	{
		return base.getTickRate();
	}
	
	@Override
	public TickHandler getTickHandler()
	{
		return base.getTickHandler();
	}
	
	private void preStart()
	{
		if(findTicked())
		{
			base.ticked = true;
			base.tickRate = findTickValue();
			base.tickHandle = findTickHandle();
		}
	}
	
	private boolean findTicked()
	{
		return getClass().isAnnotationPresent(Ticked.class);
	}
	
	private double findTickValue()
	{
		if(base.ticked)
		{
			return getClass().getAnnotationsByType(Ticked.class)[0].value();
		}
		
		return 0;
	}
	
	private TickHandler findTickHandle()
	{
		if(getClass().isAnnotationPresent(TickHandle.class))
		{
			return getClass().getAnnotationsByType(TickHandle.class)[0].value();
		}
		
		return TickHandler.SYNCED;
	}
}
