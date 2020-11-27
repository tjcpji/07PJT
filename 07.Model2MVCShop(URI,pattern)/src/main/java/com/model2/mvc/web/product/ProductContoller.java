package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductContoller {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	
	public ProductContoller() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product")Product product) throws Exception{
		
		System.out.println("addProduct");
		
		productService.addProduct(product);
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		
		System.out.println("/addProductView");
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping(value="getProduct")
	public String getProduct(@RequestParam("prodNo")int prodNo, Model model, HttpServletRequest request) throws Exception{
		
		System.out.println("/getProduct");
		
		String menu = request.getParameter("menu");
		System.out.println("menu : "+menu);
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product",product);
		System.out.println("product : "+product);
		
		if (menu != null && menu.equals("manage")) { 
			return "forward:/product/updateProductView.jsp";
		}
		
		return "forward:/product/getProduct.jsp";
	}

	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product")Product product, Model model) throws Exception{
		
		System.out.println("/updateProduct");
		
		productService.updateProduct(product);
		
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct";
	}
	
//	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
//	public ModelAndView updateProduct(@ModelAttribute("product") Product product) throws Exception{
//		
//		
//		productService.updateProduct(product);
//		
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("/getProduct.jsp");
//		modelAndView.addObject("product", product);
//		
//		return modelAndView;
//	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/updateProductView");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct");
		
		String menu = request.getParameter("menu");
		System.out.println("menu : "+menu);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage : "+resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		//model.addAttribute("product", product);
		
		return "forward:/product/listProduct.jsp";
	}
}
