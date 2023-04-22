/* FIRST MADE SEPARATE CLIENTS TO TEST THE RPC METHODS - LEFT IN TO SHOW WORK - NORMALLY WOULD DELETE
package serviceOneClientAndServer.client;

import com.proto.serviceOne.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class serviceOneClient {

    private static void doTakePicture(ManagedChannel channel) {
        System.out.println("Enter doTakePicture");
        serviceOneGrpc.serviceOneBlockingStub stub = serviceOneGrpc.newBlockingStub(channel);
        TakePictureResponse response = stub.takePicture(TakePictureRequest.newBuilder().setCamera("Front door").build());

        System.out.println("Camera sending photos: " +response.getResult());
    }

    private static void doStreamMotionEvents(ManagedChannel channel) throws InterruptedException {
        System.out.println("Enter doMotionEvent");
        serviceOneGrpc.serviceOneStub stub = serviceOneGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);

       StreamObserver <MotionEvent> stream = stub.streamMotionEvents(new StreamObserver<Alert>() {
           @Override
           public void onNext(Alert response) {
               System.out.println(response.getMessage());
           }

           @Override
           public void onError(Throwable t) {}

           @Override
           public void onCompleted() {
               latch.countDown();

           }
       });

        Arrays.asList("True","False").forEach(motion ->
                stream.onNext(MotionEvent.newBuilder().setMotionDetected(Boolean.parseBoolean(motion)).build()));

        stream.onCompleted();
        latch.await(3, TimeUnit.SECONDS);

    }

    public static void main(String[] args) throws InterruptedException {

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

*/
