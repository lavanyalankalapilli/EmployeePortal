package com.company.portal.exception;

import com.company.portal.controller.EmployeeController;
import com.company.portal.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    EmployeeController employeeController;


    @Autowired
    EmployeeExceptionHandler employeeExceptionHandler;

    Employee e1 = new Employee();

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(employeeExceptionHandler)
                .build();
        e1.setId(1);
        e1.setName("john ");
        e1.setDepartment("sales");
        e1.setDesignation("engineer");
        e1.setSalary(2000);
    }

    @Test
    public void handleEmployeeAlreadyExistsException() throws Exception {
        String message = "Employee with id 1 already exists";
        when(employeeController.createEngineer(e1)).thenThrow(new EmployeeAlreadyExistsException(message));
        mockMvc.perform(post("/employees/engineers").content(mapper.writeValueAsString(e1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(406)).andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message)).andReturn();
    }

    @Test
    public void handleEmployeeNotFoundException() throws Exception {
        String message = "Employee with id 1 not found";
        when(employeeController.getEmployee(1)).thenThrow(new EmployeeNotFoundException(message));
        mockMvc.perform(get("/employees/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404)).andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message)).andReturn();
    }

    @Test
    public void handleException() throws Exception {
        String message = "Employee with id 1 not found";
        when(employeeController.getEmployee(1)).thenThrow(new RuntimeException(message));
        mockMvc.perform(get("/employees/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400)).andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message)).andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400)).andReturn();
    }
}
