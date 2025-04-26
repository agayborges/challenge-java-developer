package br.com.neurotech.challenge.entity;

public enum VehicleModel {
	HATCH(new HatchCreditStrategy()),
	SUV(new SuvCreditStrategy());

	private final CreditStrategy creditStrategy;

	VehicleModel(CreditStrategy creditStrategy) {
		this.creditStrategy = creditStrategy;
	}

	public boolean checkCredit(NeurotechClient neurotechClient) {
		return creditStrategy.checkCredit(neurotechClient);
	}

}

