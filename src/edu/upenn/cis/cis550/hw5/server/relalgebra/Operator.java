package edu.upenn.cis.cis550.hw5.server.relalgebra;

import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;

/**
 * A basic relational algebra operator
 * 
 * @author zives
 *
 */
public interface Operator {
	
	/**
	 * Return the schema output by the operator
	 * 
	 * @return
	 */
	public Schema getSchema();
	
	/**
	 * Get the number of input relational operators
	 * 
	 * @return
	 */
	public int getNumChildren();
	
	/**
	 * Add a child relational operator (if relevant)
	 * 
	 * @param o
	 */
	public void addChild(Operator o);
	
	/**
	 * Retrieve a child relational operator
	 * 
	 * @param index Index of child to retrieve [0 .. getNumChildren()-1]
	 * @return
	 */
	public Operator getChild(int index);
	
	/**
	 * Compute and return the relational output
	 * 
	 * @return
	 */
	public Relation getResult();
}
