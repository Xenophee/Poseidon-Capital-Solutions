package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type", 10d);

		// Save
		trade = tradeRepository.save(trade);
		assertThat(trade.getTradeId()).isNotNull();
		assertThat(trade.getAccount()).isEqualTo("Trade Account");

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertThat(trade.getAccount()).isEqualTo("Trade Account Update");

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertThat(listResult).isNotEmpty();

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertThat(tradeList).isNotPresent();
	}
}