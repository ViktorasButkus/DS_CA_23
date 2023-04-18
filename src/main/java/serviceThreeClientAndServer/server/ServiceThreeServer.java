package serviceThreeClientAndServer.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import serviceThreeClientAndServer.server.serviceThreeServerImpl;

import java.io.IOException;

public class ServiceThreeServer {

        public static void main(String[] args) throws IOException, InterruptedException {

            int port = 50053;

            //builds the server on the port specified above
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(new serviceThreeServerImpl())
                    .build();

            server.start();

            System.out.println("Server for Service Two has started");
            System.out.println("Listening on port " + port);

            //When shutdown is requested, run the code
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Received shutdown request");
                server.shutdown();
                System.out.println("Server for Service Two has stopped");
            }));

            server.awaitTermination();

        }

    }

