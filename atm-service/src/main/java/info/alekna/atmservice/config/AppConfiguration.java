package info.alekna.atmservice.config;

import io.swagger.client.api.BankCoreApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${app.client.bank-core.base-url}")
    private String bankCoreApiUrl;

    @Bean
    public BankCoreApi getBankCoreApi() {
        var bankCoreApi = new BankCoreApi();
        bankCoreApi.getApiClient().setBasePath(bankCoreApiUrl);
        return bankCoreApi;
    }

}
