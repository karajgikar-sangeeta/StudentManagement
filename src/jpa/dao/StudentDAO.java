package jpa.dao;

import java.util.List;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.JpaException;

public interface StudentDAO {
	
	public List<Student> getAllStudents() throws JpaException;

	public Student getStudentByEmail(String sEmail) throws JpaException;

	public boolean validateStudent(String sEmail, String sPassword);

	public void registerStudentToCourse(String sEmail, int cId) throws JpaException;

	public List<Course> getStudentCourses(String sEmail) throws JpaException;

}
