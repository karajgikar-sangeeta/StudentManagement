package jpa.mainrunner;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import jpa.entitymodels.Student;
import jpa.exceptions.JpaException;
import jpa.service.StudentService;

class SMSRunnerTest {

	@Test
	void testGetStudentByEmail() throws JpaException {
		//given
		String emailTest = "qllorens2@howstuffworks.com";
		
		//when
		StudentService studentService = new StudentService();
		Student student = studentService.getStudentByEmail(emailTest);
		
		//then
		assertEquals(student.getsEmail(), emailTest, "should be equal");
	
	}

}
