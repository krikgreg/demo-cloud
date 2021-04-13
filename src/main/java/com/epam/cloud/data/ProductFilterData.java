package com.epam.cloud.data;

import java.math.BigDecimal;


public class ProductFilterData
{
	private String name;

	private String field;

	private BigDecimal priceFrom;

	private BigDecimal priceTo;

	private String sort;

	public ProductFilterData(final String name, final String field, final BigDecimal priceFrom, final BigDecimal priceTo,
			final String sort)
	{
		this.name = name;
		this.field = field;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.sort = sort;
	}

	public String getField()
	{
		return field;
	}

	public void setField(final String field)
	{
		this.field = field;
	}

	public BigDecimal getPriceFrom()
	{
		return priceFrom;
	}

	public void setPriceFrom(final BigDecimal priceFrom)
	{
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo()
	{
		return priceTo;
	}

	public void setPriceTo(final BigDecimal priceTo)
	{
		this.priceTo = priceTo;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(final String sort)
	{
		this.sort = sort;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
}
