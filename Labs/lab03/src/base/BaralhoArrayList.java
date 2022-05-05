package base;

import java.util.*;

public class BaralhoArrayList {
	private ArrayList<CartaLacaio> vetorCartas;
	public static final int MAX_CARDS = 30;
	
	public BaralhoArrayList(){
		vetorCartas = new ArrayList<CartaLacaio>();
		
	}
	
	public void adicionarCarta(CartaLacaio card) {
		vetorCartas.add(card);
	}
	
	public CartaLacaio comprarCarta() {
		CartaLacaio card;
		card = vetorCartas.get(vetorCartas.size() - 1);
		vetorCartas.remove(vetorCartas.size() - 1);
		return card;
	}
	
	public void embaralhar() {
		Collections.shuffle(vetorCartas);
		
		Collections.reverse(vetorCartas);
		for(int i=0; i<vetorCartas.size(); i++) {
			System.out.println(vetorCartas.get(i));
		}
		
		Collections.reverse(vetorCartas);
		
	}
}
