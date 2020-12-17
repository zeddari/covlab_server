package com.axilog.cov.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.axilog.cov.web.rest.TestUtil;

public class PoStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoStatus.class);
        PoStatus poStatus1 = new PoStatus();
        poStatus1.setId(1L);
        PoStatus poStatus2 = new PoStatus();
        poStatus2.setId(poStatus1.getId());
        assertThat(poStatus1).isEqualTo(poStatus2);
        poStatus2.setId(2L);
        assertThat(poStatus1).isNotEqualTo(poStatus2);
        poStatus1.setId(null);
        assertThat(poStatus1).isNotEqualTo(poStatus2);
    }
}
