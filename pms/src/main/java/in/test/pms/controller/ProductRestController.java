package in.test.pms.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.test.pms.dto.request.CreateProductRequest;
import in.test.pms.dto.request.ProductPageRequest;
import in.test.pms.dto.request.UpdateProductRequest;
import in.test.pms.dto.response.ApiResponse;
import in.test.pms.dto.response.ProductPagedResponse;
import in.test.pms.dto.response.ProductResponse;
import in.test.pms.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated // I have added this to enable adding validation annotation on path variable
@Slf4j
public class ProductRestController {

	private final ProductService productService;

	@PostMapping("/products")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
		log.debug("<<<<<<<<< createProduct()");
		ProductResponse savedProduct = this.productService.createProduct(createProductRequest);
		log.info("Product create sucessfully with id {}", savedProduct.getProductId());
		log.debug("createProduct() >>>>>>>>");
		return new ApiResponse<>(savedProduct, HttpStatus.CREATED.value());
	}

	@GetMapping("/products/{id}")
	public ApiResponse<ProductResponse> getProductById(
			@PathVariable("id") @Positive(message = "product id should be greter then zero") Integer productId) {
		log.debug("<<<<<<<<< getProductById ");
		ProductResponse product = this.productService.fetchProductById(productId);
		log.debug("getProductById >>>>>>>>");

		return new ApiResponse<>(product, HttpStatus.OK.value());
	}

	@DeleteMapping("/products/{id}")
	public ApiResponse<Boolean> deleteProductById(
			@PathVariable("id") @Positive(message = "product id should be greter then zero") Integer id) {
		log.debug("<<<<<<<<< deleteProductById()");
		this.productService.deleteProduct(id);
		log.debug("deleteProductById>>>>>>>>");
		return new ApiResponse<>(true, HttpStatus.OK.value());
	}

	@PutMapping("/products")
	public ApiResponse<ProductResponse> updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest) {
		log.debug("<<<<<<<<< updateProduct()");
		ProductResponse updatedProduct = this.productService.updateProduct(updateProductRequest);
		log.debug("updateProduct() >>>>>>>>");
		return new ApiResponse<>(updatedProduct, HttpStatus.OK.value());
	}

	@PostMapping("/paged-products")
	public ApiResponse<ProductPagedResponse> getAllProduct(@Valid @RequestBody ProductPageRequest productPageRequest) {
		log.debug("<<<<<<<<< getAllProduct()");
		ProductPagedResponse pagedProductList = this.productService.getPagedProductList(productPageRequest);
		log.debug("getAllProduct() >>>>>>>>>");
		return new ApiResponse<>(pagedProductList, HttpStatus.OK.value());
	}

}
