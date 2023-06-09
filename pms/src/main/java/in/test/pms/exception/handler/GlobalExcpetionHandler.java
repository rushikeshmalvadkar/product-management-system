package in.test.pms.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import in.test.pms.dto.response.ApiResponse;
import in.test.pms.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExcpetionHandler {

	private static final String EXCEPTION_MESSAGE = "Exception occured : ";

	@ExceptionHandler(ProductNotFoundException.class)
	public ApiResponse<Object> handleProductNotFoundException(ProductNotFoundException ex) {
		String error = ex.getMessage();
		log.error(EXCEPTION_MESSAGE , ex);
		return new ApiResponse<>(Arrays.asList(error), HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> finalErrors = new ArrayList<>();
		List<ObjectError> errors = ex.getAllErrors();
		for (ObjectError error : errors) {
			String message = error.getDefaultMessage();
			finalErrors.add(message);
		}
		log.error(EXCEPTION_MESSAGE , ex);
		return new ApiResponse<>(finalErrors, HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class , MethodArgumentTypeMismatchException.class})
	public ApiResponse<Object> handlePathVaribaleVoilationException(Exception ex) {
		List<String> errors = new ArrayList<>();
		
		if (ex instanceof ConstraintViolationException) {
			errors.add(ex.getMessage());
		}
		
		if (ex instanceof MethodArgumentTypeMismatchException methodArgumentMismatchEX) {
			String name = methodArgumentMismatchEX .getName();
			errors.add("Incorrect data type value for " + name);
		}

		log.error(EXCEPTION_MESSAGE , ex);
		return new ApiResponse<>(errors, HttpStatus.BAD_REQUEST.value());
	}

}
