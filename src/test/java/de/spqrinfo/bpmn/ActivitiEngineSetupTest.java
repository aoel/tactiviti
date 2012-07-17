package de.spqrinfo.bpmn;

import org.activiti.engine.repository.ProcessDefinition;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.After;
import org.junit.Before;

import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 * Wiring process engine configuration tests.
 */
public class ActivitiEngineSetupTest {

    public static final String MINIMAL_FILENAME = "minimal.bpmn20.xml";


    private ProcessEngineConfiguration processEngineConfiguration;
    private ProcessEngine processEngine;

    public ActivitiEngineSetupTest() {
    }

    @Before
    public void beforeTest() {
        processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        processEngine = processEngineConfiguration.buildProcessEngine();
    }

    @After
    public void afterTest() {
        processEngine.close();
        processEngine = null;
        processEngineConfiguration = null;
    }

    private InputStream getMinimalBPMNDefinition() {
        StringBuilder b = new StringBuilder();

        b.append("<?xml version='1.0'?>");
        b.append("<definitions id='definitions'");
        b.append("             targetNamespace='http://activiti.org/bpmn20'");
        b.append("             xmlns:activiti='http://activiti.org/bpmn'");
        b.append("             xmlns='http://www.omg.org/spec/BPMN/20100524/MODEL'>");
        b.append("  <process id='minimalProcess' name='A minimal BPMN2 process'>");
        b.append("    <startEvent id='theStart' />");
        b.append("    <sequenceFlow id='flow1' sourceRef='theStart' targetRef='theEnd' />");
        b.append("    <endEvent id='theEnd' />");
        b.append("  </process>");
        b.append("</definitions>");

        return new ByteArrayInputStream(b.toString().getBytes());
    }

    @Test
    public void testBasicSetup() {
        System.out.println("Name " + processEngine.getName());
        System.out.println("VERSION " + ProcessEngine.VERSION);
    }

    @Test
    @Ignore
    public void testStringInputStreamDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.addInputStream("test", getMinimalBPMNDefinition());
        deploymentBuilder.deploy(); // Finalize deployments

        assertEquals(1, repositoryService.createProcessDefinitionQuery().list().size());
    }

    @Test
    public void testFileDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.addClasspathResource(MINIMAL_FILENAME);
        deploymentBuilder.deploy();

        assertEquals(1, repositoryService.createProcessDefinitionQuery().list().size());
    }

    @Test
    public void testMinimalProcess() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.addClasspathResource(MINIMAL_FILENAME);
        deploymentBuilder.deploy();

        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("minimalProcess");
        assertTrue(processInstance.isEnded());
    }

    @Test
    public void testListProcessDefinitions() {
        System.out.println("begin testListProcessDefinitions");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.addClasspathResource(MINIMAL_FILENAME);
        deploymentBuilder.deploy();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        for (ProcessDefinition proc : processDefinitionQuery.list()) {
            System.out.println(proc);
        }
        System.out.println("end testListProcessDefinitions");
    }
}
