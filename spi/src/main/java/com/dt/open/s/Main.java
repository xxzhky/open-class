package com.dt.open.s;

import com.dt.open.s.api.IOperation;
import com.dt.open.s.api.impl.PlusOperationImpl;
import com.dt.open.s.spi.DivisionOperationImpl;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        IOperation plus = new PlusOperationImpl();

        IOperation division = new DivisionOperationImpl();
        System.out.println(plus.operation(5, 3));

        System.out.println(division.operation(9, 3));

        ServiceLoader<IOperation> operations = ServiceLoader.load(IOperation.class);
        Iterator<IOperation> operationIterator = operations.iterator();
        System.out.println("classPath:"+System.getProperty("java.class.path"));
        while (operationIterator.hasNext()) {
            IOperation operation = operationIterator.next();
            System.out.println(operation.operation(6, 3));
        }

//        com.dt.open.s.spi.DivisionOperationImpl.classPath
    }
}
