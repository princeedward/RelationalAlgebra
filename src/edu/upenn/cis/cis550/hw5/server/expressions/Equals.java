package edu.upenn.cis.cis550.hw5.server.expressions;

import java.io.Serializable;

import edu.upenn.cis.cis550.hw5.server.relalgebra.Operator;
import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * Boolean predicate:  test for equality, between an attribute and
 * a value, or between two attributes
 * 
 * @author zives
 *
 */
public class Equals implements BooleanExpression {
	Schema leftSchema;
	Tuple left;
	String leftName;
	Schema rightSchema;
	Tuple right = null;
	String rightName = null;
	Serializable right2 = null;
	
	boolean secondIsAttribute = false; 
	
	/**
	 * Constructor:  take a tuple's attribute and test against another tuple's attribute
	 * 
	 * @param a Left tuple
	 * @param leftName Specific attribute name
	 * @param b Right tuple
	 * @param rightName Specific attribute name
	 */
	public Equals(Tuple a, String leftName, Tuple b, String rightName) {
		left = a;
		leftSchema = a.getSchema();
		this.leftName = leftName;
		if (!leftSchema.getNames().contains(leftName))
			throw new RuntimeException("Attempt to test equality over attribute " + leftName + 
					" that doesn't exist in " + leftSchema);
		right = b;
		rightSchema = b.getSchema();
		this.rightName = rightName;
		if (!rightSchema.getNames().contains(rightName))
			throw new RuntimeException("Attempt to test equality over attribute " + rightName + 
					" that doesn't exist in " + rightSchema);
		secondIsAttribute = true;
	}

	/**
	 * Constructor:  take a tuple's attribute and test against a (serializable) object
	 * 
	 * @param a Tuple
	 * @param name Specific attribute name
	 * @param b Value to test against
	 */
	public Equals(Tuple a, String name, Serializable b) {
		left = a;
		leftSchema = a.getSchema();
		this.leftName = name;
		if (!leftSchema.getNames().contains(leftName))
			throw new RuntimeException("Attempt to test equality over attribute " + leftName + 
					" that doesn't exist in " + leftSchema);
		right2 = b;
		secondIsAttribute = false;
	}
	
	/**
	 * Constructor:  take a relation's attribute and test against another relation's attribute
	 * 
	 * @param a Left relation
	 * @param leftName Specific attribute name
	 * @param b Right relation
	 * @param rightName Specific attribute name
	 */
	public Equals(Relation a, String leftName, Relation b, String rightName) {
		this.leftName = leftName;
		leftSchema = a.getSchema();
		if (!leftSchema.getNames().contains(leftName))
			throw new RuntimeException("Attempt to test equality over attribute " + leftName + 
					" that doesn't exist in " + leftSchema);
		this.rightName = rightName;
		rightSchema = b.getSchema();
		if (!rightSchema.getNames().contains(rightName))
			throw new RuntimeException("Attempt to test equality over attribute " + rightName + 
					" that doesn't exist in " + rightSchema);
		secondIsAttribute = true;
	}

	/**
	 * Constructor:  take a relation's attribute and test against a (serializable) object
	 * 
	 * @param a Relation to test against
	 * @param name Specific attribute name
	 * @param b Value to test against
	 */
	public Equals(Relation a, String name, Serializable b) {
		this.leftName = name;
		leftSchema = a.getSchema();
		
		if (!leftSchema.getNames().contains(name))
			throw new RuntimeException("Attempt to test equality over attribute " + name + " that doesn't exist in " + leftSchema);
		right2 = b;
		secondIsAttribute = false;
	}
	
	/**
	 * Constructor:  take an operator output's attribute and test against another operator output's attribute
	 * 
	 * @param a Left source operator
	 * @param leftName Specific attribute name
	 * @param b Right source operator
	 * @param rightName Specific attribute name
	 */
	public Equals(Operator a, String leftName, Relation b, String rightName) {
		this.leftName = leftName;
		leftSchema = a.getSchema();
		if (!leftSchema.getNames().contains(leftName))
			throw new RuntimeException("Attempt to test equality over attribute " + leftName + 
					" that doesn't exist in " + leftSchema);
		this.rightName = rightName;
		rightSchema = b.getSchema();
		if (!rightSchema.getNames().contains(rightName))
			throw new RuntimeException("Attempt to test equality over attribute " + rightName + 
					" that doesn't exist in " + rightSchema);
		secondIsAttribute = true;
	}

	/**
	 * Constructor:  take an operator's output's attribute and test against a (serializable) object
	 * 
	 * @param a Source operator
	 * @param name Specific attribute name
	 * @param b Value to test against
	 */
	public Equals(Operator a, String name, Serializable b) {
		this.leftName = name;
		leftSchema = a.getSchema();
		
		if (!leftSchema.getNames().contains(name))
			throw new RuntimeException("Attempt to test equality over attribute " + name + " that doesn't exist in " + leftSchema);
		right2 = b;
		secondIsAttribute = false;
	}
	
	/**
	 * Constructor:  take a schema's attribute and test against another schema's attribute
	 * 
	 * @param a Left input's schema
	 * @param leftName Specific attribute name
	 * @param b Right input's schema
	 * @param rightName Specific attribute name
	 */
	public Equals(Schema a, String leftName, Schema b, String rightName) {
		this.leftName = leftName;
		leftSchema = a;
		if (!leftSchema.getNames().contains(leftName))
			throw new RuntimeException("Attempt to test equality over attribute " + leftName + 
					" that doesn't exist in " + leftSchema);
		rightSchema = b;
		this.rightName = rightName;
		if (!rightSchema.getNames().contains(rightName))
			throw new RuntimeException("Attempt to test equality over attribute " + rightName + 
					" that doesn't exist in " + rightSchema);
		secondIsAttribute = true;
	}

	/**
	 * Constructor:  take a schema's attribute and test against a (serializable) object
	 * 
	 * @param a Schema
	 * @param name Specific attribute name
	 * @param b Value to test against
	 */
	public Equals(Schema a, String name, Serializable b) {
		this.leftName = name;
		leftSchema = a;
		
		if (!leftSchema.getNames().contains(name))
			throw new RuntimeException("Attempt to test equality over attribute " + name + " that doesn't exist in " + leftSchema);
		right2 = b;
		secondIsAttribute = false;
	}
	
	@Override
	public boolean evaluate() {
		if (secondIsAttribute)
			return (left.getValue(leftName).compareTo(right.getValue(rightName)) == 0);
		else
			return (left.getValue(leftName).compareValue(right2) == 0);
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	public void setLeft(Tuple a) {
		left = a;
	}
	
	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	public void setRight(Tuple b) {
		if (!secondIsAttribute)
			throw new RuntimeException("Can't switch from value to attribute!");
		
		right = b;
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual value
	 */
	public void setRight(Serializable b) {
		if (secondIsAttribute)
			throw new RuntimeException("Can't switch from attribute to value!");
		
		right2 = b;
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	@Override
	public void setInput(Tuple t) {
		setLeft(t);
	}
}
