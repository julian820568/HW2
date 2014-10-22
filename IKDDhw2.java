import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.sql.*;

public class IKDDhw2 {
	public static void main(String[] args) throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String query_str = new String( "'"+args[0]+"'" );
		long [] id = new long [15];
		String [] text = new String [15];
		String [] name = new String [15];
		
		// TODO Auto-generated method stub
		Class.forName("org.postgresql.Driver");
		Connection connect = DriverManager.getConnection( "jdbc:postgresql://iservdb.cloudopenlab.org.tw:5432/julian820568jp_db_8009",
				"julian820568jp_user_8009", "Ip8DFLxg" );
		/*, "julian820568jp@gmail.com","d7ttd7tt"*/
		
		String SQL = "select * from twitter where q = " + query_str;
		Statement stmt = connect.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		
		//循環搜尋結果，請依你的資料庫來作提取資料欄位，以將下面的資料欄位對映你的資料欄位
		int i = 0;
		while ( rs.next() ) {
			id[i] =  Long.parseLong( rs.getString("user_id") );
			text[i] = rs.getString("text");
			name[i++] = rs.getString("user_name");
		}
		rs.close();
		stmt.close();
		
		Sort( id, text, name );
		
		String [] headings= new String[] { "text", "user_name", "user_id" };
		Object[][] data = new Object[100][3];
		i = 0;
		int tmp = 0;
		for( i = 0; i < id.length; i++ )
			if( id[i] != 0 ) {
				tmp = i;
				i = id.length;
			}
		for( int oj = 0; oj < 15 && tmp < 15; oj++ ) {
			data[oj][0] = text[tmp];
			data[oj][1] = new String( name[tmp] );
			data[oj][2] = new String( String.valueOf(id[tmp++]) );
		}
		
		System.out.println( data[1][1] );
 // Step 3: 建立 Table
		javax.swing.JTable table=new javax.swing.JTable(data,headings);
 // 建立一個 Frame 秀出表格
		javax.swing.JFrame MyFrame=new javax.swing.JFrame("TableStep1 表格測試");
		MyFrame.setSize(500,200);
		MyFrame.setLocation(200,200);
		MyFrame.getContentPane().add(new javax.swing.JScrollPane(table));
		MyFrame.setVisible(true);
	}
	
	public static void Sort( long[] array, String[] textarr, String[] namearr ) {
		for (int i = array.length - 1; i > 0; --i)
			for ( int j = 0; j < i; ++j )
				if (array[j] > array[j + 1]) {
					Swap( array, j, j + 1 );
					Swap( textarr, j, j + 1 );
					Swap( namearr, j, j + 1 );
                }
    }
 
	private static void Swap( long[] array, int indexA, int indexB) {
		long tmp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = tmp;
	}
	
	private static void Swap( String[] array, int indexA, int indexB) {
		String tmp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = tmp;
	}
}
