package com.minhtu.thrift.impl;

import com.minhtu.thrift.gen.tutorial.Calculator;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class CalculatorServer {
	public static final int PORT = 9090;

	public static void main(String[] args) {
		try {
			CalculatorHandler handler = new CalculatorHandler();
			Calculator.Processor processor = new Calculator.Processor(handler);
            TServer server = createSimpleServer(processor);
			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
    
    /** Single thread blocking server */
    public static TServer createSimpleServer(Calculator.Processor processor) throws TTransportException {
        TServerTransport transport = new TServerSocket(PORT);
		return new TSimpleServer(new Args(transport).processor(processor));
    }
    
    /** One thread for accepting new request and one thread pool for handling requests */
    public static TServer createThreadPoolServer(Calculator.Processor processor) throws TTransportException {
        TServerTransport transport = new TServerSocket(PORT);
		return new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
    }
    
    /** Nonblocking server */
    public static TServer createNonblockingServer(Calculator.Processor processor) throws TTransportException {
        TNonblockingServerTransport transport = new TNonblockingServerSocket(PORT);
		return new TNonblockingServer(new TNonblockingServer.Args(transport).processor(processor));
    }
    
    /** One thread pool for accepting new connections and another thread pool for handling requests */
    public static TServer createHsHaServer(Calculator.Processor processor) throws TTransportException {
        return null;
    }
    
    public static TServer createThreadedSelectorServer(Calculator.Processor processor) throws TTransportException {
        TNonblockingServerTransport transport = new TNonblockingServerSocket(PORT);
		return new TThreadedSelectorServer(new TThreadedSelectorServer.Args(transport));
    }
}
