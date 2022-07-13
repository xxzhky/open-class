package com.gp.vip.ptn.algorithm.inst;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

public class CreateEmployeeObj {

    public static void main(String[] args) throws Exception {

        //1、Using new keyword
        Employee employee1 = new Employee();
        employee1.setName("employee");

        //2、Using Class.newInstance() method
        Employee employee2 = Employee.class.newInstance();
        employee2.setName("employee2");

        //Using Constructor newInstance method
        Constructor<Employee> constructor = Employee.class.getConstructor();
        Employee employee3 = constructor.newInstance();
        employee3.setName("employee3");

        //4、Using clone method
        Employee employee4 = (Employee) employee3.clone();
        employee4.setName("employee4");

        //5、Serializable(序列化)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"))) {
            out.writeObject(employee4);
        }

        //6、Using Deserailizable(反序列化)
        Employee employee5;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {
            employee5 = (Employee) in.readObject();
            employee5.setName("employee5");
        }

        System.out.println(employee1 + "hashCode:" + employee1);
        System.out.println(employee2 + "hashCode:" + employee2);
        System.out.println(employee3 + "hashCode:" + employee3);
        System.out.println(employee4 + "hashCode:" + employee4);
        System.out.println(employee5 + "hashCode:" + employee5);

    }

}