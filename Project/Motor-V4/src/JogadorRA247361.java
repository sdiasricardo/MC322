import java.util.ArrayList;

/**
* Esta classe representa um Jogador aleatório (realiza jogadas de maneira aleatória) para o jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class JogadorRA247361 extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	private Comportamento orgBaralho;
	private Comportamento comportamentoAtual;

	/**
	  * O método construtor do JogadorAleatorio.
	  * 
	  * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida. 
	  * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
	  */
	public JogadorRA247361(ArrayList<Carta> maoInicial, boolean primeiro){
		primeiroJogador = primeiro;
		
		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();
		orgBaralho = Comportamento.AGRESSIVO;
		comportamentoAtual = null;
		organizaBaralho();
		
		// Mensagens de depuração:
		System.out.println("*Classe JogadorRA247361* Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
		System.out.println("Mao inicial:");
		for(int i = 0; i < mao.size(); i++)
			System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}
	
	/**
	  * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
	  * 
	  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
	  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
	  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
	  * @return            um ArrayList com as Jogadas decididas
	  */
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana, minhaVida;

		if(cartaComprada != null){ // Inserindo a carta comprada na mão conforme a organização esperada
			if(cartaComprada instanceof CartaLacaio){
				for(int i=0; i < mao.size(); i++){
					if(mao.get(i) instanceof CartaLacaio) {
						if (((CartaLacaio) mao.get(i)).getAtaque() < ((CartaLacaio) cartaComprada).getAtaque()) {
							mao.add(i, cartaComprada);
							break;
						}
					}else{
						mao.add(i, cartaComprada);
						break;
					}
				}
			}else{
				for(int i=0; i < mao.size(); i++) {
					if(!(mao.get(i) instanceof CartaLacaio)) {
						if (((CartaMagia) cartaComprada).getMagiaTipo() == TipoMagia.ALVO) {
							if (((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.ALVO) {
								if (((CartaMagia) mao.get(i)).getMagiaDano() < ((CartaMagia) cartaComprada).getMagiaDano()) {
									mao.add(i, cartaComprada);
									break;
								}
							}else{
								mao.add(i, cartaComprada);
								break;
							}
						}else if(((CartaMagia) cartaComprada).getMagiaTipo() == TipoMagia.AREA){
							if(((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.AREA){
								if (((CartaMagia) mao.get(i)).getMagiaDano() < ((CartaMagia) cartaComprada).getMagiaDano()) {
									mao.add(i, cartaComprada);
									break;
								}
							}else if(((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.BUFF){
								mao.add(i, cartaComprada);
								break;
							}
						}else{
							if(((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.BUFF){
								if (((CartaMagia) mao.get(i)).getMagiaDano() < ((CartaMagia) cartaComprada).getMagiaDano()) {
									mao.add(i, cartaComprada);
									break;
								}else if(i== mao.size()){
									mao.add(i, cartaComprada);
									break;
								}
							}else if(i== mao.size()){
								mao.add(i, cartaComprada);
								break;
							}
						}
					}
				}
			}


		}
		if(primeiroJogador){
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas;
		
		// O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível.
		minhasJogadas = modoAgressivo(minhaMana);
		
		return minhasJogadas;
	}

	private void organizaBaralho(){// Para facilitar as operações, após a chamada do construtor, a mão do jogador
		// será organizada da seguinte forma:
		// Lacaio(Alto ataque) -> Lacaio(Baixo Ataque) -> Magia de Alvo -> Magia de Área -> Magia de buff. Para
		// os lacaios, a mana é o critério de desempate, ou seja, quem possui a menor mana deve ser baixado primeiro
		// Para as magias, não há critério de desempate.
		CartaMagia mag1;
		CartaLacaio lac1;
		CartaMagia mag2;
		CartaLacaio lac2;
		for(int i = 0; i < mao.size(); i++) {
			for (int j = 0; j <  mao.size() - i - 1; j++){

				if(mao.get(j) instanceof CartaLacaio){ // Caso em que j é um lacaio
					lac1 = (CartaLacaio) mao.get(j);

					if(mao.get(j + 1) instanceof CartaLacaio) { // A substituição deve ser verificada apenas
						// se a proxima carta for um lacaio. Do contrário, a ordem deve ser mantida
						lac2 = (CartaLacaio) mao.get(j + 1);
						if ((lac1.getAtaque() < lac2.getAtaque()) ||
								(lac1.getAtaque() == lac2.getAtaque() && lac1.getMana() > lac2.getMana())) {
							mao.set(j, lac2);
							mao.set(j + 1, lac1);
						}

					}
				}else{ // Caso em que j é uma CartaMagia
					mag1 = (CartaMagia) mao.get(j);
					if(mao.get(j + 1) instanceof CartaLacaio){
						lac1 = (CartaLacaio) mao.get(j + 1);
						mao.set(j, lac1);
						mao.set(j + 1, mag1);
					}else{
						mag2 = (CartaMagia) mao.get(j + 1);
						if(mag2.getMagiaTipo() == TipoMagia.ALVO && (mag1.getMagiaTipo() == TipoMagia.AREA
								|| mag1.getMagiaTipo() == TipoMagia.BUFF)){
							mao.set(j, mag2);
							mao.set(j + 1, mag1);
						}else if(mag2.getMagiaTipo() == TipoMagia.ALVO && mag1.getMagiaTipo() == TipoMagia.ALVO){
							if(mag2.getMagiaDano() > mag2.getMagiaDano()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}
						}else if(mag2.getMagiaTipo() == TipoMagia.AREA && mag1.getMagiaTipo() == TipoMagia.BUFF) {
							mao.set(j, mag2);
							mao.set(j + 1, mag1);
						}else if(mag2.getMagiaTipo() == TipoMagia.AREA && mag1.getMagiaTipo() == TipoMagia.AREA){
							if(mag2.getMagiaDano() > mag2.getMagiaDano()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}
						}
					}
				}

			}
		}
	}


	private ArrayList<Jogada> modoAgressivo(int minhaMana){
		ArrayList<Jogada> jogadas = new ArrayList<>();


		// Neste modo, são colocados à mesa todos os lacaios, por ordem de maior ataque.
		// Depois, se ainda restar mana, são utilizadas as cartas de magia no herói inimigo. Por fim,
		// os lacaios que ja estão na mesa executam seus ataques ao inimigo.

		for(int i = 0; i < mao.size(); i++){ // Baixando todos os lacaios possíveis e utilizando as
			// magias
			Carta card = mao.get(i);
			if(minhaMana != 0) {
				if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					jogadas.add(lac);
					minhaMana -= card.getMana();
					System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + card);
					mao.remove(i);
					i--;
				} else if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO ||
							((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {
						Jogada mag = new Jogada(TipoJogada.MAGIA, card, null);
						jogadas.add(mag);
						minhaMana -= card.getMana();
						System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + card);
						mao.remove(i);
						i--;
					}else if(((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF){
						// A carta de buff será usada no lacaio de maior ataque, que precisa ser identificado
						// entre os lacaios da mesa, como feito a seguir
						CartaLacaio lacAtq = lacaios.get(i);
						for(Carta bigger: lacaios){
							if(((CartaLacaio) bigger).getAtaque() > lacAtq.getAtaque()){
								bigger = lacAtq;
							}
						}

						Jogada mag = new Jogada(TipoJogada.MAGIA, card, lacAtq);
						jogadas.add(mag);
						minhaMana -= card.getMana();
						System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + card);
						mao.remove(i);
					}
				}
			}
		}

		for (Carta card : lacaios) {
			Jogada lac = new Jogada(TipoJogada.ATAQUE, card, null);
			jogadas.add(lac);
			System.out.println("Jogada: Decidi uma jogada de atacar com o lacaio: " + card);
		}
		return jogadas;
	}

	private ArrayList<Jogada> modoCurvaMana(int minhaMana, Mesa mesa){
		ArrayList<Jogada> jogadas = new ArrayList<>();
		// Nesse modo de jogo, é priorizado a utilização mais eficiente da mana d



		return jogadas;
	}
}

enum Comportamento{ // Enum para definir em qual estado o jogador está jogando e como o baralho está organizado
	AGRESSIVO, CONTROLE, MANA
}