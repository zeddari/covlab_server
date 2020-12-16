package com.axilog.cov.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.axilog.cov.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class InventoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventory.class);
        Inventory inventory1 = new Inventory();
        inventory1.setId(1L);
        Inventory inventory2 = new Inventory();
        inventory2.setId(inventory1.getId());
        assertThat(inventory1).isEqualTo(inventory2);
        inventory2.setId(2L);
        assertThat(inventory1).isNotEqualTo(inventory2);
        inventory1.setId(null);
        assertThat(inventory1).isNotEqualTo(inventory2);
    }
}
