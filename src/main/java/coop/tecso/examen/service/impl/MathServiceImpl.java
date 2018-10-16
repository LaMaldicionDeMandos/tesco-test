package coop.tecso.examen.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import coop.tecso.examen.service.MathService;

@Service
public class MathServiceImpl implements MathService {

	@Override
	public BigDecimal sumar(final BigDecimal...val) {
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (BigDecimal param : val) {
			total = total.add(param.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		return total;
	}

}
