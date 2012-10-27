package original;

public class ObjectForMergeSort implements Comparable, java.io.Serializable {

	public String name;

	public ObjectForMergeSort(String name){
		this.name = name;
	}

	public int compareTo(Object o) {
		ObjectForMergeSort param = (ObjectForMergeSort) o;
		return (this.name).compareTo(param.name);
	}

}
