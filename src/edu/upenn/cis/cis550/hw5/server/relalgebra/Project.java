package edu.upenn.cis.cis550.hw5.server.relalgebra;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * This operator implements relational projection
 * 
 * @author zives
 *
 */
public class Project implements Operator {
	Operator child;
	List<Integer> projectedColumns;
	Schema output;
	
	static int count = 0;
	
	/**
	 * Construct a projection operator
	 * 
	 * @param projectedNames List of attribute names to project
	 * @param child The child (input) relational algebra operator to the project
	 */
	public Project(List<String> projectedNames, Operator child) {
		this.child = child;
		
		projectedColumns = new ArrayList<Integer>();
		
		Schema s = child.getSchema();
		List<Attribute.Type> types = new ArrayList<Attribute.Type>();
		for (String name : projectedNames) {
			int pos = s.getIndexOf(name);
		
			if (pos == -1)
				throw new RuntimeException("Project could not find attribute " + name + " to project!");
			
			projectedColumns.add(pos);
			types.add(s.getType(pos));
		}
		
		output = new Schema(projectedNames, types);
	}
	
	/**
	 * Construct a projection operator
	 * 
	 * @param projectedNames List of attribute names to project
	 * @param child The child (input) relational algebra operator to the project
	 */
	public Project(List<String> projectedNames, Relation table) {
		this(projectedNames, new Table(table));
	}
	
	@Override
	public Schema getSchema() {
		return output;
	}
	@Override
	public int getNumChildren() {
		return 1;
	}
	@Override
	public void addChild(Operator o) {
		if (child == null)
			child = o;
		else
			throw new RuntimeException("Project already has one child; cannot add another");
		
	}
	@Override
	public Operator getChild(int index) {
		if (index == 0)
			return child;
		else
			throw new RuntimeException("Only one child of project operator");
	}
	
	/**
	 * Construct the relation output for the project operator
	 */
	@Override
	public Relation getResult() {
		Relation subresult = child.getResult();
		Relation result = new Relation("Project" + (++count), output);
		
		for (Tuple t: subresult) {
			List<Serializable> values = new ArrayList<Serializable>();
			for (Integer index: projectedColumns)
				values.add(t.getValue(index).getValue());
			
			result.add(new Tuple(output, values));
		}
		
		return result;
	}

}
