package base;

import java.util.Random;

public class Baralho {
	private CartaLacaio[] vetorCartas;
	private int nCartas;
	private static Random gerador = new Random();
	
	public Baralho() {
		vetorCartas = new CartaLacaio[10];
		nCartas = 0;
	}
	
	public void adicionarCarta(CartaLacaio card) {
		vetorCartas[nCartas] = card;
		nCartas ++;
	}
	
	public CartaLacaio comprarCarta() {
		nCartas --;
		return vetorCartas[nCartas];
	}
	
	public void embaralhar() {
		int i, j;
		
		for(i=0; i<nCartas; i++) {
			j = gerador.nextInt(i+1);
			
			if(i != j) {
				CartaLacaio a = vetorCartas[i];
				vetorCartas[i] = vetorCartas[j];
				vetorCartas[j] = a;
			}
		}
		
		for(i=nCartas - 1; i>=0; i--) {
			System.out.println(vetorCartas[i]);
		}
	}
	
}
