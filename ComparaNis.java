package indice;
import java.util.Comparator;


public class ComparaNis implements Comparator<ElementoIndice> {

	public int compare (ElementoIndice e, ElementoIndice e1){
		return e.getNis().compareTo(e1.getNis());	
	}


}
