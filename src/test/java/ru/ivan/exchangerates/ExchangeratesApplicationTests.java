package ru.ivan.exchangerates;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ivan.exchangerates.feign.ExchangeRatesClient;
import ru.ivan.exchangerates.feign.GifClient;

@WebMvcTest
class ExchangeratesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExchangeRatesClient exchangeRatesClient;
	@MockBean
	private GifClient gifClient;

	@Test
	void  fullTestForExchangeRatesController() throws Exception {
		/*
		Данный тест не работает. ExchangeRatesController использует 2 вызова одного и того же
		внешнего сервиса (ExchangeRatesClient) с разными выходными данными. Я не имею понятия о том,
		как можно поставить на него Mock...
		Так же функции сервисов возвращают много данных,
		из-за чего дальнейшая их фальсификация становится затруднительной.
		 */


		String url = new JSONObject().put("data",
				new JSONArray().put(
				new JSONObject().put("images",
				new JSONObject().put("original",
				new JSONObject().put("url", "broke.gif"))))).toString();

		String yr = new JSONObject().put("rates",
				new JSONObject().put("RUB", 74).put("USD", 1)).toString();
		String tr = new JSONObject().put("rates",
				new JSONObject().put("RUB", 73).put("USD", 1)).toString();

		String response = new JSONObject()
				.put("rate", "Broke")
				.put("message", "Reply received successfully!")
				.put("url", "broke.gif")
				.toString();

		when(gifClient.getGif("", "", 1)).thenReturn(url);
		when(exchangeRatesClient.getExchangeRates("", "2021-09-03")).thenReturn(tr);
		when(exchangeRatesClient.getExchangeRates("", "2021-09-02")).thenReturn(yr);

		this.mockMvc.perform(get("/api/rate")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(response)));
	}

}
