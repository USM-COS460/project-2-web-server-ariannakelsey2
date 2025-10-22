import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Server {

	public static void main(String[] args) throws Exception{
		int portNumber = Integer.parseInt(args[0]);
		String s = args[1];
		File docRoot = new File(s);
		
		try (ServerSocket srvr = new ServerSocket(portNumber)) {
			System.out.println("Server started. Listening on portnumber: " + portNumber);
			
			while (true) {
				Socket client = srvr.accept();
				System.out.println("New client connected!");
				
				new Thread(new Clients(client, docRoot)).start();	
					
			}
		} catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
      }

	}
}
class Clients implements Runnable {
	//Data fields
	private Socket soc;
	private File docR; 
	
	//Constructor
	public Clients(Socket socket, File docRoot) {
		this.soc = socket;
		this.docR = docRoot;
	}
	
	@Override
	public void run() {
		try {
			OutputStream output = soc.getOutputStream();
			InputStream in = soc.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			PrintWriter writer = new PrintWriter(output, true);
			
			String request = read.readLine();
			System.out.println(request);
			
			if (request == null || request.isEmpty()) {
				soc.close();
				return;
			}
			
			String[] split = request.split(" "); 
			if (split.length < 2) {
			    soc.close();
			    return;
			}
			String path = split[1];
			
			File file = new File(docR, URLDecoder.decode(path, "UTF-8")).getCanonicalFile();
			
			if (file.isDirectory()) {
				file = new File(file, "index.html");
	        }

	        if (file.exists() && file.isFile()) {
	            File newFile = file;
	            Path filePath = newFile.toPath();
	            byte[] fileBytes = Files.readAllBytes(filePath);
	            
	            String contentType = getContentType(file);

	            String response =
                        "HTTP/1.1 200 OK\r\n" +
                        "Date: " + getDate() + "\r\n" +
                        "Server: Server/1.0\r\n" +
                        "Content-Length: " + fileBytes.length + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "\r\n";

                System.out.println(response);

                writer.print(response);
                writer.flush();
                output.write(fileBytes);
                output.flush();
                soc.close();
	            
	      
	        } else {
	        	 int code = 404;
	        	 String status = "File Not Found";
	        	

	        	 String body = "<html><body><h1>" + code + " " + status + "</h1><p>" + "</p></body></html>";
	        	 byte[] bodyBytes = body.getBytes();

	        	 String response =
	                        "HTTP/1.1 " + code + " " + status + "\r\n" +
	                        "Date: " + getDate() + "\r\n" +
	                        "Server: Server/1.0\r\n" +
	                        "Content-Length: " + bodyBytes.length + "\r\n" +
	                        "Content-Type: text/html\r\n" +
	                        "\r\n";

	                System.out.println(response);

	                writer.print(response);
	                writer.flush();
	                output.write(bodyBytes);
	                output.flush();
	                soc.close();
	        	 
	     
	            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private static String getContentType(File file) {
		  String name = file.getName().toLowerCase();
	        if (name.endsWith(".html") || name.endsWith(".htm")) {
	        	return "text/html";
	        }
	        if (name.endsWith(".txt")) {
	        	return "text/plain";
	        }
	        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
	        	return "image/jpeg";
	        }
	   
	        if (name.endsWith(".css")) {
	        	return "text/css";
	        }
	        if (name.endsWith(".js")) {
	        	return "application/javascript";
	        }
	        return "application/octet-stream";
	}
	
	   private static String getDate() {
	        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	        return sdf.format(new Date());
	    }
	
	
}


