import java.io.BufferedOutputStream;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(9999);

        server.addHandler("GET", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                ProcessRequest.handleProcess(request, responseStream);
            }
        });
        server.addHandler("POST", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                ProcessRequest.handleProcess(request, responseStream);
            }
        });

        server.start();
    }
}

