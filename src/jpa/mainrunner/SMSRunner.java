package jpa.mainrunner;

import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.JpaException;
import jpa.service.CourseService;
import jpa.service.StudentService;

public class SMSRunner {

	private Scanner sin;
	private CourseService courseService;
	private StudentService studentService;
	private StringBuilder sb;
	private Student student = null;
	
	public SMSRunner() {
		this.courseService = new CourseService();
		this.studentService = new StudentService();
		this.sin = new Scanner(System.in);
		this.sb = new StringBuilder();
	}
	
	
	private void displayFirstChoices() {
		sb.append("\n1. Student Login\n2. Quit Application\n\n   Enter your selection:");
		System.out.print(sb.toString());
		sb.delete(0, sb.length());
		
		int firstChoice = sin.nextInt();
		switch (firstChoice) {
		case 1:
			boolean isValid = doStudentLogin();
			if (isValid) {
				if (!registerOrLogout()) {
					System.exit(1);
				} else {
					System.exit(0);
				}
			}
			else {
				System.exit(1);
			}
			break;
		case 2:
		default:
			System.out.println("GoodBye! Exiting the application..");
			System.exit(0);
			break;
		}
	}
	
	private boolean doRegister() {
		//1. get all courses
		//2. ask to select a course 
		//3. register the selected course to the student
		//4. logout
		
		List<Course> courses = null;
		try {
			courses = courseService.getAllCourses();
		}
		catch (JpaException ex) {
			printExceptionDetails(ex);
			return false;
		}
		
		System.out.println("All Courses:");
		
		String indexHeader = getFormattedText(5, "ID");  //String.format("%-" + 10 + "s", "#");
		String courseNameHeader = getFormattedText(20, "COURSE NAME"); // String.format("%-" + 20 + "s", "COURSE NAME");
		String instructorNameHeader = getFormattedText(20, "INSTRUCTOR NAME");//  String.format("%-" + 20 + "s", "INSTRUCTOR NAME");
		
		System.out.println(indexHeader + courseNameHeader + instructorNameHeader);
		for (int i = 0; i < courses.size(); i++) {
			String indexValue = getFormattedText(5, "" + courses.get(i).getcId() + "");
			String courseNameValue = getFormattedText(20, courses.get(i).getcName());
			String instructorNameValue = getFormattedText(20, courses.get(i).getcInstructorName());
			System.out.println(indexValue + courseNameValue + instructorNameValue);
		}
		
		System.out.println();
		System.out.println("Please select the ID of the course to register");
		int courseChoice = sin.nextInt();
		
		try {
			studentService.registerStudentToCourse(student.getsEmail(), courseChoice);
		}
		catch (JpaException exc) {
			printExceptionDetails(exc);
			return false;
		}
		
		courses = null;
		
		try	{
			courses = studentService.getStudentCourses(student.getsEmail());
		}
		catch (JpaException exc) {
			printExceptionDetails(exc);
			return false;
		}
		
		
		System.out.println("My Classes:");
		
		
		String indexHeader1 = getFormattedText(10, "COURSE ID");  //String.format("%-" + 10 + "s", "#");
		String courseNameHeader1 = getFormattedText(20, "COURSE NAME"); // String.format("%-" + 20 + "s", "COURSE NAME");
		String instructorNameHeader1 = getFormattedText(20, "INSTRUCTOR NAME");//  String.format("%-" + 20 + "s", "INSTRUCTOR NAME");
		
		System.out.println(indexHeader1 + courseNameHeader1 + instructorNameHeader1);
		for (int i = 0; i < courses.size(); i++) {
			String indexValue = getFormattedText(5, "" + courses.get(i).getcId() + "");
			String courseNameValue = getFormattedText(20, courses.get(i).getcName());
			String instructorNameValue = getFormattedText(20, courses.get(i).getcInstructorName());
			System.out.println(indexValue + courseNameValue + instructorNameValue);
		}
		return true;
	}
	
	private boolean registerOrLogout() {
		
		System.out.println();
		System.out.println();
		sb.append("\n1. Register to Class\n2. Logout\n\n   Enter your selection:");
		System.out.print(sb.toString());
		sb.delete(0, sb.length());
		
		int secondChoice = sin.nextInt();
		switch (secondChoice) {
		case 1:
			return doRegister();
		case 2:
		default:
			System.out.println("GoodBye! Exiting the application..");
			return true;
		}
	}
	
	private void printExceptionDetails(JpaException exception) {
		switch (exception.getCode()) {
		case "NO_RESULTS_FOUND":
			System.out.println("No results found");
			break;
			
		case "INTERNAL_ERROR":
			System.out.println("Error performing the operation");
			break;
			
		case "DUPLICATE_ENTRY":
			System.out.println(exception.getMessage());
		}
	}
	
	private boolean doStudentLogin() {
		System.out.println("Enter your email:");
		String email = sin.next();
		
		System.out.println("Enter your password:");
		String password = sin.next();
		
		try	{
			student = studentService.getStudentByEmail(email);
		} 
		catch (JpaException exc) {
			printExceptionDetails(exc);
			return false;
		}
		
		if (student != null && !student.getsPass().equals(password)) {
			System.out.println("Invalid Credentials! Exiting the application. Please try again..");
			return false;
		}
		
		List<Course> studentCourses = null;
		try {
			studentCourses = studentService.getStudentCourses(email);
		}
		catch (JpaException exc) {
			printExceptionDetails(exc);
			return false;
		}
		
		System.out.println("My Classes:");
		
		
		String indexHeader = getFormattedText(5, "#");  //String.format("%-" + 10 + "s", "#");
		String courseNameHeader = getFormattedText(20, "COURSE NAME"); // String.format("%-" + 20 + "s", "COURSE NAME");
		String instructorNameHeader = getFormattedText(20, "INSTRUCTOR NAME");//  String.format("%-" + 20 + "s", "INSTRUCTOR NAME");
		
		System.out.println(indexHeader + courseNameHeader + instructorNameHeader);
		for (int i = 0; i < studentCourses.size(); i++) {
			String indexValue = getFormattedText(5, "" + i + 1 + "");
			String courseNameValue = getFormattedText(20, studentCourses.get(i).getcName());
			String instructorNameValue = getFormattedText(20, studentCourses.get(i).getcInstructorName());
			System.out.println(indexValue + courseNameValue + instructorNameValue);
		}
		
		return true;
	}
	
	private String getFormattedText(int space, String word) {
		return String.format("%-" + space + "s", word);
	}
	
	public static void main(String[] args) {
		SMSRunner sms = new SMSRunner();
		sms.displayFirstChoices();
	}

}
