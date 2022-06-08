package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private List<Business> vertici;
	private List<Coppia> archi;
	private List<String> citta;
	private Graph<Business, DefaultWeightedEdge> grafo;
	private Map<String, Business> idMap;
	
	private List<Business> migliore;
	private Business start;
	private Business end;
	private Double soglia;
	private double kmTot;
	 
	
	
	public Model() {
		this.dao= new YelpDao();
		this.citta= dao.getCitta();
		this.idMap= dao.getAllBusiness();
		
	}
	
	public String creaGrafo(String citta) {
		
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici= new ArrayList<>(dao.getVertici(citta));
		archi= new ArrayList<>(dao.getCoppie(citta));
		
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Coppia c: archi) {
			
			Graphs.addEdge(this.grafo, c.getB1(), c.getB2(), c.getDistanza());
		}
		
		String string= "Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi!\n";
		
		return string;
		
	}
	
	public List<String> getCitta(){
		return citta;
	}
	
	public String getPiuDistante(Business locale) {
		
		double distMax=0;
		Coppia locMax= null;
		String string="";
		
		for(Coppia c: archi) {
			if(c.getB1().equals(locale) || c.getB2().equals(locale)) {
				if(c.getDistanza()>distMax) {
					distMax= c.getDistanza();
					locMax = c;
				}
			}
		}
		
		if(locMax.getB1().equals(locale)) {
			string= "LOCALE PIU DISTANTE:\n"+locMax.getB2()+" = "+distMax;
		}
		else {
			string= "LOCALE PIU DISTANTE:\n"+locMax.getB1()+" = "+distMax;
		}
		
		return string;
	}
	
	public List<Business> getVertici(){
		return vertici;
	}
	
	
	public List<Business> cercaPercorso(Business start, Business end, Double soglia){
		
		migliore= new ArrayList<>();
		List<Business> parziale= new ArrayList<>();
		this.start= start;
		this.end= end;
		this.soglia= soglia;
		parziale.add(start);
		kmTot=0;
		cerca(parziale);

		return migliore;
		
	}

	private void cerca(List<Business> parziale) {
		
		//condizione di terminazione
		if(parziale.get(parziale.size()-1).equals(end)) {
			if(parziale.size()>migliore.size()) {
				migliore= new ArrayList<>(parziale);
			}
			return;
		}
		
		List<Business> vicini= Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		
		for(Business v: vicini) {
			DefaultWeightedEdge e= this.grafo.getEdge(parziale.get(parziale.size()-1), v);
			if(!parziale.contains(v)) {
			if(v.getStars()>soglia) { 
				parziale.add(v);
				kmTot+=this.grafo.getEdgeWeight(e);
				cerca(parziale);
				parziale.remove(v);
				kmTot-=this.grafo.getEdgeWeight(e);
				
			}
			else if(v.equals(end)) {
				parziale.add(v);
				kmTot+=this.grafo.getEdgeWeight(e);
				cerca(parziale);
				parziale.remove(v);
				kmTot-=this.grafo.getEdgeWeight(e);
			}
			}
		}
		
	
			
		/*if(parziale.size()>migliore.size()) {  //condizione che potrebbe andare bene
				
			migliore= new ArrayList<>(parziale);
				
		}
		
		if(parziale.get(parziale.size()-1).equals(end)) {  //condizione di terminazione
			
			return;
		}
		
			
		
	
		
		List<Business> vicini= Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		
		for(Business v: vicini) {
			
			if(!parziale.contains(v)) {
			
			if(v.getStars()>=soglia && !v.equals(end)) {
				parziale.add(v);
				sommaDistanza(v, parziale.get(parziale.size()-2));
				if(v.equals(end)) {
					System.out.print("eccomi!\n");
				}
				cerca(parziale);
				togliDistanza(v, parziale.get(parziale.size()-2));
				parziale.remove(v);
			
			}
			else if(v.equals(end)) {
				parziale.add(v);
				sommaDistanza(v, parziale.get(parziale.size()-2));
				cerca(parziale);
				togliDistanza(v, parziale.get(parziale.size()-2));
				parziale.remove(v);
				
			}}
			
			
		}*/

		
	}
	
	public double getKmTOT() {
		return kmTot;
	}
	
	
	/*private void sommaDistanza(Business b1, Business b2) {
		
		for(Coppia c: archi) {
			if(c.getB1().equals(b1) && c.getB2().equals(b2)) {
				kmTot+=c.getDistanza();
			}
			else if(c.getB1().equals(b2) && c.getB2().equals(b1)) {
				kmTot+=c.getDistanza();
			}
	} 
	}
	
private void togliDistanza(Business b1, Business b2) {
		
		for(Coppia c: archi) {
			if(c.getB1().equals(b1) && c.getB2().equals(b2)) {
				kmTot-=c.getDistanza();
			}
			else if(c.getB1().equals(b2) && c.getB2().equals(b1)) {
				kmTot-=c.getDistanza();
			}
	} 
	}*/
}
