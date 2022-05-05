import base.Baralho;
import base.Lacaio;
import base.Magia;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Baralho baralho = new Baralho();
		
		Lacaio lac1 = new Lacaio("Frodo Bolseiro", 2, 1, 1);
		Lacaio lac2 = new Lacaio("Aragorn", 5, 7, 6);
		Lacaio lac3 = new Lacaio("Gandalf", 5, 7, 6);
		
		Magia mag1 = new Magia("Cega todos os inimigos", "Blind", 3);
		Magia mag2 = new Magia("Faz todos os iminigos sangrarem por 1 segundo", "Hurt", 1);
		Magia mag3 = new Magia("Cura a vida dos aliados", "Heal", 4);
				
		baralho.adicionarCarta(lac1);
		baralho.adicionarCarta(lac2);
		baralho.adicionarCarta(lac3);
		baralho.adicionarCarta(mag3);
		baralho.adicionarCarta(mag2);
		baralho.adicionarCarta(mag1);
		
		baralho.embaralhar();
		
		
	}

}
