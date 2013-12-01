package com.github.lindenb.droolsworkflow;

public abstract class Samtools
	extends Execution
	{
	public String getVersion()
		{
		return "0.1.18";
		}
	public String getExecutable()
		{
		return "/home/lindenb/samtools-"+getVersion()+"/samtools";
		}
	
	}
