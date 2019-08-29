package com.example.easynotes.datesObj;

public class SummaryDetailes {

	private String name;
	private Long sumPrice;
	private Long daysCounted;

	public SummaryDetailes(String name, Long sumPrice, Long daysCounted) {
		super();
		this.name = name;
		this.sumPrice = sumPrice;
		this.daysCounted = daysCounted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Long sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Long getDaysCounted() {
		return daysCounted;
	}

	public void setDaysCounted(Long daysCounted) {
		this.daysCounted = daysCounted;
	}

}
