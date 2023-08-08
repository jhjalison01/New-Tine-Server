package com.umc.NewTine.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass   // JPA Entity 클래스들이 BaseTimeEntity 를 상속하는 경우, 필드(createdAt, modifiedAt)들도 모두 컬럼으로 인식하도록 한다,
@EntityListeners(AuditingEntityListener.class)  // BastTimeEntity 클래스에 시간 데이터를 자동으로 매핑하여 값을 넣어주는 JPA Auditing 기능 포함
public class BaseTimeEntity {

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp modifiedAt;
}