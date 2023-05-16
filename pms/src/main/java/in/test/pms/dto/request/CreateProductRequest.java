package in.test.pms.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
	
	@NotEmpty(message = "product name should not be null or empty")
	private String productName;
	
	@NotEmpty(message = "product Description should not be null or empty")
	private String  productDescription;
    
	@NotNull(message = "product price should not be null")
	@Positive(message = "product price should be greter then zero")
	private BigDecimal  productPrice; 


	
	

}
