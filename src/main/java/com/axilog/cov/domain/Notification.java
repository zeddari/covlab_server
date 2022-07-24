package com.axilog.cov.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A notification.
 */
@Entity
@Table(name = "notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "updated_at")
    private Date updateAt;

    @Column(name = "notif_title")
    private String notifTitle;

    @Column(name = "notif_description")
    private String notifDescription;

    @Column(name = "notif_severity")
    private String notifSeverity;


}
