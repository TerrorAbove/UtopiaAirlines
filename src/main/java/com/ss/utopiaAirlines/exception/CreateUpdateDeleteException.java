package com.ss.utopiaAirlines.exception;

/**
 * 
 * @author David Pitcher
 *
 */
public class CreateUpdateDeleteException extends Throwable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436026077435628784L;
	
	public CreateUpdateDeleteException() {}
	
	public CreateUpdateDeleteException(String message)
	{
		super(message);
	}
}
