package edu.upenn.cis.cis550.hw5.server.relalgebra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis.cis550.hw5.server.expressions.BooleanExpression;
import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * Join relational algebra operator
 * 
 * @author zives
 *
 */
public class Join implements Operator {
	BooleanExpression joinCondition;
	Operator leftChild;
	Operator rightChild;
	Schema output;
	
	static int count = 0;
	
	/**
	 * Construct a new join operator
	 * 
	 * @param test The join predicate or condition
	 * @param left The left relational algebra input
	 * @param right The right relational algebra input
	 */
	public Join(BooleanExpression test, Operator left, Operator right) {
		joinCondition = test;
		leftChild = left;
		rightChild = right;
		
		List<String> joinedNames = new ArrayList<String>();
		List<Attribute.Type> joinedTypes = new ArrayList<Attribute.Type>();
		
		joinedNames.addAll(leftChild.getSchema().getNames());
		joinedNames.addAll(rightChild.getSchema().getNames());
		joinedTypes.addAll(leftChild.getSchema().getTypes());
		joinedTypes.addAll(rightChild.getSchema().getTypes());
		
		output = new Schema(joinedNames, joinedTypes);
	}

	/**
	 * Construct a new join operator
	 * 
	 * @param test The join predicate or condition
	 * @param left The left relation
	 * @param right The right relational algebra input
	 */
	public Join(BooleanExpression test, Relation left, Operator right) {
		this(test, new Table(left), right);
	}

	/**
	 * Construct a new join operator
	 * 
	 * @param test The join predicate or condition
	 * @param left The left relational algebra input
	 * @param right The right relation
	 */
	public Join(BooleanExpression test, Operator left, Relation right) {
		this(test, left, new Table(right));
	}

	/**
	 * Construct a new join operator
	 * 
	 * @param test The join predicate or condition
	 * @param left The left relation
	 * @param right The right relation
	 */
	public Join(BooleanExpression test, Relation left, Relation right) {
		this(test, new Table(left), new Table(right));
	}

	@Override
	public Schema getSchema() {
		return output;
	}

	@Override
	public int getNumChildren() {
		return 2;
	}

	@Override
	public void addChild(Operator o) {
		if (leftChild == null)
			leftChild = o;
		else if (rightChild == null)
			rightChild = o;
		else
			throw new RuntimeException("Relational join only takes two inputs, which are already full");
	}

	@Override
	public Operator getChild(int index) {
		if (index == 0)
			return leftChild;
		else if (index == 1)
			return rightChild;
		else
			throw new RuntimeException("Join only has two inputs");
	}

	/**
	 * Compute relational result, which is a Cartesian product minus
	 * any results that fail to satisfy the join condition
	 */
	@Override
	public Relation getResult() {
		Relation leftResult = leftChild.getResult();
		Relation rightResult = rightChild.getResult();
		Relation result = new Relation("Join" + (++count), output);
		
		for (Tuple left: leftResult) {
			for (Tuple right: rightResult) {
				joinCondition.setLeft(left);
				joinCondition.setRight(right);
				
				if (joinCondition.evaluate()) {
					List<Serializable> values = new ArrayList<Serializable>(output.size());
					
					for (Attribute v: left.getValues())
						values.add(v.getValue());
					for (Attribute v: right.getValues())
						values.add(v.getValue());
					
					result.add(new Tuple(output, values));
				}
			}
		}

		return result;
	}

}
