package in.test.pms.exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String msg) {
    
		super(msg);
	}
}
