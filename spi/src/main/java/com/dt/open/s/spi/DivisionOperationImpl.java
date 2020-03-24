package com.dt.open.s.spi;

import com.dt.open.s.api.IOperation;

public class DivisionOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA / numberB;
    }
}
