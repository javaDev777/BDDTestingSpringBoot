package net.javaguides.springboottesting.repository;

import net.javaguides.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    //JUnit test for save employee operation

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Lolita")
                .lastName("Young")
                .email("ramesh@gmail,com")
                .build();
    }
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmoloyeeObject_whenSave_thenReturnSavedEmployee() {

        employeeRepository.save(employee);
        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        //then -  verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void given_when_then() {
        //given - precondition or setup

         Employee employee1 = Employee.builder()
                 .firstName ("Lolita")
                 .lastName("Young")
                 .email("lolita.young777@gmail.com")
                 .build();

         employeeRepository.save(employee);
         employeeRepository.save(employee1);

        //when - action or the behavior that we are going test
        List<Employee> employeeList = employeeRepository.findAll();



        //then - verify the output

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    // JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Object employeeDB =  employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation ")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        employeeRepository.save(employee);
        String firstName = "Lolita";
        String lastName = "Young";
        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
    // JUnit test for custom query using JPQL with Named params
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Lolita";
        String lastName = "Young";

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using native SQL with index
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_then() {
        //given - precondition or setup
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Lolita";
        String lastName = "Young";

        //when - action or the behavior that we are going test
       Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

        employeeRepository.save(employee);
        String firstName = "Lolita";
        String lastName = "Young";

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParam(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();


    }
}





