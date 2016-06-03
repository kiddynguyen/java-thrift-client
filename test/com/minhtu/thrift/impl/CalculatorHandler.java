package com.minhtu.thrift.impl;

import com.minhtu.thrift.gen.shared.SharedStruct;
import com.minhtu.thrift.gen.tutorial.Calculator;
import com.minhtu.thrift.gen.tutorial.InvalidOperation;
import com.minhtu.thrift.gen.tutorial.Operation;
import com.minhtu.thrift.gen.tutorial.Work;
import org.apache.thrift.TException;

/**
 *
 * @author nminhtu
 */
public class CalculatorHandler implements Calculator.Iface {

	@Override
	public void ping() throws TException {
		System.out.println("A ping from client");
	}

	@Override
	public int add(int num1, int num2) throws TException {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {}
        
		return num1 + num2;
	}

	@Override
	public int calculate(int logid, Work w) throws InvalidOperation, TException {
		int result = 0;

		if (w.op == Operation.ADD) {
			result = w.num1 + w.num2;
		} else if (w.op == Operation.SUBTRACT) {
			result = w.num1 - w.num2;
		} else if (w.op == Operation.MULTIPLY) {
			result = w.num1 * w.num2;
		} else if (w.op == Operation.DIVIDE) {
			result = w.num1 / w.num2;
		}

		return result;
	}

	@Override
	public void zip() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SharedStruct getStruct(int key) throws TException {
		SharedStruct sharedStruct = new SharedStruct();
		sharedStruct.setKey(5);
		sharedStruct.setValue("hello");
		return sharedStruct;
	}
}
