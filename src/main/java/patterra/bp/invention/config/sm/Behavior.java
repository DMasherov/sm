package patterra.bp.invention.config.sm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import patterra.domain.Invention;

import java.util.Collections;
import java.util.Map;

@Configuration
public class Behavior {
    @Bean
    public Map<Object, Object> variablesForBP() {
        Invention invention = new Invention();
        invention.setId(1);
        invention.setDocuments(Collections.emptySet());
        return Collections.singletonMap("invention", invention);
    }
}
