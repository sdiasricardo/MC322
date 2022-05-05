package base;

import java.util.*;

public class Baralho {
	private ArrayList<Carta> vetorCartas;
	public static final int MAX_CARDS = 30;
	
	public Baralho(){
		vetorCartas = new ArrayList<Carta>();
		
	}
	
	public void adicionarCarta(Carta card) {
		vetorCartas.add(card);
	}
	
	public Carta comprarCarta() {
		Carta card;
		card = vetorCartas.get(vetorCartas.size() - 1);
		vetorCartas.remove(vetorCartas.size() - 1);
		return card;
	}
	
	public void embaralhar() {
		Collections.shuffle(vetorCartas);
		
		for(int i=0; i<vetorCartas.size(); i++) {
			System.out.println(vetorCartas.get(i));
		}
		
		
	}
}
