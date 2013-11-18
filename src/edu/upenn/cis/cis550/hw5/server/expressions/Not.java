package edu.upenn.cis.cis550.hw5.server.expressions;

import java.io.Serializable;

import edu.upenn.cis.cis550.hw5.server.relalgebra.Operator;
import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

/**
 * Boolean predicate:  AND between two expressions
 * 
 * @author zives
 *
 */
public class Not implements BooleanExpression {
	BooleanExpression aExpr = null;
	
	/**
	 * Constructor:  take an expression and invert it
	 * 
	 * @param aExpr First expression
	 */
	public Not(BooleanExpression aExpr) {
		this.aExpr = aExpr;
	}

	@Override
	public boolean evaluate() {
		return !aExpr.evaluate();
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	public void setLeft(Tuple a) {
		aExpr.setLeft(a);
	}
	
	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	@Override
	public void setInput(Tuple t) {
		aExpr.setInput(t);
	}

	@Override
	public void setRight(Tuple b) {
		aExpr.setRight(b);
	}

	@Override
	public void setRight(Serializable b) {
		aExpr.setRight(b);
	}
}
