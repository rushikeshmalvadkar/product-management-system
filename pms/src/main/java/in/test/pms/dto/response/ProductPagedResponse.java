package in.test.pms.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPagedResponse {
  
	private List<ProductResponse> productList;
	
	private long totalRecords;
	
	private boolean isFirstPage;
	
	private boolean isLastPage;
	
	private boolean hasPrevious;
	
	private boolean hasNext;
	
	
	
	
	
	
	
	
	
		
	
}
