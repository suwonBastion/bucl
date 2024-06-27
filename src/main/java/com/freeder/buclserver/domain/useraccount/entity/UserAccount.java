package com.freeder.buclserver.domain.useraccount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.openbanking.dto.ReqApiDto;
import com.freeder.buclserver.domain.openbanking.vo.BANK_CODE;
import com.freeder.buclserver.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user_account")
@Getter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Setter
public class UserAccount {

    @Id
    @Column(name = "user_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BANK_CODE bankName;

    private String account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private User user;

    public static UserAccount setEntity(CustomUserDetails userDetails, ReqApiDto reqApiDto){
        return new UserAccount(
                null,
                reqApiDto.bankNm(),
                reqApiDto.account(),
                User.builder().id(Long.valueOf(userDetails.getUserId())).build()
        );
    }
}
