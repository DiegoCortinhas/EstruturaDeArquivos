package indice;
import java.util.*;
import java.io.DataOutput;
import java.io.*;

public class ElementoIndice {
	
	public String nis;
	public long posicao;
	
	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}
	
	public long getPosicao() {
		return posicao;
	}

	public void setPosicao(long posicao) {
		this.posicao = posicao;
	}
	

	void escreve (DataOutput out) throws IOException{
	out.writeUTF(this.nis);
	out.writeLong(this.posicao);
	}

	
	void le (DataInput in) throws IOException{
	this.nis=in.readUTF();
	this.posicao=in.readLong();
	}
		
}