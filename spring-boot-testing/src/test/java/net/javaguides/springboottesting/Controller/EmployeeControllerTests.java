package net.javaguides.springboottesting.Controller;

import antlr.TokenStreamRewriteEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static javax.management.Query.value;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest

public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Bryson")
                .lastName("Solomon")
                .email("bs@gmail.com")
                .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
        .willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or behavior that we are going test
       ResultActions response =  mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the result or output using assert statements
        response.andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())));

    }

    @Test
    public void giveListofEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
       //given - precondition or setup
       List<Employee> listOfEmployees = new ArrayList<>();
       listOfEmployees.add(Employee.builder().firstName("Bryson").lastName("Solomon").email("remesh@gmail.com").build());
       listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());
       given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
       //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(listOfEmployees.size()));

    }

}
