package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		
		
		product.setProdName("test이름");
		product.setProdDetail("test디테일");
		product.setManuDate("test제조일자");
		product.setPrice(1000);
		product.setFileName("test사진");
		
		productService.addProduct(product);
		
		product = productService.getProduct(10100);
		
		System.out.println("product========="+product);
		
		Assert.assertEquals("test이름", product.getProdName());
		Assert.assertEquals("test디테일", product.getProdDetail());
		Assert.assertEquals("test제조일자", product.getManuDate());
		Assert.assertEquals(1000, product.getPrice());
		Assert.assertEquals("test사진", product.getFileName());
		
	}
	//@Test
	public void testGetProduct() throws Exception{
		
		Product product = new Product();
		
		
		product.setProdName("testProdName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("ManuDate");
		product.setPrice(1000);
		product.setFileName("testFileName");
		System.out.println(product);
		//product = productService.getProduct(0);
		//System.out.println(product);
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("ManuDate", product.getManuDate());
		Assert.assertEquals(1000, product.getPrice());
		Assert.assertEquals("testFileName", product.getFileName());
		
		//Assert.assertNotNull(productService.getProduct(0));
		//Assert.assertNotNull(productService.getProduct(10000));
	}
	@Test
	public void testUpdateProduct() throws Exception{
		
		
		Product product = productService.getProduct(10120);
		System.out.println(product);
	//	product.setProdName("testProdName");
	//	product.setProdDetail("testProdDetail");
	//	product.setManuDate("ManuDate");
	//	product.setPrice(1000);
	//	product.setFileName("testFileName");
	//	Assert.assertNotNull(product);
	//	
	//	Assert.assertEquals("testProdName", product.getProdName());
	//	Assert.assertEquals("testProdDetail", product.getProdDetail());
	//	Assert.assertEquals("testManuDate", product.getManuDate());
	//	Assert.assertEquals(1000, product.getPrice());
	//	Assert.assertEquals("testFileName", product.getFileName());
		
		product.setProdName("change");
		product.setProdDetail("change detail");
		product.setManuDate("changedate");
		product.setPrice(5000);
		product.setFileName("changeFileName");
		
		productService.updateProduct(product);
		
	//	product = productService.getProduct(10073);
	//	Assert.assertNotNull(product);
	
	//	Assert.assertEquals("change", product.getProdName());
	//	Assert.assertEquals("change detail", product.getProdDetail());
	//	Assert.assertEquals("changedate", product.getProdName());
	//	Assert.assertEquals(5000, product.getPrice());
		//Assert.assertEquals("", product.getFileName());
		System.out.println("product ===== "+product);
	}
	//@Test
	public void testGetProductListAll() throws Exception{
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
		
		System.out.println("========================");
		
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);
		
		list = (List<Object>)map.get("list");
		Assert.assertEquals(3,  list.size());
		
		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
	}
	
	//@Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("자전거");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	// @Test
	 public void testGetProductListByPrice() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("200000");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}
