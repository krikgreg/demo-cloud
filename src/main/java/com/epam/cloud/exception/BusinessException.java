package com.epam.cloud.exception;

public class BusinessException extends RuntimeException
{
	public BusinessException(final String message)
	{
		super(message);
	}
}
