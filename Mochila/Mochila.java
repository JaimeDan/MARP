package codigo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;;

public class Mochila implements Cloneable{
	
	private Vector<Objeto> mochila;
	private double capacidad;
	private ArrayList<Objeto>	 menores;
	
	private long t0;
	private long t1;
	
	public Mochila(double capacidad){
		this.mochila = new Vector<Objeto>();
		this.capacidad = capacidad;
	}
	
	public void add(Objeto o){
		this.mochila.add(o);
	}
	
	public void add(double peso, double valor){
		Objeto cosa = new Objeto(peso, valor);
		this.mochila.add(cosa);
		//this.mochila.add(new Objeto(peso, valor));
	}
	
	public int capacity(){
		return this.mochila.capacity();
	}

	public static void print(Vector<Objeto> mochila){
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i=0; i< mochila.size(); i++){
			System.out.println("Peso: " + mochila.elementAt(i).peso + ", Valor: "
						+ mochila.elementAt(i).valor
						+ ", Densidad valor/peso: " + df.format(mochila.elementAt(i).densidad));
		}
	}
	
	public Vector<Objeto> utilizaQuicksort(){
		Vector<Objeto> respuesta = new Vector<Objeto>();
		//System.out.println("Vector sin ordenar: ");
		//print(this.mochila);
		this.t0 = System.nanoTime();
		this.quicksort(0, this.mochila.size()-1);
		this.t1 = System.nanoTime();
		//System.out.println("Valores iniciales: ");
		//print(this.mochila);
		//System.out.println("Vale. ");
		double sumaPesos = 0;
		int j= mochila.size() -1;
		for ( ; j>=0; j--){
			//sumaCapacidades += mochila.elementAt(j).peso;
			if (sumaPesos + mochila.elementAt(j).peso <= this.capacidad){
				sumaPesos += mochila.elementAt(j).peso;
				respuesta.add(mochila.elementAt(j));
			}
			else break;
		}
		if (sumaPesos != this.capacidad && j>= 0){
			double nuevoValor = mochila.elementAt(j).valor*(this.capacidad - sumaPesos)/mochila.elementAt(j).peso;
			respuesta.add(new Objeto(this.capacidad-sumaPesos, nuevoValor));
		}
		//this.t1 = System.nanoTime();
		return respuesta;

	}
	
	public void quicksort(int izq, int der) {

		if (izq < der){
			int pivote= particion(izq, der);
			quicksort(izq, pivote-1);
			quicksort(pivote+1, der);
		}
	}

	public ArrayList<Objeto> utilizaQuickSelect(){
		//this.quickSelect(0, this.mochila.size()-1, this.mochila.size()/2);
		ArrayList<Objeto> mochila2 = new ArrayList<Objeto>(mochila);
		this.t0 = System.nanoTime();
		ArrayList<Objeto> respuesta = quickSelect(mochila2, capacidad);
		this.t1 = System.nanoTime();
		double ocupado = 0;
		for (Objeto obj: respuesta )ocupado += obj.peso;
		if (ocupado < capacidad){
			Objeto objFinal = new Objeto(1, 0);
			for (Objeto obj: this.menores){
				if (obj.densidad > objFinal.densidad)
					objFinal = obj;
			}
			Objeto addObj = new Objeto(capacidad-ocupado, objFinal.valor*(capacidad-ocupado)/objFinal.peso);
			respuesta.add(addObj);
		}
		//this.t1 = System.nanoTime();
		return respuesta;//this.quickSelect(mochila, capacidad);
	}
	
	public double quickSelect(int izq, int der, int k){
		int pivote = particion(izq, der);
		if (k < pivote-izq+1)
			return quickSelect(izq, pivote, k);
		else if(k > pivote - izq +1)
			return quickSelect(pivote+1, der, k-pivote);
		else return mochila.elementAt(k).densidad;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Objeto> quickSelect(ArrayList<Objeto> objetos, double capacidad){
		//return null;
		ArrayList<Objeto> respuesta = new ArrayList<Objeto>();
		if (!objetos.isEmpty()){
			Objeto pivote = objetos.get(0);
			double pesoMayores = 0;
			double pesoIguales = 0;
			double pesoAuxiliar = 0;
			double pesoOcupado = 0;
			ArrayList<Objeto> mayores = new ArrayList<Objeto>();
			ArrayList<Objeto> iguales = new ArrayList<Objeto>();
			ArrayList<Objeto> menores = new ArrayList<Objeto>();
			for(Objeto obs: objetos){
				if (obs.densidad == pivote.densidad){
					iguales.add(obs);
					pesoIguales += obs.peso;
				}
				else if (obs.densidad > pivote.densidad){
					mayores.add(obs);
					pesoMayores += obs.peso;
				}
				else menores.add(obs);
			}
			this.menores = menores;
			if (pesoMayores > capacidad)
				return quickSelect(mayores, capacidad);
			else {
				for (Objeto obj: iguales){
					if (pesoOcupado + obj.peso + pesoMayores<= capacidad){
						respuesta.add(obj);
						pesoOcupado += obj.peso;
					}
					/*else{
					pesoAuxiliar = obj.peso;
					break;
				}*/
				}
				//se han gastado todos los iguales y aun queda espacio
				if (pesoOcupado == pesoIguales && pesoOcupado + pesoMayores < capacidad){
					//Entonces se busca entre los menores
					if (!mayores.isEmpty())
					respuesta.addAll(mayores);
					if (!menores.isEmpty())
					respuesta.addAll(quickSelect(menores, capacidad-pesoMayores-pesoOcupado));
					return respuesta;
				}
				else {
					respuesta.addAll(mayores);
					return respuesta;
				}
				/*int j= 0; 
			while ((j < iguales.size()) && (pesoOcupado + iguales.elementAt(j).peso <= capacidad)){
				respuesta.add(iguales.elementAt(j));
				j++;
			}*/
				//for (int i=0; i< iguales.size()-1 && (pesoMayores+pesoOcupado <= capacidad); i++)
				/*if (pesoOcupado + pesoMayores <= capacidad && pesoOcupado + pesoMayores + pesoAuxiliar > capacidad && pesoAuxiliar != 0){
				respuesta.addAll(mayores);
				return respuesta;
			}
			else {
				respuesta.addAll(mayores);
				if (capacidad-pesoMayores-pesoOcupado >= 0)
				respuesta.addAll(quickSelect(menores, capacidad-pesoMayores- pesoOcupado));
				return respuesta;
			}*/
			}
		}else return null;
		//return null;
	}
	
	public int particion(int izq, int der){
		double x = mochila.elementAt(der).densidad;
		int pivote= izq-1;
		for (int j=izq; j < der; j++)
		{
			if (mochila.elementAt(j).densidad <= x){
				pivote=pivote+1;
				swap(pivote, j);
			}
		}swap(pivote+1, der);
		return pivote+1;
	}
	
	public void swap(int i, int j){
		Objeto temp = mochila.elementAt(i);
		mochila.setElementAt(mochila.elementAt(j), i);
		mochila.setElementAt(temp, j);
	}

	public Vector<Objeto> utilizaHeapsort(){
		this.t0 = System.nanoTime();
		PriorityQueue<Objeto> monticulo = heapSort();
		Vector<Objeto> objetos = new Vector<Objeto>();
		Objeto cosa;
		int i=0;
		double peso = 0;
		for ( ; i<monticulo.size() && peso < this.capacidad ; i++){
			cosa = monticulo.poll();
			objetos.add(cosa);
			peso += cosa.peso;
		}
		if (i<monticulo.size() -1) {
			cosa = new Objeto(this.capacidad - peso, monticulo.peek().valor*(this.capacidad - peso)/peso);
			objetos.add(cosa);
		}
		this.t1 = System.nanoTime();
		//System.out.println(this.t1-this.t0);
		return objetos;
	}
	
	public PriorityQueue<Objeto> heapSort(){
		PriorityQueue<Objeto> monticulo = new PriorityQueue<Objeto>();
		for (Objeto obj: this.mochila)
			monticulo.add(obj);
		return monticulo;
	}
	
	public long getTime(){
		return this.t1 - this.t0;
	}
	
	@Override
	public Object clone(){
		Object obj=null;
		try{
			obj=super.clone();
		}catch(CloneNotSupportedException ex){
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}

	public static void print(ArrayList<Objeto> utilizaQuickSelect) {
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i=0; i< utilizaQuickSelect.size(); i++){
			System.out.println("Peso: " + utilizaQuickSelect.get(i).peso + ", Valor: "
						+ utilizaQuickSelect.get(i).valor
						+ ", Densidad valor/peso: " + df.format(utilizaQuickSelect.get(i).densidad));
		}
	}


}
