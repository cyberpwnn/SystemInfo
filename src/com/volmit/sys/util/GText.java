package com.volmit.sys.util;

import org.bukkit.entity.Player;

/**
 * RawText Wrapper
 * 
 * @author cyberpwn
 */
public class GText
{
	private RTX t;
	
	/**
	 * Creates a text wrapper for raw text objects. Make your changes, then
	 * invoke pack() for finishing up
	 */
	public GText()
	{
		this.t = new RTX();
	}
	
	/**
	 * Adds an element to the json array dynamically adding multiple elements
	 * for multiple colors even with the same hover and command if supplied.
	 * Place chat colors in the string itself.
	 * 
	 * @param s
	 *            the text which may or may not have chat colors in it
	 */
	public void add(String s)
	{
		for(String i : F.colorSplit(s))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			t.addText(C.stripColor(i), C.getByChar(colors));
		}
	}
	
	/**
	 * Adds an element to the json array dynamically adding multiple elements
	 * for multiple colors even with the same hover and command if supplied.
	 * Place chat colors in the string itself.
	 * 
	 * @param s
	 *            the text which may or may not have chat colors in it
	 * @param hover
	 *            the hover text which may have up to one chat color in it
	 */
	public void addWithHover(String s, String hover)
	{
		GList<ColoredString> colorsx = new GList<ColoredString>();
		
		for(String i : F.colorSplit(hover))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			C c = C.getByChar(colors);
			colorsx.add(new ColoredString(c, C.stripColor(i)));
		}
		
		RTEX rtex = new RTEX(colorsx.toArray(new ColoredString[colorsx.size()]));
		
		for(String i : F.colorSplit(s))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			t.addTextHover(C.stripColor(i), rtex, C.getByChar(colors));
		}
	}
	
	/**
	 * Adds an element to the json array dynamically adding multiple elements
	 * for multiple colors even with the same hover and command if supplied.
	 * Place chat colors in the string itself.
	 * 
	 * @param s
	 *            the text which may or may not have chat colors in it
	 * @param hover
	 *            the hover text which may have up to one chat color in it
	 * @param command
	 *            the command itself
	 */
	public void addWithHoverCommand(String s, String hover, String command)
	{
		GList<ColoredString> colorsx = new GList<ColoredString>();
		
		for(String i : F.colorSplit(hover))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			C c = C.getByChar(colors);
			colorsx.add(new ColoredString(c, C.stripColor(i)));
		}
		
		RTEX rtex = new RTEX(colorsx.toArray(new ColoredString[colorsx.size()]));
		
		for(String i : F.colorSplit(s))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			t.addTextFireHoverCommand(C.stripColor(i), rtex, command, C.getByChar(colors));
		}
	}
	
	/**
	 * Adds an element to the json array dynamically adding multiple elements
	 * for multiple colors even with the same hover and command if supplied.
	 * Place chat colors in the string itself.
	 * 
	 * @param s
	 *            the text which may or may not have chat colors in it
	 * @param hover
	 *            the hover text which may have up to one chat color in it
	 * @param command
	 *            the command itself
	 */
	public void addWithHoverSuggestCommand(String s, String hover, String command)
	{
		GList<ColoredString> colorsx = new GList<ColoredString>();
		
		for(String i : F.colorSplit(hover))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			C c = C.getByChar(colors);
			colorsx.add(new ColoredString(c, C.stripColor(i)));
		}
		
		RTEX rtex = new RTEX(colorsx.toArray(new ColoredString[colorsx.size()]));
		
		for(String i : F.colorSplit(s))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			t.addTextSuggestedHoverCommand(C.stripColor(i), rtex, command, C.getByChar(colors));
		}
	}
	
	/**
	 * Adds an element to the json array dynamically adding multiple elements
	 * for multiple colors even with the same hover and command if supplied.
	 * Place chat colors in the string itself.
	 * 
	 * @param s
	 *            the text which may or may not have chat colors in it
	 * @param hover
	 *            the hover text which may have up to one chat color in it
	 * @param command
	 *            the command itself
	 */
	public void addWithHoverURL(String s, String hover, String url)
	{
		GList<ColoredString> colorsx = new GList<ColoredString>();
		
		for(String i : F.colorSplit(hover))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			C c = C.getByChar(colors);
			colorsx.add(new ColoredString(c, C.stripColor(i)));
		}
		
		RTEX rtex = new RTEX(colorsx.toArray(new ColoredString[colorsx.size()]));
		
		for(String i : F.colorSplit(s))
		{
			String colors = C.getLastColors(i);
			
			if(colors.length() > 1)
			{
				colors = colors.substring(1, 2);
			}
			
			t.addTextOpenHoverURL(C.stripColor(i), rtex, url, C.getByChar(colors));
		}
	}
	
	public GText pack()
	{
		return this;
	}
	
	/**
	 * Cram it into json
	 * 
	 * @return the json
	 */
	public JSONArray toJSON()
	{
		return t.toJSON();
	}
	
	/**
	 * Tell raw to a player (ASYNC SAFE)
	 * 
	 * @param p
	 *            the player
	 */
	public void tellRawTo(Player p)
	{
		t.tellRawTo(p);
	}
}
