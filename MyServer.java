package shahgs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class ThreadSocket extends Thread {
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	BufferedReader br;

	void set() throws IOException {
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	ThreadSocket(Socket s) {
		this.s = s;
		try {
			set();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		String str = "", str2 = "";
		while (!str.equals("stop")) {
			try {
				str = din.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(str);
			try {
				str2 = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dout.writeUTF(str2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dout.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			din.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyServer {
	public static void main(String args[]) throws Exception {
		ServerSocket ss = new ServerSocket(6666);
		while (!ss.isClosed()) {
			Socket s = ss.accept();
			ThreadSocket t1 = new ThreadSocket(s);
			t1.start();
		}
		ss.close();

	}
}
