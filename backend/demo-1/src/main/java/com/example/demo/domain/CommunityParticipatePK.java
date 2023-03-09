package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class CommunityParticipatePK implements Serializable {
    private Long userId;
    private Long communityId;
}
