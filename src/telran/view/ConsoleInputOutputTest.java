package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.dto.Employee;

class ConsoleInputOutputTest {
	
	InputOutput io = new ConsoleInputOutput();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void employeeInputAsOneStringTest() {
		Employee empl = io.readObject("Enter employee data as string <id>#<name>#<birthdate>#<salary>#<department>", "Wrong format of employee data", ConsoleInputOutputTest::toEmployeeFromStr);
		io.writeObjectLine(empl);
	}
	static Employee toEmployeeFromStr(String str) {
		String emplTokens[] = str.split("#");
		long id = Long.parseLong(emplTokens[0]);
		String name = emplTokens[1];
		LocalDate birthDate = LocalDate.parse(emplTokens[2]);
		int salary = Integer.parseInt(emplTokens[3]);
		String department = emplTokens[4];
		return new Employee(id, name, birthDate, salary, department);
	}
	
	@Test
	void readPredicateTest() {
		String str = io.readStringPredicate("Enter any string containing exactly 3 symbols", "This is not a sting containing exactly 3 symbols", s -> s.length() == 3);
		assertEquals(3, str.length());
	}
	
	@Test
	void employeeBySeparateFields() {
		/* V.R. To print out entered value with it's name after test will help to understand.
		 *  For example, it may look like:
		 *  io.writeObjectLine("employee's name="+name);
		 */
		Long ID = io.readLong("Enter ID");
		String name = io.readStringPredicate("Enter name using only letters with capital first letter", "There are not only letters with capital first letter", str -> str.matches("[a-zA-Z]+") && Character.isUpperCase(str.charAt(0)));
		LocalDate birthDate = io.readDate("Enter date of birth as dd/MM/yyyy", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		int salary = io.readInt("Enter salary from 6000 to 15000", 6000, 15000);
		Set<String> options = new HashSet<String>(Arrays.asList("dep1","dep2","dep3"));
		String department = io.readStringOption("Enter department", options );
		//TODO
		//enter ID by readLong method
		//enter Name by readStringPredicate (only letters with capital first letter)
		//enter birthdate by readDate 
		//enter salary by readInt(prompt, min, max)
		//enter department by readStringOption specifying possible departments
		
	}
}
