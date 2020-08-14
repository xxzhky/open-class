package com.gp.vip.ptn.eventfred;

import com.gp.vip.ptn.eventfred.meventfred.Mouse;
import com.gp.vip.ptn.eventfred.meventfred.MouseEventCallback;
import com.gp.vip.ptn.eventfred.meventfred.MouseEventType;

/**
 * @author: Fred
 * @date: 2020/8/2 5:23 下午
 * @description: (类的描述)
 */
public class MouseEventTest {



    public static void main(String[] args) {

        MouseEventCallback  callback= new MouseEventCallback();

        Mouse mouse= new Mouse();

        mouse.addListener(MouseEventType.ON_CLICK, callback);
        mouse.addListener(MouseEventType.ON_FOCUS, callback);

        mouse.click();
        mouse.focus();


    }



}
