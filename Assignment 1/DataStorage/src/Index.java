import java.io.IOException;
import java.io.RandomAccessFile;

public class Index {
	public int product_id;
	public int offset;
	public int index_offset;
	private final static String fileName = "PrimaryIndex.bin";

	Index() {

	}

	public void writeIndex(long pointer) throws IOException // write the index
	{
		RandomAccessFile index_file = new RandomAccessFile(fileName, "rw");
		this.index_offset = (int) pointer;
		index_file.seek(pointer);
		index_file.writeInt(this.product_id);
		index_file.writeInt(this.offset);
		index_file.close();
	}

	public void readIndex(long pointer) throws IOException {
		RandomAccessFile index_file = new RandomAccessFile(fileName, "r");
		index_file.seek(pointer);
		this.index_offset = (int) pointer;
		this.product_id = index_file.readInt();
		this.offset = index_file.readInt();
		index_file.close();

	}

	public Index readRandomIndex() throws IOException {
		RandomAccessFile index_file = new RandomAccessFile(fileName, "r");
		Index index = new Index();
		index.setOffset((int) index_file.getFilePointer());
		index.setProduct_id(index_file.readInt());
		index.setOffset(index_file.readInt());
		index_file.close();
		return index;

	}

	public static void readAllIndex() throws IOException {
		RandomAccessFile index_file = new RandomAccessFile(fileName, "r");
		long len = index_file.length();
		index_file.seek(len);
		long eof = index_file.getFilePointer();
		index_file.seek(0);
		while (index_file.getFilePointer() != eof) {
			System.out.println("----------------------");
			System.out.println("ProductID: " + index_file.readInt());
			System.out.println("Offset: " + index_file.readInt());
		}
		index_file.close();
	}

	public static int binarySearch(int x) throws IOException {
		
		RandomAccessFile index_file = new RandomAccessFile(fileName, "r");
		int index_file_size = (int) index_file.length();
		index_file.seek(index_file_size);
		index_file.close();
		
		Index index = new Index();
		int l = 0, r = index_file_size / 8;
		while (l <= r) {
			int m = l + (r - l) / 2;
			index.readIndex(m * 8);

			if (index.getProduct_id() == x)
				return index.getOffset();

			if (index.getProduct_id() < x)
				l = m + 1;

			else
				r = m - 1;
		}
		return -1;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getIndex_offset() {
		return index_offset;
	}

	public void setIndex_offset(int index_offset) {
		this.index_offset = index_offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return this.offset;
	}
}
