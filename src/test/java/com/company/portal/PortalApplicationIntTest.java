package com.company.portal;

import com.company.portal.controller.EmployeeController;
import com.company.portal.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class PortalApplicationIntTest {

    MockMvc mockMvc;

    @Autowired
    EmployeeController employeeController;

    ObjectMapper mapper;

    Employee e1 = new Employee();
    Employee e2 = new Employee();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
        mapper = new ObjectMapper();
        e1.setId(1);
        e1.setName("john ");
        e1.setDepartment("sales");
        e1.setDesignation("engineer");
        e1.setSalary(2000);

        e2.setId(2);
        e2.setName("harry");
        e2.setDepartment("sales");
        e2.setDesignation("manager");
        e2.setSalary(5000);
    }

    @Test
    public void crudTest() throws Exception {

        mockMvc.perform(get("/employees"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));

        Employee employee = employeeController.createEngineer(e1);
        mockMvc.perform(get("/employees"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
        assertNotNull(employee.getId());
        assertThat(employee.getId(), greaterThan(0));
        MvcResult result = mockMvc.perform(get("/employees/{id}", employee.getId()))
                .andExpect(status().is(200))
                .andReturn();
        assertEquals(e1, getObjectFromResult(result));

        result = mockMvc.perform(post("/employees/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(e2)))
                .andExpect(status().is(201))
                .andReturn();
        employee = getObjectFromResult(result);
        assertNotNull(employee.getId());
        assertThat(employee.getId(), greaterThan(0));
        assertEquals(e2, getObjectFromResult(result));
    }

    public Employee getObjectFromResult(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        String json = result.getResponse().getContentAsString();
        return (mapper.readValue(json, Employee.class));
    }

    public List<Employee> getEmployeeList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String json = result.getResponse().getContentAsString();
        List<Employee> employeeList = Arrays.asList(mapper.readValue(json, Employee[].class));
        return employeeList;
    }
}
