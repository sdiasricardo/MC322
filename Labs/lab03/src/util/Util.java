package util;

import base.CartaLacaio;

public class Util {
	public static void alteraNomeFortalecido(CartaLacaio lac) {
		lac.setNome(lac.getNome() + " Buffed");
	}
	
	
	public static void buffar(CartaLacaio lac, int a) {
		if(a > 0) {
			lac.setVidaAtual(lac.getVidaAtual() + a);
			lac.setVidaMaxima(lac.getVidaMaxima() + a);
			alteraNomeFortalecido(lac);
		}
	}
	
	public static void buffar(CartaLacaio lac, int a, int v) {

		if(a > 0) {
			lac.setVidaAtual(lac.getVidaAtual() + a);
		}
		
		if(v > 0) {
			lac.setVidaMaxima(lac.getVidaMaxima() + v);
		}
		
		if(lac.getVidaAtual() > lac.getVidaMaxima())
			lac.setVidaAtual(lac.getVidaMaxima());
		
		if(a > 0 || v > 0) {
			alteraNomeFortalecido(lac);
		}
		
	}
}
