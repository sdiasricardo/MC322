package base;
import java.util.UUID;

public class Carta {
	private UUID id;
	private String nome;
	private int custoMana;
	
	public Carta(String nome, int custoMana){
		this.id = UUID.randomUUID();
		this.custoMana = custoMana;
		this.nome = nome;
	}
	
	public Carta(UUID id, String nome, int custoMana){
		this.id = id;
		this.custoMana = custoMana;
		this.nome = nome;
	}
	
	
	public UUID getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCustoMana() {
		return custoMana;
	}
	
	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	public void usar(Carta alvo) {
		
	}
	
	
	@Override
	public String toString() {
		
		return "Nome: " + nome + "\n" + "ID: " + this.getId() + "\n" + "Custo de Mana: " + 
				this.getCustoMana() + "\n";
		
	}
	
	
}
