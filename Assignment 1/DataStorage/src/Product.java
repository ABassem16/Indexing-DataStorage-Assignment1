import java.io.IOException;
import java.io.RandomAccessFile;

public class Product {
	private int id;
	private int price;
	private int quantity;
	private int offset;
	private final static String fileName = "SampleDataFile.bin";
	Product()
	{
		
	}
	public static void printProduct(Product p)
	{
		System.out.println("ProductID: "+p.id);
		System.out.println("ProductPrice: "+p.price);
		System.out.println("ProductQuantity: "+p.quantity);
		System.out.println("------------------------------");
	}
	public static void readAllData() throws IOException //Prints all data in the data file
	{
		RandomAccessFile data_file = new RandomAccessFile(fileName,"r");
		long len1 = data_file.length();
		data_file.seek(len1);
		long eof = data_file.getFilePointer();
		data_file.seek(0);
		while(data_file.getFilePointer() != eof)
		{
			System.out.println("ProductPointer: "+data_file.getFilePointer());
			System.out.println("ProductID: "+data_file.readInt());
			System.out.println("ProductPrice: "+data_file.readInt());
			System.out.println("ProductQuantity: "+data_file.readInt());
			System.out.println("------------------------------");
		}
		data_file.close();
	}
	public void readProduct(long pointer) throws IOException //read a certain product with address(offset) known 
	{
		RandomAccessFile data_file = new RandomAccessFile(fileName,"r");
		data_file.seek(pointer);
		this.offset = (int) pointer;
		this.id = data_file.readInt();
		this.price = data_file.readInt();
		this.quantity = data_file.readInt();
		data_file.close();
	}
	public Product readRandomProduct() throws IOException // read a random product with address(offset) is unknown
	{
		RandomAccessFile data_file = new RandomAccessFile(fileName,"r");
		Product p = new Product();
		p.setId(data_file.readInt());
		p.setPrice(data_file.readInt());
		p.setQuantity(data_file.readInt());
		data_file.close();
		return p;
		
		
	}
	public static void writeRandomProduct(Product p) throws IOException //write a random product at the end of file
	{
		RandomAccessFile data_file = new RandomAccessFile(fileName,"r");
		data_file.seek(data_file.length());
		p.setOffset((int)data_file.getFilePointer());
		data_file.writeInt(p.getId());
		data_file.writeInt(p.getPrice());
		data_file.writeInt(p.getQuantity());
		data_file.close();
	
		
	}
	public void writeProduct() throws IOException //write a product at the end of file
	{
		RandomAccessFile data_file = new RandomAccessFile(fileName,"r");
		data_file.seek(data_file.length());
		this.offset = (int) data_file.getFilePointer();
		data_file.writeInt(this.id);
		data_file.writeInt(this.price);
		data_file.writeInt(this.quantity);
		data_file.close();
	}
	
	
	public int getOffset() {
		return offset;
	}
	private void setOffset(int offset) {
		this.offset = offset;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
