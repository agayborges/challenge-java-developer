package br.com.neurotech.challenge.entity;

public enum VehicleModel {
	HATCH(new HatchCheckCreditStrategy()),
	SUV(new SuvCheckCreditStrategy());

	private final CheckCreditStrategy checkCreditStrategy;

	VehicleModel(CheckCreditStrategy checkCreditStrategy) {
		this.checkCreditStrategy = checkCreditStrategy;
	}

	public boolean checkCredit(NeurotechClient neurotechClient) {
		return checkCreditStrategy.checkCredit(neurotechClient);
	}

}

