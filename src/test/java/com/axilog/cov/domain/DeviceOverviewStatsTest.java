package com.axilog.cov.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.axilog.cov.web.rest.TestUtil;

public class DeviceOverviewStatsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceOverviewStats.class);
        DeviceOverviewStats deviceOverviewStats1 = new DeviceOverviewStats();
        deviceOverviewStats1.setId(1L);
        DeviceOverviewStats deviceOverviewStats2 = new DeviceOverviewStats();
        deviceOverviewStats2.setId(deviceOverviewStats1.getId());
        assertThat(deviceOverviewStats1).isEqualTo(deviceOverviewStats2);
        deviceOverviewStats2.setId(2L);
        assertThat(deviceOverviewStats1).isNotEqualTo(deviceOverviewStats2);
        deviceOverviewStats1.setId(null);
        assertThat(deviceOverviewStats1).isNotEqualTo(deviceOverviewStats2);
    }
}
