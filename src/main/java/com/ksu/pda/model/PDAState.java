package com.ksu.pda.model;

import java.util.HashMap;
import java.util.Map;

public class PDAState {

	private String name;
	
	private boolean accepted;
	
	
	private Map<String, String> delta = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Map<String, String> getDelta() {
		return delta;
	}

	public void setDelta(Map<String, String> delta) {
		this.delta = delta;
	}


	public PDAState(String name, boolean accepted) {
		this.name = name;
		this.accepted = accepted;
	}
	
	
	
	
}
