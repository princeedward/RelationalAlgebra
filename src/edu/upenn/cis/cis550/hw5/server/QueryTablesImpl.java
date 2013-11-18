package edu.upenn.cis.cis550.hw5.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.upenn.cis.cis550.hw5.client.QueryTables;
import edu.upenn.cis.cis550.hw5.server.expressions.BooleanExpression;
import edu.upenn.cis.cis550.hw5.server.expressions.Equals;
import edu.upenn.cis.cis550.hw5.server.relalgebra.Join;
import edu.upenn.cis.cis550.hw5.server.relalgebra.Project;
import edu.upenn.cis.cis550.hw5.server.relalgebra.Rename;
import edu.upenn.cis.cis550.hw5.server.relalgebra.Select;
import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;

/**
 * This class operates on the server (not the browser) and does a computation.
 * Its output will be returned to the browser.
 * 
 * @author zives
 *
 */
public class QueryTablesImpl extends RemoteServiceServlet implements QueryTables {
	
	Relation R;
	Relation S;
	Relation Student;
	
	// TODO: create and populate this
	Relation Professor;
	// TODO: add more tables
	Relation Advises;
	Relation Course;
	Relation Teaches;
	Relation Takes;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor -- initialize our source tables and the object
	 */
	public QueryTablesImpl() {
		initTables();
	}
	
	private void initTables() {
		// Used in our examples
		createR();
		createS();
		
		// TODO: extend this
		createStudent();
		createProfessor();		
		createAdvises();
		createCourse();
		createTeaches();
		createTakes();
	}
	
	/**
	 * Creates the schema for relation R
	 */
	private void createR() {
		Schema schema = new Schema();
		
		schema.addColumn("First", Attribute.Type.IntType, true);
		schema.addColumn("SSN", Attribute.Type.IntType, false);
		
		R = new Relation("R", schema);
		
		Serializable[] firstTuple = {new Integer(1), new Integer(123456789)};
		R.add(schema.createTuple(firstTuple));
		
		Serializable[] secondTuple = {new Integer(2), new Integer(234567892)};
		R.add(schema.createTuple(secondTuple));
		
	}

	/**
	 * Creates the schema for relation S
	 */
	private void createS() {
		Schema schema = new Schema();
		
		schema.addColumn("SSN", Attribute.Type.IntType, true);
		schema.addColumn("Name", Attribute.Type.StringType, false);
		
		S = new Relation("S", schema);
		
		Serializable[] firstTuple = {new Integer(123456789), "Bob"};
		S.add(schema.createTuple(firstTuple));
		
		Serializable[] secondTuple = {new Integer(234567892), "Maya"};
		S.add(schema.createTuple(secondTuple));
		
		Serializable[] thirdTuple = {new Integer(234567892), "Bo"};
		S.add(schema.createTuple(thirdTuple));
		
	}
	
	/**
	 * Creates the schema for relation Student
	 */
	private void createStudent() {
		Schema  schema = new Schema();
		
		schema.addColumn("studentID", Attribute.Type.IntType, true);
		schema.addColumn("name", Attribute.Type.StringType, false);
		schema.addColumn("email", Attribute.Type.StringType, false);
		schema.addColumn("GPA", Attribute.Type.DoubleType, false);
		
		Student = new Relation("Student", schema);
		
		int numberOfStudents = 100;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), "Stu" + Integer.toString(i * i),
					"s" + Integer.toString(i) + "@seas.upenn.edu",
					new Double(1.0 + i / 34)};
			Student.add(schema.createTuple(tuple));
			}
		
	}
	
	private void createProfessor() {
		Schema  schema = new Schema();
		
		schema.addColumn("profID", Attribute.Type.IntType, true);
		schema.addColumn("name", Attribute.Type.StringType, false);
		schema.addColumn("email", Attribute.Type.StringType, false);
		
		Professor = new Relation("Professor", schema);
		
		int numberOfStudents = 20;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), "Prof" + Integer.toString(i * i),
					"p" + Integer.toString(i) + "@seas.upenn.edu"};
			Professor.add(schema.createTuple(tuple));
			}
		
	}
	
	private void createAdvises() {
		Schema  schema = new Schema();
		
		schema.addColumn("studentID", Attribute.Type.IntType, true);
		schema.addColumn("profID", Attribute.Type.IntType, true);
		
		Advises = new Relation("Advises", schema);
		
		int numberOfStudents = 71;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), i%20+1};
			Advises.add(schema.createTuple(tuple));
			}
		for(int i = 1; i <= 4; ++i){
			Serializable[] tuple = {
					72 + (int)(Math.random() * 29), 12 + (int)(Math.random() * 9)};
			Advises.add(schema.createTuple(tuple));
		}
		
	}
	
	private void createCourse() {
		Schema  schema = new Schema();
		
		schema.addColumn("courseID", Attribute.Type.IntType, true);
		schema.addColumn("description", Attribute.Type.StringType, false);
		schema.addColumn("semester", Attribute.Type.StringType, false);
		
		Course = new Relation("Course", schema);
		
		int numberOfStudents = 20;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), "This class teaches material" + Integer.toString(i),
					"fall"};
			Course.add(schema.createTuple(tuple));
		}
		
	}
	
	private void createTeaches() {
		Schema  schema = new Schema();
		
		schema.addColumn("profID", Attribute.Type.IntType, true);
		schema.addColumn("courseID", Attribute.Type.IntType, true);
		
		Teaches = new Relation("Teaches", schema);
		
		int numberOfStudents = 20;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), new Integer(i)};
			Teaches.add(schema.createTuple(tuple));
		}
		
	}
	
	private void createTakes() {
		Schema  schema = new Schema();
		
		schema.addColumn("studentID", Attribute.Type.IntType, true);
		schema.addColumn("courseID", Attribute.Type.IntType, true);
		
		Takes = new Relation("Takes", schema);
		
		int numberOfStudents = 100;
		for (int i = 1; i <= numberOfStudents; ++i) {
			Serializable[] tuple = {
					new Integer(i), 10};
			Takes.add(schema.createTuple(tuple));
		}
		
	}
	
	/**
	 * This is a sample expression using R and S
	 * 
	 * @return
	 */
	public static Relation execSampleQuery(Relation R, Relation S) {
		// The first operator joins R with S
		// It has a test that S.SSN = R.SSN
		
		BooleanExpression joinThese = new Equals(S, "SSN", R, "SSN");
		Join join = new Join(joinThese, R, S);

		// The next operator takes the output of the join and removes
		// the redundant "SSN" column
		
		List<String> projectThese = new ArrayList<String>();
		projectThese.add("First");
		projectThese.add("SSN");
		projectThese.add("Name");
		Project project = new Project(projectThese, join);
		
		// The next operator takes the output of the select and
		// filters the results to only include Name = "Bob"
		
		BooleanExpression isBob = new Equals(project, "Name", "Bob");
		Select sel = new Select(isBob, project);
		
		List<String> oldList = new ArrayList<String>();
		List<String> newList = new ArrayList<String>();
		oldList.add("Name");
		newList.add("Person-Name");
		Rename ren = new Rename(oldList, newList, sel);
		
		// We return the final results to the server
		return ren.getResult();
	}
	
	public static Relation execMyQuery1(
			Relation Student,
			Relation Professor,
			Relation Advises,
			Relation Course,
			Relation Takes,
			Relation Teaches) {
		// TODO: create RA query expression #1
		int[] Sizes;
		Sizes = new int[10];
		
		// Join Student with Advises
		// On S.studentID = A.studentID
		BooleanExpression joinThese = new Equals(Student, "studentID", Advises, "studentID");
		Join join = new Join(joinThese, Student, Advises);
		
		Sizes[0] = join.getResult().size();
		int SizeAccu = Sizes[0];
		System.out.println("First step results size is "+Integer.toString(Sizes[0])+"\n");
		
		// Join Results with Professor
		// On P.profID = A.profID
		joinThese = new Equals(join, "profID", Professor, "profID");
		join = new Join(joinThese, join, Professor);
		
		Sizes[1] = join.getResult().size();
		SizeAccu += Sizes[1];
		System.out.println("Second step results size is "+Integer.toString(Sizes[1])+"\n");
		
		// Join Results with Takes
		// On S.studentID = T.studentID
		joinThese = new Equals(join, "studentID", Takes, "studentID");
		join = new Join(joinThese, join, Takes);
		
		Sizes[2] = join.getResult().size();
		SizeAccu += Sizes[2];
		System.out.println("Third step results size is "+Integer.toString(Sizes[2])+"\n");
		
		// Join Results with Teaches
		// On P.profID = E.profID
		joinThese = new Equals(join, "profID", Teaches, "profID");
		join = new Join(joinThese, join, Teaches);
		
		Sizes[3] = join.getResult().size();
		SizeAccu += Sizes[3];
		System.out.println("Fourth step results size is "+Integer.toString(Sizes[3])+"\n");
		
		// Join Results with Teaches
		// On T.courseID = E.courseID
		joinThese = new Equals(join, "courseID", Teaches, "courseID");
		join = new Join(joinThese, join, Teaches);
		
		Sizes[4] = join.getResult().size();
		SizeAccu += Sizes[4];
		System.out.println("Fifth step results size is "+Integer.toString(Sizes[4])+"\n");
		
		// Join Results with Course
		// On T.courseID = C.courseID
		joinThese = new Equals(join, "courseID", Course, "courseID");
		join = new Join(joinThese, join, Course);
		
		Sizes[5] = join.getResult().size();
		SizeAccu += Sizes[5];
		System.out.println("Sixth step results size is "+Integer.toString(Sizes[5])+"\n");
		
		// The next operator takes the output of the join and removes
		// the redundant "SSN" column
		
		List<String> projectThese = new ArrayList<String>();
		projectThese.add("description");
		projectThese.add("studentID");
		projectThese.add("profID");
		Project project = new Project(projectThese, join);
		
		Sizes[6] = join.getResult().size();
		SizeAccu += Sizes[6];
		System.out.println("Seventh step results size is "+Integer.toString(Sizes[6])+"\n");
		
		System.out.println("The accumulate size of results is "+Integer.toString(SizeAccu)+"\n");
				
		return project.getResult();
	}
	
	public static Relation execMyQuery2(
			Relation Student,
			Relation Professor,
			Relation Advises,
			Relation Course,
			Relation Takes,
			Relation Teaches) {
		// TODO: create RA query expression #1
		return null;
	}

	/**
	 * Returns a relation output by computing a query
	 */
	@Override
	public Relation getRelation() throws IllegalArgumentException {
		
//		return execSampleQuery(R, S);
		return execMyQuery1(Student,Professor,Advises,Course,Takes,Teaches);
		
		// TODO: call execMyQuery1 or execMyQuery2
	}
	
}
