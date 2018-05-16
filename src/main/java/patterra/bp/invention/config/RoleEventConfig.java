package patterra.bp.invention.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import patterra.bp.invention.config.sm.Events;
import patterra.domain.RoleType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static patterra.bp.invention.config.sm.Events.*;

@Configuration
public class RoleEventConfig {

    @Bean
    public Map<RoleType, Set<Events>> role2Events() {
        Map<RoleType, Set<Events>> role2Events = new HashMap<>();
        role2Events.put(RoleType.ADMIN, EnumSet.allOf(Events.class));
        role2Events.put(RoleType.FORMAL_EXPERTISE, EnumSet.of(
                FORMAL_SUBMIT,
                FORMAL_REGISTER_OFFICE_REQUEST,
                FORMAL_PROLONG_REPLYING,
                FORMAL_REPLY_TO_OFFICE_REQUEST,
                FORMAL_WITHDRAW,
                FORMAL_RESTORE,
                FORMAL_COMPLETE
        ));
        role2Events.put(RoleType.ESSENTIAL_EXPERTISE, EnumSet.of(
                ESSENTIAL_SUBMIT_APPLICATION,
                ESSENTIAL_REGISTER_OFFICE_REQUEST,
                ESSENTIAL_PROLONG_REPLYING,
                ESSENTIAL_REPLY_TO_OFFICE_REQUEST,
                ESSENTIAL_REGISTER_GRANTING,
                ESSENTIAL_REGISTER_DENY,
                ESSENTIAL_SUBMIT_APPEAL,
                ESSENTIAL_PATENT_RECEIVE,
                ESSENTIAL_WITHDRAW,
                ESSENTIAL_RESTORE
        ));
        role2Events.put(RoleType.GRANTED_PATENT_CONTROL, EnumSet.of(
                RECEIVE_PATENT,
                SEND_CERTIFICATE,
                PAY_ANNUAL_FEE,
                PROLONG_PATENT
        ));
        return role2Events;
    }
}
