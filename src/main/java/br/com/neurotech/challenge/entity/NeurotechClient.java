package br.com.neurotech.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "clients", indexes = {
		@Index(name = "idx_client_age", columnList = "age"),
		@Index(name = "idx_client_income", columnList = "income"),
})
public class NeurotechClient {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank(message = "O nome do cliente é obrigatório")
	@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
	private String name;

	@NotNull(message = "A idade é obrigatória")
	@PositiveOrZero(message = "A idade deve ser maior ou igual a zero")
	private Short age;

	@NotNull(message = "A renda é obrigatória")
	@Digits(integer = 10, fraction = 2, message = "A renda deve ter no máximo 10 dígitos inteiros e 2 decimais")
	private BigDecimal income;

	public NeurotechClient() {}

	public NeurotechClient(UUID id, String name, Short age, BigDecimal income) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.income = income;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}
}