package edu.upenn.cis.cis550.hw5.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.upenn.cis.cis550.hw5.shared.Relation;

/**
 * This interface is used to make calls from the browser in JavaScript,
 * to the server's Java code in QueryTablesAsync
 * 
 * @author zives
 *
 */
@RemoteServiceRelativePath("QueryTables")
public interface QueryTables extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static QueryTablesAsync instance;
		public static QueryTablesAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(QueryTables.class);
			}
			return instance;
		}
	}
	
	public Relation getRelation() throws IllegalArgumentException;
}
