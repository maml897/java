public class Starttest
{
	public static void main(String[] args)
	{
		ConfigurableApplicationContext run = SpringApplication.run(SpringBoot2YyjApplication.class, args);

		JdbcTemplate jdbcTemplate = run.getBean("jdbcTemplateTj", JdbcTemplate.class);
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from s_se");
		System.out.println(list.size());
	}
}
