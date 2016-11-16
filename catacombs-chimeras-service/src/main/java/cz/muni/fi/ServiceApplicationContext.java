/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi;

import cz.muni.fi.facade.HeroFacadeImpl;
import cz.muni.fi.service.HeroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {HeroServiceImpl.class, HeroFacadeImpl.class})
public class ServiceApplicationContext {

    @Autowired
    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
