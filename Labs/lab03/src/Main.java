import base.Baralho;
import base.BaralhoArrayList;
import base.CartaLacaio;
import util.Util;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1);
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		Util.buffar(lac1, 2);
		Util.buffar(lac2, 2, 3);
		
		Baralho baralho = new Baralho();
		baralho.adicionarCarta(lac1);
		baralho.adicionarCarta(lac2);
		baralho.adicionarCarta(lac3);
		
		
		baralho.embaralhar();
		
		BaralhoArrayList baralho_list = new BaralhoArrayList();
		 baralho_list.adicionarCarta(lac1);
		 baralho_list.adicionarCarta(lac2);
		 baralho_list.adicionarCarta(lac3);
		 
		 baralho_list.embaralhar();
		 
		 System.out.println(baralho_list.comprarCarta());
	}

}
