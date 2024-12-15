package hcs.hellospring.exrate;

import hcs.hellospring.PaymentConfig;
import hcs.hellospring.payment.ExRateProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest(value = RestClientExRateProvider.class)
@ContextConfiguration(classes = {PaymentConfig.class})
class RestClientExRateProviderTest {

    @Value("${hello.base-url}")
    private String baseUrl;
    @Autowired
    private RestClient.Builder builder;
    private final MockRestServiceServer mockServer;
    private ExRateProvider exRateProvider;

    public RestClientExRateProviderTest(@Autowired RestClient.Builder builder) {
        this.mockServer = MockRestServiceServer.bindTo(builder).ignoreExpectOrder(true).build();
    }

    @BeforeEach
    void setUp() {
        exRateProvider = new RestClientExRateProvider(builder, baseUrl);
    }

    @AfterEach
    void afterEach(){
        this.mockServer.verify();
    }

    @Test
    void normalApiCall() {
        String expectedResult = """
                {"result":"success","provider":"https://www.exchangerate-api.com","documentation":"https://www.exchangerate-api.com/docs/free","terms_of_use":"https://www.exchangerate-api.com/terms","time_last_update_unix":1733529751,"time_last_update_utc":"Sat, 07 Dec 2024 00:02:31 +0000","time_next_update_unix":1733616781,"time_next_update_utc":"Sun, 08 Dec 2024 00:13:01 +0000","time_eol_unix":0,"base_code":"USD","rates":{"USD":1,"AED":3.6725,"AFN":68.4003,"ALL":93.600729,"AMD":401.437954,"ANG":1.79,"AOA":920.343044,"ARS":1015.75,"AUD":1.561063,"AWG":1.79,"AZN":1.70015,"BAM":1.850781,"BBD":2,"BDT":119.523744,"BGN":1.850527,"BHD":0.376,"BIF":2928.42441,"BMD":1,"BND":1.341097,"BOB":6.921221,"BRL":6.008094,"BSD":1,"BTN":84.719815,"BWP":13.616813,"BYN":3.280978,"BZD":2,"CAD":1.411006,"CDF":2849.267653,"CHF":0.878254,"CLP":971.229672,"CNY":7.272977,"COP":4442.102613,"CRC":511.540885,"CUP":24,"CVE":104.342603,"CZK":23.726776,"DJF":177.721,"DKK":7.056785,"DOP":60.216567,"DZD":133.747303,"EGP":49.979442,"ERN":15,"ETB":122.562696,"EUR":0.946291,"FJD":2.278299,"FKP":0.784238,"FOK":7.056781,"GBP":0.784362,"GEL":2.79661,"GGP":0.784238,"GHS":14.951175,"GIP":0.784238,"GMD":72.422929,"GNF":8603.847301,"GTQ":7.710313,"GYD":209.068242,"HKD":7.780084,"HNL":25.299467,"HRK":7.129818,"HTG":131.169239,"HUF":391.343782,"IDR":15858.515572,"ILS":3.589215,"IMP":0.784238,"INR":84.719857,"IQD":1311.144739,"IRR":41921.952417,"ISK":137.658311,"JEP":0.784238,"JMD":157.001574,"JOD":0.709,"JPY":150.059904,"KES":129.149149,"KGS":86.672066,"KHR":4034.723351,"KID":1.561049,"KMF":465.543875,"KRW":1421.453181,"KWD":0.307199,"KYD":0.833333,"KZT":514.374487,"LAK":21943.253847,"LBP":89500,"LKR":290.25839,"LRD":185.448549,"LSL":18.02554,"LYD":4.839883,"MAD":9.958754,"MDL":18.272349,"MGA":4655.722649,"MKD":58.399067,"MMK":2100.625825,"MNT":3393.640785,"MOP":8.013487,"MRU":39.793228,"MUR":46.468893,"MVR":15.448964,"MWK":1744.926737,"MXN":20.188867,"MYR":4.420921,"MZN":63.97118,"NAD":18.02554,"NGN":1561.577265,"NIO":36.777289,"NOK":11.12459,"NPR":135.551705,"NZD":1.71402,"OMR":0.384497,"PAB":1,"PEN":3.736767,"PGK":4.004418,"PHP":57.873645,"PKR":278.045907,"PLN":4.034097,"PYG":7839.164642,"QAR":3.64,"RON":4.70366,"RSD":110.745764,"RUB":100.526027,"RWF":1410.300536,"SAR":3.75,"SBD":8.490408,"SCR":14.202177,"SDG":510.840784,"SEK":10.911581,"SGD":1.341099,"SHP":0.784238,"SLE":22.72591,"SLL":22725.910257,"SOS":570.95959,"SRD":35.452732,"SSP":3765.102586,"STN":23.184091,"SYP":12905.23635,"SZL":18.02554,"THB":34.055952,"TJS":10.821822,"TMT":3.499818,"TND":3.137152,"TOP":2.351884,"TRY":34.78823,"TTD":6.751303,"TVD":1.561049,"TWD":32.394417,"TZS":2593.939625,"UAH":41.448511,"UGX":3667.36451,"UYU":42.328498,"UZS":12838.093651,"VES":48.7903,"VND":25374.816165,"VUV":118.7718,"WST":2.778456,"XAF":620.725167,"XCD":2.7,"XDR":0.75842,"XOF":620.725167,"XPF":112.922609,"YER":249.825361,"ZAR":18.025568,"ZMW":27.224799,"ZWL":5.6132}}
                """;
        mockServer.expect(ExpectedCount.manyTimes(), requestTo(baseUrl + "/v6/latest/USD"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        BigDecimal rate = exRateProvider.getExRate("USD");

        Assertions.assertThat(rate.doubleValue()).isEqualTo(1421.453181);
    }

    @Test
    void timeOutTest(){
        mockServer.expect(ExpectedCount.manyTimes(), requestTo(baseUrl + "/v6/latest/USD"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withGatewayTimeout());

        assertThrows(RuntimeException.class, () -> exRateProvider.getExRate("USD"));
    }

    @Test
    void returns4xxError() {
        mockServer.expect(ExpectedCount.manyTimes(), requestTo(baseUrl + "/v6/latest/USD"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        assertThrows(RuntimeException.class, () -> exRateProvider.getExRate("USD"));
    }
}