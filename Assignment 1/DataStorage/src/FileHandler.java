import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class FileHandler {
	private final static String IndexFileName = "PrimaryIndex.bin";
	private final static String DataFileName = "SampleDataFile.bin";
	private int data_file_size = 0;
	private static int index_file_size = 0;
	private static int data_file_eof = 0;
	private int index_file_eof = 0;

	FileHandler() throws IOException {
		RandomAccessFile data_file = new RandomAccessFile(DataFileName, "r");
		RandomAccessFile index_file = new RandomAccessFile(IndexFileName, "rw");

		this.data_file_size = (int) data_file.length();
		data_file.seek(data_file_size);
		FileHandler.data_file_eof = (int) data_file.getFilePointer();

		FileHandler.index_file_size = (int) index_file.length();
		index_file.seek(index_file_size);
		this.index_file_eof = (int) index_file.getFilePointer();

		data_file.close();
		index_file.close();
	}

	public void constructIndexFile() throws IOException {
		long pointer = 0;
		Product product = new Product();
		Index index = new Index();
		Map<Integer, Integer> map = new HashMap<>();
		while (pointer != data_file_eof) {
			product.readProduct(pointer);
			pointer = pointer + 12;
			map.put(product.getId(), product.getOffset());
		}
		Map<Integer, Integer> indexMap = new TreeMap<Integer, Integer>(map);
		System.out.println(indexMap);
		pointer = 0;
		
		for (Map.Entry<Integer, Integer> entry : indexMap.entrySet()) {
			
			index.setProduct_id(entry.getKey());
			index.setOffset(entry.getValue());
			index.writeIndex(pointer);
			pointer = pointer + 8;
		}
		Index.readAllIndex();

	}
	public Product searchProduct(int p_id) throws IOException
	{
		Product p = new Product();
		int offset = Index.binarySearch(p_id);
		p.readProduct(offset);
		
		return p;
	}
	public void addProduct(Product p)
	{
		
	}
	public void UpdateProduct(int p_id) throws IOException
	{
		RandomAccessFile data_file = new RandomAccessFile(DataFileName ,"rws"); 
		Product p=new Product();
		int offset=Index.binarySearch(p_id);
		if(offset==-1)
		{
			System.out.println("Cannot Find Product");
		}
		else
		{
		p.readProduct(offset);
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter new Price and Quantity for Product Number: " + p.getId());
		int p_price=scan.nextInt();
		int p_qua=scan.nextInt();
		p.setPrice(p_price);
		p.setQuantity(p_qua);
		data_file.seek(offset+4);
		data_file.writeInt(p_price);
		data_file.writeInt(p_qua);
		scan.close();
		data_file.close();
		}
	}
	  
}
