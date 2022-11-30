import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private ExecutorService executorService;
    public static final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    public static HashMap<String, HashMap<String, Handler>> handlers = new HashMap<>();


    public Server(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(64);
    }

    public void start() {

        try (final var serverSocket = new ServerSocket(9999)) {
            while (true) {
                final var clientSocket = serverSocket.accept();
                ProcessRequest processRequest = new ProcessRequest(clientSocket);
                executorService.submit(processRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // закрытие пула нитей после завершения работы всех нитей
        executorService.shutdown();
    }

    public void addHandler(String method, String file, Handler handler) {
        HashMap<String, Handler> innerHandlerMap = new HashMap<>();
        innerHandlerMap.put(method, handler);
        handlers.put(file.concat(":").concat(method), innerHandlerMap);
    }
}
