package br.gov.al.sefaz.test.cucumber;

import br.gov.al.sefaz.test.IntegrationTest;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
