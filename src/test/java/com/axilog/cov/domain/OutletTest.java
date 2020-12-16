package com.axilog.cov.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.axilog.cov.web.rest.TestUtil;

public class OutletTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outlet.class);
        Outlet outlet1 = new Outlet();
        outlet1.setId(1L);
        Outlet outlet2 = new Outlet();
        outlet2.setId(outlet1.getId());
        assertThat(outlet1).isEqualTo(outlet2);
        outlet2.setId(2L);
        assertThat(outlet1).isNotEqualTo(outlet2);
        outlet1.setId(null);
        assertThat(outlet1).isNotEqualTo(outlet2);
    }
}
