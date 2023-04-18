package serviceOneClientAndServer.client;

import com.proto.serviceOne.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class serviceOneClient {

    private static void doTakePicture(ManagedChannel channel) {
        System.out.println("Enter doTakePicture");
        serviceOneGrpc.serviceOneBlockingStub stub = serviceOneGrpc.newBlockingStub(channel);
        TakePictureResponse response = stub.takePicture(TakePictureRequest.newBuilder().setCamera("Front door").build());

        System.out.println("Camera sending photos: " +response.getResult());
    }

    private static void doStreamMotionEvents(ManagedChannel channel) {
        System.out.println("Enter doMotionEvent");
        serviceOneGrpc.serviceOneBlockingStub stub = serviceOneGrpc.newBlockingStub(channel);
        CountDownLatch latch = new CountDownLatch(1);

       // StreamObserver <MotionEvents> stream = stub.streamMotionEvents(new StreamObserver<Alert> () { }

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
            case "motionEvent": doStreamMotionEvents(channel);break;
            default:
                System.out.println("keyword invalid- " + args[0]);
        }


        System.out.println("Shutting down");
        channel.shutdown();
    }
}
