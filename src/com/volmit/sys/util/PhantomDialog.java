package com.volmit.sys.util;

import org.bukkit.entity.Player;

/**
 * Implementation of the dialog
 * 
 * @author cyberpwn
 *
 */
public class PhantomDialog extends PhantomWindow implements Dialog
{
	protected boolean cancellable;
	
	/**
	 * Create a phantomDialog
	 * 
	 * @param title
	 *            the title
	 * @param viewer
	 *            the player
	 * @param cancellable
	 *            should this dialog be cancellable?
	 */
	public PhantomDialog(String title, Player viewer, boolean cancellable)
	{
		super(title, viewer);
		
		setCancellable(cancellable);
	}
	
	public Window close()
	{
		super.close();
		
		if(!isCancellable())
		{
			open();
		}
		
		else
		{
			new TaskLater(0)
			{
				public void run()
				{
					onCancelled(viewer, PhantomDialog.this, PhantomDialog.this);
				}
			};
		}
		
		return this;
	}
	
	/**
	 * Is this dialog cancellable?
	 * 
	 * @return true if it is
	 */
	public boolean isCancellable()
	{
		return cancellable;
	}
	
	public Dialog setCancellable(boolean cancellable)
	{
		this.cancellable = cancellable;
		
		return this;
	}
	
	@Override
	public void onCancelled(Player p, Window w, Dialog d)
	{
		
	}

	@Override
	public boolean onClick(Element element, Player p)
	{
		return true;
	}
}
