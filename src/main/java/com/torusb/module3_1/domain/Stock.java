package com.torusb.module3_1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		Stock stock = (Stock) o;
		return companyName != null && Objects.equals(companyName, stock.companyName);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
