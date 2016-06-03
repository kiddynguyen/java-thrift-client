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

public class Server {
	public static final int PORT = 9090;

	public static void main(String[] args) {
		try {
			CalculatorHandler handler = new CalculatorHandler();
			Calculator.Processor processor = new Calculator.Processor(handler);
//            TServer server = createThreadPoolServer(processor);
//            TServer server = createSimpleBlockingServer(processor);
            TServer server = createNonblockingServer(processor);
            
			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
    
    public static TServer createSimpleBlockingServer(Calculator.Processor processor) throws TTransportException {
        TServerTransport transport = new TServerSocket(PORT);
		return new TSimpleServer(new Args(transport).processor(processor));
    }
    
    public static TServer createThreadPoolServer(Calculator.Processor processor) throws TTransportException {
        TServerTransport transport = new TServerSocket(PORT);
		return new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
    }
    
    public static TServer createNonblockingServer(Calculator.Processor processor) throws TTransportException {
        TNonblockingServerTransport transport = new TNonblockingServerSocket(PORT);
		return new TNonblockingServer(new TNonblockingServer.Args(transport).processor(processor));
    }
    
    public static TServer createHsHaServer(Calculator.Processor processor) throws TTransportException {
        return null;
    }
    
    public static TServer createThreadedSelectorServer(Calculator.Processor processor) throws TTransportException {
        TNonblockingServerTransport transport = new TNonblockingServerSocket(PORT);
		return new TThreadedSelectorServer(new TThreadedSelectorServer.Args(transport));
    }
}
