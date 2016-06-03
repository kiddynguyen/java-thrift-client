package com.minhtu.thrift.impl;

import com.minhtu.thrift.gen.tutorial.Calculator;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client2 {
    
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    
    private static Logger logger = Logger.getLogger(Client2.class);

	public static void main(String[] args) throws TException, IOException {
//        TTransport transport = createTSocketTransport();
//        TTransport transport = createNonblockingTransport();
        TTransport transport = createBlockingTFramedTransport();
        
        // connect to server
        logger.info("++ Called transport.open()");
        transport.open();
        logger.info("++ Connected to server");
        // create protocol wraps transport
        TProtocol protocol = new TBinaryProtocol(transport);
        Calculator.Client client = new Calculator.Client(protocol);

//        client.ping();
        logger.info("++ Called client.add(1, 2)");
        int result = client.add(1, 2);
        logger.info("1 + 2 = " + result);
        
        try {
            logger.info("Sleeping 15 seconds...");
            Thread.sleep(TimeUnit.SECONDS.toMillis(15));
            
            logger.info("++ Called client.add(100, 99)");
            int result2 = client.add(100, 99);
            logger.info("100 + 99 = " + result2);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

	}
    
    /**
     * Used for TSimpleServer, TThreadPoolServer
     */
    public static TTransport createTSocketTransport() {
        return new TSocket(HOST, PORT);
    }
    
    /**
     * Used for nonblocking server
     */
    public static TNonblockingTransport createNonblockingTransport() throws IOException {
        return new TNonblockingSocket(HOST, PORT);
    }
    
    /**
     * Used for blocking server with better performance
     */
    public static TTransport createBlockingTFramedTransport() throws IOException {
        TTransport innerTransport = new TSocket(HOST, PORT);
        return new TFramedTransport(innerTransport);
    }
    
    /**
     * Used for nonblocking server with better performance
     */
    public static TTransport createNonblockingTFramedTransport() throws IOException {
        TNonblockingTransport innerTransport = new TNonblockingSocket(HOST, PORT);
        return new TFramedTransport(innerTransport);
    }
}
