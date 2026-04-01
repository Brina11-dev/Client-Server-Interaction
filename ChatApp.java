import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatApp {

    private static final int PORT = 5000;

    public static void main(String[] args) throws Exception {

        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (true) {
                    Socket client = serverSocket.accept();
                    new Thread(() -> handleClient(client)).start();
                }
            } catch (IOException e) {
                System.err.println("[Server crashed] " + e.getMessage());
            }
        });
        serverThread.setDaemon(true); 
        serverThread.start();

        Thread.sleep(500);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     Java Client-Server  — One Terminal   ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Commands: TIME | ECHO <msg> | UPPER <msg> | REVERSE <msg> | QUIT\n");

        try (
            Socket socket       = new Socket("localhost", PORT);
            BufferedReader in   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter    out  = new PrintWriter(socket.getOutputStream(), true);
            Scanner        scan = new Scanner(System.in)
        ) {
            in.readLine();

            while (true) {
                System.out.print("You    >> ");
                String input = scan.nextLine().trim();
                if (input.isEmpty()) continue;

                out.println(input);
                String response = in.readLine();
                System.out.println("Server >> " + response);
                System.out.println();

                if (input.equalsIgnoreCase("QUIT")) break;
            }
        }

        System.out.println("Goodbye!");
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter    out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("connected"); 

            String line;
            while ((line = in.readLine()) != null) {
                out.println(processCommand(line.trim()));
                if (line.trim().equalsIgnoreCase("QUIT")) break;
            }
        } catch (IOException e) {
            
        }
    }

    private static String processCommand(String input) {
        if (input.equalsIgnoreCase("TIME")) {
            return "Current time: " + new java.util.Date();

        } else if (input.toUpperCase().startsWith("ECHO ")) {
            return input.substring(5);

        } else if (input.toUpperCase().startsWith("UPPER ")) {
            return input.substring(6).toUpperCase();

        } else if (input.toUpperCase().startsWith("REVERSE ")) {
            return new StringBuilder(input.substring(8)).reverse().toString();

        } else if (input.equalsIgnoreCase("QUIT")) {
            return "Bye!";

        } else {
            return "Unknown command: '" + input + "'. Try TIME | ECHO | UPPER | REVERSE | QUIT";
        }
    }
}