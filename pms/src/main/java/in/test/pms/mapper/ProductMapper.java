package in.test.pms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.test.pms.dto.request.CreateProductRequest;
import in.test.pms.dto.response.ProductResponse;
import in.test.pms.entity.ProductEntity;

@Component
public class ProductMapper {

	public ProductEntity toEntity(CreateProductRequest dto) {
		
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName(dto.getProductName());
		productEntity.setDescription(dto.getProductDescription());
		productEntity.setPrice(dto.getProductPrice());
		return productEntity;
		
		
	}

	public ProductResponse toDto(ProductEntity productEntity) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(productEntity.getId());
		productResponse.setProductName(productEntity.getName());
		productResponse.setProductDescription(productEntity.getDescription());
		productResponse.setProductPrice(productEntity.getPrice());
		productResponse.setStatus(productEntity.getStatus());
		productResponse.setProductCreatedDateTime(productEntity.getCreatedDateTime());
		productResponse.setProductUpdatedDateTime(productEntity.getUpdatedDateTime());
		return productResponse;
	}

	public List<ProductResponse> toDtoList(List<ProductEntity> productEntites) {
		List<ProductResponse> productsResponse = new ArrayList<>();
		for (ProductEntity product : productEntites) {
			ProductResponse dto = toDto(product);
			productsResponse.add(dto);
		}
		return productsResponse;
	}

}
