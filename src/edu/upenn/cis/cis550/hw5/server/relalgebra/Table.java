package edu.upenn.cis.cis550.hw5.server.relalgebra;

import edu.upenn.cis.cis550.hw5.shared.Relation;
import edu.upenn.cis.cis550.hw5.shared.Schema;

public class Table implements Operator {
	Relation rel;
	
	public Table(Relation r) {
		rel = r;
	}

	@Override
	public int getNumChildren() {
		return 0;
	}

	@Override
	public void addChild(Operator o) {
		throw new RuntimeException("Can't add child operator to Table operator");
	}

	@Override
	public Operator getChild(int index) {
		throw new RuntimeException("Can't get child of Table operator");
	}

	@Override
	public Relation getResult() {
		return rel;
	}

	@Override
	public Schema getSchema() {
		return rel.getSchema();
	}

}
