package base;
public class Lacaio extends Carta{
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	
	public Lacaio(String nome, int ataque, int vida, int mana) {
		super(nome, mana);
		this.ataque = ataque;
		this.vidaAtual = vida;
		this.vidaMaxima = vida;
		
	}
	
	
	public Lacaio(String nome, int mana) {
		super(nome, mana);
	}
	
	
	public Lacaio(Lacaio origem) {
		super(origem.getId(), origem.getNome(), origem.getCustoMana());
		this.ataque = origem.getAtaque();
		this.vidaAtual = origem.getVidaAtual();
		this.vidaMaxima = origem.getVidaMaxima();
	}


	public int getAtaque() {
		return ataque;
	}


	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}


	public int getVidaAtual() {
		return vidaAtual;
	}


	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}


	public int getVidaMaxima() {
		return vidaMaxima;
	}


	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public void usar(Carta alvo) {
		Lacaio alvoaux = (Lacaio) alvo;
		alvoaux.setVidaAtual(alvoaux.getVidaAtual() - this.getAtaque());
	}
	
@Override
	public String toString() {
		String out = super.toString();
		out = out + "Ataque = "+getAtaque()+"\n";
		out = out + "Vida Atual = "+getVidaAtual()+"\n";
		out = out + "VidaMaxima = "+getVidaMaxima()+"\n";
		return out;
		
		
	}

	
	
	
}
