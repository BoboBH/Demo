package com.bobo.zktest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.zktest.bean.elasticsearch.Department;
import com.bobo.zktest.bean.elasticsearch.Employee;
import com.bobo.zktest.bean.elasticsearch.Organization;
import com.bobo.zktest.bean.elasticsearch.Stock;
import com.bobo.zktest.domain.StockExample;
import com.bobo.zktest.mapper.StockMapper;
import com.bobo.zktest.repository.EmployeeRepository;
import com.bobo.zktest.repository.StockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = ZktestApplication.class)
public class ElasticSearchTest {

	private static final String STOCK_INDEX="finance_index";
	private static final String STOCK_TYPE="stock";
	@Autowired
	private ElasticsearchTemplate esTemplate;
	@Autowired 
	private StockMapper stockMapper;
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private StockRepository stockRepository;
	
	@Test
	public void testSave(){
		Organization organization = new Organization();
		organization.setId("TEST_ORG_001");
		organization.setName("TEST ORGNANIZATION");
		Department department = new Department();
		department.setId("TEST_DEP_001");
		department.setName("TEST DEPARTMENT");
		
		Employee employee = new Employee();
		employee.setId("TEST_000001");
		try {
			employee.setDob(new SimpleDateFormat("yyyy-mm-dd").parse("2016-04-04"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employee.setName("Happy Huang");
		employee.setAddress("阳光花园17-2403");
		employee.setDepartment(department);
		employee.setOrganization(organization);
		repository.save(employee);
		Optional<Employee> n1 = repository.findById(employee.getId());
		Assert.assertEquals(employee.getName(), n1.get().getName());
		employee = new Employee();
		employee.setId("TEST_000002");
		try {
			employee.setDob(new SimpleDateFormat("yyyy-mm-dd").parse("1980-10-14"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employee.setName("Bobo Huang");
		employee.setAddress("阳光花园17-2403");
		employee.setDepartment(department);
		employee.setOrganization(organization);
		repository.save(employee);
		
		try {
			employee = new Employee("TEST_00003","Wu Wang",new SimpleDateFormat("yyyy-mm-dd").parse("1981-12-14"),
					"荣超花园1126", 30, organization, department);
			repository.save(employee);
			employee = new Employee("TEST_00004","Liu Zhao",new SimpleDateFormat("yyyy-mm-dd").parse("1982-12-14"),
					"荣超花园1126", 30, organization, department);
			repository.save(employee);
			employee = new Employee("TEST_00004","Ba Huang",new SimpleDateFormat("yyyy-mm-dd").parse("1991-12-14"),
					"明珠花园1126", 30, organization, department);
			repository.save(employee);
			employee = new Employee("TEST_00006","San Zhang",new SimpleDateFormat("yyyy-mm-dd").parse("1971-12-14"),
					"明珠花园1126", 30, organization, department);
			repository.save(employee);
			employee = new Employee("TEST_00007","Si Li",new SimpleDateFormat("yyyy-mm-dd").parse("1971-12-14"),
					"尚水天成1126", 30, organization, department);
			repository.save(employee);

			employee = new Employee("TEST_00007","San Zhang",new SimpleDateFormat("yyyy-mm-dd").parse("1971-12-14"),
					"尚水天成1226", 30, organization, department);
			repository.save(employee);

			employee = new Employee("TEST_00008","王花园",new SimpleDateFormat("yyyy-mm-dd").parse("1971-12-14"),
					"尚水天成1226", 30, organization, department);
			repository.save(employee);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		List<Employee> employees = repository.findByName("Bobo Huang");
		Assert.assertTrue(employees.size() > 0);
		employees = repository.findByName("Huang");
		Assert.assertTrue(employees.size() > 0);
		employees = repository.findByOrganizationName("TEST");
		Assert.assertTrue(employees.size() > 0);
		employees = repository.findByOrganizationName("XXXXX");
		Assert.assertTrue(employees.size() == 0);
		String primaryKeyWord = "花园";
		String keyword = "花园";
		Page<Employee> pe = searchEmployeeByKeyword(keyword);
		Assert.assertTrue(pe.getSize() > 0);
		for (Employee e : pe) {
			if(!"TEST_00008".equalsIgnoreCase(e.getId()))
				Assert.assertTrue(e.getAddress().contains(primaryKeyWord));
			else
				Assert.assertTrue(e.getName().contains(primaryKeyWord));
		}
		
		keyword = "ab花园";
		pe = searchEmployeeByKeyword(keyword);
		Assert.assertTrue(pe.getSize() > 0);
		for (Employee e : pe) {
			if(!"TEST_00008".equalsIgnoreCase(e.getId()))
				Assert.assertTrue(e.getAddress().contains(primaryKeyWord));
			else
				Assert.assertTrue(e.getName().contains(primaryKeyWord));
			
		}
		
		//模糊搜索
		keyword = "朝阳花园";
		pe = searchEmployeeByKeyword(keyword);
		Assert.assertTrue(pe.getSize() > 0);
		for (Employee e : pe) {
			if(!"TEST_00008".equalsIgnoreCase(e.getId()))
				Assert.assertTrue(e.getAddress().contains(primaryKeyWord));
			else
				Assert.assertTrue(e.getName().contains(primaryKeyWord));
		}
	}
	
	private Page<Employee> searchEmployeeByKeyword(String keyword){
		Pageable pageable = PageRequest.of(0, 100);
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
		searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keyword));
		SearchQuery searchQuery = searchQueryBuilder.build();
		return esTemplate.queryForPage(searchQuery, Employee.class);
	}
	@Test
	public void testAdSearch() throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String keyword = "明珠花园";
		Pageable pageable = PageRequest.of(0, 100);
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("name", "huang"));
		boolQueryBuilder.must(QueryBuilders.matchQuery("address", "花园"));
		Date startDate =new SimpleDateFormat("yyyy-mm-dd").parse("1970-12-11");
		Date endDate= new SimpleDateFormat("yyyy-mm-dd").parse("1990-12-11");
		boolQueryBuilder.must(QueryBuilders.rangeQuery("dob").gt(simpleDateFormat.format(startDate)));
		boolQueryBuilder.must(QueryBuilders.rangeQuery("dob").lt(simpleDateFormat.format(endDate))); 
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
	            .withPageable(pageable)
	            .withQuery(boolQueryBuilder)
	            .build()
	            ;
		Page<Employee> pe = esTemplate.queryForPage(searchQuery, Employee.class);
		for (Employee employee : pe) {
			Assert.assertTrue(employee.getName().toLowerCase().contains("huang"));
			Assert.assertTrue(employee.getAddress().contains("花园"));
			Assert.assertTrue(employee.getDob().compareTo(startDate) >= 0);
			Assert.assertTrue(employee.getDob().compareTo(endDate) <= 0);
		}
	}
	//@Test
	public void testStockSearch(){		
		List<Stock> stocks = stockRepository.findBySymbol("000422");
		Assert.assertTrue(stocks.size() > 0);
		Assert.assertTrue(stocks.get(0).getSymbol().contains("000422"));
		stocks = stockRepository.findBySymbol("600036");
		Assert.assertTrue(stocks.size() > 0);
		Assert.assertTrue(stocks.get(0).getSymbol().contains("600036"));
		stocks = stockRepository.findBySymbol("600028");
		Assert.assertTrue(stocks.size() > 0);
		Assert.assertTrue(stocks.get(0).getSymbol().contains("600028"));
		stocks = stockRepository.findByName("中国石化");
		Assert.assertTrue(stocks.size() > 0);
		Assert.assertTrue(stocks.get(0).getName().contains("中国石化"));
		
		Pageable pageable = PageRequest.of(0, 100);
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
		String keyword = "股份";
		searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keyword));
		SearchQuery searchQuery = searchQueryBuilder.build();
		Page<Stock> stockPage = esTemplate.queryForPage(searchQuery, Stock.class);
		Assert.assertTrue(stockPage.getSize() > 0);
		
	}
	
	
	@Test	
	public void testSaveOne(){
		StockExample example = new StockExample();
		example.createCriteria().andIdEqualTo("sz000422");
		List<com.bobo.zktest.domain.Stock> stocks = stockMapper.selectByExample(example);
		for (com.bobo.zktest.domain.Stock stock : stocks) {
			Stock item = generateStock(stock);
			if(item != null)
				stockRepository.save(item);
		}
	}
	//@Test
	public void testBulkImport(){
		int maxCount = 15000;
		int pageSize = 1000;
		stockRepository.deleteAll();
		for(int i = 0; i < maxCount/pageSize; i++){
			int currentIndex = i * pageSize;
			List<com.bobo.zktest.domain.Stock> stocks = stockMapper.selectAllByPagination(currentIndex, pageSize);
			BulkImportDataIntoES(stocks);
			if(stocks.size() == 0)
				break;
		}
	}
	
	private void BulkImportDataIntoES(List<com.bobo.zktest.domain.Stock> stocks){
		List queries = new ArrayList();
		for (com.bobo.zktest.domain.Stock stock : stocks) {
			try{
			Stock item = this.generateStock(stock);
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setId(item.getId());
			indexQuery.setSource(toJson(item));
			indexQuery.setIndexName(STOCK_INDEX);
			indexQuery.setType(STOCK_TYPE);
			queries.add(indexQuery);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(queries.size() > 0)
			esTemplate.bulkIndex(queries);
	}
	
	private String toJson(Stock stock) throws JsonProcessingException{		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(stock);
	}
	private Stock generateStock(com.bobo.zktest.domain.Stock stock){
		if(stock == null)
			return null;
		Stock rtn = new Stock();
		rtn.setId(stock.getId());
		rtn.setName(stock.getName());
		rtn.setSymbol(stock.getSymbol());
		rtn.setMarket(stock.getMarket());
		rtn.setDate(stock.getDate());
		rtn.setPrice(stock.getPrice());
		rtn.setDate(stock.getCreatedon());
		rtn.setModifiedon(stock.getModifiedon());
		rtn.setStatus(stock.getStatus());
		return rtn;
	}
	
}
