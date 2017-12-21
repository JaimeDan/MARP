package codigo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class ProblemaMochila {
	
	//private objeto[] objetos;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long nuevaCapacidad = 300;
		FileWriter quickS = null;
		FileWriter quickSel = null;
		FileWriter heapS =  null;
		try {
			quickS = new FileWriter("quicksort.txt");
			quickSel = new FileWriter("quickSelect.txt");
			heapS = new FileWriter("heapSort.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter out_QS = new BufferedWriter(quickS);
		BufferedWriter out_QSel = new BufferedWriter(quickSel);
		BufferedWriter out_HS = new BufferedWriter(heapS);
		
		
		int ganaQuick=0, ganaSelect=0, ganaHeap = 0;
		// TODO Auto-generated method stub
		for (int j=0, capa=0; j<1; j+=3, capa+=300){
			for (int k=1, objes = 100; k<=1; k+=3, objes += 300){
				Mochila prob = new Mochila(nuevaCapacidad);
				for (int i=0; i<objes; i++)
				{
					double peso = Math.floor(Math.random()*100) + 1;
					double valor = Math.floor(Math.random()*50);
					//System.out.println("Peso: " + peso + ", Valor: " + valor + ", densidad: " + valor/peso);
					prob.add(peso, valor);
				}
				Mochila copia = (Mochila)prob.clone();
				Mochila.print(copia.utilizaQuicksort());
				Mochila quick = (Mochila) prob.clone();
				//quick.utilizaQuicksort();
				System.out.println("Resultado quicksort: " + quick.getTime());
				
				//Mochila.print(quick.utilizaQuicksort());

				Mochila select = (Mochila) prob.clone();
				select.utilizaQuickSelect();
				System.out.println("Resultado quick select: " + select.getTime());
				Mochila.print(select.utilizaQuickSelect());

				Mochila heap = (Mochila) prob.clone();
				heap.utilizaHeapsort();
				System.out.println("Resultado heap sort: " + heap.getTime());
				Mochila.print(heap.utilizaHeapsort());
				if (quick.getTime() <= select.getTime() && quick.getTime() <= heap.getTime()){
					System.out.println("Gana quicksort");
					ganaQuick++;
				}
				else if (select.getTime() <= quick.getTime() && select.getTime() <= heap.getTime()){
					System.out.println("Gana quickselect");
					ganaSelect++;
				}
				else if (heap.getTime() <= quick.getTime() && heap.getTime() <= quick.getTime()){
					System.out.println("Gana heapsort");
					ganaHeap++;
				}
				System.out.println("Se han usado " + (objes-1)  + "objetos. ");
				try {
					out_QS.write((quick.getTime() + "\n"));
					out_QSel.write((select.getTime() + "\n"));
					out_HS.write((heap.getTime() + "\n"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} 
		System.out.println("Gana quicksort: " + ganaQuick + " Gana Quickselect " + ganaSelect + " Gana heapsort" + ganaHeap);
		System.out.println("Capacidad mochila: " + nuevaCapacidad);
		try {
			out_QS.close();
			out_QSel.close();
			out_HS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
