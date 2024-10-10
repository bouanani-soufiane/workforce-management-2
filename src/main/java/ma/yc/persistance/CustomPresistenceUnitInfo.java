package ma.yc.persistance;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import ma.yc.util.DotenvReader;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class CustomPresistenceUnitInfo implements PersistenceUnitInfo {
    @Override
    public String getPersistenceUnitName () {
        return "workforce";
    }

    @Override
    public String getPersistenceProviderClassName () {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType () {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource () {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource () {
        return null;
    }

    @Override
    public List<String> getMappingFileNames () {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls () {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl () {
        return null;
    }

    @Override
    public List<String> getManagedClassNames () {
        return List.of(
                "ma.yc.entity.Admin",
                "ma.yc.entity.Candidature",
                "ma.yc.entity.Employee",
                "ma.yc.entity.FamillyAllowance",
                "ma.yc.entity.HistoryUpdate",
                "ma.yc.entity.JobOffer",
                "ma.yc.entity.Person",
                "ma.yc.entity.Vacation"
        );
    }

    @Override
    public boolean excludeUnlistedClasses () {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode () {
        return null;
    }

    @Override
    public ValidationMode getValidationMode () {
        return null;
    }

    @Override
    public Properties getProperties () {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.connection.driver_class", DotenvReader.get("DB_DRIVER"));
        properties.setProperty("hibernate.connection.url", DotenvReader.get("DB_URL"));
        properties.setProperty("hibernate.connection.username", DotenvReader.get("DB_USERNAME"));
        properties.setProperty("hibernate.connection.password", DotenvReader.get("DB_PASSWORD"));
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion () {
        return "";
    }

    @Override
    public ClassLoader getClassLoader () {
        return null;
    }

    @Override
    public void addTransformer ( ClassTransformer classTransformer ) {

    }

    @Override
    public ClassLoader getNewTempClassLoader () {
        return null;
    }
}

