package in.test.pms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import in.test.pms.dto.request.CreateProductRequest;
import in.test.pms.dto.request.ProductPageRequest;
import in.test.pms.dto.response.ProductPagedResponse;
import in.test.pms.dto.response.ProductResponse;
import in.test.pms.entity.ProductEntity;
import in.test.pms.exception.ProductNotFoundException;
import in.test.pms.mapper.ProductMapper;
import in.test.pms.repository.ProductRepository;

class ProductServiceTest {

	@Test
	 void createProductTest1() {
		ProductRepository productRepoMock = Mockito.mock(ProductRepository.class);
		ProductMapper productMapper = new ProductMapper();
		CreateProductRequest createProductRequest = new CreateProductRequest();
		createProductRequest.setProductName("mobile");
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(1);
		productEntity.setName("mobile");
		Mockito.when(productRepoMock.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);
		ProductService productService = new ProductService(productMapper, productRepoMock);
		ProductResponse ProductResponse = productService.createProduct(createProductRequest);
		assertEquals("mobile", ProductResponse.getProductName());
		assertEquals(1, ProductResponse.getProductId());
	}

	@Test
	 void createProductTest2() {
		ProductRepository productRepoMock = Mockito.mock(ProductRepository.class);
		ProductMapper productMapper = new ProductMapper();
		
		ProductService productService = new ProductService(productMapper, productRepoMock);

		NullPointerException ex = assertThrows(NullPointerException.class, () -> productService.createProduct(null));
		Assertions.assertThat(ex.getMessage()).isEqualTo("product object should not be null");
	}
	
	@Test
    void	fetchProductById(){
		ProductRepository productRepoMock = Mockito.mock(ProductRepository.class);
		ProductMapper productMapper = new ProductMapper();
		Mockito.when(productRepoMock.findById(2)).thenReturn(Optional.empty());
		ProductService productService = new ProductService(productMapper, productRepoMock);
		ProductNotFoundException assertThrows2 = assertThrows(ProductNotFoundException.class,()->productService.fetchProductById(2));
		
	}
	
	@Test
    void	fetchProductByIdWithoutNull(){
		ProductRepository productRepoMock = Mockito.mock(ProductRepository.class);
		ProductMapper productMapper = new ProductMapper();
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(1);
		productEntity.setName("mobile");
		Mockito.when(productRepoMock.findById(1)).thenReturn(Optional.ofNullable(productEntity));
		ProductService productService = new ProductService(productMapper, productRepoMock);
		ProductResponse productResponce = productService.fetchProductById(1);
		assertThat(productResponce.getProductId()).isEqualTo(1);
	}
	
	@Test
	void getPagedProductList() {
		ProductRepository productRepoMock = Mockito.mock(ProductRepository.class);
		ProductMapper productMapper = new ProductMapper();
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(1);
		productEntity.setName("mobile");
		List<ProductEntity> products = new ArrayList<>();
          products.add(productEntity);
          
        ProductPageRequest productPageRequest = new ProductPageRequest();
        productPageRequest.setPageNumber(1);
        productPageRequest.setPageSize(1);
		Integer pageNumber=productPageRequest.getPageNumber();
		Integer pageSize=productPageRequest.getPageSize();
		//Pageable pageble = PageRequest.of(pageNumber,pageSize);
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		
		ProductEntity productEntityExampale = new ProductEntity();
		productEntity.setName("mobile");
		Example<ProductEntity> exampale = Example.of(productEntityExampale);
		Page page = new PageImpl<>(products, pageable, 1);
		
		Mockito.when(productRepoMock.findAll(Mockito.any(Example.class) ,Mockito.any(Pageable.class))).thenReturn(page);
		ProductService productService = new ProductService(productMapper, productRepoMock);
		ProductPagedResponse pagedProductList = productService.getPagedProductList(productPageRequest);
		assertEquals(1, pagedProductList.getProductList().size());
	} 
	
	
	
	
     
}
