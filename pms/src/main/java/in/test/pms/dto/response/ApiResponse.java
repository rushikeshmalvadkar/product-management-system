package in.test.pms.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private T data;

	private Integer statusCode;

	private List<String> errors;

	public ApiResponse(T data, Integer status) {
		this.data = data;
		this.statusCode = status;
	}

	public ApiResponse(List<String> errors, Integer status) {
		this.errors = errors;
		this.statusCode = status;
	}

	/*
	 * This will be help during serialization, automatically status type property
	 * will be added into final JSON
	 * 
	 * Note : statusType property name is decided from getter method name
	 */
	public String getStatusType() {
		return HttpStatus.valueOf(statusCode).name();
	}

}
