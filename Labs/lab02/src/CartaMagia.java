public class CartaMagia {
	private int ID;
	public String nome;
	private int dano;
	private boolean area;
	private int custoMana;
	
	public CartaMagia(int ID, String nome, int dano, boolean area, int custoMana) {
		this.ID = ID;
		this.nome = nome;
		this.dano = dano;
		this.area = area;
		this.custoMana = custoMana;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public int getDano() {
		return dano;
	}
	
	
	public void setDano(int dano) {
		this.dano = dano;
	}
	
	
	public boolean isArea() {
		return area;
	}
	
	
	public void setArea(boolean area) {
		this.area = area;
	}
	
	
	public int getCustoMana() {
		return custoMana;
	}
	
	
	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	
@Override
	public String toString() {
		String out = getNome() + " (ID: "+getID()+")\n";
		out = out + "Nome = " + getNome()+"\n";
		out = out + "Dano = "+getDano()+"\n";
		out = out + "Area = "+isArea()+"\n";
		out = out + "Custo de Mana = "+getCustoMana()+"\n";
		return out;
	}

	
}
