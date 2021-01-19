package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geoinfo {

	@SerializedName("lat")
	@Expose
	private double lat;
	@SerializedName("lng")
	@Expose
	private double lng;
	@SerializedName("rad")
	@Expose
	private long rad;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public long getRad() {
		return rad;
	}

	public void setRad(long rad) {
		this.rad = rad;
	}
}
