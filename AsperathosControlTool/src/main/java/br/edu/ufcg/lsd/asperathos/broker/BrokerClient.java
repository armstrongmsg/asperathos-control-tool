package br.edu.ufcg.lsd.asperathos.broker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import br.edu.ufcg.lsd.asperathos.jobs.Job;

public class BrokerClient {

	private String ip;
	private Integer port;
	
	public BrokerClient(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
	}
	
	public List<Job> getJobs() throws ClientProtocolException, IOException {
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpGet httpGet = new HttpGet(getBrokerURL() + "/submissions");

    	String content = "";
    	CloseableHttpResponse response1 = httpclient.execute(httpGet);
    	// The underlying HTTP connection is still held by the response object
    	// to allow the response content to be streamed directly from the network socket.
    	// In order to ensure correct deallocation of system resources
    	// the user MUST call CloseableHttpResponse#close() from a finally clause.
    	// Please note that if response content is not fully consumed the underlying
    	// connection cannot be safely re-used and will be shut down and discarded
    	// by the connection manager. 
    	try {
    	    HttpEntity entity1 = response1.getEntity();
    	    // do something useful with the response body
    	    // and ensure it is fully consumed
    	    byte[] b = entity1.getContent().readAllBytes();
    	    content = new String(b);
    	    EntityUtils.consume(entity1);
    	} finally {
    	    response1.close();
    	}

    	return parseJobInfo(content);
	}

	private String getBrokerURL() {
		return String.format("http://%s:%d", this.ip, this.port);
	}
	
	private List<Job> parseJobInfo(String jobString) {
		List<Job> jobs = new ArrayList<Job>();
		JSONObject obj = new JSONObject(jobString);
		
		Set<String> jobIDs = obj.keySet();
		
		for (String id : jobIDs) {
			JSONObject info = (JSONObject) obj.get(id);
			String status = info.getString("status");
			Double executionTime = info.getDouble("execution_time");
			jobs.add(new Job(id, status, executionTime));
		}

		return jobs;
	}
}
