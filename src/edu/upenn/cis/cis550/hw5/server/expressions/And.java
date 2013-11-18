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
public class And implements BooleanExpression {
	BooleanExpression bExpr = null;
	BooleanExpression aExpr = null;
	
	/**
	 * Constructor:  take two expressions over two tuples
	 * 
	 * @param aExpr First expression
	 * @param bExpr Second expression
	 */
	public And(BooleanExpression aExpr, BooleanExpression bExpr) {
		this.aExpr = aExpr;
		this.bExpr = bExpr;
	}

	@Override
	public boolean evaluate() {
		return aExpr.evaluate() && bExpr.evaluate();
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	public void setLeft(Tuple a) {
		aExpr.setLeft(a);
		bExpr.setLeft(a);
	}
	
	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	public void setRight(Tuple b) {
		aExpr.setRight(b);
		bExpr.setRight(b);
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual value
	 */
	public void setRight(Serializable b) {
		aExpr.setRight(b);
		bExpr.setRight(b);
	}

	/**
	 * Before evaluating, we need to set the operator to point to an individual tuple
	 */
	@Override
	public void setInput(Tuple t) {
		aExpr.setInput(t);
		bExpr.setInput(t);
	}
}
