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
                SUBMIT_APPLICATION,
                REGISTER_OFFICE_REQUEST,
                EXTEND_REQUEST,
                REPLY_TO_OFFICE_REQUEST,
                WITHDRAW,
                RESTORE,
                FORMAL_COMPLETE
        ));
        role2Events.put(RoleType.ESSENTIAL_EXPERTISE, EnumSet.of(
                SUBMIT_APPLICATION,
                REGISTER_OFFICE_REQUEST,
                EXTEND_REQUEST,
                REPLY_TO_OFFICE_REQUEST,
                REGISTER_GRANTING,
                REGISTER_DENY,
                SUBMIT_APPEAL,
                PATENT_RECEIVE,
                WITHDRAW,
                RESTORE
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
