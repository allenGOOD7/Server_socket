/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
 @author allen
 **/


package javaapplication2;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.swing.Timer;

public class NewJFrame extends javax.swing.JFrame 
{
    static ServerSocket ss;
    static InputStreamReader isr;
    static BufferedReader br;
    static String message;
    static PrintWriter pw;



    public static int b2i(byte[] b) {
	int value = 0;
	for (int i = 0; i < 4; i++) {
	    int shift = (4 - 1 - i) * 8;
	    value += (b[i] & 0x000000FF) << shift;
	}
	return value;
    }

    public static String showresult() throws IOException {
	BufferedReader reader = null;
	String str = null;
	String str2 = null;
	while(str2 == null)
	{
	    try
	    {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\receive\\result.txt"), "UTF-8")); // 指定讀取文件的編碼格式，以免出現中文亂碼

		while ((str = reader.readLine()) != null) {
		    System.out.println("文件內容 : " + str);
		    str2 = str;
		}
	    }catch (FileNotFoundException e) {
		e.printStackTrace();
	    }catch (IOException e) {
		e.printStackTrace();
	    }
	}
	System.out.println("文件內容2 : " + str2);
	return str2;
    }

    public static void cleartxt()
    {
	//File f = new File("E:\\receive\\result.txt");
	try
	{
	    BufferedWriter out = new BufferedWriter(new FileWriter(new File("E:\\receive\\result.txt")));
	    //OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
	    //BufferedWriter out = new BufferedWriter(write);

	    out.write("");

	    out.flush(); // 把快取區內容壓入檔案
	    out.close(); // 最後記得關閉檔案

	}catch (FileNotFoundException e) {
	    e.printStackTrace();
	}catch (IOException e) {
	    e.printStackTrace();
	}
    }



	/**
	 * Creates new form NewJFrame
	 */
	public NewJFrame() {
	    initComponents();
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">   

    private void initComponents() {

	jScrollPane1 = new javax.swing.JScrollPane();
	jTextArea1 = new javax.swing.JTextArea();

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	jTextArea1.setColumns(20);
	jTextArea1.setRows(5);
	jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
	jScrollPane1.setViewportView(jTextArea1);

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
	    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	    .addGroup(layout.createSequentialGroup()
		.addGap(28, 28, 28)
		.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
		.addGap(32, 32, 32))
	);
	layout.setVerticalGroup(
	    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	    .addGroup(layout.createSequentialGroup()
		.addGap(27, 27, 27)
		.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
		.addGap(36, 36, 36))
	);

	pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
	/* Set the Nimbus look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
	 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new NewJFrame().setVisible(true);
	    }
	});            

    int count = 0;
    String label = "";
    try { 
	    //ServerSocket serverSkt = new ServerSocket(8080); 

	    ServerSocket ss = new ServerSocket(8080);
	    //jTextArea1.setText("");
	    while(true) { 
		System.out.println("傾聽中....");                
		Socket s = ss.accept();                             
		///////////
		InputStream in = s.getInputStream();

		int content;
		//裝載檔名的陣列
		byte[] c = new byte[1024];
		//解析流中的檔名,也就是開頭的流
		int namenum = 0;        //計算字數
		for (int i = 0; (content = in.read()) != -1; i++) {
		    //表示檔名已經讀取完畢
		    namenum += 1;
		    if (content == '#') 
		    {
			break;
		    }
		    c[i] = (byte) content;
		}
		//將byte[]轉化為字元,也就是我們需要的檔名
		String FileName = new String(c, "utf-8").trim();
		System.out.println("filename : " +  FileName);


		if (FileName.equals("LED1.PNG") || FileName.equals("LED2.PNG") || FileName.equals("LED3.PNG") || FileName.equals("LED4.PNG")
			|| FileName.equals("LED5.PNG") || FileName.equals("LED6.PNG") || FileName.equals("dark.PNG")
			|| FileName.equals("wLED1.PNG") || FileName.equals("wLED2.PNG") || FileName.equals("wLED3.PNG")
			|| FileName.equals("wLED4.PNG") || FileName.equals("wLED5.PNG") || FileName.equals("wLED6.PNG") || FileName.equals("wdark.PNG")
			|| FileName.equals("label_of_mushroom") || FileName.equals("label_of_phalaenopsis"))
		{
		    if (FileName.equals("label_of_mushroom") || FileName.equals("label_of_phalaenopsis"))
		    {
			if (FileName.equals("label_of_mushroom"))
			{
			    label = "mushroom";
			    System.out.println("讀到杏鮑菇label");
			}
			if (FileName.equals("label_of_phalaenopsis"))
			{
			    label = "phalaenopsis";
			    System.out.println("讀到蝴蝶蘭label");
			}
		    }
		    else
		    {
			FileOutputStream fos = new FileOutputStream("E:\\receive\\target\\" + FileName);

			byte[] buf = new byte[1024];
			int len = 0;
			//往字節流裡面讀取數據
			while ((len = in.read(buf)) != -1)
			{
				fos.write(buf,0,len);                        
			}

			if(jTextArea1.getText().toString().equals("")){
			    jTextArea1.setText("已接收:  " + FileName);
			}
			else{
			    jTextArea1.setText(jTextArea1.getText() + "\n" + FileName);
			}

			System.out.println("檔案接收完畢！"); 

			//獲取輸出流，準備給客戶端發送訊息
			//OutputStream out = s.getOutputStream();

			//關閉資源
			fos.close();
			/*out.write("上傳成功".getBytes());
			out.flush();
			out.close();*/

			count += 1;
			if (count == 1)
			{
			    cleartxt();
			}
			System.out.println("count : " + count + "\n");

			//String freshnum = null;
			if ((count == 14 ) && (label == "mushroom"))
			{
			    //cleartxt();
			    System.out.println("activate mushroom");
			    Runtime rt = Runtime.getRuntime();     
			    Process p = rt.exec("E:\\GUI_A\\GUI\\for_testing\\GUI.exe") ;

			    jTextArea1.setText(jTextArea1.getText() + "\n" + "等待計算......");


			    System.out.println("等待計算");

			    String freshnum = showresult();
			    count = 0;
			    System.out.println("freshnum : " + freshnum);

			    OutputStream out = s.getOutputStream();
			    out.write(freshnum.getBytes());
			    out.flush();
			    out.close();

			    System.out.println("開啟杏鮑菇GUI");

			    Process pr = rt.exec("taskkill /f /im GUI.exe") ;
			}
			if ((count == 14 ) && (label == "phalaenopsis"))
			{
			    //cleartxt();
			    System.out.println("activate phalaenopsis");
			    Runtime rt = Runtime.getRuntime();     
			    Process p = rt.exec("E:\\蝴蝶蘭\\V2\\GUI\\for_testing\\GUI.exe") ;

			    jTextArea1.setText(jTextArea1.getText() + "\n" + "等待計算......");

			    System.out.println("等待計算");

			    String freshnum = showresult();
			    count = 0;
			    System.out.println("level_of_health : " + freshnum);

			    OutputStream out = s.getOutputStream();
			    out.write(freshnum.getBytes());
			    out.flush();
			    out.close();

			    System.out.println("開啟蝴蝶蘭GUI");

			    Process pr = rt.exec("taskkill /f /im GUI.exe") ;
			}
		    }
		}
		else
		{
		    System.out.println("駭客讀取！"); 
		}
		//關閉資源
		in.close();
		s.close();
		//ss.close(); 
	    }

	} 
	catch(IOException e) { 
	    e.printStackTrace(); 
	}    
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    // End of variables declaration                   


}
