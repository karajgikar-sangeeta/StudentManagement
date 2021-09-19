package jpa.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name= "Course")
public class Course {
	
	public Course() {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int cId;
	
	@Column(name = "name")
	private String cName;
	
	@Column(name = "instructor")
	private String cInstructorName;
	
	@ManyToMany(mappedBy = "sCourses")
	private List<Student> cStudents = new ArrayList<Student>();

	public List<Student> getcStudents() {
		return cStudents;
	}

	public void setcStudents(List<Student> cStudents) {
		this.cStudents = cStudents;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcInstructorName() {
		return cInstructorName;
	}

	public void setcInstructorName(String cInstructorName) {
		this.cInstructorName = cInstructorName;
	}

	
	public Course(int cId, String cName, String cInstructorName, List<Student> cStudents) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.cInstructorName = cInstructorName;
		this.cStudents = cStudents;
	}

	@Override
	public String toString() {
		return "Course [cId=" + cId + ", cName=" + cName + ", cInstructorName=" + cInstructorName + ", cStudents="
				+ cStudents + "]";
	}

	

	
	
	
	

}
