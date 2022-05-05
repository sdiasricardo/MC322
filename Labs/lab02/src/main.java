public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1);
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		CartaMagia mag1 = new CartaMagia(4, "You shall not pass", 3, true, 7);
		CartaMagia mag2 = new CartaMagia(5, "Telecinese", 3, false, 2);
		
		//Questão 1
		CartaLacaio lac4 = new CartaLacaio(6, "Gandalf", 4);
		System.out.println(lac4);
		
		
		//Questão2
		lac1.setAtaque(lac3.getAtaque());
		System.out.println(lac1);
		
		//Questão3
		System.out.println(mag1);
		
		//Questão 4
		CartaLacaio lac5 = new CartaLacaio(lac2);
		System.out.println(lac2);
		System.out.println(lac5);
		
		//Questão 5
		System.out.println(mag1.nome);
		System.out.println(mag1.getNome());
		
		//Questão 6
		lac1.buffar(0);
		System.out.println(lac1);
		lac1.buffar(2);
		System.out.println(lac1);
		
		
	}

}
