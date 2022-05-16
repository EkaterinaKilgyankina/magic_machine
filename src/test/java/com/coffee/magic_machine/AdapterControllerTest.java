//
//package com.zaptain.providergateway.provideradapter.currencycloudadapter.controller;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.zaptain.paymentgateway.provideradapter.api.model.CurrencyExchangeQuoteResponse;
//import com.zaptain.paymentgateway.provideradapter.api.model.CurrencyExchangeRequest;
//import com.zaptain.paymentgateway.provideradapter.api.model.CurrencyExchangeResponse;
//import com.zaptain.paymentgateway.provideradapter.api.model.GetIndicativeRatesResponse;
//import com.zaptain.paymentgateway.provideradapter.api.model.IndicativeRate;
//import com.zaptain.providergateway.provideradapter.currencycloudadapter.service.AdapterService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//    @ActiveProfiles("test")
//    @WebMvcTest(controllers = AdapterController.class)
//    @SuppressWarnings("LineLength")
//    class AdapterControllerTest {
//        @Autowired
//        MockMvc mockMvc;
//        @Autowired
//        ObjectMapper objectMapper;
//        @MockBean
//        AdapterService service;
//
//        @Test
//        void getIndicative() throws Exception {
//            final GetIndicativeRatesResponse response = new GetIndicativeRatesResponse()
//                    .setRates(List.of(
//                            new IndicativeRate()
//                                    .setSellCurrency("USD")
//                                    .setBuyCurrency("EUR")
//                    ));
//            when(service.getIndicative(List.of("USDEUR")))
//                    .thenReturn(response);
//
//            //test
//            mockMvc.perform(get("/v1/fx/rates/indicative?currencyPairs=USDEUR"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
//        }
//
//        @Test
//        void getQuote() throws Exception {
//            final CurrencyExchangeQuoteResponse response = new CurrencyExchangeQuoteResponse()
//                    .setSellCurrency("USD")
//                    .setBuyCurrency("EUR");
//            when(service.getExchangeQuote("USD", "EUR", new BigDecimal("100"), "EUR"))
//                    .thenReturn(response);
//
//            //test
//            mockMvc.perform(get("/v1/fx/rates/quotes/USD/EUR?amount=100&amountCurrency=EUR"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
//        }
//
//        @Test
//        void getQuoteBadRequest() throws Exception {
//            //test
//            mockMvc.perform(get("/v1/fx/rates/quotes/USD1/EUR?amount=100&amountCurrency=EUR"))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [sellCurrency]\"}"));
//        }
//
//        @Test
//        void getQuoteBadRequestWrongFormatAmount() throws Exception {
//            //test
//            mockMvc.perform(get("/v1/fx/rates/quotes/USD/EUR?amount=aaa&amountCurrency=EUR"))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [amount]\"}"));
//        }
//
//        @Test
//        void getQuoteBadRequestNoAmount() throws Exception {
//            //test
//            mockMvc.perform(get("/v1/fx/rates/quotes/USD/EUR"))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [amount]\"}"));
//        }
//
//        @Test
//        void exchangeWrongFormatBuyCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR1")
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [buyCurrency]\"}"));
//        }
//
//        @Test
//        void exchangeEmptyBuyCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [buyCurrency]\"}"));
//        }
//
//        @Test
//        void exchangeWrongFormatSellCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD1")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [sellCurrency]\"}"));
//        }
//
//        @Test
//        void exchangeEmptySellCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [sellCurrency]\"}"));
//        }
//
//        @Test
//        void exchangeWrongFormatAmount() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("-100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [amount]\"}"));
//        }
//
//        @Test
//        void exchangeEmptyAmount() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [amount]\"}"));
//        }
//
//        @Test
//        void exchangeEmptyUniqRequestId() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setQuoteId(UUID.randomUUID())
//                    .setSellCurrency("USD");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [uniqRequestId]\"}"));
//        }
//
//        @Test
//        void exchange() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setSellCurrency("USD")
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//            final CurrencyExchangeResponse response = new CurrencyExchangeResponse()
//                    .setValueDate(LocalDate.now())
//                    .setUniqRequestId(request.getUniqRequestId());
//            when(service.exchange(request)).thenReturn(response);
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andDo(MockMvcResultHandlers.print())
//                    .andExpect(status().isCreated())
//                    .andExpect(content()
//                            .json(objectMapper.writeValueAsString(response)));
//        }
//
//        @Test
//        void exchangeWrongJsonAmount() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("EUR")
//                    .setQuoteId(UUID.randomUUID())
//                    .setSellCurrency("USD")
//                    .setUniqRequestId("RRRRRRRRRR");
//            final CurrencyExchangeResponse response = new CurrencyExchangeResponse()
//                    .setValueDate(LocalDate.now())
//                    .setUniqRequestId(request.getUniqRequestId());
//            when(service.exchange(request)).thenReturn(response);
//
//            final JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(request));
//            final String payload = ((ObjectNode) jsonNode)
//                    .put("amount", "fff.9")
//                    .toPrettyString()
//                    .replace("\"fff.9\"", "fff.9");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(payload))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [amount]\"}"));
//        }
//
//        @Test
//        void exchangeWrongFormatAmountCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("EUR")
//                    .setAmountCurrency("USD1")
//                    .setQuoteId(UUID.randomUUID())
//                    .setSellCurrency("USD")
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1110\",\"message\":\"Wrong format: [amountCurrency]\"}"));
//        }
//
//        @Test
//        void exchangeEmptyAmountCurrency() throws Exception {
//            final CurrencyExchangeRequest request = new CurrencyExchangeRequest()
//                    .setAmount(new BigDecimal("100"))
//                    .setBuyCurrency("USD")
//                    .setSellCurrency("EUR")
//                    .setAmountCurrency(null)
//                    .setQuoteId(UUID.randomUUID())
//                    .setUniqRequestId("RRRRRRRRRR");
//
//            //test
//            mockMvc.perform(post("/v1/fx/exchange")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(request)))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content()
//                            .json("{\"code\":\"1100\",\"message\":\"Required value not found: [amountCurrency]\"}"));
//        }
//
//    }
//
//
//}
