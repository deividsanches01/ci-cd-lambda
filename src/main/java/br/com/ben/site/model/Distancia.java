package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distancia {

	@SerializedName("metric")
	@Expose
	private String metric;
	@SerializedName("value")
	@Expose
	private float value;

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public float getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
