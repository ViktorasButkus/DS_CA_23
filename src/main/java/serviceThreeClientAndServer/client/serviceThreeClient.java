/* FIRST MADE SEPARATE CLIENTS TO TEST THE RPC METHODS - LEFT IN TO SHOW WORK - NORMALLY WOULD DELETE
package serviceThreeClientAndServer.client;

import com.proto.serviceThree.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class serviceThreeClient {

    //Unary
    private static void doActivateSensors(ManagedChannel channel) {
        System.out.println("Enter doActivateSensors");

        SensorSystemServiceGrpc.SensorSystemServiceBlockingStub stub = SensorSystemServiceGrpc.newBlockingStub(channel);
        ActivateSensorsResponse response = stub.activateSensors(ActivateSensorsRequest.newBuilder().setActivate(true).build());

        System.out.println("Sensor message: " +response.getMessage());

    }

    //Client Streaming
    private static void doDetectMotion(ManagedChannel channel) throws InterruptedException {
        System.out.println("Enter doDetectMotion");

        SensorSystemServiceGrpc.SensorSystemServiceStub stub = SensorSystemServiceGrpc.newStub(channel);

        List<String> sensors = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        Collections.addAll(sensors, "1", "2", "3", "4");

        StreamObserver<DetectMotionRequest> stream = stub.detectMotion(new StreamObserver<DetectMotionResponse>() {
            @Override
            public void onNext(DetectMotionResponse response) {
                System.out.println(response.getAlertMessage());
            }

            @Override
            public void onError(Throwable t) {}

            @Override
            public void onCompleted() {
                latch.countDown();

            }
        });

        for (String sensor: sensors) {
            stream.onNext(DetectMotionRequest.newBuilder().setSensorId(sensor).build());
        }
        stream.onCompleted();
        latch.await(3, TimeUnit.SECONDS);

    }

    public static void main(String[] args) throws InterruptedException {


     if (args.length == 0) {
        System.out.println("Need one argument to work");
        return;
    }

    ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 50052)
            .usePlaintext()
            .build();

        switch (args[0]) {
        case "activateAlerts": doActivateSensors(channel); break;
        case "streamAlerts": doDetectMotion(channel);break;
        default:
            System.out.println("keyword invalid- " + args[0]);
    }


        System.out.println("Shutting down");
        channel.shutdown();
    }
}
*/