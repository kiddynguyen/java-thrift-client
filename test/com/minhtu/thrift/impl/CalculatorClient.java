package com.minhtu.thrift.impl;

import com.minhtu.thrift.gen.tutorial.Calculator;
import java.io.IOException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CalculatorClient {
    
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    
    private int id;
    
    private TTransport transport;
    private Calculator.Client calClient;
    
	public static void main(String[] args) throws TException, IOException, TTransportException, InterruptedException {
        // run client 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CalculatorClient client1 = new CalculatorClient(1);
                    client1.request();
                } catch (TException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        
        // run client 2
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CalculatorClient client2 = new CalculatorClient(2);
                    client2.request();
                } catch (TException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
	}
    
    public CalculatorClient(int clientId) throws TTransportException {
        id = clientId;
        
        transport = createTSocketTransport(HOST, PORT);
        transport.open(); // connect to server
        TProtocol protocol = new TBinaryProtocol(transport);
        calClient = new Calculator.Client(protocol);
    }
    
    public void request() throws TTransportException, TException, InterruptedException {
        int result1 = calClient.add(1, 2);
        System.out.println("[" + id + "] result1: " + result1);
//        
//        Thread.sleep(5000);
//        
//        int result2 = calClient.add(10, 11);
//        System.out.println("[" + id + "] result2: " + result2);
    }
    
    /** Used for TSimpleServer, TThreadPoolServer */
    private TTransport createTSocketTransport(String host, int port) {
        return new TSocket(host, port);
    }
    
    /** Used for nonblocking server */
    private TNonblockingTransport createNonblockingTransport(String host, int port) throws IOException {
        return new TNonblockingSocket(host, port);
    }
    
    /** Used for blocking server with better performance */
    private TTransport createBlockingTFramedTransport(String host, int port) throws IOException {
        TTransport innerTransport = new TSocket(host, port);
        return new TFramedTransport(innerTransport);
    }
    
    /** Used for nonblocking server with better performance */
    private TTransport createNonblockingTFramedTransport(String host, int port) throws IOException {
        TNonblockingTransport innerTransport = new TNonblockingSocket(host, port);
        return new TFramedTransport(innerTransport);
    }
}
