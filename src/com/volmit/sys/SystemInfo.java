package com.volmit.sys;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import com.volmit.sys.util.C;
import com.volmit.sys.util.F;
import com.volmit.sys.util.FU;
import com.volmit.sys.util.GList;

public class SystemInfo extends JavaPlugin implements Listener
{
	private GList<String> dat;
	private Sigar s;
	
	@Override
	public void onEnable()
	{
		dat = new GList<String>();
		loadSigar();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	private void loadSigar()
	{
		GList<String> library = new GList<String>();
		library.add(new String[] {"sigar-1.6.4.jar", "libsigar-amd64-freebsd-6.so", "libsigar-amd64-linux.so", "libsigar-amd64-solaris.so", "libsigar-ia64-hpux-11.sl", "libsigar-ia64-linux.so", "libsigar-pa-hpux-11.sl", "libsigar-ppc64-aix-5.so", "libsigar-ppc64-linux.so", "libsigar-ppc-aix-5.so", "libsigar-ppc-linux.so", "libsigar-s390x-linux.so", "libsigar-sparc64-solaris.so", "libsigar-sparc-solaris.so", "libsigar-universal64-macosx.dylib", "libsigar-universal-macosx.dylib", "libsigar-x86-freebsd-5.so", "libsigar-x86-freebsd-6.so", "libsigar-x86-linux.so", "libsigar-x86-solaris.so", "sigar-amd64-winnt.dll", "sigar-x86-winnt.dll", "sigar-x86-winnt.lib"});
		File sigar = new File(getDataFolder(), "lib");
		sigar.mkdirs();
		
		System.out.println("=================================================================");
		
		for(String i : library)
		{
			if(new File(sigar, i).exists())
			{
				continue;
			}
			
			copyResource("com/volmit/sys/kernel/" + i, new File(sigar, i));
		}
		
		System.out.println("=================================================================");
		System.out.println("Referencing Kernel Library: " + sigar.getAbsolutePath());
		System.setProperty("java.library.path", sigar.getAbsolutePath() + ";" + System.getProperty("java.library.path"));
		System.out.println("Library: " + System.getProperty("java.library.path"));
		System.out.println("=================================================================");
		System.out.println("Patching Library...");
		GList<String> li = new GList<String>(System.getProperty("java.library.path").split(";"));
		li.removeDuplicates();
		System.setProperty("java.library.path", li.toString(";"));
		System.out.println("Patch Complete: " + System.getProperty("java.library.path"));
		System.out.println("=================================================================");
		System.out.println("Installing API");
		
		try
		{
			LibraryUtil.install(new File(sigar, "sigar-1.6.4.jar"));
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("=================================================================");
		s = new Sigar();
		
		try
		{
			dat.add(C.RED + "CPU MODEL / VIN: " + C.WHITE + s.getCpuInfoList()[0].getModel() + " " + s.getCpuInfoList()[0].getVendor());
			dat.add(C.RED + "CPU MHZ: " + C.WHITE + F.f(s.getCpuInfoList()[0].getMhz()) + " (" + s.getCpuInfoList()[0].getMhz() / 1000.0 + "GHZ )");
			dat.add(C.RED + "CPU CACHE: " + C.WHITE + F.fileSize(s.getCpuInfoList()[0].getCacheSize()));
			dat.add(C.RED + "SYSTEM SOCKETS: " + C.WHITE + s.getCpuInfoList()[0].getTotalSockets());
			dat.add(C.RED + "SYSTEM TOTAL CORES: " + C.WHITE + s.getCpuInfoList()[0].getTotalCores());
			dat.add(C.RED + "SYSTEM CORES/SOCKET: " + C.WHITE + s.getCpuInfoList()[0].getCoresPerSocket());
			
			for(String i : dat)
			{
				Bukkit.getConsoleSender().sendMessage(i);
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void on(ServerCommandEvent e)
	{
		if(e.getCommand().equalsIgnoreCase("sys"))
		{
			e.getSender().sendMessage(C.WHITE + "---------------------------------------------");
			for(String i : dat)
			{
				e.getSender().sendMessage(i);
			}
			e.getSender().sendMessage(C.WHITE + "---------------------------------------------");
			int cpus = 0;
			
			try
			{
				for(CpuPerc i : s.getCpuPercList())
				{
					e.getSender().sendMessage(C.RED + "CPU " + cpus + ": " + C.WHITE + F.pc(i.getCombined(), 4) + " @ " + s.getCpuInfoList()[cpus].getMhz() + "MHz");
					cpus++;
				}
			}
			
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
			
			e.getSender().sendMessage(C.WHITE + "---------------------------------------------");
		}
	}
	
	private void copyResource(String location, File dest)
	{
		System.out.println("Installing Kernel Library: " + dest.toString());
		URL in = getClass().getResource("/" + location);
		
		if(in == null)
		{
			System.out.println("NULL");
		}
		
		try
		{
			FU.copyURLToFile(in, dest);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
