package app.log;

public class LogMessageBuilder {

	private static final String MESSAGE_TEMPALTE = "[Operation: %s][%s][Message: %s]";
	private static final String SUCCESS_TAG = "SUCCESS";
	private static final String EXCEPTION_TAG = "EXCEPTION";
	
	private String operation;
	
	public LogMessageBuilder(String operation) {
		this.operation = operation;
	}
	
	public String buildRequestStartMessage() {
		return String.format(MESSAGE_TEMPALTE, operation, SUCCESS_TAG, "Request Start");
	}

	public String buildSuccessMessage(String message) {
		return String.format(MESSAGE_TEMPALTE, operation, SUCCESS_TAG, message);
	}
	
	public String buildExceptionMessage(String message) {
		return String.format(MESSAGE_TEMPALTE, operation, EXCEPTION_TAG, message);
	}

}
