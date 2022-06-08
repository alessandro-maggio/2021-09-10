package it.polito.tdp.yelp.model;

import java.security.PublicKey;

import org.slf4j.impl.StaticLoggerBinder;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.javadocmd.simplelatlng.window.SortWrapper.DistanceComparator;

public class Coppia {
	
	private Business b1;
	private Business b2;
	private Double distanza;
	
	
	public Coppia(Business b1, Business b2) {
		super();
		this.b1 = b1;
		this.b2 = b2;
	}
	
	public Business getB1() {
		return b1;
	}
	public void setB1(Business b1) {
		this.b1 = b1;
	}
	public Business getB2() {
		return b2;
	}
	public void setB2(Business b2) {
		this.b2 = b2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	
	public void calcolaDistanza() {
		
		LatLng start= new LatLng(b1.getLatitude(), b1.getLongitude());
		LatLng end= new LatLng(b2.getLatitude(), b2.getLongitude());
		
		this.distanza= LatLngTool.distance(start, end, LengthUnit.KILOMETER);
		
	}

	
	

}
