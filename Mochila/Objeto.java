package codigo;

public class Objeto implements Comparable<Objeto>{
	
	public double peso; 
	public double valor;
	public double densidad;
	
	public Objeto(double peso, double valor){
		this.peso = peso;
		this.valor = valor;
		this.densidad = valor/peso;
	}
	/**
	 * 
	 * @param objeto con el que se quiere comparar
	 * @return true si la densidad es mayor o igual, false en caso contrario
	 */
	public boolean comparaCon(Objeto obj){
		return this.densidad >= obj.densidad;
	}
	@Override
	public int compareTo(Objeto o) {
		if (this.densidad < o.densidad) return 1;
		else if (this.densidad == o.densidad) return 0;
		else return -1;
	}
	
	
	
	
}
