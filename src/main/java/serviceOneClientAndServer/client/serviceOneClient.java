package serviceOneClientAndServer.client;

import com.proto.serviceOne.TakePictureRequest;
import com.proto.serviceOne.TakePictureResponse;
import com.proto.serviceOne.serviceOneGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class serviceOneClient {

    private static void doTakePicture(ManagedChannel channel) {
        System.out.println("Enter doTakePicture");
        serviceOneGrpc.serviceOneBlockingStub stub = serviceOneGrpc.newBlockingStub(channel);
        TakePictureResponse response = stub.takePicture(TakePictureRequest.newBuilder().setCamera("Front door").build());

        System.out.println("Camera sending photos: " +response.getResult());
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Need one argument to work");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        switch (args[0]) {
            case "takePicture": doTakePicture(channel); break;
            default:
                System.out.println("keyword invalid- " + args[0]);
        }


        System.out.println("Shutting down");
        channel.shutdown();
    }
}
