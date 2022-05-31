package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import models.Message;



public class TCPServer extends Thread {
	private static Scanner scanner = new Scanner(System.in);
	private static List<Socket> clients = new Vector<>();
	private Socket currentClient;
	
	
	public static List<String> users=new ArrayList<String>() {
		{
			add("antonio@retele.ro");
			add("andra@retele.ro");
			add("astrid@retele.ro");
			add("simona@retele.ro");
		}
	};
	
	
	public TCPServer(Socket currentClient) {
		this.currentClient = currentClient;
	}
	
	private Message receive() throws Exception {
		InputStream inputStream = currentClient.getInputStream();
		ObjectInputStream objectInputStream = 
				new ObjectInputStream(inputStream);
		Message message = (Message)objectInputStream.readObject();
		return message;
	}
	
	private void send(Message message) throws Exception {
			for(Socket s : clients) {
				OutputStream outputStream = s.getOutputStream();
				DataOutputStream dataOutputStream =
						new DataOutputStream(outputStream);
				dataOutputStream.writeUTF(message.toString());
		}
		}
	
	
	
	@Override
	public void run() {
		try {
			while(true) {
				Message m = receive();
					send(m);	
			}
		} catch(Exception e) {
			e.printStackTrace();
			clients.remove(currentClient);
		}
	}

	public static void main(String[] args) throws IOException {
		
		List<Integer> ports=new ArrayList<Integer>(){
			{
				add(8080);
				add(8000);
				add(4000);
			}
		};
		
		System.out.print("Port: ");
		 int currentPort= Integer.parseInt(scanner.nextLine());
		
		 for(Integer port: ports)
			 {
			 if(port==currentPort)
		{
				 try(ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server started on port " + port);
			
			while(true) {
				Socket clientSocket = serverSocket.accept();
				clients.add(clientSocket);
				TCPServer serverInstance = new TCPServer(clientSocket);
				serverInstance.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
				 }
			 }
	}
}