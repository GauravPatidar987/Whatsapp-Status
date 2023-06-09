package com.wh;

public class Status
{
	int id;
	String username;
	String status;

	public Status(int id, String username, String status)
	{
		this.id = id;
		this.username = username;
		this.status = status;
	}

	public Status()
	{

	}
	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}}
