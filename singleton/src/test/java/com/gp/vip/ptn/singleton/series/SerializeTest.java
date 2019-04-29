package com.gp.vip.ptn.singleton.series;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SerializeTest {

    @Test
    public void getInstance() {

        Serialize s1=null;
        Serialize s2=Serialize.getInstance();
        FileOutputStream fos=null;
        try {
            fos= new FileOutputStream("serialize.obj");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();


            ObjectInputStream ois= new ObjectInputStream(new FileInputStream("serialize.obj"));

            s1= (Serialize) ois.readObject();
            ois.close();

            assertSame("the same obj or not -->", s1, s2);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}