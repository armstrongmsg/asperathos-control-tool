package br.edu.ufcg.lsd.asperathos.jobs;

public class Job {
	private String id;
	private String status;
	private Double executionTime;
	
	public Job(String id, String state, Double executionTime) {
		super();
		this.id = id;
		this.status = state;
		this.executionTime = executionTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Double executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", status=" + status + ", executionTime=" + executionTime + "]";
	}
}
