package in.test.pms.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import in.test.pms.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@DynamicUpdate // I have done this to update in sql query only changed value
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID" , updatable = false , nullable = false)
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PRICE")
	private BigDecimal price;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ACTIVE_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@Column(name = "CREATED_DATE_TIME")
	private LocalDateTime createdDateTime;

	@Column(name = "UPDATED_DATE_TIME")
	private LocalDateTime updatedDateTime;

	@PrePersist
	public void beforePersist() {
		this.createdDateTime = LocalDateTime.now();
		this.updatedDateTime=LocalDateTime.now();
		this.status = StatusEnum.ACTIVE;
	}

	@PreUpdate
	public void beforeUpdate() {
		this.updatedDateTime = LocalDateTime.now();
	}

}