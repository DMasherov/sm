package patterra.bp.statemachineconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import patterra.domain.GroupType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static patterra.bp.statemachineconfig.InventionEvents.*;

@Component
public class Group2EventsMapping {

    @Bean
    public Map<GroupType, Set<InventionEvents>> group2Events() {
        Map<GroupType, Set<InventionEvents>> group2Events = new HashMap<>();
        group2Events.put(GroupType.ADMIN, EnumSet.allOf(InventionEvents.class));
        group2Events.put(GroupType.FORMAL_EXPERTISE, EnumSet.of(
                FORMAL_SUBMIT,
                FORMAL_REGISTER_OFFICE_REQUEST,
                FORMAL_PROLONG_REPLYING,
                FORMAL_REPLY_TO_OFFICE_REQUEST,
                FORMAL_WITHDRAW,
                FORMAL_RESTORE,
                FORMAL_COMPLETE
        ));
        group2Events.put(GroupType.ESSENTIAL_EXPERTISE, EnumSet.of(
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
        group2Events.put(GroupType.GRANTED_PATENT_CONTROL, EnumSet.of(
                RECEIVE_PATENT,
                SEND_CERTIFICATE,
                PAY_ANNUAL_FEE,
                PROLONG_PATENT
        ));
        return group2Events;
    }
}
