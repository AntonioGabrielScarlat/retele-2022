package client;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.Message;
import server.TCPServer;



public class TCPClient {
	private static Scanner scanner = new Scanner(System.in);

	
	public static void main(String[] args) {
		Message message = new Message();
		System.out.print("Port: ");
		Integer port = Integer.parseInt(scanner.nextLine());
		System.out.print("\nAdresa de email: ");
		String email = scanner.nextLine();
		message.setSender(email);
		
		if(TCPServer.users.contains(email)) {
			System.out.print("\nDestinatar: ");
		try(Socket socket = new Socket("localhost", port)) {
			new Thread(() -> {
				try {
					while(true) {
						InputStream inputStream = socket.getInputStream();
						DataInputStream dataInputStream = 
								new DataInputStream(inputStream);
						String receivedMessage = dataInputStream.readUTF();
						if(receivedMessage.contains(email) && !receivedMessage.startsWith(email))
						{   int t=0;
							String currentDestination=null;
							for(String u:TCPServer.users) {
								if(receivedMessage.startsWith(u))
								{	t=1;
									currentDestination=u;
							File theDir = new File("C:\\Users\\anton\\Desktop\\retele\\"+currentDestination);
							if (!theDir.exists()){
							    theDir.mkdirs();
							}
							Date date = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
							File file = new File(theDir+"\\"+dateFormat.format(date) + ".tsv") ;
							BufferedWriter out = new BufferedWriter(new FileWriter(file));
							out.write(receivedMessage);
							out.close();
							System.out.println("Mesajul a fost transmis cu succes!\n");
								}
							}
							if(t==0) {
								System.out.println("Introdu un destinatar valid!");
							}
							
							System.out.print("\nDestinatar: ");
							}
						
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}).start();
			
			while(true) {
				String destination = scanner.nextLine();
				message.setDestination(destination);
				System.out.print("Subiect: ");
				String subject = scanner.nextLine();
				message.setSubject(subject);
				System.out.print("Text: ");
				String text = scanner.nextLine();
				message.setText(text);
				
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream objectOutputStream = 
						new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(message);
			}
			

		} catch(Exception e) {
			e.printStackTrace();
		}
		}
		else
		{
			System.out.println("Introdu o adresa de email valida!");
		}
		
		scanner.close();
	}

}