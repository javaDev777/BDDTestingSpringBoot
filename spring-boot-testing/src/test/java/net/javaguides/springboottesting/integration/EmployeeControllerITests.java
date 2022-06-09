package net.javaguides.springboottesting.integration;

import antlr.TokenStreamRewriteEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.value;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();

    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Bryson")
                .lastName("Solomon")
                .email("bs@gmail.com")
                .build();
        //when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));
    }
    @Test
    public void givenListofEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        //given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Bryson").lastName("Solomon").email("brysolomon.com").build());
        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build())  ;
        employeeRepository.saveAll(listOfEmployees);
        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(listOfEmployees.size()));

    }
}

















