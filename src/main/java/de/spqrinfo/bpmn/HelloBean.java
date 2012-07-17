package de.spqrinfo.bpmn;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class HelloBean 	implements JavaDelegate {	  
		  
	public void execute(DelegateExecution execution) throws Exception {
		//String var = (String) execution.getVariable("input");
		//var = var.toUpperCase();
		//execution.setVariable("input", var);
		
		String msg = "Hello, World!";
		System.out.println(msg);
		//execution.setVariable("res1", msg);
		//execution.
	}
}
