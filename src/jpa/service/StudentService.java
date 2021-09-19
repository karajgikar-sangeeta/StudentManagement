package jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.JpaException;

public class StudentService implements StudentDAO {

	private EntityManager entityManager = null;

	public StudentService() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMS");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public List<Student> getAllStudents() throws JpaException {
		// -This method reads the student table in your
		// database and returns the data as a List<Student>
		String sql = "SELECT s FROM Student s";
		try {
			TypedQuery<Student> query = entityManager.createQuery(sql, Student.class);
			return query.getResultList();
		} 
		catch (NoResultException noExc) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("NO_RESULTS_FOUND");
			jpaException.setMessage("No students found");
			throw jpaException;
		}
		catch (Exception exp) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while getting the students list");
			throw jpaException;
		}
	}

	@Override
	public Student getStudentByEmail(String sEmail) throws JpaException {
		// –This method takes a Student’s email as a String and parses the student list
		// for
		// a Student with that email and returns a Student Object.
		String sql = "SELECT s FROM Student s WHERE s.sEmail = :sEmail";
		try {
			TypedQuery<Student> query = entityManager.createQuery(sql, Student.class);
			query.setParameter("sEmail", sEmail);
			return query.getSingleResult();
		}
		catch (NoResultException noExc) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("NO_RESULTS_FOUND");
			jpaException.setMessage("Student " + sEmail + " not found");
			throw jpaException;
		}
		catch (Exception exp) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while getting the student " + sEmail);
			throw jpaException;
		}
	}

	@Override
	public boolean validateStudent(String sEmail, String sPassword) {
		String sql = "SELECT s FROM Student s WHERE s.sEmail = :sEmail AND s.sPass = :sPassword";
		try {
			TypedQuery<Student> query = entityManager.createQuery(sql, Student.class);
			query.setParameter("sEmail", sEmail);
			query.setParameter("sPassword", sPassword);
			Student student = query.getSingleResult();
			return student != null;
		} catch (Exception exc) {
			return false;
		}
	}

	@Override
	public void registerStudentToCourse(String sEmail, int cId) throws JpaException {
		Student student = null;
		List<Course> courseList = null;
		try {
			student = getStudentByEmail(sEmail);
			courseList = student.getsCourses();
		} catch (Exception ex) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while getting the students courses Email:" + sEmail + " courseId: " + cId);
			throw jpaException;
		}

		String courseSql = "SELECT c from Course c WHERE c.cId = :cId";
		Course course = null;
		
		try {
			TypedQuery<Course> cQuery = entityManager.createQuery(courseSql, Course.class);
			cQuery.setParameter("cId", cId);

			course = cQuery.getSingleResult();
			courseList.add(course);
		} 
		catch (NoResultException noExc) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("NO_RESULTS_FOUND");
			jpaException.setMessage("Course " + cId + " not found");
			throw jpaException;
		}
		catch (Exception exp) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while getting the course " + cId);
			throw jpaException;
		}
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(student);
			entityManager.getTransaction().commit();
		}
		catch (RollbackException rExc) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("DUPLICATE_ENTRY");
			jpaException.setMessage("Student " + sEmail + " and courseid " + cId + " already exists");
			throw jpaException;
		}
		catch (Exception ex) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while rgistering the course " + cId + " to Student " + sEmail);
			throw jpaException;
		}
	}

	@Override
	public List<Course> getStudentCourses(String sEmail) throws JpaException {
		try	{
			Student student = getStudentByEmail(sEmail);
			return student.getsCourses();
		}
		catch (NoResultException noExc) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("NO_RESULTS_FOUND");
			jpaException.setMessage("Student " + sEmail + " not found");
			throw jpaException;
		}
		catch (Exception exp) {
			JpaException jpaException = new JpaException();
			jpaException.setCode("INTERNAL_ERROR");
			jpaException.setMessage("Error while getting the student courses for " + sEmail);
			throw jpaException;
		}
	}
}
