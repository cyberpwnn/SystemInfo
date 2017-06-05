package com.volmit.sys.util;

import org.bukkit.entity.Player;

/**
 * Bind objects to elements and return the objects via callback methods instead
 * of the clicked element
 * 
 * @author cyberpwn
 *
 * @param <T>
 *            the type of object to bind elements to
 */
public class PhantomSelectDialog<T> extends PhantomDialog implements SelectDialog<T>
{
	protected GMap<Element, T> bindings;
	
	/**
	 * Create a selectable dialog
	 * 
	 * @param title
	 *            the title
	 * @param viewer
	 *            the player
	 * @param cancellable
	 *            can it be cancelled
	 */
	public PhantomSelectDialog(String title, Player viewer, boolean cancellable)
	{
		super(title, viewer, cancellable);
		
		bindings = new GMap<Element, T>();
	}
	
	public SelectDialog<T> bind(Element e, T t)
	{
		bindings.put(e, t);
		
		return this;
	}
	
	public boolean onClick(Element element, Player p)
	{
		if(bindings.containsKey(element))
		{
			onSelected(bindings.get(element), element);
		}
		
		return true;
	}
	
	public void onSelected(T clicked, Element e)
	{
		
	}
}
