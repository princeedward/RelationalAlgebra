package edu.upenn.cis.cis550.hw5.server.expressions;

import java.io.Serializable;

import edu.upenn.cis.cis550.hw5.shared.Attribute;
import edu.upenn.cis.cis550.hw5.shared.Tuple;

public interface BooleanExpression {
	public void setLeft(Tuple a);
	
	public void setRight(Tuple b);

	public void setRight(Serializable b);
	
	public void setInput(Tuple a);

	public boolean evaluate();
}
