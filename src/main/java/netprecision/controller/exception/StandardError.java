package netprecision.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StandardError {

	private Long timestamp;
	private Integer status;
	private String error;
}
