package com.distributedSystems;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.proto.serviceOne.*;
import com.proto.serviceThree.*;
import com.proto.serviceTwo.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;


public class MainGUIClient implements ActionListener {

    //Service 1 - unary - takePicture
    private JTextField entry1, reply1;

    //Service 1 - bidirectional - streamMotionEvents
    private JSlider entry2;
    private JTextField reply2;

    //Service 2 - unary - activateAlerts
    private JCheckBox entry3;
    private JTextField reply3;

    //Service 2 - server stream - streamAlerts2
    private JTextField entry4;
    private JTextArea reply4;

    //Service 3 - unary - activateSensors
    private JCheckBox entry5;
    private JTextField reply5;

    //Service 3 - client stream - detectMotion
    private JCheckBox entry6;
    private JCheckBox entry7;
    private JCheckBox entry8;
    private JTextField reply6;


    //SERVICE 1 - CAMERA SYSTEM
    //UNARY METHOD
    private JPanel getService1UnaryJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Camera Location (Door, Window, Garden): ");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry1 = new JTextField("", 10);
        panel.add(entry1);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Invoke Service 1 (Unary)");
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply1 = new JTextField("", 10);
        reply1.setEditable(false);
        panel.add(reply1);

        panel.setLayout(boxlayout);

        return panel;

    }

    //SERVICE 1 - CAMERA SYSTEM
    //BIDIRECTIONAL STREAMING
    private JPanel getService1BidirectionalJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel zoomlabel = new JLabel(" Zoom level ");
        panel.add(zoomlabel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry2 = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);

        entry2.setMajorTickSpacing(1);
        entry2.setPaintTicks(true);
        entry2.setPaintLabels(true);
        panel.add(entry2);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Invoke Service 1 (Bidirectional)");
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply2 = new JTextField("", 10);
        reply2.setEditable(false);
        panel.add(reply2);

        panel.setLayout(boxlayout);
        return panel;

    }

    //SERVICE 2 - ALERT SYSTEM
    //UNARY METHOD
    private JPanel getService2UnaryJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Activate Alerts?");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry3 = new JCheckBox();
        panel.add(entry3);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Invoke Service 2 (Unary)");
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply3 = new JTextField("", 10);
        reply3.setEditable(false);
        panel.add(reply3);

        panel.setLayout(boxlayout);

        return panel;

    }

    //SERVICE 2 - ALERT SYSTEM
    //SERVER STREAMING
    private JPanel getService2ServerStreamJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Location (Door, Window, Garden): ");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry4 = new JTextField();
        panel.add(entry4);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton streambutton = new JButton("Invoke Service 2 (Server Stream)");
        streambutton.addActionListener(this);
        panel.add(streambutton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply4 = new JTextArea(10, 20);
        reply4.setEditable(false);
        panel.add(reply4);

        panel.setLayout(boxlayout);

        return panel;

    }


    //SERVICE 3 - SENSOR SYSTEM
    //UNARY METHOD
    private JPanel getService3UnaryJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Activate Sensors?");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry5 = new JCheckBox();
        panel.add(entry5);

        JButton button = new JButton("Invoke Service 3 (Unary)");
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply5 = new JTextField("", 10);
        reply5.setEditable(false);
        panel.add(reply5);

        panel.setLayout(boxlayout);

        return panel;

    }

    //SERVICE 3 - SENSOR SYSTEM
    //CLIENT STREAMING
    private JPanel getService3ClientStreamJPanel() {

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Activate Sensor 1?");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry6 = new JCheckBox();
        panel.add(entry6);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        JLabel label2 = new JLabel("Activate Sensor 2?");
        panel.add(label2);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry7 = new JCheckBox();
        panel.add(entry7);
        JLabel label3 = new JLabel("Activate Sensor 3?");
        panel.add(label3);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry8 = new JCheckBox();
        panel.add(entry8);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));


        JButton button = new JButton("Invoke Service 3 (Client Stream)");
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply6 = new JTextField("", 10);
        reply6.setEditable(false);
        panel.add(reply6);

        panel.setLayout(boxlayout);

        return panel;

    }


    public static void main(String[] args) throws IOException {


        MainGUIClient gui = new MainGUIClient();

        gui.build();

    }


    private void build() {

        JFrame frame = new JFrame("SERVICE CONTROLLER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the panel to add buttons
        JPanel panel = new JPanel();

        // Set the BoxLayout to be X_AXIS: from left to right
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        // Set border for the panel
        panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100)));

        panel.add(getService1UnaryJPanel());
        panel.add(getService1BidirectionalJPanel());
        panel.add(getService2UnaryJPanel());
        panel.add(getService2ServerStreamJPanel());
        panel.add(getService3UnaryJPanel());
        panel.add(getService3ClientStreamJPanel());

        // Set size for the frame
        frame.setSize(300, 300);

        // Set the window to be visible as the default to be false
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String label = button.getActionCommand();

        if (label.equals("Invoke Service 1 (Unary)")) {
            System.out.println("Service 1 Unary to be invoked ...");


            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
            serviceOneGrpc.serviceOneBlockingStub stub = serviceOneGrpc.newBlockingStub(channel);

            //preparing message to send
            TakePictureRequest request = TakePictureRequest.newBuilder().setCamera(entry1.getText()).build();

            //retrieving reply from service
            TakePictureResponse response = stub.takePicture(request);

            reply1.setText(response.getResult());

        } else if (label.equals("Invoke Service 1 (Bidirectional)")) {
            System.out.println("Service 1 Bidirectional to be invoked ...");

            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50054).usePlaintext().build();
            serviceOneGrpc.serviceOneStub stub = serviceOneGrpc.newStub(channel);
            CountDownLatch latch = new CountDownLatch(1);
            StreamObserver<MotionEvent> stream = stub.streamMotionEvents(new StreamObserver<Alert>() {
                @Override
                public void onNext(Alert response) {

                    reply2.setText(response.getCameraFrame());

                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onCompleted() {
                    latch.countDown();

                }
            });

            // Create an event with the current zoom value
            MotionEvent event = MotionEvent.newBuilder().setZoom(entry2.getValue()).build();

            // Send the event to the server
            stream.onNext(event);

            // Wait for user to change zoom value and update the event accordingly
            entry2.addChangeListener(s -> {
                MotionEvent newEvent = MotionEvent.newBuilder().setZoom(entry2.getValue()).build();
                stream.onNext(newEvent);
            });

            // Indicate that we have completed sending events
            stream.onCompleted();


        } else if (label.equals("Invoke Service 2 (Unary)")) {
            System.out.println("Service 2 Unary to be invoked ...");

            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();
            AlertSystemServiceGrpc.AlertSystemServiceBlockingStub stub = AlertSystemServiceGrpc.newBlockingStub(channel);

            //preparing message to send
            ActivateAlertsRequest request = ActivateAlertsRequest.newBuilder().setActivate(entry3.isSelected()).build();

            //retrieving reply from service
            ActivateAlertsResponse response = stub.activateAlerts(request);

            reply3.setText(String.valueOf(response.getMessage()));

        } else if (label.equals("Invoke Service 2 (Server Stream)")) {
            System.out.println("Service 2 Unary to be invoked ...");

            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50058).usePlaintext().build();
            AlertSystemServiceGrpc.AlertSystemServiceBlockingStub stub = AlertSystemServiceGrpc.newBlockingStub(channel);

            StreamAlertsRequest request = StreamAlertsRequest.newBuilder()
                    .setLocation(entry4.getText())
                    .build();

            // Call the server streaming RPC method
            stub.streamAlerts(request).forEachRemaining(response -> {
                reply4.setText(response.getAlertMessage());

            });



                } else if (label.equals("Invoke Service 3 (Unary)")) {
                    System.out.println("Service 3 Unary to be invoked ...");

                    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053).usePlaintext().build();
                    SensorSystemServiceGrpc.SensorSystemServiceBlockingStub stub = SensorSystemServiceGrpc.newBlockingStub(channel);

                    //preparing message to send
                    ActivateSensorsRequest request = ActivateSensorsRequest.newBuilder().setActivate(entry5.isSelected()).build();

                    //retrieving reply from service
                    ActivateSensorsResponse response = stub.activateSensors(request);

                    reply5.setText(response.getMessage());


                } else if (label.equals("Invoke Service 3 (Client Stream)")) {
                    System.out.println("Service 3 Client Stream to be invoked ...");

                    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50056).usePlaintext().build();
                    SensorSystemServiceGrpc.SensorSystemServiceStub stub = SensorSystemServiceGrpc.newStub(channel);

                    // Get the state of the three checkboxes
                    boolean sensor1Activate = entry6.isSelected();
                    boolean sensor2Activate = entry7.isSelected();
                    boolean sensor3Activate = entry8.isSelected();


                    //preparing message to send
                    DetectMotionRequest request = DetectMotionRequest.newBuilder()
                            .setSensor1Activated(sensor1Activate)
                            .setSensor2Activated(sensor2Activate)
                            .setSensor3Activated(sensor3Activate)
                            .build();

                    //retrieving reply from service
                    CountDownLatch latch = new CountDownLatch(1);
                    StreamObserver<DetectMotionRequest> stream = stub.detectMotion(new StreamObserver<>() {


                        @Override
                        public void onNext(DetectMotionResponse response) {

                            reply6.setText(response.getAlertMessage());

                        }

                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onCompleted() {
                            latch.countDown();


                        }
                    });
                    stream.onNext(request);
                    stream.onCompleted();
                    try {
                        latch.await(3, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
    }
