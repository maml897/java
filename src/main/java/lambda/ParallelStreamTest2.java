package lambda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParallelStreamTest2
{
	private static final int COUNT = 300000;

	public static void main(String[] args)
	{
		List<RiderDto> orilist = new ArrayList<RiderDto>();
		for (int i = 0; i < COUNT; i++)
		{
			orilist.add(init());
		}
		
		
		final List<RiderDto> copeList = Collections.synchronizedList(new ArrayList<RiderDto>());
		
		long s =System.currentTimeMillis();
		orilist.parallelStream().forEach(rider -> {
			RiderDto t = new RiderDto();
			t.setId(rider.getId());
			t.setCityId(rider.getCityId());
			copeList.add(t);
		});
		System.out.println(System.currentTimeMillis()-s);
		
		System.out.println("===============");
		
		final List<RiderDto> copeList1 = new ArrayList<RiderDto>();
		s =System.currentTimeMillis();
		orilist.stream().forEach(rider -> {
			RiderDto t = new RiderDto();
			t.setId(rider.getId());
			t.setCityId(rider.getCityId());
			copeList1.add(t);
		});
		System.out.println(System.currentTimeMillis()-s);
	}

	private static RiderDto init()
	{
		RiderDto t = new RiderDto();
		Random random = new Random();
		t.setId(random.nextInt(2 ^ 20));
		t.setCityId(random.nextInt(1000));
		return t;
	}

	static class RiderDto implements Serializable
	{
		private static final long serialVersionUID = 1;

		// 城市Id
		private Integer cityId;

		// 骑手Id
		private Integer id;

		public Integer getCityId()
		{
			return cityId;
		}

		public void setCityId(Integer cityId)
		{
			this.cityId = cityId;
		}

		public Integer getId()
		{
			return id;
		}

		public void setId(Integer id)
		{
			this.id = id;
		}

	}
}