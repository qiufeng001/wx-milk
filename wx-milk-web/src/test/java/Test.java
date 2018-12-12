import org.apache.poi.ss.formula.functions.T;
import wx.milk.web.utils.DESUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/29/029.
 */
public class Test {


    public void testHeap(){
        for(;;){
            ArrayList list = new ArrayList (2000);
            //System.out.println("1");
        }
    }
    static int num=1;
    public void testStack(){
        num++;
        System.out.println(num);
        this.testStack();
    }

    public static void main(String[] args) {

        /*List<? extends Father> list = new LinkedList<Father>();*/
        //list.add(new Son());

        Test  t  = new Test ();
        t.testHeap();
        t.testStack();
        System.out.println("最后" + num);
    }

}

class Person { }

class Father extends Person { }

class Son extends Father { }

class Faker extends Son { }

class ZhongH extends Son { }