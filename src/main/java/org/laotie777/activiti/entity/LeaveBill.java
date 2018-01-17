package org.laotie777.activiti.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 请假单
 */
@Entity
@Table(name="a_leaveBill")
@DynamicInsert(true)
@DynamicUpdate(true)
public class LeaveBill {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	/**
	 * 请假天数
	 */
	@Column(name = "days")
	private Integer days;

	/**
	 * 请假内容
	 */
	@Column(name = "content")
	private String content;

	@Column(name = "leaveDate")
	private Date leaveDate = new Date();
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 请假人
	 */
	@ManyToOne(targetEntity = Employee.class)
	@JoinColumn(name="user_id")
	private Employee user;
	/**
	 * 请假单状态 0初始录入,1.开始审批,2为审批完成
	 */
	@Column(name = "state")
	private Integer state=0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

    @Override
    public String toString() {
        return "LeaveBill{" +
                "id=" + id +
                ", days=" + days +
                ", content='" + content + '\'' +
                ", leaveDate=" + leaveDate +
                ", remark='" + remark + '\'' +
                ", user=" + user +
                ", state=" + state +
                '}';
    }
}
