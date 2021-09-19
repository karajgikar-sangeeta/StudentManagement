package jpa.exceptions;

public class JpaException extends Throwable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	


	public JpaException() {
        super();
    }

    public JpaException(String message) {
    	super(message);
    }

    public JpaException(String message, Throwable cause) {
        super(message, cause);
    }

    public JpaException(Throwable cause) {
        super(cause);
    }

    protected JpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
