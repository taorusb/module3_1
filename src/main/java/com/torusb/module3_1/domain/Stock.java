package com.torusb.module3_1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Stock {

	@Id
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "currency")
	private String currency;
	@Column(name = "previous_volume")
	private Integer previousVolume;
	@Column(name = "volume")
	private Integer volume;
	@Column(name = "latest_price")
	private Integer latestPrice;
	@Transient
	private Integer changeValue;

	public Integer getVolume() {
		return Optional.ofNullable(volume).orElse(previousVolume);
	}
}
