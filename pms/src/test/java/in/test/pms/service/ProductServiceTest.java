package in.test.pms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import in.test.pms.dto.request.CreateProductRequest;
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
	
	
	
	
     
}
