package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Random random = new Random();

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .executor(executor)
            .build();

    public static void main(String[] args) throws IOException {
        new Main().start();
    }

    private void start() throws IOException {
        final HttpServer localhost = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 200);
        localhost.setExecutor(executor);
        localhost.createContext("/", exchange -> {
            try {
                final HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://192.168.68.117:7070/LICENSE?" + random.nextDouble())).build();
                final HttpResponse<byte[]> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
                exchange.sendResponseHeaders(httpResponse.statusCode(), httpResponse.body().length);
                exchange.getResponseBody().write(httpResponse.body());
                exchange.getResponseBody().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("HTTP server started on port: " + localhost.getAddress().getPort());
        localhost.start();
    }
}
