package edu.upenn.cis.cis550.hw5.server.relalgebra;

import edu.upenn.cis.cis550.hw5.server.expressions.BooleanExpression;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * Relational selection operator
 * 
 * @author zives
 *
 */
public class Select implements Operator {
	Operator child;
	BooleanExpression condition;
	int count = 0;
	
	/**
	 * Constructor for the relational selection operator
	 * 
	 * @param test The Boolean condition (test) to apply
	 * @param child Child relational operator
	 */
	public Select(BooleanExpression test, Operator child) {
		condition = test;
		this.child = child;
	}

	/**
	 * Constructor for the relational selection operator
	 * 
	 * @param test The Boolean condition (test) to apply
	 * @param child Child intput table
	 */
	public Select(BooleanExpression test, Relation table) {
		this(test, new Table(table));
	}

	@Override
	public int getNumChildren() {
		return 1;
	}

	@Override
	public void addChild(Operator o) {
		if (child != null)
			throw new RuntimeException("Can't add another child operator to Select operator");
		else
			child = o;
	}

	@Override
	public Operator getChild(int index) {
		if (index == 0)
			return child;
		else
			throw new RuntimeException("Only 1 child for a select operator");
	}

	/**
	 * Construct the relation output for the select operator
	 */
	@Override
	public Relation getResult() {
		Relation subresult = child.getResult();
		Relation result = new Relation("Sel" + (count++), subresult.getSchema());
		
		for (Tuple t: subresult) {
			condition.setInput(t);
			
			if (condition.evaluate())
				result.add(t);
		}
		return result;
	}

	@Override
	public Schema getSchema() {
		return child.getSchema();
	}

}
