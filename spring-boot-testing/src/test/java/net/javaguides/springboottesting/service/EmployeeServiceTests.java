package net.javaguides.springboottesting.service;

import net.javaguides.springboottesting.exception.ResourceNotFoundException;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    @BeforeEach
    public void setup() {

        employee = Employee.builder()
                .firstName("Lolita")
                .lastName("Young")
                .email("lolita.young777@gmail.com")
                .build();
           }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);


        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        //when - action or the behavior that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
                    employeeService.saveEmployee(employee);
        });

        //then - verify the output

        Mockito.verify(employeeRepository, never()).save(ArgumentMatchers.any(Employee.class));
    }

    // JUnit test for
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tracy")
                .lastName("Bowling")
                .email("TracyBowling@gmail.com")
                .build();

         given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        //when - action or the behavior that we are going test
         List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output
       assertThat(employeeList).isNotNull();
       assertThat(employeeList.size()).isEqualTo(2);

    }

   // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tracy")
                .lastName("Bowling")
                .email("TracyBowling@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when - action or the behavior that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }

    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
          // given
           given(employeeRepository.findById(0L)).willReturn(Optional.of(employee));
        // when
           Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
        //then
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail(("ylo@gmail.com"));
        employee.setFirstName("lisa");
        //when - action or the behavior that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ylo@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("lisa");

    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(1L);
        //when - action or the behavior that we are going test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        Mockito.verify((employeeRepository), times(1)).deleteById(employeeId);
    }

}





