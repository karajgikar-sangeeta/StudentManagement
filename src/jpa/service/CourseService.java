package jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.exceptions.JpaException;

public class CourseService implements CourseDAO {

	private EntityManager entityManager = null;
	
	public CourseService() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMS");
		entityManager = entityManagerFactory.createEntityManager();
	}
	@Override
	public List<Course> getAllCourses() throws JpaException {
		String sql = "SELECT c from Course c";
		try	{
		TypedQuery<Course> query = entityManager.createQuery(sql, Course.class);
		List<Course> courses = query.getResultList();
		
		return courses;
		}
		catch (Exception exc) {
			throw new JpaException(exc.getMessage());
		}
	}

}
