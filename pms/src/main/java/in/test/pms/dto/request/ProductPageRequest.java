package in.test.pms.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageRequest {
    
	@NotNull(message="pageNumber should not be null")
	@Positive(message = "page number should be greter then zero")
	private Integer pageNumber = 1;
	
	@NotNull(message="pageSize should not be null")
	@Positive(message = "page size should be greter then zero")
	private Integer pageSize = 5;
	
	
	
}
