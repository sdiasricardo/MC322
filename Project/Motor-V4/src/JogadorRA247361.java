import java.util.ArrayList;

/**
* Esta classe representa um Jogador aleatório (realiza jogadas de maneira aleatória) para o jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class JogadorRA247361 extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;

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
		organizaBaralhoAtq();
		
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

		if(minhaVida <= 15 || lacaios.size() > lacaiosOponente.size()){
			organizaBaralhoAtq();
			minhasJogadas = modoAgressivo(minhaMana);
		}else if(lacaios.size() < lacaiosOponente.size()){
			organizaBaralhoMana();
			minhasJogadas = modoControle(minhaMana);
		}else{
			organizaBaralhoAtq();
			minhasJogadas = modoAgressivo(minhaMana);
		}


		
		return minhasJogadas;
	}

	private void organizaBaralhoAtq(){// Para facilitar as operações, após a chamada do construtor, a mão do jogador
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
							if(mag2.getMagiaDano() > mag1.getMagiaDano()){
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



	private void organizaBaralhoMana(){// Para facilitar as operações, após a chamada do construtor, a mão do jogador
		// será organizada da seguinte forma:
		// Lacaio(Alta Mana) -> Lacaio(Baixa Mana) -> Magia de Alvo(Alta Mana) -> Magia de Alvo(Baixa Mana) -> Magia de Área -> Magia de buff. Para
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
						if ((lac1.getMana() < lac2.getMana()) ||
								(lac1.getMana() == lac2.getMana() && lac1.getAtaque() < lac2.getAtaque())) {
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
							if(mag2.getMana() > mag1.getMana()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}if(mag2.getMana() == mag1.getMana() && mag2.getMagiaDano() > mag1.getMagiaDano()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}
						}else if(mag2.getMagiaTipo() == TipoMagia.AREA && mag1.getMagiaTipo() == TipoMagia.BUFF) {
							mao.set(j, mag2);
							mao.set(j + 1, mag1);
						}else if(mag2.getMagiaTipo() == TipoMagia.AREA && mag1.getMagiaTipo() == TipoMagia.AREA){
							if(mag2.getMana() > mag1.getMana()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}if(mag2.getMana() == mag1.getMana() && mag2.getMagiaDano() > mag1.getMagiaDano()){
								mao.set(j, mag2);
								mao.set(j + 1, mag1);
							}
						}
					}
				}

			}
		}
	}


	private void baixaLacaios(int minhaMana, ArrayList<Jogada> jogadas){
		// Método que baixa todos os lacaios possíveis, e utiliza as magias restantes para atacar o herói inimigo
		int qtd = lacaios.size();
		for(int i = 0; i < mao.size(); i++){ // Baixando todos os lacaios possíveis e utilizando as
			// magias
			Carta card = mao.get(i);

			if(minhaMana != 0) {

				if (card instanceof CartaLacaio && card.getMana() <= minhaMana && qtd < 7) {
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					jogadas.add(lac);
					minhaMana -= card.getMana();
					System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + card);
					mao.remove(i);
					qtd++;
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

					}else if(((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() != 0){
						// A carta de buff será usada no lacaio de maior ataque, que precisa ser identificado
						// entre os lacaios da mesa, como feito a seguir. Isso só ocorre caso existam lacaios
						// baixadaos na mesa

						CartaLacaio lacAtq = lacaios.get(0);
						for(CartaLacaio bigger: lacaios){
							if(bigger.getAtaque() > lacAtq.getAtaque()){
								bigger = lacAtq;
							}
						}

						Jogada mag = new Jogada(TipoJogada.MAGIA, card, lacAtq);
						jogadas.add(mag);
						minhaMana -= card.getMana();
						System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + card);
						mao.remove(i);
						i--;
					}
				}
			}
		}
	}




	private void trocasFavoraveis(ArrayList<Jogada> jogadas){
		for(int i=0; i< lacaios.size(); i++){ // Iterando sobre os lacaios ja baixados
			boolean usado = false;

			for(int j=0; j< lacaiosOponente.size(); j++){ // Iterando sobre os lacaios do oponente, para
				// verificar se há alguma troca favorável

				if(lacaios.get(i).getAtaque() >= lacaiosOponente.get(j).getVidaAtual()){ // Verificando se o lacaio do oponente
					// morrerá com a troca (Primeiro critério para sua realização)

					if(lacaios.get(i).getVidaAtual() > lacaiosOponente.get(j).getAtaque()){ // Caso em que apenas o lacaio inimigo
						// morre
						Jogada mag = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), lacaiosOponente.get(j));
						jogadas.add(mag);
						System.out.println("Jogada: Decidi uma jogada de realizar uma troca: " + lacaios.get(i));
						usado = true;
						lacaiosOponente.remove(j);
						break;

					}else{ // Caso em que ambos os lacaios morrem
						if((lacaios.get(i).getMana() < lacaiosOponente.get(j).getMana()) ||
								(lacaios.get(i).getVidaAtual() < lacaios.get(i).getVidaMaxima()
										&& lacaios.get(i).getVidaAtual() < lacaiosOponente.get(j).getVidaAtual())){
							// Caso em que o lacaio do oponente possui maior custo de mana, ou que o lacaio aliado
							// ja estava danificado e possui menor vida do que o lacaio oponente.

							Jogada mag = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), lacaiosOponente.get(j));
							jogadas.add(mag);
							System.out.println("Jogada: Decidi uma jogada de realizar uma troca: " + lacaios.get(i));
							usado = true;
							lacaiosOponente.remove(j);
							lacaios.remove(i);
							i--;
							break;
						}
					}

				}
			}

			if(!usado){ // Caso o lacaio não tenha encontrado nenhuma trocar favorável, ele atacará o herói inimigo
				Jogada lacaio = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
				jogadas.add(lacaio);
				System.out.println("Jogada: Decidi uma jogada de atacar com o lacaio: " + lacaios.get(i));
			}
		}
	}


	private ArrayList<Jogada> modoAgressivo(int minhaMana){
		ArrayList<Jogada> jogadas = new ArrayList<>();
		// Neste modo, são colocados à mesa todos os lacaios, por ordem de maior ataque.
		// Depois, se ainda restar mana, são utilizadas as cartas de magia no herói inimigo. Por fim,
		// os lacaios que ja estão na mesa executam seus ataques ao inimigo.

		baixaLacaios(minhaMana, jogadas);

		// Após baixar todos os possíveis lacaios, os lacaios ja presentes na mesa serão utilizados para
		// atacar o herói inimigo
		for (Carta card : lacaios) {
			Jogada lac = new Jogada(TipoJogada.ATAQUE, card, null);
			jogadas.add(lac);
			System.out.println("Jogada: Decidi uma jogada de atacar com o lacaio: " + card);
		}
		return jogadas;
	}


	private ArrayList<Jogada> modoControle(int minhaMana){
		ArrayList<Jogada> jogadas = new ArrayList<>();
		// Nesse modo de jogo, objetiva-se ter controle sobre a mesa. Para isso, primeiro serão eliminados
		// o maior número de lacaios do oponente possível utilizando as magias (seguindo as regras descritas
		// na descrição da jogdada).

		// Primeiro, serão utilizadas as magias para eliminar os possíveis lacaios da mão do oponente
		for(int i =0; i < mao.size(); i++) { // Iterando sobre as cartas, para identificar as magias
			if (mao.get(i) instanceof CartaMagia) {

				if (((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.AREA && mao.get(i).getMana() <= minhaMana) {
					if (lacaiosOponente.size() >= 2) {
						Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(i), null);
						jogadas.add(mag);
						minhaMana -= mao.get(i).getMana();
						System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(i));


						for(int j=0; j<lacaiosOponente.size(); j++){ // Removendo os lacaios que serão eliminados com o
							// dano da magia em área
							if(lacaiosOponente.get(j).getVidaAtual() <= ((CartaMagia) mao.get(i)).getMagiaDano()){
								lacaiosOponente.remove(j);
								j--;
							}else{
								lacaiosOponente.get(j).setVidaAtual(lacaiosOponente.get(j).getVidaAtual() -
										((CartaMagia) mao.get(i)).getMagiaDano());
							}
						}
						mao.remove(i);
						i--;

					}

				} else if (((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.ALVO && mao.get(i).getMana() <= minhaMana) {
					CartaLacaio alvo = null;

					for (CartaLacaio cartaLacaio : lacaiosOponente) { // Para decidir em qual lacaio será utilizada a
						// magia de alvo, serão feitas algumas verificações, seguindo uma ordem de prioridade. A primeira
						// é o critério mínimo para utilização

						if (((CartaMagia) mao.get(i)).getMagiaDano() >= cartaLacaio.getVidaAtual() &&
								((CartaMagia) mao.get(i)).getMagiaDano() - cartaLacaio.getVidaAtual() <= 1) {

							if (alvo == null) {
								alvo = cartaLacaio;

							} else if (alvo.getVidaAtual() < cartaLacaio.getVidaAtual()) { //1: Será atacado o lacaio de maior ataque
								alvo = cartaLacaio;

							} else if (alvo.getVidaAtual() == cartaLacaio.getVidaAtual()) {

								if(alvo.getAtaque() < cartaLacaio.getAtaque()){//2: Será atacado o lacaio de maior vida
									alvo = cartaLacaio;

								}else if(alvo.getAtaque() == cartaLacaio.getAtaque()){

									if(alvo.getMana() < cartaLacaio.getMana()){//3: Será atacado o lacaio de maior custoMana
										alvo = cartaLacaio;
									}
								}
							}
						}
					}

					if(alvo != null) { // Ao fim da verificação, a magia apenas será usada se encontrar o alvo ideal.
						// Ela só será utilizada para atacar o herói inimigo caso não hajam mais lacaios possíveis de
						// serem baixados, que ocorrerá na função baixaLacaios.
						Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(i), alvo);
						jogadas.add(mag);
						minhaMana -= mao.get(i).getMana();
						System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(i));

						lacaiosOponente.remove(alvo);
						mao.remove(i);
						i--;

					}


				}
			}
		}


		// Após a utilização de todas as magias possíveis para a eliminação dos Lacaios inimigos, serão
		// baixados todos os lacaios possíveis, utilizando as magias restantes para atacar o herói do oponente
		// e as magias de buff para buffar os lacaios da mesa.

		baixaLacaios(minhaMana, jogadas);

		// Após baixar todos os lacaios, serão realizadas as trocas favoráveis com os lacaios ja presentes
		// na mesa. Aqueles lacaios que não encontrarem trocas favoráveis atacarão o herói inimigo.
		trocasFavoraveis(jogadas);

		return jogadas;
	}


	private ArrayList<Jogada> modoCurvaMana(int minhaMana){
		ArrayList<Jogada> jogadas = new ArrayList<>();

		boolean finalizado = false;

		for(int i=0; i < mao.size(); i++){
			if(mao.get(i) instanceof CartaLacaio && mao.get(i).getMana() == minhaMana && lacaios.size() < 7){ // Caso haja um lacaio com custo
				// de mana exato, ele será o único lacaio baixado.
				Jogada lac = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
				jogadas.add(lac);
				minhaMana -= mao.get(i).getMana();
				System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));
				mao.remove(i);
				break;

			}else if(mao.get(i) instanceof CartaLacaio){ // Caso não existam lacaios com custo de mana exato, é necessário
				// encontrar a combinação de dois lacaios ou lacaio e magia que somem a quantidade exata de mana. No
				// segundo caso, é necessário, também, que a magia cumpra alguns requisitos, a serem verificados. Como
				// as cartas estão organizadas por ordem de mana, e o segundo critério é o ataque, é garantido que a
				// primeira combinação exata encontrada será a combinação de mais alto ataque.


				for(int j=i + 1; j<mao.size(); j++){
					if(mao.get(i).getMana() + mao.get(j).getMana() == minhaMana) {
						if (mao.get(j) instanceof CartaLacaio && lacaios.size() <= 5) {

							// Dois lacaios somam a mana exata
							finalizado = true;

							Jogada lac1 = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
							jogadas.add(lac1);
							minhaMana -= mao.get(i).getMana();
							System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));



							Jogada lac2 = new Jogada(TipoJogada.LACAIO, mao.get(j), null);
							jogadas.add(lac2);
							minhaMana -= mao.get(j).getMana();
							System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(j));
							mao.remove(j);
							mao.remove(i);
							i--;
							break;

						}
						if (mao.get(j) instanceof CartaMagia) {
							if(((CartaMagia) mao.get(j)).getMagiaTipo() == TipoMagia.ALVO && lacaiosOponente.size() > 0){

								for(int k=0; k<lacaiosOponente.size(); k++){ // Fazendo as verificações para cada uma
									// das cartasLacaio do oponente em jogo

									if(lacaiosOponente.get(k).getMana() > mao.get(j).getMana() &&
									((CartaMagia) mao.get(j)).getMagiaDano() >= lacaiosOponente.get(k).getVidaAtual() &&
											((CartaMagia) mao.get(j)).getMagiaDano() - lacaiosOponente.get(k).getVidaAtual() <= 1){
										// Caso em que uma magia de alvo + lacaio tem o custo exato de mana, e a magia de alvo
										//cumpre os requisitos

										finalizado = true;
										Jogada lac1 = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
										jogadas.add(lac1);
										minhaMana -= mao.get(i).getMana();
										System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));


										Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(j), lacaiosOponente.get(k));
										jogadas.add(mag);
										minhaMana -= mao.get(j).getMana();
										System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(j));
										lacaiosOponente.remove(k);
										mao.remove(j);
										mao.remove(i);
										i--;
										j--;
										break;
									}
								}

							}else if(((CartaMagia) mao.get(j)).getMagiaTipo() == TipoMagia.AREA){

								if(lacaiosOponente.size() >= 2){
									boolean satisfeito = false;

									for (CartaLacaio cartaLacaio : lacaiosOponente) {

										if (((CartaMagia) mao.get(j)).getMagiaDano() >= cartaLacaio.getVidaAtual()) {
											satisfeito = true;
											break;
										}
									}
									if(satisfeito){
										for(int k=0; k< lacaiosOponente.size(); k++){
											if(((CartaMagia) mao.get(j)).getMagiaDano() >= lacaiosOponente.get(k).getVidaAtual()){
												lacaiosOponente.remove(k);
												k--;
											}else{
												lacaiosOponente.get(k).setVidaAtual(lacaiosOponente.get(k).getVidaAtual() - ((CartaMagia) mao.get(j)).getMagiaDano());
											}
										}


										finalizado = true;
										Jogada lac1 = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
										jogadas.add(lac1);
										minhaMana -= mao.get(i).getMana();
										System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));

										Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(j), null);
										jogadas.add(mag);
										minhaMana -= mao.get(j).getMana();
										System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(j));
										mao.remove(j);
										mao.remove(i);
										i--;
										break;
									}

								}


							}else if(((CartaMagia) mao.get(j)).getMagiaTipo() == TipoMagia.BUFF){
								if(lacaios.size() > 0){
									CartaLacaio bigger = lacaios.get(0);
									for(int k=1; k< lacaios.size(); k++){
										if(bigger.getAtaque() < lacaios.get(k).getAtaque()){
											bigger = lacaios.get(k);
										}
									}

									finalizado = true;
									Jogada lac1 = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
									jogadas.add(lac1);
									minhaMana -= mao.get(i).getMana();
									System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));


									Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(j), bigger);
									jogadas.add(mag);
									minhaMana -= mao.get(j).getMana();
									System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(j));
									mao.remove(j);
									mao.remove(i);
									i--;
									break;

								}
							}


							if(finalizado){
								break;
							}
						}
					}
				}
				if(finalizado){
					break;
				}
			}
		}

		if(!finalizado){ // Caso em que nenhum custo de mana exato foi encontrado. Para esse caso, será baixado
			// os lacaios e magias de maior custo de mana. Caso ainda sobre mana, a magia de herói será utilizada
			// no herói inimigo
			int qtd = lacaios.size();
			for(int i=0; i< mao.size(); i++){

				if(mao.get(i).getMana() < minhaMana) {

					if (mao.get(i) instanceof CartaLacaio && qtd < 7) {
						Jogada lac1 = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
						jogadas.add(lac1);
						minhaMana -= mao.get(i).getMana();
						System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: " + mao.get(i));
						mao.remove(i);
						i--;
						qtd++;

					} else if(mao.get(i) instanceof CartaMagia){

						if (((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.AREA && lacaiosOponente.size() >= 1) {
							Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(i), null);
							jogadas.add(mag);
							minhaMana -= mao.get(i).getMana();
							System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(i));


							for (int j = 0; j < lacaiosOponente.size(); j++) {
								if (lacaiosOponente.get(j).getVidaAtual() <= ((CartaMagia) mao.get(i)).getMagiaDano()) {
									lacaiosOponente.remove(j);
									j--;
								}
							}
							mao.remove(i);
							i--;


						} else if (((CartaMagia) mao.get(i)).getMagiaTipo() == TipoMagia.ALVO) {
							if(lacaiosOponente.size() > 0){
								CartaLacaio bigger = lacaiosOponente.get(0);
								for(CartaLacaio lac : lacaiosOponente){
									if(lac.getVidaAtual() <= ((CartaMagia) mao.get(i)).getMagiaDano() &&
									lac.getMana() > bigger.getMana()){
										bigger = lac;
									}
								}
								Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(i), bigger);
								jogadas.add(mag);
								minhaMana -= mao.get(i).getMana();
								System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(i));
								mao.remove(i);
								lacaiosOponente.remove(bigger);
								i--;

							}
						}else{
							if(lacaios.size() > 0){
								CartaLacaio bigger = lacaios.get(0);
								for(CartaLacaio lac : lacaios){
									if(lac.getAtaque() > bigger.getAtaque()){
										bigger = lac;
									}
								}
								Jogada mag = new Jogada(TipoJogada.MAGIA, mao.get(i), bigger);
								jogadas.add(mag);
								minhaMana -= mao.get(i).getMana();
								System.out.println("Jogada: Decidi uma jogada de usar uma magia: " + mao.get(i));
								mao.remove(i);
								i--;
							}
						}
					}
				}
			}
		}

		if(minhaMana >= 2){ // Caso em que nem toda a mana foi utilizada. Nesse caso, a magia de herói será
			// utilizada para atacar o oponente inimigo
			Jogada pod = new Jogada(TipoJogada.PODER, null, null);
			jogadas.add(pod);
			minhaMana -= 2;
		}

		// Por fim, serão verificadas as trocas favoráveis. Aqueles que não puderam realiza-las atacarão o herói
		//inimigo.
		trocasFavoraveis(jogadas);

		return jogadas;
	}
}
