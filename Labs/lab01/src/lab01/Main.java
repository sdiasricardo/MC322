package lab01;

public class Main {

	public static void main(String[] args) {
		// Instaciação dos objetos
		CartaLacaio lac1 = new CartaLacaio(1, "Gandalf", 9, 10, 4);
		CartaMagia mag1 = new CartaMagia(2, "Lightning", 6, true, 3);
		CartaLacaio lac2 = new CartaLacaio(3, "Sauron", 15, 30, 10);
		CartaMagia mag2 = new CartaMagia (4, "Atract", 4, false, 2);
		
		// Impressão dos objetos
		System.out.println("Lacaio 1:\n"+lac1);
		System.out.println("\nLacaio 2:\n"+lac2);
		System.out.println("\nMagia1:\n"+mag1);
		System.out.println("\nMagia2:\n"+mag2);

	}

}
