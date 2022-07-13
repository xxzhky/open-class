package com.gp.vip.ptn.algorithm.inst;

import java.io.Serializable;

public class Employee implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public Employee() {
        System.out.println("Employee Constructor Called....");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        Employee employee = (Employee) obj;
//        return Objects.equals(name, employee.name);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }

//    @Override
//    public String toString() {
//        return String.format("Employee{name='%s'}", name);
//    }

    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}