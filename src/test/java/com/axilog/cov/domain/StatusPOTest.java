package com.axilog.cov.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.axilog.cov.web.rest.TestUtil;

public class StatusPOTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusPO.class);
        StatusPO statusPO1 = new StatusPO();
        statusPO1.setId(1L);
        StatusPO statusPO2 = new StatusPO();
        statusPO2.setId(statusPO1.getId());
        assertThat(statusPO1).isEqualTo(statusPO2);
        statusPO2.setId(2L);
        assertThat(statusPO1).isNotEqualTo(statusPO2);
        statusPO1.setId(null);
        assertThat(statusPO1).isNotEqualTo(statusPO2);
    }
}
