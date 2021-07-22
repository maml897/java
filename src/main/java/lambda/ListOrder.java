package lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListOrder {

	public static void main(String[] args) {
		List<Hosting> list = new ArrayList<>();
		list.add(new Hosting(10, "liquidweb.com", 80000));
		list.add(new Hosting(2, "linode.com", 90000));
		list.add(new Hosting(3, "digitalocean.com", 120000));
		list.add(new Hosting(4, "aws.amazon.com", 200000));
		list.add(new Hosting(5, "mkyong.com", 1));

		list.sort(Comparator.comparing(Hosting::getId).reversed());
		
		list.forEach(x->{
			System.out.println(x.getId());
		});
	}
}
