package edu.upenn.cis.cis550.hw5.server.relalgebra;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * This operator implements relational rename
 * 
 * @author zives
 *
 */
public class Rename implements Operator {
	Operator child;
	Schema output;
	
	static int count = 0;
	
	/**
	 * Construct a rename operator
	 * 
	 * @param originalNames List of attribute names to rename
	 * @param newNames List of new names (must be same length)
	 * @param child The child (input) relational algebra operator to the project
	 */
	public Rename(List<String> originalNames, List<String> newNames, Operator child) {
		this.child = child;
		
		if (newNames.size() != originalNames.size())
			throw new RuntimeException("Mismatched attribute lists");
		
		Map<String,String> renameThese = new HashMap<String,String>();
		
		for (int i = 0; i < originalNames.size(); i++)
			renameThese.put(originalNames.get(i), newNames.get(i));

		List<String> outputAttribs = new ArrayList<String>();
		Schema s = child.getSchema();
		List<Attribute.Type> types = new ArrayList<Attribute.Type>();
		int pos = 0;
		for (String name : s.getNames()) {
			if (renameThese.containsKey(name))
				outputAttribs.add(renameThese.get(name));
			else
				outputAttribs.add(name);
			types.add(s.getType(pos++));
		}
		
		output = new Schema(outputAttribs, types);
	}
	
	/**
	 * Construct a rename operator
	 * 
	 * @param oldNames List of attribute names to rename
	 * @param newNames List of new names (must be same length)
	 * @param child The child (input) relational algebra operator to the project
	 */
	public Rename(List<String> oldNames, List<String> newNames, Relation table) {
		this(oldNames, newNames, new Table(table));
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
			throw new RuntimeException("Rename already has one child; cannot add another");
		
	}
	@Override
	public Operator getChild(int index) {
		if (index == 0)
			return child;
		else
			throw new RuntimeException("Only one child of rename operator");
	}
	
	/**
	 * Construct the relation output for the rename operator
	 */
	@Override
	public Relation getResult() {
		Relation subresult = child.getResult();
		Relation result = new Relation("Rename" + (++count), output);
		
		for (Tuple t: subresult) {
			List<Serializable> values = new ArrayList<Serializable>();
			for (int i = 0; i < t.getValues().size(); i++)
				values.add(t.getValue(i).getValue());
			
			result.add(new Tuple(output, values));
		}
		
		return result;
	}

}
