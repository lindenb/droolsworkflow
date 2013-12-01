package com.github.lindenb.droolsworkflow;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.api.io.ResourceType;


public class DroolsWorkflow
	{
	private static final Logger LOG=Logger.getLogger("droolsworkflow");
	@SuppressWarnings("unused")
	private SamtoolsSort _ignore;//force javac
	
	
	
	private int mainInstance(String args[])
		{
		LOG.setLevel(Level.ALL);
		List<File> ruleFiles=new ArrayList<File>();
		int optind=0;
		while(optind< args.length)
			{
			if(args[optind].equals("-h") ||
			   args[optind].equals("-help") ||
			   args[optind].equals("--help"))
				{
				System.err.println("Pierre Lindenbaum PhD. 2013");
				System.err.println("Options:");
				System.err.println(" -h help; This screen.");
				return 0;
				}
			else if(args[optind].equals("-R") && optind+1 < args.length)
				{
				String filename=args[++optind];
				LOG.info("Adding "+filename);
				ruleFiles.add(new File(filename));
				}
			else if(args[optind].equals("--"))
				{
				optind++;
				break;
				}
			else if(args[optind].startsWith("-"))
				{
				System.err.println("Unknown option "+args[optind]);
				return -1;
				}
			else 
				{
				break;
				}
			++optind;
			}
			
		
		
		try
			{
			
	        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        LOG.info("reading rules");
	        for(File ruleFile:ruleFiles)
	        	{
	        	LOG.info("Reading Rule file: "+ruleFile);
	        	kbuilder.add(
	        		ResourceFactory.newFileResource( ruleFile),
	        		ResourceType.DRL );
	        	}
	        Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
	        kbase.addKnowledgePackages(pkgs);
	        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	        
	        while(optind < args.length)
	        	{
	        	String filename=args[optind++];
	        	LOG.info("insert file "+filename);
	        	ksession.insert(new File(filename));
	        	}
	        
	        LOG.info("Firing rules");
	        ksession.fireAllRules();
	        
	        LOG.info("End");
	        return 0;
			}
		catch (Exception e)
			{
			e.printStackTrace();
			return -1;
			}
		}
	
	private void mainInstanceWithExit(String args[])
		{
		System.exit(mainInstance(args));
		}

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		new DroolsWorkflow().mainInstanceWithExit(args);
		}

	}
