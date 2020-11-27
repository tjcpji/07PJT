package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println(getClass().getName()+" : setSqlSessino() call");
		this.sqlSession = sqlSession;
	}
	
	public ProductDaoImpl() {
		System.out.println(getClass()+" : default Constructor call");
	}
	
	//insert
	public void addProduct(Product product) throws Exception {
		System.out.println("productMapper.addProduct start");
		sqlSession.insert("ProductMapper.addProduct",product);

	}

	//selectOne
	public Product getProduct(int prodNo) throws Exception {
		
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	//selectList
	public List<Product> getProductList(Search search) throws Exception {
		
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}

	//update
	public void updateProduct(Product product) throws Exception {
		
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	
	//totalCount
	public int getTotalCount(Search search) throws Exception{
		
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	
	}
}
