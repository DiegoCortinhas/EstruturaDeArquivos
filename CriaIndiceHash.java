package indice;

import java.io.*;
import java.util.*;
import java.util.Collections;

public class CriaIndiceHash {
	
	public static void main(String args[]) throws Exception
	{
				
		String linha, nis;
		String colunas[];
		long posicao;	
		RandomAccessFile f = new RandomAccessFile("bolsa.csv", "r");
		ArrayList<ElementoIndice> ind = new ArrayList<ElementoIndice>();
		f.readLine();
		ElementoIndice e = new ElementoIndice();
		long cont = 0; 
		
		while(f.getFilePointer() < f.length())
		{
			posicao = f.getFilePointer();
			linha = f.readLine();
			colunas = linha.split("\t");
			e.nis = colunas[7];
			e.posicao = posicao;
			ind.add(e);
			cont++;
			
			
			//para teste
			if (cont%10000==0) {
				System.out.print(".");
				System.out.flush();
			}
			
			// System.out.println("NIS => " + e.getNis() + " esta na posicao " + posicao);
		}
		
		f.close();
		
		Collections.sort(ind,new ComparaNis()); 
		
		RandomAccessFile f1 = new RandomAccessFile("indicebolsaordenado.ind", "rw");
		
		// System.out.println("teste");
		
		for (int i =0; i<ind.size(); i++){
				
			ElementoIndice elemento = ind.get(i);
			elemento.escreve(f1);
		}
		
		f1.close();
		// System.out.println("teste1");
		
		Scanner x = new Scanner(System.in);
		System.out.println ("Insira o NIS: ");
		String y = x.nextLine();
		ElementoIndice entrada = new ElementoIndice();
		entrada.setNis(y);
		
		ElementoIndice learquivo = new ElementoIndice();
		
		RandomAccessFile f2 = new RandomAccessFile("indicebolsaordenado.ind", "r");
		
		learquivo.le(f2);
		
		long tamanhoRegistro = f2.getFilePointer();  // tamanho de 1 registro
		
		long inicio = 0;
		long tamanho = f2.length(); 
		long ultimo=(tamanho/tamanhoRegistro)-1;  //quantidade de registros
		long meio;  
		
		
		ElementoIndice elemento2 = new ElementoIndice();
		
		while(inicio <= ultimo)
		{
					
			meio = (ultimo + inicio)/2;			// meio arquivo bolsaindiceordenado
			
			f2.seek(meio*tamanhoRegistro);		//move a cabeça de leitura para o meio;
			
			elemento2.le(f2);
			
			
			ComparaNis comparaElemento = new ComparaNis(); 
			
				
			if (comparaElemento.compare(entrada,elemento2) == 0){
				
				
				RandomAccessFile f3 = new RandomAccessFile("bolsa.csv", "r"); //abrir arquivo csv 
				f3.seek(elemento2.posicao);  // mover cabeca de leitura para posicao do nis
				linha = f3.readLine();
				colunas = linha.split("\t");
				System.out.println("O Nome do favorecido é: %s\t" + colunas[8] + " e seu NIS é: %s\t" + colunas[7]);
				
				break;
			}
			
			else{
			
				if (comparaElemento.compare(entrada,elemento2) < 0){
				ultimo = meio - 1;
				}
				
				else{
				inicio = meio + 1;
				}
			}
		}
		
		
	}			
}		