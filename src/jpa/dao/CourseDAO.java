package jpa.dao;

import java.util.List;

import jpa.entitymodels.Course;
import jpa.exceptions.JpaException;

public interface CourseDAO {

	public List<Course> getAllCourses() throws JpaException;
}
