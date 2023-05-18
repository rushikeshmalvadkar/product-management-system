package in.test.pms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import in.test.pms.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
  
	private Integer productId;
	
	private String productName;
	
	private String productDescription;
	
	private BigDecimal productPrice;
	
	private StatusEnum status;
	
	private LocalDateTime productCreatedDateTime;
	
	private LocalDateTime productUpdatedDateTime; 
}
