package br.com.clickleitos.domain.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEvent<U> {

    @CreatedBy
    private U createBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @LastModifiedBy
    private U updateBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public U getCreateBy() {
        return createBy;
    }

    public void setCreateBy(U createBy) {
        this.createBy = createBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public U getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(U updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
