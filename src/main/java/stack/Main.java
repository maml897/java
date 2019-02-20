package stack;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Main
{
	public static void main(String[] args)
	{
		List<StackModel> list = new ArrayList<>();
		list.add(new StackModel(2));
		list.add(new StackModel(3));
		list.add(new StackModel(4));
		list.add(new StackModel(1));
		
		
		Stack<StackModel> stack = new Stack<>();
		StackModel root=new StackModel(1);
		stack.push(root);
		
		
		for (StackModel sm : list)
		{
			while (stack.peek().getId() != root.getId() && !stack.isEmpty())
			{
				stack.pop();
			}
			stack.peek().setName("maml");;
			stack.push(sm);
		}
		
		System.out.println(stack.size());
		System.out.println(stack.get(0).getName());
		System.out.println(stack.get(0).getId());
		
	}
}
