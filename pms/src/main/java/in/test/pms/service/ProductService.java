package in.test.pms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import in.test.pms.dto.request.CreateProductRequest;
import in.test.pms.dto.request.ProductPageRequest;
import in.test.pms.dto.request.UpdateProductRequest;
import in.test.pms.dto.response.ProductPagedResponse;
import in.test.pms.dto.response.ProductResponse;
import in.test.pms.entity.ProductEntity;
import in.test.pms.enums.StatusEnum;
import in.test.pms.exception.ProductNotFoundException;
import in.test.pms.mapper.ProductMapper;
import in.test.pms.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private static final String DEFAULT_SORT_BY = "updatedDateTime";

	private final ProductMapper productMapper;

	private final ProductRepository productRepository;

	public ProductResponse createProduct(CreateProductRequest createProductRequest) {
		log.debug("<<<<<<<<< createProduct() ");
		ProductEntity productEntity = this.productMapper.toEntity(createProductRequest);
		ProductEntity savedEntity = this.productRepository.save(productEntity);
		log.debug("createProduct() >>>>>>>>");

		return this.productMapper.toDto(savedEntity);
	}

	public ProductResponse fetchProductById(Integer productId) {
		log.debug("<<<<<<<<< fetchProductById()");
		ProductEntity productEntity = getProductEntityById(productId);
		log.debug("fetchProductById() >>>>>>>>>");
		return this.productMapper.toDto(productEntity);
	}

	private ProductEntity getProductEntityById(Integer productId) {
		log.debug("<<<<<<<<< getProductEntityById()");
		Optional<ProductEntity> product = this.productRepository.findById(productId);
		if (product.isPresent()) {
			log.debug("getProductEntityById() >>>>>>>");
			return product.get();
		} else {
			throw new ProductNotFoundException(String.format("product does not exists with id : %s", productId));
		}
	}

	public void deleteProduct(Integer id) {
		log.debug("<<<<<<<<< deleteProduct()");
		ProductEntity productEntity = getProductEntityById(id);
		productEntity.setStatus(StatusEnum.IN_ACTIVE);
		this.productRepository.save(productEntity);
		log.debug("deleteProduct() >>>>>>>>>");
	}

	public ProductPagedResponse getPagedProductList(ProductPageRequest productPageRequest) {
		Integer pageNumber = productPageRequest.getPageNumber() - 1;
		Integer pageSize = productPageRequest.getPageSize();
		Sort sort = Sort.by(Direction.DESC, DEFAULT_SORT_BY);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// To add where condition
		ProductEntity productEntity = new ProductEntity();
		productEntity.setStatus(StatusEnum.ACTIVE);

		Example<ProductEntity> productEntityExample = Example.of(productEntity);
		Page<ProductEntity> productPage = this.productRepository.findAll(productEntityExample, pageable);
		return prepareProductPagedResponse(productPage);

	}

	private ProductPagedResponse prepareProductPagedResponse(Page<ProductEntity> productPage) {
		List<ProductEntity> entities = productPage.getContent();
		List<ProductResponse> dtoList = this.productMapper.toDtoList(entities);
		ProductPagedResponse productPagedResponse = new ProductPagedResponse();
		productPagedResponse.setProductList(dtoList);
		productPagedResponse.setFirstPage(productPage.isFirst());
		productPagedResponse.setHasNext(productPage.hasNext());
		productPagedResponse.setHasPrevious(productPage.hasPrevious());
		productPagedResponse.setLastPage(productPage.isLast());
		productPagedResponse.setTotalRecords(productPage.getTotalElements());

		return productPagedResponse;
	}

	public ProductResponse updateProduct(UpdateProductRequest updateProductRequest) {
		log.debug("<<<<<<<<<< updateProduct()");
		ProductEntity dbProductData = this.getProductEntityById(updateProductRequest.getProductId());

		dbProductData.setName(updateProductRequest.getProductName());
		dbProductData.setDescription(updateProductRequest.getProductDescription());
		dbProductData.setPrice(updateProductRequest.getProductPrice());

		ProductEntity updatedProductEntity = this.productRepository.save(dbProductData);

		log.debug("updateProduct>>>>>>>>");
		return this.productMapper.toDto(updatedProductEntity);
	}

}
