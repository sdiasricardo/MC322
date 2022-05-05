package base;

public class Magia extends Carta{
	private String descricao;
	
	public Magia(String descricao, String nome, int custoMana) {
		super(nome, custoMana);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	 public String toString() {
		String out = super.toString();
		out = out + this.descricao;
		return out;
	}
	
}
