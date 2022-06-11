package domainapp.modules.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

@Configuration
@ComponentScan(basePackages = {"repository","service"})
public class Config {

    @Bean
    public PersistenceManagerFactory getPMFPostgres(){ return JDOHelper.getPersistenceManagerFactory("postgresql");
    }

}
