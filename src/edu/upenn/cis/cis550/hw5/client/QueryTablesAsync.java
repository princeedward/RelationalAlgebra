package edu.upenn.cis.cis550.hw5.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.upenn.cis.cis550.hw5.shared.Relation;

public interface QueryTablesAsync {

	void getRelation(AsyncCallback<Relation> callback) throws IllegalArgumentException;

}
