package original;

public class MergeSortAgent {

	//global vars declaration
	public static final String [] chars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	public static void order(Comparable [] v) {

		//local vars declaration
		int half = v.length / 2 + 1;
		Object [] leftV = new Object[half];
		Object [] rightV = new Object[half];

		mergeSort(v, 0, v.length - 1, leftV, rightV);
	}

	public static void mergeSort(Comparable [] v, int l, int h, Object [] leftV, Object [] rightV) {

		//local vars declaration
		int middle;
		int left;
		int right;
		int sizeL;
		int sizeR;

		if (l >= h)
			return;

		middle = (l + h) / 2;
		mergeSort(v, l, middle, leftV, rightV);
		mergeSort(v, middle + 1, h, leftV, rightV);
		left = 0;
		right = 0;
		sizeL = middle - l + 1;
		sizeR = h - middle;
		System.arraycopy(v, l, leftV, 0, sizeL);
		System.arraycopy(v, middle + 1, rightV, 0, sizeR);

		move();

		while ((left < sizeL) && (right < sizeR)){
			if(((Comparable) leftV[left]).compareTo(rightV[right]) <= 0)
				v[l++] = (Comparable) leftV[left++];
			else
				v[l++] = (Comparable) rightV[right++];
		}

		while (left < sizeL)
			v[l++] = (Comparable) leftV[left++];

		while (right < sizeR)
			v[l++] = (Comparable) rightV[right++];
	}

	public static void main(String [] args){

		//local vars declaration
		ObjectForMergeSort [] v;
		int forIndex;
		int charIndex;
		String result = "";

		//create a random array
		v = new ObjectForMergeSort[50];
		for(forIndex = 0; forIndex < v.length; forIndex++){
			charIndex = (int)(Math.random() * 26);
			v[forIndex] = new ObjectForMergeSort(chars[charIndex]);
		}

		result += "NOT ORDERED ARRAY:\n";
		for(forIndex = 0; forIndex < v.length; forIndex++){
			result += v[forIndex].name + " ";
		}

		order(v);

		result += "\nORDERED ARRAY:\n";
		for(forIndex = 0; forIndex < v.length; forIndex++){
			result += v[forIndex].name + " ";
		}

		System.out.println(result);
	}

}
