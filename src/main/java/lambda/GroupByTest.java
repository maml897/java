package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByTest {
	public static void main(String[] args) {
		 myObject obj1 = new myObject();
	        obj1.setX(1);
	        obj1.setY(1);
	        obj1.setText("a");
	 
	        myObject obj2 = new myObject();
	        obj2.setX(2);
	        obj2.setY(2);
	        obj2.setText("a");
	 
	        myObject obj3 = new myObject();
	        obj3.setX(3);
	        obj3.setY(3);
	        obj3.setText("a");
	 
	        myObject obj4 = new myObject();
	        obj4.setX(4);
	        obj4.setY(4);
	        obj4.setText("d");
	 
	        myObject obj5 = new myObject();
	        obj5.setX(5);
	        obj5.setY(5);
	        obj5.setText("d");
	 
	        List<myObject> ls = new ArrayList<myObject>();
	        ls.add(obj1);
	        ls.add(obj2);
	        ls.add(obj3);
	        ls.add(obj4);
	        ls.add(obj5);
	        
	        
	        Map<String, Integer> group1 = ls.stream()
	                .collect(Collectors.groupingBy(t -> t.text, Collectors.summingInt(t -> t.x)));
	        
	        Map<String, Long> group2 = ls.stream()
	                .collect(Collectors.groupingBy(t -> t.text, Collectors.counting()));
	        
	        System.out.println(group2);
	}
}

class myObject {
    int x;
    int y;
    String text;
 
    public int getX() {
        return x;
    }
 
    public void setX(int x) {
        this.x = x;
    }
 
    public int getY() {
        return y;
    }
 
    public void setY(int y) {
        this.y = y;
    }
 
    public String getText() {
        return text;
    }
 
    public void setText(String text) {
        this.text = text;
    }
}