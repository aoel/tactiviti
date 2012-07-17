package de.spqrinfo.bpmn;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class FirstExecutionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("eventName " + execution.getEventName());
		for (String varName : execution.getVariableNames()) {
			System.out.println("Variable " + varName + " -> " + execution.getVariable(varName));
		}
	}
}
