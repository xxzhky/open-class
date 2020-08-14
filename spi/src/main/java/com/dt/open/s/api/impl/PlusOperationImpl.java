package com.dt.open.s.api.impl;

import com.dt.open.s.api.IOperation;

public class PlusOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA + numberB;
    }
}
